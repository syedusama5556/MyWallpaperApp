package usama.utech.wallpaperapp;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import usama.utech.wallpaperapp.Adapter.ImageLoadAdapter;
import usama.utech.wallpaperapp.Comman.Comman;
import usama.utech.wallpaperapp.Model.ImageModelClass;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ImageLoadAdapter imageLoadAdapter;
    ArrayList<ImageModelClass> imageModelClassArrayList = new ArrayList<>();
    private BottomAppBar bottomapp;
    private FloatingActionButton fab_homepage;
    DatabaseReference databaseReference;


    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home_screen);
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



        fab_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeScreen.this,AddNewImageUrl.class));
                finish();

            }
        });



        imageLoadAdapter = new ImageLoadAdapter(HomeScreen.this, imageModelClassArrayList);
        recyclerView.setAdapter(imageLoadAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) );


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnap: dataSnapshot.getChildren() ){

                   ImageModelClass imageModelClass =  dataSnap.getValue(ImageModelClass.class);

                   imageModelClassArrayList.add(imageModelClass);

                   imageLoadAdapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });









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

        if (id == R.id.profileMenu) {


        } else if (id == R.id.findserviceMenu) {


        } else if (id == R.id.messangerMenue) {


        } else if (id == R.id.contactMenu) {
            Toast.makeText(this, "contactMenu", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.requests) {


        } else if (id == R.id.logoutMenu) {


        } else if (id == R.id.nav_shareNav) {
            Toast.makeText(this, "nav_shareNav", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_sendNav) {
            Toast.makeText(this, "nav_sendNav", Toast.LENGTH_SHORT).show();

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
