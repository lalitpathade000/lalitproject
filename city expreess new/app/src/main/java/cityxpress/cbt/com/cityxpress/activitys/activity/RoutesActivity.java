package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.adapter.TrainListAdpater;
import cityxpress.cbt.com.cityxpress.activitys.database.SQLiteDatabse;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainDedatils;

public class RoutesActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    TextView from, to, title;
    ListView lv;
    ImageView back;
    int current_hour;
    int current_min;
    String url;
    Dialog pDialog;
    TrainListAdpater adpater;
    String strDate;
    private static String url_temp = "https://api.railwayapi.com/v2/between/source/";
    Button addfaviorate;
    ArrayList<TrainDedatils> trainlist, before, after;
    String fromid, toid;
    SQLiteDatabse databse;

    LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
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
        databse = new SQLiteDatabse(context);

        title.setText(" Train List");
        init();
        initvalue();

        Calendar rightNow = Calendar.getInstance();
        current_hour = rightNow.get(Calendar.HOUR_OF_DAY);
        current_min = rightNow.get(Calendar.MINUTE);
        getList();
    }

    private void init() {
        from = findViewById(R.id.routes_source);
        to = findViewById(R.id.routes_destination);
        lv = (ListView) findViewById(R.id.routes_trianlist);


        addfaviorate = findViewById(R.id.routes_addfaviorate);
        trainlist = new ArrayList<>();
        before = new ArrayList<>();
        after = new ArrayList<>();
        setDate();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
        strDate = mdformat.format(calendar.getTime()).toString();
        Log.d("aaa", "date=" + strDate);
    }

    private void initvalue() {
        Log.e("aaa", "from=" + MyApplication.fromstr);
        Log.e("aaa", "to=" + MyApplication.tostr);
        String fromstn = MyApplication.fromstr;

        int firstBracket = fromstn.indexOf('(');
        String contentOfBrackets = fromstn.substring(firstBracket + 1, fromstn.indexOf(')', firstBracket));
        fromid = contentOfBrackets;
        MyApplication.fromid = fromid;

        from.setText(fromstn.substring(0, fromstn.indexOf("(")));
        MyApplication.fromstationname = fromstn.substring(0, fromstn.indexOf("("));
        Log.e("stationname", "fromstationname=" + MyApplication.fromstationname);
        String tostn = MyApplication.tostr;
        int firstBracket1 = tostn.indexOf('(');
        String contentOfBrackets1 = tostn.substring(firstBracket1 + 1, tostn.indexOf(')', firstBracket1));
        toid = contentOfBrackets1;
        MyApplication.toid = toid;
        to.setText(tostn.substring(0, tostn.indexOf("(")));
        MyApplication.tostationname = tostn.substring(0, tostn.indexOf("("));
        Log.e("stationname", "tostationname=" + MyApplication.tostationname);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainDedatils td = trainlist.get(position);
                MyApplication.selectedTrain = td;
                Intent i = new Intent(context, DisplayTrainDetailsActivity.class);
                startActivity(i);

               /* i.putExtra("TrainNum",trainnum);
                i.putExtra("TrainName",trainame);
                i.putExtra("TrainDeparts",traindepart);
                i.putExtra("TrainArrives",trainarrive);
                startActivity(i);

                MyApplication.sourcelat=soureclat;
                MyApplication.sourcelon=sourcelons;
                MyApplication.destnlat=destnlat;
                MyApplication.destnlog=destnlog;
*/
            }
        });


        addfaviorate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (MyApplication.fevId == 0) {

                    Log.e("fav", "fav=" + MyApplication.fromstr + " To " + MyApplication.tostr);
                    boolean f = databse.addHistory(MyApplication.fromstr + " To " + MyApplication.tostr);
                    Toast.makeText(context, "Added In Express Favorite Route", Toast.LENGTH_LONG).show();


                } else if (MyApplication.fevId == 1) {

                    Log.e("fav", "fav=" + MyApplication.fromstr + " To " + MyApplication.tostr);
                    boolean f = databse.addLocalHistory(MyApplication.fromstr + " To " + MyApplication.tostr);
                    Toast.makeText(context, "Added In Local Favorite Route", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private void getList() {

        url = url_temp + (fromid) + "/dest/" + (toid) + "/date/" + strDate + MyApplication.api;
        //  Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();


       /* pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();*/

        pDialog = new Dialog(context);
        pDialog.setCancelable(false);
        pDialog.setContentView(R.layout.dialog_progress);
        pDialog.setTitle("Please Wait");
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("aaa", "url=" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
                try {

                    int hr, min;
                    Log.e("res", "train res=" + response);
                    JSONObject reader = new JSONObject(response);
                    if (reader.getInt("response_code") == 200) {

                        if (pDialog.isShowing()) {
                            pDialog.dismiss();
                        }

                        //   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        JSONArray trains = reader.getJSONArray("trains");


                        Log.e("route", "res=" + trains.toString());
                        for (int i = 0; i < trains.length(); i++) {
                            JSONObject t = trains.getJSONObject(i);
                            JSONObject job = t.getJSONObject("from_station");
                            JSONObject tostnjob = t.getJSONObject("to_station");

                            String s[] = t.getString("src_departure_time").split(":");
                            hr = Integer.parseInt(s[0].trim());
                            min = Integer.parseInt(s[1].trim());

                            if (current_hour > hr) {
                                before.add(new TrainDedatils(t.getString("number"), t.getString("name"), time24to12(t.getString("src_departure_time")), time24to12(t.getString("dest_arrival_time")), t.getString("travel_time"), job.getString("lat"), job.getString("lng"), tostnjob.getString("lat"), tostnjob.getString("lng")));
                            } else if (current_hour == hr) {
                                if (current_min > min) {
                                    before.add(new TrainDedatils(t.getString("number"), t.getString("name"), time24to12(t.getString("src_departure_time")), time24to12(t.getString("dest_arrival_time")), t.getString("travel_time"), job.getString("lat"), job.getString("lng"), tostnjob.getString("lat"), tostnjob.getString("lng")));

                                }
                            } else {
                                after.add(new TrainDedatils(t.getString("number"), t.getString("name"), time24to12(t.getString("src_departure_time")), time24to12(t.getString("dest_arrival_time")), t.getString("travel_time"), job.getString("lat"), job.getString("lng"), tostnjob.getString("lat"), tostnjob.getString("lng")));

                            }
                            Log.e("time", "24time=" + t.getString("src_departure_time"));
                            Log.e("time", "12time=" + time24to12(t.getString("src_departure_time")));


                            //  Toast.makeText(getApplicationContext(), "hello1223", Toast.LENGTH_LONG).show();

                        }
                        trainlist.clear();
                        ;
                        for (int i = 0; i < after.size(); i++) {
                            trainlist.add(after.get(i));

                        }
                        for (int i = 0; i < before.size(); i++) {
                            trainlist.add(before.get(i));
                        }



                        // trainlist.add(new TrainDedatils(t.getString("number"),t.getString("name"),time24to12(t.getString("src_departure_time")),time24to12(t.getString("dest_arrival_time")),t.getString("travel_time"),job.getString("lat"),job.getString("lng"),tostnjob.getString("lat"),tostnjob.getString("lng")));


                        adpater = new TrainListAdpater((Activity) context, trainlist);
                        lv.setAdapter(adpater);
                    } else if (reader.getInt("response_code") == 500) {
                        if (pDialog.isShowing()) {
                            pDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "Service Not Available now Try After Sometime", Toast.LENGTH_LONG).show();
                    } else if (reader.getInt("response_code") == 405) {

                        if (pDialog.isShowing()) {
                            pDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "Please try Again", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception E) {

                    Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

    public String time24to12(String s) {
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
        return "";
    }

    @Override
    public void onClick(View v) {


    }
}

/*

try {

        Log.e("res","train res="+response);
        JSONObject reader = new JSONObject(response);
        if (reader.getInt("response_code") == 200) {

        if (pDialog.isShowing())
        {
        pDialog.dismiss();
        }

        //   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
        JSONArray trains = reader.getJSONArray("trains");

        Log.e("route","res="+trains.toString());
        for (int i = 0; i < trains.length(); i++) {
        JSONObject t = trains.getJSONObject(i);
        JSONObject job=t.getJSONObject("from_station");
        JSONObject tostnjob=t.getJSONObject("to_station");
        // Toast.makeText(getApplicationContext(),t.toString()+ "hello", Toast.LENGTH_LONG).show();

        Log.e("time","24time="+t.getString("src_departure_time"));
        Log.e("time","12time="+time24to12(t.getString("src_departure_time")));
        trainlist.add(new TrainDedatils(t.getString("number"),t.getString("name"),time24to12(t.getString("src_departure_time")),time24to12(t.getString("dest_arrival_time")),t.getString("travel_time"),job.getString("lat"),job.getString("lng"),tostnjob.getString("lat"),tostnjob.getString("lng")));
        //  Toast.makeText(getApplicationContext(), "hello1223", Toast.LENGTH_LONG).show();

        }
        adpater = new TrainListAdpater((Activity) context, trainlist);
        lv.setAdapter(adpater);
        } else if(reader.getInt("response_code") == 500){
        if (pDialog.isShowing())
        {
        pDialog.dismiss();
        }
        Toast.makeText(getApplicationContext(), "Service Not Available now Try After Sometime", Toast.LENGTH_LONG).show();
        }
        else if(reader.getInt("response_code") == 405){

        if (pDialog.isShowing())
        {
        pDialog.dismiss();
        }
        Toast.makeText(getApplicationContext(), "No Trains are available on this Route", Toast.LENGTH_LONG).show();
        }

        } catch (Exception E) {

        Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_LONG).show();

        }*/
