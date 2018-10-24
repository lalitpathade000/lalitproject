package com.lalit.lalit_.lalitproject.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lalit.lalit_.lalitproject.Comman.MyApplication;
import com.lalit.lalit_.lalitproject.Model.Movies;
import com.lalit.lalit_.lalitproject.R;
import com.lalit.lalit_.lalitproject.adapter.MovieListAdapter;
import com.lalit.lalit_.lalitproject.adapter.SlidingAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {


    TextView title, overview;
    RatingBar ratingBar;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    Context context;
    private ViewPager mPager;
    CirclePageIndicator indicator;
    SlidingAdapter slidingAdapter;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        init();
        initvalue();
        getData();
        getImages();
    }


    private void init() {
        context = this;
        images = new ArrayList<>();
        title = findViewById(R.id.moviedetails_title);
        overview = findViewById(R.id.moviedetails_overview);
        ratingBar = findViewById(R.id.moviedetails_ratingBar);
        requestQueue = Volley.newRequestQueue(context);
        mPager = findViewById(R.id.moviedetails_pager);
        indicator = findViewById(R.id.moviedetails_indicator);
    }

    private void initvalue() {

        slidingAdapter = new SlidingAdapter(context, images);
        mPager.setAdapter(slidingAdapter);
        slidingAdapter.notifyDataSetChanged();
        indicator.setViewPager(mPager);
    }

    private void getData() {

        //https://api.themoviedb.org/3/movie/335983?api_key=b7cd3340a794e5a2f35e3abb820b497f
        String url = MyApplication.URL_MovieDeatails + MyApplication.selectedMovie + "?api_key=" + MyApplication.Key;
        Log.e("bbb", "url=" + url);
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("aaa", "output" + response);

                    JSONObject object = new JSONObject(response);
                    title.setText(object.getString("title"));
                    overview.setText(object.getString("overview"));

                    overview.setMaxLines(3);
                    overview.setEllipsize(TextUtils.TruncateAt.END);

                    Log.e("bbb", "rting=" + object.getString("vote_average"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("aaa", "output" + error);


                // Toast.makeText(ExploreCity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }


    private void getImages() {
        //https://api.themoviedb.org/3/movie/335983/images
        String url = MyApplication.URL_MovieDeatails + MyApplication.selectedMovie + "/images?api_key=" + MyApplication.Key;
        Log.e("bbb", "url=" + url);
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object = new JSONObject(response);
                    JSONArray js = object.getJSONArray("results");
                    for (int i = 0; i < js.length(); i++) {
                        JSONObject job = js.getJSONObject(i);

                        images.add(job.getString("images"));
                    }
                    slidingAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("aaa", "output" + error);


                // Toast.makeText(ExploreCity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
}
