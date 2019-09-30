package usama.utech.wallpaperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ShowAllCategoriList extends AppCompatActivity {

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_categori_list);
        //will check again tomorrow.
        MobileAds.initialize(this,
                getResources().getString(R.string.appid_admob));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    public void allNatureWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Nature");

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


    public void allCarWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Cars");

        startActivity(intent);
    }







    public void allAnimalWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Animals");

        startActivity(intent);

    }

    public void allArchetectureWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Architecture");

        startActivity(intent);
    }

    public void allDessertsWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Deserts");

        startActivity(intent);
    }

    public void allSportsWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Sports");

        startActivity(intent);
    }




    public void allTextureWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Texture");

        startActivity(intent);

    }

    public void allTravelWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Travel");

        startActivity(intent);
    }

    public void allUniverseWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);

        intent.putExtra("dataitem","Universe");

        startActivity(intent);
    }


    public void showAllWallpaper(View view) {
        Intent intent = new Intent(this,ShowAllWallpapers.class);


        startActivity(intent);
    }
}
