package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.adapter.TrainRoutesAdapter;
import cityxpress.cbt.com.cityxpress.activitys.internet.CheckNetwork;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainDedatils;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainRoutModel;

public class DisplayTrainDetailsActivity extends AppCompatActivity {

    Context context;
    Button map, checkin;
    ImageView back;
    TextView trainno, trainname, traindtime, trainaaime, title;
    TextView MON, TUE, WED, THU, FRI, SAT, SUN;
    TrainDedatils trainDedatils;
    private RecyclerView routelist;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<TrainRoutModel> rtlist, newlist;
    private static String url_temp = "https://api.railwayapi.com/v2/route/train/";
    String url;
    String trainnostr, current_date, current_hour, current_min;
    Dialog pDialog;
    SwipeRefreshLayout swipetraintime;
    TrainRoutesAdapter adapter;
    Handler handler = new Handler();
    int fromindex = 0, toindex = 0;
    LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_train_details);
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
        checkin = findViewById(R.id.traindetails_checkin);
        title = view.findViewById(R.id.ab_searchtrain_title);
        title.setText(" Train Routes");

        setDate();
        init();
        initvalue();


    }

    private void getData() {
        url = url_temp + trainnostr + MyApplication.api;

        pDialog = new Dialog(context);

        pDialog.setCancelable(false);
        pDialog.setContentView(R.layout.dialog_progress);
        pDialog.setTitle("Please Wait");


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject reader = new JSONObject(response);

                    Log.e("aaa", "route res=" + response);
                    JSONArray trains = reader.getJSONArray("route");

                    for (int i = 0; i < trains.length(); i++) {
                        JSONObject t = trains.getJSONObject(i);
                        JSONObject job = t.getJSONObject("station");
                        Log.e("aaaa", "aaa" + job.toString());
                        rtlist.add(new TrainRoutModel(time24to12(t.getString("scharr")), time24to12(t.getString("schdep")), t.getString("distance"), job.getString("name"), job.getString("lat"), job.getString("lng"), i, job.getString("code")));

                    }
                    getIndex();

                    adapter.notifyDataSetChanged();

                    JSONObject train1 = reader.getJSONObject("train");
                    JSONArray days = train1.getJSONArray("days");
                    for (int i = 0; i < days.length(); i++) {
                        JSONObject t = days.getJSONObject(i);

                        String code = t.getString("code");
                        String runs = t.getString("runs");
                        if (code.equalsIgnoreCase("SUN")) {
                            if (runs.equalsIgnoreCase("y")) {
                                SUN.setText("Yes");
                                SUN.setTextColor(Color.DKGRAY);
                            } else {
                                SUN.setText("No");
                                SUN.setTextColor(Color.RED);
                            }

                        } else if (code.equalsIgnoreCase("MON")) {
                            if (runs.equalsIgnoreCase("y")) {
                                MON.setText("Yes");
                                MON.setTextColor(Color.DKGRAY);
                            } else {
                                MON.setText("No");
                                MON.setTextColor(Color.RED);
                            }

                        } else if (code.equalsIgnoreCase("TUE")) {
                            if (runs.equalsIgnoreCase("y")) {
                                TUE.setText("Yes");
                                TUE.setTextColor(Color.DKGRAY);
                            } else {
                                TUE.setText("No");
                                TUE.setTextColor(Color.RED);
                            }

                        } else if (code.equalsIgnoreCase("WED")) {
                            if (runs.equalsIgnoreCase("y")) {
                                WED.setText("Yes");
                                WED.setTextColor(Color.DKGRAY);
                            } else {
                                WED.setText("No");
                                WED.setTextColor(Color.RED);
                            }

                        } else if (code.equalsIgnoreCase("THU")) {
                            if (runs.equalsIgnoreCase("y")) {
                                THU.setText("Yes");
                                THU.setTextColor(Color.DKGRAY);
                            } else {
                                THU.setText("No");
                                THU.setTextColor(Color.RED);
                            }

                        } else if (code.equalsIgnoreCase("FRI")) {
                            if (runs.equalsIgnoreCase("y")) {
                                FRI.setText("Yes");
                                FRI.setTextColor(Color.DKGRAY);
                            } else {
                                FRI.setText("No");
                                FRI.setTextColor(Color.RED);
                            }

                        } else if (code.equalsIgnoreCase("SAT")) {
                            if (runs.equalsIgnoreCase("y")) {
                                SAT.setText("Yes");
                                SAT.setTextColor(Color.DKGRAY);
                            } else {
                                SAT.setText("No");
                                SAT.setTextColor(Color.RED);
                            }

                        }


                    }



                    /*for (int i = 0; i < days.length(); i++) {
                        JSONObject t = days.getJSONObject(i);
                        Log.e("train","days="+t.getString("code")+" "+"status="+t.getString("runs"));

                        Log.e("train","days="+t.getString("code")+" "+"status="+t.getString("runs"));


                    }*/


                } catch (Exception E) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }

    private void init() {
        trainno = findViewById(R.id.traindetails_train_no);
        trainname = findViewById(R.id.traindetails_name);
        traindtime = findViewById(R.id.traindetails_departtime);
        trainaaime = findViewById(R.id.traindetails_arrivale);
        swipetraintime = (SwipeRefreshLayout) findViewById(R.id.swipetraintime);

        routelist = (RecyclerView) findViewById(R.id.traindetails_routelist_rv);

        map = findViewById(R.id.traindetails_map);

        MON = findViewById(R.id.traindetails_mon);
        TUE = findViewById(R.id.traindetails_tue);
        WED = findViewById(R.id.traindetails_wed);
        THU = findViewById(R.id.traindetails_thu);
        FRI = findViewById(R.id.traindetails_fri);
        SAT = findViewById(R.id.traindetails_sat);
        SUN = findViewById(R.id.traindetails_sun);


    }

    private void initvalue() {
        rtlist = new ArrayList<>();
        newlist = new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        trainDedatils = MyApplication.selectedTrain;
        trainno.setText(trainDedatils.getTrainNo());
        trainname.setText(trainDedatils.getTrainName());
        traindtime.setText(trainDedatils.getTrainDTime());
        trainaaime.setText(trainDedatils.getTrainATime());
        routelist.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.onSaveInstanceState();
        routelist.setLayoutManager(mLayoutManager);
        trainnostr = trainDedatils.getTrainNo();

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

        if (CheckNetwork.isInternetAvailable(context)) //returns true if internet available
        {
            getData();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
            ;
        }


        adapter = new TrainRoutesAdapter(rtlist);
        routelist.setAdapter(adapter);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  map.setText("Please wait");

               /* pDialog = new ProgressDialog(context);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
*/
                pDialog = new Dialog(context);

                pDialog.setCancelable(false);
                pDialog.setContentView(R.layout.dialog_progress);
                pDialog.setTitle("Please Wait");
                pDialog.show();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        addlatlong();


                        MyApplication.rtlist = newlist;
                        startActivity(new Intent(context, TrainMapsActivity.class));
                        pDialog.dismiss();
                    }
                }, 2000);


            }
        });

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.checkindialog);

                TextView trainno = (TextView) dialog.findViewById(R.id.checkindialog_trainno);
                trainno.setText(trainDedatils.getTrainNo());

                TextView date = (TextView) dialog.findViewById(R.id.checkindialog_date);
                date.setText(current_date);

                TextView time = (TextView) dialog.findViewById(R.id.checkindialog_time);
                time.setText(current_hour + ":" + current_min);

                Button checkinbtn = (Button) dialog.findViewById(R.id.checkindialog_checkin);
                checkinbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (statusCheck()) {


                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

    }

    public boolean statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        } else {
            return true;
        }


    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void addlatlong() {


        for (int i = 0; i < rtlist.size(); i++) {
            String address = rtlist.get(i).getTrainName();


            LatLng latLng = getaddress(address + " station");
            // rtlist.add(new TrainRoutModel(time24to12(t.getString("scharr")),time24to12(t.getString("schdep")),t.getString("distance"),job.getString("name"),job.getString("lat"),job.getString("lng"),i));
            if (latLng == null) {

            } else {


                newlist.add(new TrainRoutModel(rtlist.get(i).getTrainATime(), rtlist.get(i).getTrainschdep(), rtlist.get(i).getTraindistance(), rtlist.get(i).getTrainName(), String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), i, rtlist.get(i).getCode()));

            }


        }

    }

    private LatLng getaddress(String add) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;

        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(add, 5);


            if (address == null || address.isEmpty()) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return p1;
    }

    private void getIndex() {
        for (int i = 0; i < rtlist.size(); i++) {
            Log.e("index", "code=" + rtlist.get(i).getCode().toLowerCase().trim());
            Log.e("index", "my from statin=" + MyApplication.fromid.toLowerCase());
            Log.e("index", "my to statin=" + MyApplication.toid.toLowerCase());

            String s1 = rtlist.get(i).getCode().toLowerCase().trim();
            String s2 = MyApplication.fromid.toLowerCase().trim();
            String s3 = MyApplication.toid.toLowerCase().trim();
            if (s1.equals(s2)) {
                MyApplication.fromindex = i;
                Log.e("index", "from index=" + i);
            } else if (s1.equals(s3)) {
                MyApplication.toindex = i;
                Log.e("index", "to index=" + i);
            }
        }


    }


    public String time24to12(String s) {
        if (s.equalsIgnoreCase("source")) {
            return "source";
        } else {
            try {
                String _24HourTime = s;
                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                System.out.println(_24HourDt);
                System.out.println(_12HourSDF.format(_24HourDt));
                return _12HourSDF.format(_24HourDt);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
        current_date = mdformat.format(calendar.getTime()).toString();


        Calendar rightNow = Calendar.getInstance();

        current_hour = String.valueOf(rightNow.get(Calendar.HOUR_OF_DAY));
        current_min = String.valueOf(rightNow.get(Calendar.MINUTE));
    }
}
