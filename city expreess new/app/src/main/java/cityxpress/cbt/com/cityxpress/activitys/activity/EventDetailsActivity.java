package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;

public class EventDetailsActivity extends AppCompatActivity {


    RequestQueue requestQueue;
    Context context;
    ImageView logo, backtop, backbuttom;
    TextView name, organiser, venue, charges, date, time, mobileno, description;
    AppBarLayout appBarLayout;

    ProgressDialog progressDoalog;
    LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
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

        displayprogressbar();
        init();
        initvalue();
        getData();
    }

    private void init() {
        name = findViewById(R.id.eventdetails_name);
        organiser = findViewById(R.id.eventdetails_organiser);
        logo = findViewById(R.id.eventdetails_logo);
        venue = findViewById(R.id.eventdetails_venue);
        charges = findViewById(R.id.eventdetails_charges);
        date = findViewById(R.id.eventdetails_date);
        time = findViewById(R.id.eventdetails_time);
        mobileno = findViewById(R.id.eventdetails_mobileno);
        description = findViewById(R.id.eventdetails_description);

        backtop = findViewById(R.id.eventdetails_back_top);
        backbuttom = findViewById(R.id.eventdetails_back_bottum);

    }

    private void initvalue() {
        appBarLayout = (AppBarLayout) findViewById(R.id.eventddetails_appbar);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
        int devicewidth = displaymetrics.heightPixels;
        //if you need 4-5-6 anything fix imageview in height
        appBarLayout.getLayoutParams().height = devicewidth / 2;


        Log.e("ccc", "TotalScrollRange= " + appBarLayout.getTotalScrollRange());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                if (verticalOffset == 0) {
                    backbuttom.setVisibility(View.GONE);
                    backtop.setVisibility(View.VISIBLE);

                } else {
                    Log.e("ccc", "Not fully expanded or collapsed ");
                    Log.e("ccc", "verticalOffset=" + verticalOffset);
                    Log.e("ccc", "TotalScrollRange= " + appBarLayout.getTotalScrollRange());
                    if (Math.abs(verticalOffset) == Math.abs(appBarLayout.getTotalScrollRange())) {
                        backbuttom.setVisibility(View.VISIBLE);
                        backtop.setVisibility(View.GONE);
                    }
                }
            }
        });

        backbuttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void displayprogressbar() {
        progressDoalog = new ProgressDialog(context);

        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("Please Wait");
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
    }

    private void getData() {
        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, "http://www.cityxpress.in/api/EventApi/GetEvent?eventID=" + MyApplication.SelectEvent_Id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject t) {

                try {

                    Log.e("res", "res=" + t.toString());

                    String NameOfEvent, Organiser, Venue, OrganiserContactNo, EventDate, EventTime, ImageUpload, EventID, Charges, Description;
                    NameOfEvent = t.getString("nameOfEvent");
                    if (t.getString("nameOfEvent") == null || t.getString("nameOfEvent") == "null") {
                        NameOfEvent = "";
                    } else {
                        NameOfEvent = t.getString("nameOfEvent");
                    }
                    name.setText(NameOfEvent);
                    Log.e("name", "name=" + NameOfEvent);
                    EventID = t.getString("eventID");
                    if (t.getString("eventID") == null || t.getString("eventID") == "null") {
                        EventID = "";
                    } else {
                        EventID = t.getString("eventID");
                    }

                    Organiser = t.getString("organiser");
                    if (t.getString("organiser") == null || t.getString("organiser") == "null") {
                        Organiser = "";
                    } else {
                        Organiser = t.getString("organiser");
                    }
                    organiser.setText(Organiser);

                    Venue = t.getString("venue");
                    if (t.getString("venue") == null || t.getString("venue") == "null") {
                        Venue = "";
                    } else {
                        Venue = t.getString("venue");
                    }

                    venue.setText(Venue);

                    OrganiserContactNo = t.getString("organiserContactNo");
                    if (t.getString("organiserContactNo") == null || t.getString("organiserContactNo") == "null") {
                        OrganiserContactNo = "";
                    } else {
                        OrganiserContactNo = t.getString("organiserContactNo");
                    }

                    mobileno.setText(OrganiserContactNo);
                    EventDate = t.getString("eventDate");
                    if (t.getString("eventDate") == null || t.getString("eventDate") == "null") {
                        EventDate = "";
                    } else {
                        EventDate = t.getString("eventDate");
                    }

                    date.setText(EventDate);

                    EventTime = t.getString("eventTime");
                    if (t.getString("eventTime") == null || t.getString("eventTime") == "null") {
                        EventTime = "";
                    } else {
                        EventTime = t.getString("eventTime");
                    }

                    time.setText(EventTime);


                    ImageUpload = t.getString("imageUpload");
                    if (t.getString("imageUpload") == null || t.getString("imageUpload") == "null") {
                        ImageUpload = "";
                    } else {
                        ImageUpload = t.getString("imageUpload");
                    }

                    Charges = t.getString("charges");
                    if (t.getString("charges") == null || t.getString("charges") == "null") {
                        Charges = "";
                    } else {
                        Charges = t.getString("charges");
                    }

                    charges.setText(Charges);
                    Description = t.getString("description");
                    if (t.getString("description") == null || t.getString("description") == "null") {
                        Description = "";
                    } else {
                        Description = t.getString("description");
                    }

                    description.setText(Description);

                    progressDoalog.dismiss();
                    Log.e("img", "img=" + "http://www.cityxpress.in" + ImageUpload);
                    if (ImageUpload.length() != 0) {

                        Picasso.with(context).load("http://www.cityxpress.in" + ImageUpload)
                                .placeholder(R.drawable.ic_train).error(R.drawable.ic_logo)
                                .fit()
                                .into(logo);
                    }


                    // eventlist.add(new EventModel(EventID,NameOfEvent,Organiser,Venue,OrganiserContactNo,EventDate,EventTime,ImageUpload,Charges,Description));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("msg", "called catch");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("msg", "called error responce");
                progressDoalog.dismiss();

            }


        });
        requestQueue.add(stringRequest);

    }


}
