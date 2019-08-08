package usama.utech.wallpaperapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import usama.utech.wallpaperapp.Model.ImageModelClass;
import usama.utech.wallpaperapp.R;

public class ImageLoadAdapter extends RecyclerView.Adapter<ImageLoadAdapter.MyItemViewHolder>{


    Context context;
    ArrayList<ImageModelClass> imageModelClasses = new ArrayList<>();

    public ImageLoadAdapter(Context context, ArrayList<ImageModelClass> imageModelClasses) {
        this.context = context;
        this.imageModelClasses = imageModelClasses;
    }

    @NonNull
    @Override
    public MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_v_item,null);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, int position) {

        ImageModelClass imageModel =  imageModelClasses.get(position);

        if (imageModel.getVerified().equals("true")) {

            Picasso.get()
                    .load(imageModel.getImage_url())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.myImage);
        }


    }

    @Override
    public int getItemCount() {
        return imageModelClasses.size();
    }

    class MyItemViewHolder extends RecyclerView.ViewHolder{

    CardView myCard;
    ImageView myImage;

    public MyItemViewHolder(@NonNull View itemView) {
        super(itemView);

        myCard = itemView.findViewById(R.id.rec_mycard);
        myImage = itemView.findViewById(R.id.rec_myimgV);
    }
}
}
