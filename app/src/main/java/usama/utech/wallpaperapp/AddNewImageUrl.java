package usama.utech.wallpaperapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import usama.utech.wallpaperapp.Comman.Comman;
import usama.utech.wallpaperapp.Model.ImageModelClass;

public class AddNewImageUrl extends AppCompatActivity implements View.OnClickListener {


    private TextInputEditText editTextUrl;
    Spinner spinner_category;

    String categoryies[] = new String[]{"Abstract", "Nature", "Animal", "Night", "Deserts"};

    String selectedCategori = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_image_url);

        editTextUrl = (TextInputEditText) findViewById(R.id.edit_text_url);

        spinner_category = (Spinner) findViewById(R.id.category_spinner);
        findViewById(R.id.btn_name).setOnClickListener(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_layout, categoryies);

        spinner_category.setAdapter(adapter);





    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_name: {

                if (validateUrl(editTextUrl.getText().toString())) {

                    selectedCategori = spinner_category.getSelectedItem().toString();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Comman.wallpaper_refrence).push();

                    ImageModelClass imageModelClass = new ImageModelClass(databaseReference.getKey(), editTextUrl.getText().toString(), "false",selectedCategori);
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


    public boolean validateUrl(String url) {


        if (url.startsWith("https://") || url.startsWith("http://")) {

//            if (url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif") || url.endsWith(".jpeg") || url.endsWith(".JPG") || url.endsWith(".PNG") || url.endsWith(".GIF") || url.endsWith(".JPEG"))
//            {

            return true;
//
//            }

        }

        return false;
    }
}
