package usama.utech.wallpaperapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import usama.utech.wallpaperapp.Model.ImageModelClass;
import usama.utech.wallpaperapp.R;
import usama.utech.wallpaperapp.ShowLargeImageWithDetailsAndOptions;

public class HomeImageAdapter extends RecyclerView.Adapter<HomeImageAdapter.MyItemViewHolderNew> {


    Context context;
    ArrayList<ImageModelClass> imageModelClasses = new ArrayList<>();
    private HttpURLConnection conn;

    public HomeImageAdapter(Context context, ArrayList<ImageModelClass> imageModelClasses) {
        this.context = context;
        this.imageModelClasses = imageModelClasses;
    }

    @NonNull
    @Override
    public MyItemViewHolderNew onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_for_homepage_item, null);
        return new MyItemViewHolderNew(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolderNew holder, final int position) {

        ImageModelClass imageModel = imageModelClasses.get(position);


        try {
            URL url = new URL(imageModel.getImage_url());

            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.setConnectTimeout(1);

        if (imageModel.getVerified().equals("true")) {

            holder.textView.setText(imageModel.getCategory());



            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.skeleton_style);

            Glide.with(context)
                    .load(imageModel.getImage_url())
                    .apply(requestOptions)
                    .into(holder.myImage);

            holder.myCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ShowLargeImageWithDetailsAndOptions.class);

                    intent.putExtra("pos", position);

                    Toast.makeText(context, "text" + position, Toast.LENGTH_SHORT).show();

                    context.startActivity(intent);


                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return imageModelClasses.size();
    }

    class MyItemViewHolderNew extends RecyclerView.ViewHolder {

        CardView myCard;
        ImageView myImage;
        TextView textView;

        public MyItemViewHolderNew(@NonNull View itemView) {
            super(itemView);

            myCard = itemView.findViewById(R.id.card_home);
            myImage = itemView.findViewById(R.id.rec_img_card);
            textView = itemView.findViewById(R.id.rec_name_card);
        }
    }
}
