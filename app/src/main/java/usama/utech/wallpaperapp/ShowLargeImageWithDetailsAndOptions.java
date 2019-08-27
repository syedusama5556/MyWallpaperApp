package usama.utech.wallpaperapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import usama.utech.wallpaperapp.Adapter.ViewPagerImageAdapter;
import usama.utech.wallpaperapp.Comman.Comman;
import usama.utech.wallpaperapp.Model.ImageModelClass;


public class ShowLargeImageWithDetailsAndOptions extends AppCompatActivity {
    ViewPager viewPager;
    ViewPagerImageAdapter adapter;
    ArrayList<ImageModelClass> imageModelClasses = new ArrayList<>();
    DatabaseReference databaseReference;

    String positionFormHomeScreen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_with_large_details_activity);


        if (getIntent() != null){

            positionFormHomeScreen= getIntent().getStringExtra("pos");

            Toast.makeText(this, ""+positionFormHomeScreen, Toast.LENGTH_SHORT).show();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference(Comman.wallpaper_refrence);


        viewPager = (ViewPager) findViewById(R.id.view_pager);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {

                    ImageModelClass imageModelClass = dataSnap.getValue(ImageModelClass.class);

                    imageModelClasses.add(imageModelClass);

                    adapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        adapter = new ViewPagerImageAdapter(this, imageModelClasses);
        viewPager.setAdapter(adapter);

        if (positionFormHomeScreen != null && !positionFormHomeScreen.equals("")) {
            viewPager.setCurrentItem(Integer.parseInt(positionFormHomeScreen));
        }else {
            viewPager.setCurrentItem(adapter.getCount() -1);
        }
    }
}
