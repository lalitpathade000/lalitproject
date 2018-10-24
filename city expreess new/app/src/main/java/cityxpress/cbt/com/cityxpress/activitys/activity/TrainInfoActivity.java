package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.GSON.Route;
import cityxpress.cbt.com.cityxpress.activitys.GSON.TrainStatusGsonPojo;
import cityxpress.cbt.com.cityxpress.activitys.adapter.LiveTrainInfoAdapter;
import cityxpress.cbt.com.cityxpress.activitys.internet.CheckNetwork;
import cityxpress.cbt.com.cityxpress.activitys.model.LiveTrainDetailsModel;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainInfoModel;

import static java.lang.reflect.Modifier.TRANSIENT;

public class TrainInfoActivity extends AppCompatActivity {


    Context context;
    TextView trainname, position, currentstation;
    SwipeRefreshLayout swipetraintime;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView route_rv;
    EditText searchtrain, date;
    String trainnamestr;
    String positionstr;
    String currentstationstr;
    ArrayList<LiveTrainDetailsModel> routelist;
    String strDate;
    Button search;
    List<Route> rlist;
    int dates, months, years;
    LiveTrainInfoAdapter adapter;
    LinearLayout linearLayout;
    ArrayList<TrainInfoModel> list;
    ProgressBar progressBar;
    String url = "https://api.railwayapi.com/v2/live/train/";
    ImageView back;
    TextView title;
    LinearLayout linearlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_info);
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

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.ab_searchtrain);
        View view = getSupportActionBar().getCustomView();

        back = view.findViewById(R.id.ab_searchtrain_back);
        title = view.findViewById(R.id.ab_searchtrain_title);
        title.setText("Search Train");
        init();
        initvalue();
    }

    private void init() {
        trainname = findViewById(R.id.traininfo_trainname);
        position = findViewById(R.id.traininfo_positon);
        currentstation = findViewById(R.id.traininfo_currentstation);
        swipetraintime = findViewById(R.id.traininfo_swipetraintime);
        currentstation = findViewById(R.id.traininfo_currentstation);
        route_rv = findViewById(R.id.traininfo_route_rv);
        searchtrain = findViewById(R.id.traininfo_trainno);
        search = findViewById(R.id.traininfo_search);
        list = new ArrayList<>();
        date = findViewById(R.id.traininfo_date);
        routelist = new ArrayList<>();
        final Calendar c = Calendar.getInstance();
        dates = c.get(Calendar.DAY_OF_MONTH);
        months = c.get(Calendar.MONTH);
        years = c.get(Calendar.YEAR);
        progressBar = findViewById(R.id.traininfo_pbar);
        linearLayout = findViewById(R.id.traininfo_detailslayout);

        setDate();
        route_rv.setLayoutManager(new LinearLayoutManager(context));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void setDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
        strDate = mdformat.format(calendar.getTime()).toString();
        date.setText(strDate);
        Log.d("aaa", "date=" + strDate);
    }

    private void initvalue() {


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datepicker();

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
                            setdata();

                        }
                    }, 100);

                } catch (Exception E) {

                }
            }
        });




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckNetwork.isInternetAvailable(context)) {

                    setdata();


                } else {
                    Toast.makeText(context, "Please check  Internet Connection  ", Toast.LENGTH_LONG).show();


                }
            }
        });


    }

    private void setdata() {


        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        String triannostr = searchtrain.getText().toString();
        String datestr = date.getText().toString();

        if (triannostr.length() == 0) {
            searchtrain.setError("Enter Train Number");
        }
        if (datestr.length() == 0) {
            date.setError("Enter date Number");
        } else {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            Log.d("aaa", "url=" + url);
            //https://api.railwayapi.com/v2/live/train/17305/date/05-09-2018/apikey/c9it1eodjl/
            String newurl = url + triannostr + "/" + "date/" + datestr + MyApplication.api;
            Log.e("url", "url=" + newurl);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, newurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("res", "res=" + response);

                    try {
                        JSONObject mainobj = new JSONObject(response);
                        String rescode = mainobj.getString("response_code");


                        if (rescode.equals("200")) {
                            String cuurentstationstr = mainobj.getJSONObject("current_station").getString("name");
                            String positionstr = mainobj.getString("position");


                            currentstation.setText(cuurentstationstr);
                            position.setText(positionstr);

                            String trainnamestr = mainobj.getJSONObject("train").getString("name");
                            Log.e("train", "train name=" + trainnamestr);
                            trainname.setText(trainnamestr);

                            JSONArray route = mainobj.getJSONArray("route");
                            Log.e("train", "cuurentstation=" + cuurentstationstr);
                            Log.e("train", "position=" + positionstr);

                            for (int i = 0; i < route.length(); i++) {
                                JSONObject r = route.getJSONObject(i);
                                String stationname = r.getJSONObject("station").getString("name");
                                String dep = r.getString("has_arrived");
                                String b;
                                if (dep.equalsIgnoreCase("true")) {
                                    b = "Yes";
                                } else {
                                    b = "No";
                                }
                                String latemin = r.getString("latemin");

                                list.add(new TrainInfoModel(r.getString("actarr_date"), r.getString("actarr"), b, latemin, stationname));


                                Log.e("train", "actarr_date=" + r.getString("actarr_date"));
                                Log.e("train", "actarr" + r.getString("actarr"));
                                Log.e("train", "dep=" + b);
                                Log.e("train", "latemin=" + latemin);
                                Log.e("train", "stationname=" + stationname);

                            }
                            adapter = new LiveTrainInfoAdapter(context, list);
                            route_rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                           /*    // GsonBuilder gsonBuilder = new GsonBuilder();
                               // Gson gson = gsonBuilder  .excludeFieldsWithModifiers(TRANSIENT)
                                        //.create();

                                Gson gson=new Gson();
                                TrainStatusGsonPojo trainStatusGsonPojo = gson.fromJson(response, TrainStatusGsonPojo.class);

                                Toast.makeText(context, "aaa", Toast.LENGTH_LONG).show();
                                Toast.makeText(context,trainStatusGsonPojo.getResponseCode() , Toast.LENGTH_LONG).show();

*/
/*

                                if (trainStatusGsonPojo.getResponseCode() == 200) {
                                    Train trains = trainStatusGsonPojo.getTrain();
                                    trainnamestr = trains.getName();
                                    positionstr = trainStatusGsonPojo.getPosition();
                                    CurrentStation s = trainStatusGsonPojo.getCurrentStation();
                                    currentstationstr = s.getName();
                                    rlist = trainStatusGsonPojo.getRoute();
                                    route_rv.setLayoutManager(new LinearLayoutManager(context));
                                    route_rv.setAdapter(new LiveTrainInfoAdapter(context, rlist));
                                }
                                if (trainStatusGsonPojo.getResponseCode() == 210) {
                                    Toast.makeText(context, "Train doesn’t run on the date queried.", Toast.LENGTH_LONG).show();
                                }
                                if (trainStatusGsonPojo.getResponseCode() == 500) {
                                    Toast.makeText(context, "Unauthorized API Key.", Toast.LENGTH_LONG).show();
                                }
                                if (trainStatusGsonPojo.getResponseCode() == 501) {
                                    Toast.makeText(context, "Account Expired.", Toast.LENGTH_LONG).show();
                                }
                                if (trainStatusGsonPojo.getResponseCode() == 502) {
                                    Toast.makeText(context, "Invalid arguments passed..", Toast.LENGTH_LONG).show();
                                }*/

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(context, "Please check  Train No  .", Toast.LENGTH_LONG).show();

                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);


        }


    }

    private void datepicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        dates = c.get(Calendar.DAY_OF_MONTH);
        months = c.get(Calendar.MONTH);
        years = c.get(Calendar.YEAR);

        DatePickerDialog timePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int dpyear, int dpmonth, int dpdayOfMonth) {

                        date.setText(dpdayOfMonth + "-" + (dpmonth + 1) + "-" + dpyear);
                    }
                }, years, months, dates);


        c.add(Calendar.DATE, 1);
        Date newDate = c.getTime();
        timePickerDialog.getDatePicker().setMinDate(newDate.getTime() - (newDate.getTime() % (24 * 60 * 60 * 1000)));

        //timePickerDialog.getDatePicker().setMaxDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
        //timePickerDialog.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()));
        timePickerDialog.show();
    }

}

//https://api.railwayapi.com/v2/live/train/17305/date/05-09-2018/apikey/c9it1eodjl/
//https://api.railwayapi.com/v2/live/train/12779/date/6-8-2018/apikey/c9it1eodjl/