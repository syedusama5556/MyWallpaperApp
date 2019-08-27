package usama.utech.wallpaperapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import usama.utech.wallpaperapp.Model.ImageModelClass;
import usama.utech.wallpaperapp.R;
import usama.utech.wallpaperapp.ShowLargeImageWithDetailsAndOptions;

public class ImageLoadAdapter extends RecyclerView.Adapter<ImageLoadAdapter.MyItemViewHolder> {


    Context context;
    ArrayList<ImageModelClass> imageModelClasses = new ArrayList<>();

    public ImageLoadAdapter(Context context, ArrayList<ImageModelClass> imageModelClasses) {
        this.context = context;
        this.imageModelClasses = imageModelClasses;
    }

    @NonNull
    @Override
    public MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_v_item, null);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, final int position) {

        ImageModelClass imageModel = imageModelClasses.get(position);

        if (imageModel.getVerified().equals("true")) {

            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

            Glide.with(context)
                    .load(imageModel.getImage_url())
                    .apply(requestOptions)
                    .into(holder.myImage);

            holder.myCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ShowLargeImageWithDetailsAndOptions.class);

                    intent.putExtra("pos",position);

                    context.startActivity(intent);



                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return imageModelClasses.size();
    }

    class MyItemViewHolder extends RecyclerView.ViewHolder {

        CardView myCard;
        ImageView myImage;

        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);

            myCard = itemView.findViewById(R.id.rec_mycard);
            myImage = itemView.findViewById(R.id.rec_myimgV);
        }
    }
}
