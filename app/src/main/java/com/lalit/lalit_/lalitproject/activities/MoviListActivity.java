package com.lalit.lalit_.lalitproject.activities;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.*;

public class MoviListActivity extends AppCompatActivity {

    RecyclerView movielist_rv;
    Context context;
    TextView title;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ArrayList<Movies> list;
    boolean temp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movi_list);
        setCustomeActionBar();
        init();
        initvalue();
        getMovieList();
    }

    private void setCustomeActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custome_appbar);
        View view = getSupportActionBar().getCustomView();
        title = view.findViewById(R.id.toolbar_title);
        title.setText("Upcoming Movies");

    }


    private void init() {
        context = this;
        movielist_rv = findViewById(R.id.movielist_rv);
        list = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);


    }

    private void initvalue() {
        movielist_rv.setLayoutManager(new LinearLayoutManager(context));
    }

    private void getMovieList() {
        // https://api.themoviedb.org/3/movie/upcoming?api_key=b7cd3340a794e5a2f35e3abb820b497f
        stringRequest = new StringRequest(Method.GET, MyApplication.URL_MovieList + "=" + MyApplication.Key, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("aaa", "output" + response);

                    JSONObject object = new JSONObject(response);
                    JSONArray js = object.getJSONArray("results");
                    for (int i = 0; i < js.length(); i++) {
                        JSONObject job = js.getJSONObject(i);
                        if (job.getString("overview").equals("false"))
                            temp = false;
                        else
                            temp = true;

                        list.add(new Movies(job.getString("id"), job.getString("title"), job.getString("poster_path"), job.getString("release_date"), temp));
                        Log.e("aaa", "data=" + job.getString("id") + job.getString("title") + job.getString("poster_path") + job.getString("release_date") + job.getString("overview"));
                    }

                    movielist_rv.setAdapter(new MovieListAdapter(context,list));

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
        })
        ;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


}

