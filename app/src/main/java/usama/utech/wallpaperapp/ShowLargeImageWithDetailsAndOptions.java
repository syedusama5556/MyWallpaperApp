package usama.utech.wallpaperapp;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;

import usama.utech.wallpaperapp.Adapter.ViewPagerImageAdapter;
import usama.utech.wallpaperapp.Comman.Comman;
import usama.utech.wallpaperapp.Model.ImageModelClass;


public class ShowLargeImageWithDetailsAndOptions extends AppCompatActivity {
    ViewPager viewPager;
    ViewPagerImageAdapter adapter;
    ArrayList<ImageModelClass> imageModelClasses = new ArrayList<>();
    DatabaseReference databaseReference;

    int positionFormHomeScreen = 0, pageScrollChangenumber = 0;

    FloatingActionButton setAsWallpaper;
    ExtendedFloatingActionButton nextFab, backFab;
    private DisplayMetrics displayMetrics;
    private int width = 0;
    private int height = 0;

    private WallpaperManager wallpaperManager;
    private ProgressDialog progressDialog;
    private String selectedCategori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.show_image_with_large_details_activity);

        databaseReference = FirebaseDatabase.getInstance().getReference(Comman.wallpaper_refrence);


        viewPager = findViewById(R.id.view_pager);


        if (getIntent() != null) {

            positionFormHomeScreen = getIntent().getIntExtra("pos", 0);

            selectedCategori = getIntent().getStringExtra("categori");

            Toast.makeText(this, "" + positionFormHomeScreen, Toast.LENGTH_SHORT).show();
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {

                    ImageModelClass imageModelClass = dataSnap.getValue(ImageModelClass.class);

                    if (selectedCategori != null && !selectedCategori.equals("")) {

                        if (imageModelClass.getCategory().equals(selectedCategori)) {

                            imageModelClasses.add(imageModelClass);

                            adapter.notifyDataSetChanged();
                        }

                    } else {

                        imageModelClasses.add(imageModelClass);

                        adapter.notifyDataSetChanged();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        adapter = new ViewPagerImageAdapter(this, imageModelClasses);
        viewPager.setAdapter(adapter);


        viewPager.postDelayed(new Runnable() {

            @Override
            public void run() {

                viewPager.setCurrentItem(positionFormHomeScreen);

                pageScrollChangenumber = positionFormHomeScreen;
            }
        }, 10);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pageScrollChangenumber = position;

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void next(View view) {

        viewPager.setCurrentItem(pageScrollChangenumber + 1);
    }

    public void back(View view) {

        viewPager.setCurrentItem(pageScrollChangenumber - 1);
    }

    public void setWallPaper(View view) {

        progressDialog = new ProgressDialog(ShowLargeImageWithDetailsAndOptions.this);
        progressDialog.setCancelable(false);


        Picasso.get().load(imageModelClasses.get(pageScrollChangenumber).getImage_url()).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {


                GetScreenWidthHeight();

                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, width, height, false);


                wallpaperManager = WallpaperManager.getInstance(ShowLargeImageWithDetailsAndOptions.this);

                try {

                    wallpaperManager.setBitmap(bitmap1);

                    wallpaperManager.suggestDesiredDimensions(width, height);

                    progressDialog.dismiss();
                    Toast.makeText(ShowLargeImageWithDetailsAndOptions.this, "Wallpaper Changed", Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    Toast.makeText(ShowLargeImageWithDetailsAndOptions.this, "wallpaper error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
                Toast.makeText(ShowLargeImageWithDetailsAndOptions.this, "Loading image failed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {


                progressDialog.setMessage("Downloading image");
                progressDialog.show();

                // Toast.makeText(ShowLargeImageWithDetailsAndOptions.this, "Downloading image", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void GetScreenWidthHeight() {

        displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;

        height = displayMetrics.heightPixels;

    }


}
