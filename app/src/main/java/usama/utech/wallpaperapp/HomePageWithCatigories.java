package usama.utech.wallpaperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import usama.utech.wallpaperapp.Adapter.HomeImageAdapter;
import usama.utech.wallpaperapp.Comman.Comman;
import usama.utech.wallpaperapp.Model.ImageModelClass;

public class HomePageWithCatigories extends AppCompatActivity {

    private ArrayList<ImageModelClass> imageModelClassArrayListNight = new ArrayList<>();

    private ArrayList<ImageModelClass> imageModelClassArrayListNature = new ArrayList<>();
    private ArrayList<ImageModelClass> imageModelClassArrayListAbstract = new ArrayList<>();
    private ArrayList<ImageModelClass> imageModelClassArrayListTravel = new ArrayList<>();



    RecyclerView recyclerViewNight, recyclerViewAbstract, recyclerViewNature, recyclerViewTravel;
    private HomeImageAdapter imageLoadAdapterNight,imageLoadAdapterNature,imageLoadAdapterAbstract,imageLoadAdapterTravel;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_with_catigories);


        recyclerViewNight = (RecyclerView) findViewById(R.id.recy_home_page);

        recyclerViewAbstract = (RecyclerView) findViewById(R.id.rec_home_abstract);
        recyclerViewNature = (RecyclerView) findViewById(R.id.rec_home_nature);
        recyclerViewTravel = (RecyclerView) findViewById(R.id.rec_home_travel);


        databaseReference = FirebaseDatabase.getInstance().getReference(Comman.wallpaper_refrence);
//
//
//
//        databaseReference.limitToFirst(5).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//               DatabaseReference databaseR = FirebaseDatabase.getInstance().getReference(Comman.wallpaper_refrence);
//                for (DataSnapshot ds: dataSnapshot.getChildren()){
//
//                    ImageModelClass imageModelClass = ds.getValue(ImageModelClass.class);
//
//                    HashMap<String,Object> hashMap = new HashMap<>();
//
//                    hashMap.put("category","Night");
//
//                    databaseR.child(imageModelClass.getImage_id()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(HomePageWithCatigories.this, "Done", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        imageLoadAdapterNight = new HomeImageAdapter(HomePageWithCatigories.this, imageModelClassArrayListNight);


        recyclerViewNight.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewNight.setAdapter(imageLoadAdapterNight);

        final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerViewNight)
                .adapter(imageLoadAdapterNight)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.card_for_homepage_item)
                .show(); //default count is 10
        recyclerViewNight.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        }, 3000);

        imageLoadAdapterAbstract = new HomeImageAdapter(HomePageWithCatigories.this, imageModelClassArrayListAbstract);

        recyclerViewAbstract.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewAbstract.setAdapter(imageLoadAdapterAbstract);


        final SkeletonScreen skeletonScreen1 = Skeleton.bind(recyclerViewAbstract)
                .adapter(imageLoadAdapterAbstract)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.card_for_homepage_item)
                .show(); //default count is 10
        recyclerViewAbstract.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen1.hide();
            }
        }, 3000);

        imageLoadAdapterNature = new HomeImageAdapter(HomePageWithCatigories.this, imageModelClassArrayListNature);

        recyclerViewNature.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewNature.setAdapter(imageLoadAdapterNature);

        final SkeletonScreen skeletonScreen2 = Skeleton.bind(recyclerViewNature)
                .adapter(imageLoadAdapterNature)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.card_for_homepage_item)
                .show(); //default count is 10
        recyclerViewNature.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen2.hide();
            }
        }, 3000);

        imageLoadAdapterTravel = new HomeImageAdapter(HomePageWithCatigories.this, imageModelClassArrayListTravel);

        recyclerViewTravel.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewTravel.setAdapter(imageLoadAdapterTravel);


        final SkeletonScreen skeletonScreen3 = Skeleton.bind(recyclerViewTravel)
                .adapter(imageLoadAdapterTravel)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.card_for_homepage_item)
                .show(); //default count is 10


        recyclerViewTravel.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen3.hide();
            }
        }, 3000);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {

                    ImageModelClass imageModelClass = dataSnap.getValue(ImageModelClass.class);



                    if (imageModelClass.getCategory().equals("Travel")) {

                        imageModelClassArrayListTravel.add(imageModelClass);

                        imageLoadAdapterTravel.notifyDataSetChanged();
                    }
                    if (imageModelClass.getCategory().equals("Night")) {

                        imageModelClassArrayListNight.add(imageModelClass);

                        imageLoadAdapterNight.notifyDataSetChanged();
                    }
                    if (imageModelClass.getCategory().equals("Abstract")) {

                        imageModelClassArrayListAbstract.add(imageModelClass);

                        imageLoadAdapterAbstract.notifyDataSetChanged();
                    }
                    if (imageModelClass.getCategory().equals("Nature")) {

                        imageModelClassArrayListNature.add(imageModelClass);

                        imageLoadAdapterNature.notifyDataSetChanged();
                    }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void allNatureWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Nature");

        startActivity(intent);

    }

    public void allTravelWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Travel");

        startActivity(intent);
    }

    public void allAbstractWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Abstract");

        startActivity(intent);
    }

    public void allNightWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Night");

        startActivity(intent);
    }

    public void showAllWallpaperPage(View view) {

        Intent intent = new Intent(this,ShowAllCategoriList.class);
        startActivity(intent);
    }
}