package usama.utech.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import usama.utech.wallpaperapp.Comman.Comman;
import usama.utech.wallpaperapp.Model.ImageModelClass;

public class AddNewImageUrl extends AppCompatActivity implements View.OnClickListener {


    private TextInputEditText editTextUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_image_url);

        editTextUrl = (TextInputEditText) findViewById(R.id.edit_text_url);
        findViewById(R.id.btn_name).setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_name: {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Comman.wallpaper_refrence).push();

                ImageModelClass imageModelClass = new ImageModelClass(databaseReference.getKey(),editTextUrl.getText().toString(),"false");
                databaseReference.setValue(imageModelClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(AddNewImageUrl.this, "Image is uploaded and is awaiting verification. It will be shown in main list once verified", Toast.LENGTH_LONG).show();

                    }
                });


                break;
            }
        }
    }
}
