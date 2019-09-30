package usama.utech.wallpaperapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paginate.Paginate;

import java.util.ArrayList;

import usama.utech.wallpaperapp.Adapter.ImageLoadAdapter;
import usama.utech.wallpaperapp.Comman.Comman;
import usama.utech.wallpaperapp.Model.ImageModelClass;

public class ShowAllWallpapers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ImageLoadAdapter imageLoadAdapter;
    ArrayList<ImageModelClass> imageModelClassArrayList = new ArrayList<>();
    private BottomAppBar bottomapp;
    private FloatingActionButton fab_homepage;
    DatabaseReference databaseReference;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private DrawerLayout drawer;
    private int[] lastPositions;
    private int lastVisibleItem;
    private Paginate.Callbacks onLoadMoreListener;
    private int visibleThreshold;

    String selectedItem = "";
    static boolean scroll_down;

    Handler handler;

    //InterstitialAd ads
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_show_all_walpapers);
        bottomapp = findViewById(R.id.bottomAppBar);


        setSupportActionBar(bottomapp);

        fab_homepage = (FloatingActionButton) findViewById(R.id.fab_homepage);

        recyclerView = (RecyclerView) findViewById(R.id.rec_V_home);

        databaseReference = FirebaseDatabase.getInstance().getReference(Comman.wallpaper_refrence);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, bottomapp, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        // loading ads will check again.
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);


        if (getIntent().getStringExtra("dataitem") != null && !getIntent().getStringExtra("dataitem").equals("")) {

            selectedItem = getIntent().getStringExtra("dataitem");


            imageLoadAdapter = new ImageLoadAdapter(ShowAllWallpapers.this, imageModelClassArrayList, false);


            final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (dy > 0) {

                        totalItemCount = staggeredGridLayoutManager.getItemCount();
                        if (lastPositions == null)
                            lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                        lastPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
                        lastVisibleItem = Math.max(lastPositions[0], lastPositions[1]);//findMax(lastPositions);

                        if (!loading && totalItemCount >= 20 && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            // End has been reached


                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                            loading = true;
                        }
                    }
                }
            });


            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(imageLoadAdapter);


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (scroll_down) {
                        bottomapp.performHide();
                    } else {
                        bottomapp.performShow();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 70) {
                        //scroll down
                        scroll_down = true;

                    } else if (dy < -5) {
                        //scroll up
                        scroll_down = false;
                    }
                }
            });


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {

                        ImageModelClass imageModelClass = dataSnap.getValue(ImageModelClass.class);

                        if (imageModelClass.getCategory() != null && imageModelClass.getCategory().equals(selectedItem) && imageModelClass.getVerified().equals("true")) {

                            imageModelClassArrayList.add(imageModelClass);

                            imageLoadAdapter.notifyDataSetChanged();

                        }

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {


            imageLoadAdapter = new ImageLoadAdapter(ShowAllWallpapers.this, imageModelClassArrayList, false);


            final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//
//                    if (dy > 0) {
//
//                        totalItemCount = staggeredGridLayoutManager.getItemCount();
//                        if (lastPositions == null)
//                            lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
//                        lastPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
//                        lastVisibleItem = Math.max(lastPositions[0], lastPositions[1]);//findMax(lastPositions);
//
//                        if (!loading && totalItemCount >= 20 && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                            // End has been reached
//
//
//                            if (onLoadMoreListener != null) {
//                                onLoadMoreListener.onLoadMore();
//                            }
//                            loading = true;
//                        }
//                    }
//                }
//            });


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (scroll_down) {
                        bottomapp.performHide();
                    } else {
                        bottomapp.performShow();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 70) {
                        //scroll down
                        scroll_down = true;

                    } else if (dy < -5) {
                        //scroll up
                        scroll_down = false;
                    }
                }
            });


            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(imageLoadAdapter);


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {

                        ImageModelClass imageModelClass = dataSnap.getValue(ImageModelClass.class);

                        imageModelClassArrayList.add(imageModelClass);

                        imageLoadAdapter.notifyDataSetChanged();


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


        fab_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();

                    Intent intent = new Intent(ShowAllWallpapers.this, AddNewImageUrl.class);

                    startActivity(intent);
                }else {

                    Intent intent = new Intent(ShowAllWallpapers.this, AddNewImageUrl.class);

                    startActivity(intent);
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {


            }

            @Override
            public void onAdLoaded() {

                mInterstitialAd.show();

            }

            @Override
            public void onAdOpened() {


            }
        });

    }


    public static boolean isConnected(Context context) {
//        ConnectivityManager cm = (ConnectivityManager)context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork != null && activeNetwork.isConnected()) {
//            try {
//                URL url = new URL("http://www.google.com/");
//                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
//                urlc.setRequestProperty("User-Agent", "test");
//                urlc.setRequestProperty("Connection", "close");
//                urlc.setConnectTimeout(5000); // mTimeout is in seconds
//                urlc.connect();
//                if (urlc.getResponseCode() == 200) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (IOException e) {
//                Toast.makeText(context, "Error checking internet connection", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }

        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottomappbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.navigation_explore) {


        }

        if (item.getItemId() == R.id.navigation_profile) {


        }
        if (item.getItemId() == R.id.navigation_profile22) {


        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Abstract_menu) {
            Toast.makeText(this, "Abstract_menu", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.Architecture_menu) {

            Toast.makeText(this, "Architecture_menu", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Animals_menu) {

            Toast.makeText(this, "Animals_menu", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.contactMenu) {
            Toast.makeText(this, "contactMenu", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Nature_menu) {

            Toast.makeText(this, "Nature_menu", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Night_menu) {
            Toast.makeText(this, "Night_menu", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.nav_shareNav) {
            Toast.makeText(this, "nav_shareNav", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Deserts_menu) {
            Toast.makeText(this, "Deserts_menu", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Cars_menu) {
            Toast.makeText(this, "Cars_menu", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.Universe_menu) {
            Toast.makeText(this, "Universe_menu", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Travel_menu) {
            Toast.makeText(this, "Travel_menu", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Sports_menu) {
            Toast.makeText(this, "Sports_menu", Toast.LENGTH_SHORT).show();

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void loadInterstitialAd() {

        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
