package usama.utech.wallpaperapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import usama.utech.wallpaperapp.Model.ImageModelClass;
import usama.utech.wallpaperapp.R;

public class ViewPagerImageAdapter extends PagerAdapter {
    Context context;

    LayoutInflater mLayoutInflater;
    ArrayList<ImageModelClass> imageModelClasses ;

    public ViewPagerImageAdapter(Context context, ArrayList<ImageModelClass> imageModelClasses){
        this.context=context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageModelClasses = imageModelClasses;
}
    @Override
    public int getCount() {
        return imageModelClasses.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);


        ImageModelClass imageModel = imageModelClasses.get(position);

        if (imageModel.getVerified().equals("true")) {

            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

            Glide.with(context)
                    .load(imageModel.getImage_url())
                    .apply(requestOptions)
                    .into(imageView);



        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}