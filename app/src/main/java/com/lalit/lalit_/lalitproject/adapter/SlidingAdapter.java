package com.lalit.lalit_.lalitproject.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lalit.lalit_.lalitproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlidingAdapter extends PagerAdapter {

    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    private static int NUM_PAGES = 0;
    SimpleDraweeView draweeView;

    public SlidingAdapter(Context context, ArrayList<String> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        Log.e("aaa", "instantiateItem called");

        View imageLayout = inflater.inflate(R.layout.slide, view, false);
        int height = view.getMeasuredHeight() / 2;

        imageLayout.setMinimumHeight(height);
        if (imageLayout == null) {

        } else {

        }
        assert imageLayout != null;
        try {

           /* ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.clearMemoryCaches();
            imagePipeline.clearDiskCaches();


            imagePipeline.clearCaches();*/

            //  Uri uri = Uri.parse("http://www.cityxpress.in" + IMAGES.get(position));
            //   Toast.makeText(context, "" + uri, Toast.LENGTH_SHORT).show();
            draweeView = (SimpleDraweeView) view.findViewById(R.id.sliderimage);
            //draweeView.setImageURI(uri);
            if (draweeView == null) {
                Log.e("aaa", "draweeView null");
            } else {
                Log.e("aaa", "draweeView not null");

            }
    /*

            Uri uri = Uri.parse("http://www.cityxpress.in"+IMAGES.get(position));
            draweeView.setImageURI(uri);*/

            Picasso.with(context).load("http://www.cityxpress.in" + IMAGES.get(position))

                    .fit()
                    .centerCrop()

                    .into(draweeView);


            //SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
         /*   draweeView.setImageURI(uri);            Picasso.with(context).load("http://www.cityxpress.in" + IMAGES.get(position))
                    .placeholder(R.drawable.ic_logoold).error(R.drawable.ic_logoold).fit()
                    .centerCrop()
                    .into(draweeView);*/

        } catch (Exception E) {
            E.printStackTrace();
            //Toast.makeText(context, "" + E, Toast.LENGTH_SHORT).show();

        }
        view.addView(imageLayout, 0);


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
