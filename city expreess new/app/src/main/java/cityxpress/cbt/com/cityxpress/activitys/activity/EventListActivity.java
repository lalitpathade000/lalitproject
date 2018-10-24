package cityxpress.cbt.com.cityxpress.activitys.activity;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.adapter.EventLIstAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.MyCitySubSubMenuAdapter;
import cityxpress.cbt.com.cityxpress.activitys.internet.CheckNetwork;
import cityxpress.cbt.com.cityxpress.activitys.model.BussinessListModel;
import cityxpress.cbt.com.cityxpress.activitys.model.EventListModel;

public class EventListActivity extends AppCompatActivity {

    Context context;
    SwipeRefreshLayout swipetraintime;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressBar progressBar;
    private RecyclerView eventlist_rv;
    ImageView back;
    TextView title,addevent;
    EditText searchView;
    EventLIstAdapter eventLIstAdapter;
    ArrayList<EventListModel> eventlist,newlist;
    LinearLayout linearlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list_);
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

        init();
        initvalue();
        if (CheckNetwork.isInternetAvailable(context)) //returns true if internet available
        {
            getdata();


        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
            ;
        }
    }

    private void init() {
        eventlist_rv = findViewById(R.id.eventlist_rv);
        swipetraintime = findViewById(R.id.eventlist_swipetraintime);
        title = findViewById(R.id.eventlist_title);
        back = findViewById(R.id.eventlist_back);
        addevent=findViewById(R.id.eventlist_addevent);

        searchView = findViewById(R.id.eventlist_searchview);
        progressBar = findViewById(R.id.eventlist_progressbar);

        eventlist = new ArrayList<>();
        newlist=new ArrayList<>();


    }

    private void initvalue() {
        eventlist_rv.setLayoutManager(new GridLayoutManager(context,2));

        eventLIstAdapter = new EventLIstAdapter(context, eventlist);
        eventlist_rv.setAdapter(eventLIstAdapter);

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

        title.setText("Events");
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,AddEventActivity.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
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
        for (EventListModel d : eventlist) {
            if (d.getNameOfEvent().toLowerCase().contains(newText.toLowerCase())) {
                newlist.add(d);
            }
        }
        eventLIstAdapter.updateList(newlist);
    }

    private void getdata() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, MyApplication.URL_GetEventList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String NameOfEvent, Organiser, Venue, OrganiserContactNo, EventDate, EventTime, ImageUpload, EventID, Charges, Description;
                progressBar.setVisibility(View.GONE);
                try {
                    Log.e("bbb", response);
                    JSONArray js = new JSONArray(response);
                    Log.e("bbb", "in business list=" + response);
                    for (int i = 0; i < js.length(); i++) {
                        JSONObject t = js.getJSONObject(i);
                        Log.e("bbb","size="+eventlist.size());
                        NameOfEvent=t.getString("nameOfEvent");
                        if(t.getString("nameOfEvent")==null)
                        {
                            NameOfEvent="";
                        }
                        else
                        {
                            NameOfEvent=t.getString("nameOfEvent");
                        }
                        EventID=t.getString("eventID");
                        if(t.getString("eventID")==null)
                        {
                            EventID="";
                        }
                        else
                        {
                            EventID=t.getString("eventID");
                        }

                      /*  Organiser=t.getString("organiser");
                        if(t.getString("organiser")==null || t.getString("organiser")=="null" )
                        {
                            Organiser="";
                        }
                        else
                        {
                            Organiser=t.getString("organiser");
                        }
                        */
                        EventDate=t.getString("eventDate");
                        if(t.getString("eventDate")==null)
                        {
                            EventDate="";
                        }
                        else
                        {
                            EventDate=t.getString("eventDate");
                        }

                       /* EventTime=t.getString("eventTime");
                        if(t.getString("eventDate")==null)
                        {
                            EventTime="";
                        }
                        else
                        {
                            EventTime=t.getString("eventTime");
                        }*/

                        ImageUpload=t.getString("imageUpload");
                        if(t.getString("imageUpload")==null)
                        {
                            ImageUpload="";
                        }
                        else
                        {
                            ImageUpload=t.getString("imageUpload");
                        }


                        Log.e("bbb", "event=" + EventID);
                        Log.e("bbb", "event=" + NameOfEvent);
                        Log.e("bbb", "event=" + EventDate);
                        Log.e("bbb", "event=" + ImageUpload);

                       eventlist.add(new EventListModel(EventID,NameOfEvent,EventDate,ImageUpload));


                    }
                    eventLIstAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);


    }
}
