package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.database.SQLiteDatabse;

public class SearchTrain extends AppCompatActivity {

    Context context;
    ArrayAdapter<String> trainAdapter;
    ArrayList<String> arrTrainName, arrTrainNumber, arrTrainDept, arrTrainArrival, arrTrainTravel;

    SharedPreferences pref;

    SharedPreferences.Editor editor;
    TextView froms, toos;
    Button next, serachtrain;
    ProgressDialog pDialog;
    public static final String MyPREFERENCES = "MyPrefs";

    SQLiteDatabse databse;
    ListView hlistview;
    ArrayList<String> hlist;

    private static String url_temp = "http://api.railwayapi.com/between/source/";
    //   String api = "/apikey/cf4ehkhg/";
    // String api = "/apikey/1izs8epf/";
    //  String api="jmi5myqko9";
    String frm, to;
    ImageView back;
    TextView title;
    Button out;
    ArrayAdapter<String> hlistadapter;
    ArrayAdapter<String> stationNameAdapter;
    ArrayList<String> sugetionlist;
    LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtrain);
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

        serachtrain = findViewById(R.id.searchtrain_findtrainbytrainno);
        title.setText("Search Train");
        init();
        initvalue();

        statusCheck();
        //addStation();
        addhlist();
        MyApplication.fevId = 0;
    }

    private void init() {
        sugetionlist = new ArrayList<>();
        databse = new SQLiteDatabse(context);
        froms = findViewById(R.id.searchtrain_from);
        toos = findViewById(R.id.searchtrain_to);
        next = (Button) findViewById(R.id.searchtrain_next);
        hlistview = findViewById(R.id.searchtrain_history_list);
        //addSugetion();
        arrTrainTravel = new ArrayList<String>();
        arrTrainArrival = new ArrayList<String>();
        arrTrainDept = new ArrayList<String>();
        arrTrainName = new ArrayList<String>();
        arrTrainNumber = new ArrayList<String>();
        hlist = new ArrayList<>();
        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        hlistadapter.notifyDataSetChanged();
        addhlist();
        froms.setText(MyApplication.selectedSorceStation);
        toos.setText(MyApplication.selectedDestStation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        addhlist();
        hlistadapter.notifyDataSetChanged();
    }

    private void initvalue() {
        hlistadapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, hlist);
        hlistview.setAdapter(hlistadapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ExpressCity.class));
                finish();
                ;
            }
        });


        froms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AllStation.class));
                MyApplication.allStationSource = 0;


            }
        });

        toos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AllStation.class
                ));
                MyApplication.allStationSource = 1;

            }
        });


        serachtrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, TrainInfoActivity.class));
            }
        });

        hlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setMessage("Do you want Remove Route?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                databse.removeFavorate(hlist.get(position));
                                Toast.makeText(context, "Remove Route Successfully", Toast.LENGTH_SHORT).show();
                                addhlist();
                                hlistadapter.notifyDataSetChanged();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                android.app.AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                frm = froms.getText().toString().trim();
                to = toos.getText().toString().trim();
                MyApplication.fromstnsave = frm;
                MyApplication.tostnsave = to;
                editor = pref.edit();
                editor.putString("frm", frm);
                editor.putString("to", to);
                editor.commit();


                if (frm.equals("") || frm.length() == 0) {
                    froms.requestFocus();

                    //  froms.setError("Enter source station");
                    Toast.makeText(context, "Enter source station", Toast.LENGTH_SHORT).show();
                } else if (to.equals("") || to.length() == 0) {
                    toos.requestFocus();

                    // toos.setError("Enter Destination station");
                    Toast.makeText(context, "Enter Destination station", Toast.LENGTH_SHORT).show();


                } else if (frm.equals(to)) {
                    toos.requestFocus();
                    // toos.setError("Enter Diffrent Destination");
                    Toast.makeText(context, "Enter Diffrent Destination", Toast.LENGTH_SHORT).show();
                } else {


                    if (!sugetionlist.contains(frm)) {
                        databse.addDestination(frm);

                    } else {
                        Log.e("aaa", "available from");
                    }
                    if (!sugetionlist.contains(to)) {
                        databse.addDestination(to);

                    } else {
                        Log.e("aaa", "available to");

                    }
                    try {
                        // databse.addHistory(frm+" To "+to);
                        MyApplication.fromstr = frm;
                        MyApplication.tostr = to;
                        Intent i = new Intent(context, RoutesActivity.class);
                        i.putExtra("From", frm);
                        i.putExtra("To", to);

                        startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        try {

            stationNameAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.textview, sugetionlist);

        } catch (Exception e) {
            e.getMessage();
        }


        // froms.setAdapter(stationNameAdapter);

        //  toos.setAdapter(stationNameAdapter);

/*

        froms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {


                if (count > 2) {
                    // autoCompleteText(froms, s);
                    addStation(froms, s);

                } else {
                    stationNameAdapter.clear();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/
/*
        toos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence ss, int start, int before, int count) {

                if (count > 2) {
                    // autoCompleteText(froms, s);
                    addStation(toos, ss);

                } else {
                    stationNameAdapter.clear();
                }

                //autoCompleteText(toos, ss);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hlist.get(position);

                Log.e("aaa", "Click on =" + hlist.get(position));
                String str = hlist.get(position);
                String s[] = str.split(" To ");
                String ss = s[0].trim();
                String dd = s[1].trim();
                Log.e("aaa", "source=" + ss);
                Log.e("aaa", "destination=" + dd);
                Intent i = new Intent(context, RoutesActivity.class);
                i.putExtra("From", ss);
                i.putExtra("To", dd);
                MyApplication.fromstr = ss;
                MyApplication.tostr = dd;
                startActivity(i);
            }
        });


    }
/*
    private void addStation() {

      *//*  stationNameAdapter.clear();
        Cursor c = databse.getStationByStationata(s.toString());
        String ss = "";
        if (c.moveToFirst()) {
            do {
                ss = c.getString(1) + "(" + c.getString(2) + ")";
                stationNameAdapter.add(ss);
            } while (c.moveToNext());
        }
        froms.setAdapter(stationNameAdapter);
        stationNameAdapter.notifyDataSetChanged();
*//*

        stationNameAdapter.clear();
        Cursor c = databse.getStationAll();


        String ss = "";
        if (c.moveToFirst()) {
            do {
                ss = c.getString(1) + "(" + c.getString(2) + ")";
                stationNameAdapter.add(ss);
            } while (c.moveToNext());
        }
        froms.setAdapter(stationNameAdapter);
        stationNameAdapter.notifyDataSetChanged();


    }

    private void addSugetion() {

        Cursor c = databse.getDestination();
        int a = 1;
        sugetionlist.clear();
        if (c.moveToFirst()) {
            do {
                Log.e("aaa", "Destination" + "id=" + c.getString(0) + " =" + c.getString(1));

                sugetionlist.add(c.getString(1));

            } while (c.moveToNext());
        }


    }*/

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

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


    private void addhlist() {
        Cursor c = databse.getHistory();
        int a = 1;
        hlist.clear();
        if (c.moveToFirst()) {
            do {
                Log.e("aaa", "hlist" + "id=" + c.getString(1));

                hlist.add(c.getString(1));

            } while (c.moveToNext());
        }
    }


    public void autoCompleteText(AutoCompleteTextView autoCompleteTextView, CharSequence charSequence) {
        if (charSequence.length() == 3) {
//            String[] keys = getResources().getStringArray(R.array.keys);
            String url = "https://api.railwayapi.com/v2/suggest-station/name/" + autoCompleteTextView.getText().toString() + "/apikey/o7pahdgjk1/";
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("response_code") == 200) {
                            pDialog.dismiss();
                        }
                        Log.e("errrrrr", "" + jsonObject);
                        JSONArray arr = jsonObject.getJSONArray("stations");
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject j = arr.getJSONObject(i);
                            String tempName = j.getString("name") + " (" + j.getString("code") + ")";
                            if (stationNameAdapter.getPosition(tempName) > 1) {
                                break;
                            } else {
                                if (sugetionlist.contains(tempName)) {


                                } else {
                                    stationNameAdapter.add(tempName);
                                    stationNameAdapter.notifyDataSetChanged();
                                }


                            }
                        }
                        if (jsonObject.getInt("response_code") == 500) {

                            Toast.makeText(context, "Service Not Available now Try Aftr Sometime", Toast.LENGTH_SHORT).show();
                        }
                        if (jsonObject.getInt("response_code") == 405) {
                            Toast.makeText(context, "No Trains Available On this Route", Toast.LENGTH_SHORT).show();
                        }
                    } catch (
                            Exception E)

                    {
                        E.getMessage();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();


                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }
    }

}
