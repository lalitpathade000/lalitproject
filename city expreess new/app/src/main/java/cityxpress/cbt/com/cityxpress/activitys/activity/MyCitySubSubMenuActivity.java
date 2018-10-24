package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.adapter.MyCitySubMenuAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.MyCitySubSubMenuAdapter;
import cityxpress.cbt.com.cityxpress.activitys.internet.CheckNetwork;
import cityxpress.cbt.com.cityxpress.activitys.model.BussinessListModel;
import cityxpress.cbt.com.cityxpress.activitys.model.SubCategoryPojo;

public class MyCitySubSubMenuActivity extends AppCompatActivity {

    Context context;
    SwipeRefreshLayout swipetraintime;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<BussinessListModel> sublist, newlist;

    private RecyclerView submenu_rv;
    MyCitySubSubMenuAdapter submenuadapter;
    ImageView back;
    TextView title;
    EditText searchView;
    ProgressBar progressBar;
    TextView noitem;
    LinearLayout linearlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_city_sub_sub);
        context = this;
        linearlayout=findViewById(R.id.back);
        Glide.with(this).load(R.drawable.backimg).asBitmap().placeholder(R.drawable.backimg).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    linearlayout.setBackground(drawable);
                }
            }
        });

        if (CheckNetwork.isInternetAvailable(context)) //returns true if internet available
        {

            displayprogressbar();
            getdata();
            init();
            initvalue();

        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();


        }
    }

    private void setmsg() {
        if (sublist.size() == 0) {
            noitem.setVisibility(View.VISIBLE);
        } else {
            noitem.setVisibility(View.GONE);
        }
    }

    private void init() {
        submenu_rv = findViewById(R.id.mycitysubsubmenu_submenu_rv);
        swipetraintime = findViewById(R.id.mycitysubsubmenu_swipetraintime);
        title = findViewById(R.id.mycitysubsubmenu_title);
        back = findViewById(R.id.mycitysubsubmenu_back);
        sublist = new ArrayList<>();
        newlist = new ArrayList<>();
        searchView = findViewById(R.id.mycitysubsubmenu_searchview);
        progressBar = findViewById(R.id.mycitysubsubmenu_progressbar);
        noitem = findViewById(R.id.mycitysubsubmenu_noitem);

    }

    private void initvalue() {

        submenu_rv.setLayoutManager(new GridLayoutManager(context, 2));
        submenuadapter = new MyCitySubSubMenuAdapter(context, sublist);
        submenuadapter.notifyDataSetChanged();
        submenu_rv.setAdapter(submenuadapter);

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


        title.setText(MyApplication.mycitysubcategoruselectedtitle);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filterAdapter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void filterAdapter(String newText) {
        newlist.clear();
        for (BussinessListModel d : sublist) {
            if (d.getBusinessName().toLowerCase().contains(newText.toLowerCase())) {
                newlist.add(d);
            }
        }
        submenuadapter.updateList(newlist);

    }

    public void getdata() {
        Log.e("aaa", "selected sub sub id=" + MyApplication.subcat_select_id);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyApplication.URL_MyCitySubSubMenu + MyApplication.subcat_select_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    //Toast.makeText(SubategoryShow.this, "ashif inreq" + MyApplication.educationid, Toast.LENGTH_SHORT).show();
                    JSONArray js = new JSONArray(response);
                    //  Toast.makeText(getApplicationContext(), "responseExprore City Expresss"+response, Toast.LENGTH_SHORT).show();
                    Log.e("respons", "sub menu response=" + response);
                    progressBar.setVisibility(View.GONE);
                    if (response.equals(null)) {
                        //  Toast.makeText(SubategoryShow.this, "No Data to Show", Toast.LENGTH_SHORT).show();
                    }

                    for (int i = 0; i < js.length(); i++) {

                        JSONObject t = js.getJSONObject(i);
                        sublist.add(new BussinessListModel(t.getString("businessRegistrationID"), t.getString("contactPersonName"), t.getString("businessUpload"), t.getString("businessName"), t.getString("mobileNo"), t.getString("emailId")));
                    }
                    submenuadapter.notifyDataSetChanged();
                    setmsg();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(context, "Something Went Wrong ", Toast.LENGTH_LONG).show();
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

    }
}
