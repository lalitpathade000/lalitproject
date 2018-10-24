package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.adapter.MyCityMenuAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.SlidingAdapter;
import cityxpress.cbt.com.cityxpress.activitys.internet.CheckNetwork;
import cityxpress.cbt.com.cityxpress.activitys.model.MyCityMenuModel;

public class MyCityActivity extends AppCompatActivity {

    Context context;
    private ViewPager mPager;
    JSONArray jsonArray;
    ImageView back;
    TextView title;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ArrayList<String> CATEGORYImage;
    SlidingAdapter slidingAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView menu_rv;
    MyCityMenuAdapter adapter;
    ArrayList<MyCityMenuModel> menukist;
    ArrayList<MyCityMenuModel> newlist;
    SwipeRefreshLayout swipetraintime;
    int maxlen = 0;
    Dialog progressDoalog;

    TextView noitem;

    LinearLayout linearlayout;
    StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_city);
        context = this;
        linearlayout = findViewById(R.id.back);
        Glide.with(this).load(R.drawable.backimg).asBitmap().placeholder(R.drawable.backimg).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    linearlayout.setBackground(drawable);
                }
            }
        });


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.ab_searchtrain);
        View view = getSupportActionBar().getCustomView();
        back = view.findViewById(R.id.ab_searchtrain_back);
        title = view.findViewById(R.id.ab_searchtrain_title);
        title.setText("My City");
        noitem = findViewById(R.id.mycity_noitem);

        if (CheckNetwork.isInternetAvailable(context)) //returns true if internet available
        {
            getMenu();
            getBanner();
            init();
            initvalue();
            displayprogressbar();

        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
            ;
        }

    }

    private void setMsg() {
        if (menukist.size() == 0) {
            noitem.setVisibility(View.VISIBLE);
            menu_rv.setVisibility(View.GONE);

        } else {
            noitem.setVisibility(View.GONE);
            menu_rv.setVisibility(View.VISIBLE);

        }

    }

    private void init() {
        mPager = (ViewPager) findViewById(R.id.mycity_pager);
        CATEGORYImage = new ArrayList<>();
        menu_rv = findViewById(R.id.mycity_menu_rv);
        menukist = new ArrayList<>();
        newlist = new ArrayList<>();
    }

    private void initvalue() {
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.onSaveInstanceState();
        menu_rv.setLayoutManager(new GridLayoutManager(context, 2));
        // menu_rv.setLayoutManager(new GridLayoutManager(context, 3));
        adapter = new MyCityMenuAdapter(context, menukist);
        menu_rv.setAdapter(adapter);
        swipetraintime = (SwipeRefreshLayout) findViewById(R.id.mycity_swipetraintime);
        try {
            slidingAdapter = new SlidingAdapter(context, CATEGORYImage);
            mPager.setAdapter(slidingAdapter);
            slidingAdapter.notifyDataSetChanged();
            CirclePageIndicator indicator = (CirclePageIndicator)
                    findViewById(R.id.mycitym_indicator);

            indicator.setViewPager(mPager);

            slidingAdapter.notifyDataSetChanged();
            ;

            final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
            indicator.setRadius(5 * density);

        } catch (Exception E) {
            Log.e("Exceptioget", E.getMessage());
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipetraintime.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                try {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipetraintime.setRefreshing(false);
                        }
                    }, 100);

                } catch (Exception E) {

                }
            }
        });

    }

    private void getBanner() {
        RequestQueue req = Volley.newRequestQueue(getApplicationContext());
        Log.e("aaa", "city id in get Banner=" + MyApplication.CityID);
        StringRequest stringreq = new StringRequest(Request.Method.GET, MyApplication.URL_GetBanner + MyApplication.CityID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   Toast.makeText(getApplicationContext(), "http://www.cityxpress.in/api/CommonApi/GetBannerListByCity?cityID=" + MyApplication.citysids, Toast.LENGTH_SHORT).show();
                try {
                    //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    JSONArray js = new JSONArray(response);
                    // Toast.makeText(ExploreCity.this, "" + response, Toast.LENGTH_SHORT).show();
                    // JSONArray jsonArray=new JSONArray(response);

                    Log.e("res", "Image responce=" + response);
                    CATEGORYImage.clear();
                    for (int i = 0; i < js.length(); i++) {


                        JSONObject job = js.getJSONObject(i);

                        jsonArray = new JSONArray();
                        jsonArray.put(js.toString());


                        CATEGORYImage.add(job.getString("bannerImage"));
                        Log.e("aaa", "img=" + job.getString("bannerImage"));

                    }
                    slidingAdapter.notifyDataSetChanged();
                    Log.e("bbb", "size=" + CATEGORYImage.size());


                } catch (Exception e) {

                    //  Toast.makeText(ExploreCity.this, "http://www.cityxpress.in/api/CommonApi/GetBannerListByCity?cityID=" + MyApplication.citysids, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(ExploreCity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringreq.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.add(stringreq);
    }

    public void getMenu() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyApplication.URL_MyCityMenu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //  Toast.makeText(RegisterAcitivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    JSONArray js = new JSONArray(response);

                    //  Toast.makeText(AddBuissness.this, ""+response, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < js.length(); i++) {
                        JSONObject t = js.getJSONObject(i);
                        Log.e("aaa", "menu name=" + t.getString("categoryName"));
                        menukist.add(new MyCityMenuModel(t.getString("categoryID"), t.getString("categoryName"), ""));
                        if (maxlen < t.getString("categoryName").length()) {
                            maxlen = t.getString("categoryName").length();
                        }

                    }
                    //  setMaxleanth();

                    Log.e("aaa", "menu list size=" + menukist.size());

                    adapter.notifyDataSetChanged();
                    progressDoalog.dismiss();
                    Log.e("aaa12345", "max=" + maxlen);
                    setMsg();
                } catch (Exception e) {

                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDoalog.dismiss();

            }
        }

        );


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    private void displayprogressbar() {


        progressDoalog = new Dialog(context);

        progressDoalog.setCancelable(false);
        progressDoalog.setContentView(R.layout.dialog_progress);
        progressDoalog.setTitle("Please Wait");
        progressDoalog.show();


       /*// progressDoalog = new ProgressDialog(context);

        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("Please Wait");
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);*/

    }


}
