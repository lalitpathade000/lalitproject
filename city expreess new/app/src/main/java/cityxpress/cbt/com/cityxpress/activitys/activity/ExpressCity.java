package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.database.SQLiteDatabse;

public class ExpressCity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ImageView profile, searchtrain, mycity, upcomingevent, ecalls;
    TextView mycity_tv, addbussiness_tv, upcomingevent_tv, profile_tv;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    StringBuilder total;
    SQLiteDatabse databse;
    LinearLayout linearlayout, trainlayout;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_city);
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

        init();
        initvalue();

        Cursor c = databse.getStation();
        Log.e("count", "count=" + c.getCount());
        if (c.getCount() == 0) {
            loadstation();
        } else {
            addStation(c);
        }


    }

    private void addStation(final Cursor c) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if (c.moveToFirst()) {
                    String s;
                    do {
                        s = (c.getString(1) + " (" + c.getString(2) + ")");
                        // Log.e("station", "station name=" + s);
                        MyApplication.stationArrayList.add(s);

                    } while (c.moveToNext());

                    Log.e("leanth", "arraylist leanth=" + MyApplication.stationArrayList.size());


                }

            }
        });

    }

    public String loadJSONFromAsset() {


        String json = null;
        try {
            InputStream is = context.getAssets().open("stations.json");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            Log.e("res", "buffer =" + buffer.toString());
            json = new String(buffer, "UTF-8");

            BufferedReader r = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));
            total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void loadstation() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject js = new JSONObject(loadJSONFromAsset());

                    JSONArray jsonArray = js.getJSONArray("features");

                    Log.e("leanth", "respose leanth=" + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j1 = jsonArray.getJSONObject(i);
                        // Log.e("res", "JSONObject=" + j1.toString());//  properties
                        JSONObject j2 = j1.getJSONObject("properties");
                        String station = j2.getString("name");
                        String code = j2.getString("code");
                        String upperString = station.substring(0, 1).toUpperCase() + station.substring(1).toLowerCase();
                        databse.addStation(upperString, code);

                        Log.e("res", "i=" + i);
                        Log.e("res", "station=" + station);
                        Log.e("res", "code=" + code);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void init() {
        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        profile = findViewById(R.id.expresscity_profile_iv);
        profile_tv = findViewById(R.id.expresscity_profile_tv);
        searchtrain = findViewById(R.id.expresscity_logo_iv);

        mycity = findViewById(R.id.expresscity_mycity_iv);
        upcomingevent = findViewById(R.id.expresscity_upcomingevent_iv);
        upcomingevent_tv = findViewById(R.id.expresscity_upcomingevent_tv);
        addbussiness_tv = findViewById(R.id.expresscity_ecalls_tv);
        ecalls = findViewById(R.id.expresscity_ecalls_iv);
        trainlayout = findViewById(R.id.expresscity_mycity_layout);

        mycity_tv = findViewById(R.id.expresscity_mycity_tv);
        databse = new SQLiteDatabse(context);

    }

    private void initvalue() {
        profile.setOnClickListener(this);
        mycity.setOnClickListener(this);
        upcomingevent.setOnClickListener(this);
        ecalls.setOnClickListener(this);
        searchtrain.setOnClickListener(this);
        mycity_tv.setOnClickListener(this);
        upcomingevent_tv.setOnClickListener(this);
        addbussiness_tv.setOnClickListener(this);
        profile_tv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.expresscity_profile_iv || v.getId() == R.id.expresscity_profile_tv) {

/*

            startActivity(new Intent(context, MapActivity.class));
*/


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            editor = pref.edit();
                            editor.putString("AccessToken", "");
                            editor.putString("phonenumber", "");
                            editor.putString("name", "");
                            editor.putString("UserID", "");
                            editor.putString("CityID", "");
                            editor.commit();
                            startActivity(new Intent(context, Login.class));
                            finish();
                            dialog.dismiss();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        } else if (v.getId() == R.id.expresscity_logo_iv || v.getId() == R.id.expresscity_mycity_layout) {
            startActivity(new Intent(context, TrainMenu.class));

        } else if (v.getId() == R.id.expresscity_mycity_iv) {
            startActivity(new Intent(context, MyCityActivity.class));

        } else if (v.getId() == R.id.expresscity_mycity_tv) {
            startActivity(new Intent(context, MyCityActivity.class));

        } else if (v.getId() == R.id.expresscity_ecalls_iv) {
            startActivity(new Intent(context, AddBusinessActivity.class));

        } else if (v.getId() == R.id.expresscity_ecalls_tv) {
            startActivity(new Intent(context, AddBusinessActivity.class));

        } else if (v.getId() == R.id.expresscity_upcomingevent_iv) {
            startActivity(new Intent(context, EventListActivity.class));

        } else if (v.getId() == R.id.expresscity_upcomingevent_tv) {
            startActivity(new Intent(context, EventListActivity.class));

        }
        /* final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);



           // getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_profile);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            TextView name = (TextView) dialog.findViewById(R.id.dailog_profile_name_tv);
            //  TextView city = (TextView) dialog.findViewById(R.id.dailog_profile_city_tv);
            TextView mobileno = (TextView) dialog.findViewById(R.id.dailog_profile_mobileno_tv);
            // TextView emailid = (TextView) dialog.findViewById(R.id.dailog_profile_email_tv);
            Button logout = (Button) dialog.findViewById(R.id.dailog_profile_logout_bt);
            Button cancel = (Button) dialog.findViewById(R.id.dailog_profile_cancel);

            name.setText(MyApplication.name);
            mobileno.setText(MyApplication.phonenumber);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            dialog.show();
        }*/
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }
}
