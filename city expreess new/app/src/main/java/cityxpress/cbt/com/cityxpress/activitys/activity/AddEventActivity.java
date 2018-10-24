package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.internet.CheckNetwork;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    EditText eventname, organizer, venue, mobileno, date, time, charges, description;
    TextView logo;
    Button addevent;
    String strEventname = "", strOrganize = "", strTime = "", strVenue = "", strMobileno = "", strDate = "", strCharges = "", strDescription = "", strLogo = "";
    ImageView img;
    int mHour;
    int mMinute, sec;
    ProgressDialog progressDoalog;
    int dates, months, years;
    ImageView back;
    TextView title;
    int selecteddate, selectedmonth, selectedyear;
    LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
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
        title.setText("Add Event");
        init();
        initvalue();
    }

    private void init() {
        eventname = findViewById(R.id.addevent_eventname_et);
        organizer = findViewById(R.id.addevent_organizer_et);
        venue = findViewById(R.id.addevent_venue_et);
        mobileno = findViewById(R.id.addevent_contact_no);
        date = findViewById(R.id.addevent_pickdate);
        time = findViewById(R.id.addevent_picktime);
        charges = findViewById(R.id.addevent_charges);
        description = findViewById(R.id.addevent_description);
        logo = findViewById(R.id.addevent_logo);
        addevent = findViewById(R.id.addevent_addevent_btn);
        img = findViewById(R.id.addevent_imglogo);
    }

    private void initvalue() {
        addevent.setOnClickListener(this);
        logo.setOnClickListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.addevent_addevent_btn) {
            //String  ="",="",="",="",="",="",="",strLogo="";
            strEventname = eventname.getText().toString();
            strOrganize = organizer.getText().toString();
            strVenue = venue.getText().toString();
            strMobileno = mobileno.getText().toString();
            // strDate //= date.getText().toString();
            strCharges = charges.getText().toString();
            strDescription = description.getText().toString();

            if (strEventname.length() == 0) {
                eventname.setError("Enter Evnet Name");
                eventname.setFocusable(true);
            } else if (strOrganize.length() == 0) {
                organizer.setError("Enter Organiser Name");
                organizer.setFocusable(true);
            } else if (strVenue.length() == 0) {
                venue.setError("Enter Venue Name");
                venue.setFocusable(true);
            } else if (strMobileno.length() == 0) {
                mobileno.setError("Enter Mobile No");
                mobileno.setFocusable(true);
            } else if (strMobileno.length() != 10) {
                mobileno.setError("Invalid Mobile No");
                mobileno.setFocusable(true);
            } else if (strDate.length() == 0) {
                date.setError("Enter Date");
                date.setFocusable(true);
            } else if (strTime.length() == 0) {
                time.setError("Enter Time");
                time.setFocusable(true);
            } else if (strCharges.length() == 0) {
                charges.setError("Enter Charges");
                charges.setFocusable(true);
            } else if (strDescription.length() == 0) {
                description.setError("Enter Description");
                description.setFocusable(true);
            } else if (strLogo.length() == 0) {
                logo.setText("Image Not Selected");
            } else {
                displayprogressbar();
                if (CheckNetwork.isInternetAvailable(context)) {
                    getRegistered();

                } else {
                    progressDoalog.dismiss();
                    Toast.makeText(context, "No Interet Connection ", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v.getId() == R.id.addevent_logo) {

            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
            View mView = layoutInflaterAndroid.inflate(R.layout.dialog_profile, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
            alertDialogBuilderUserInput.setView(mView);
            final AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
            mView.setBackgroundResource(android.R.color.transparent);
            alertDialogAndroid.show();
            final RadioGroup radiogroup = (RadioGroup) mView.findViewById(R.id.radio_group);
            RadioButton b11 = (RadioButton) mView.findViewById(R.id.radio1);
            RadioButton b22 = (RadioButton) mView.findViewById(R.id.radio2);
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int radioButtonID = radiogroup.getCheckedRadioButtonId();

                    if (radioButtonID == R.id.radio1) {

                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);
                        alertDialogAndroid.dismiss();
                    } else if (radioButtonID == R.id.radio2) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, 1);
                        alertDialogAndroid.dismiss();
                    }
                }
            });
        } else if (v.getId() == R.id.addevent_pickdate) {

            datepicker();
        } else if (v.getId() == R.id.addevent_picktime) {
            starttiemPicker();
        }
    }

    private void starttiemPicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        sec = c.get(Calendar.SECOND);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        if (dates == selecteddate && months == selectedmonth && years == selectedyear) {
                            if (hourOfDay > (mHour + 1)) {
                                time.setText(hourOfDay + ":" + minute);
                                strTime = time.getText().toString();
                            } else {
                                time.setText("Invalid Time");
                                strTime = "";
                            }

                        } else {
                            time.setText(hourOfDay + ":" + minute);
                            strTime = time.getText().toString();
                        }


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap bp = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    img.setImageBitmap(bp);
                    //imglogo.setVisibility(View.INVISIBLE);
                    BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data1 = baos.toByteArray();
                    logo.setText("  File Attached");
                    strLogo = Base64.encodeToString(data1, Base64.DEFAULT);

                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    img.setImageURI(selectedImage);
                    logo.setText(" File Attached");
                    BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data1 = baos.toByteArray();
                    strLogo = Base64.encodeToString(data1, Base64.DEFAULT);

                }
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


                        if (dpyear >= years) {
                            if ((dpmonth + 1) >= months) {
                                if (dpdayOfMonth >= dates) {
                                    date.setTextColor(Color.BLACK);

                                    date.setText(dpdayOfMonth + "-" + (dpmonth + 1) + "-" + dpyear);
                                    selecteddate = dpdayOfMonth;
                                    selectedmonth = (dpmonth + 1);
                                    selectedyear = dpyear;
                                    strDate = (dpmonth + 1) + "-" + dpdayOfMonth + "-" + dpyear;
                                } else {
                                    strDate = "";
                                    date.setTextColor(Color.RED);
                                    date.setText("Invalid Date");
                                }
                            } else {
                                strDate = "";
                                date.setTextColor(Color.RED);
                                date.setText("Invalid Date");
                            }
                        } else {
                            strDate = "";
                            date.setTextColor(Color.RED);
                            date.setText("Invalid Date");
                        }
                    }

                }, years, months, dates);


        c.add(Calendar.DATE, 1);
        Date newDate = c.getTime();
        timePickerDialog.getDatePicker().setMinDate(newDate.getTime() - (newDate.getTime() % (24 * 60 * 60 * 1000)));

        //timePickerDialog.getDatePicker().setMaxDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
        //timePickerDialog.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()));
        timePickerDialog.show();
    }


    public void getRegistered() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                MyApplication.URL_AddEvent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      /*  Toast.makeText(AddEvents.this, "" + response, Toast.LENGTH_SHORT).show();*/
                        try {
                            progressDoalog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status") == "true") {
                                Toast.makeText(context, "Event Added Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressDoalog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                    }
                    if (error instanceof TimeoutError) {
                        Log.e("Volley", "TimeoutError");
                    } else if (error instanceof NoConnectionError) {
                        Log.e("Volley", "NoConnectionError");
                    } else if (error instanceof AuthFailureError) {
                        Log.e("Volley", "AuthFailureError");
                    } else if (error instanceof ServerError) {
                        Log.e("Volley", "ServerError");
                    } else if (error instanceof NetworkError) {
                        Log.e("Volley", "NetworkError");
                    } else if (error instanceof ParseError) {
                        Log.e("Volley", "ParseError");
                    }


                    progressDoalog.dismiss();
                    error.printStackTrace();
                    String respone = new String(error.networkResponse.data, "utf-8");
                    // JSONObject json = new JSONObject(respone);
                    //  Toast.makeText(getApplicationContext(), "response1" + respone.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e1) {
                    //  Toast.makeText(getApplicationContext(), "JSONException" + e1.getMessage(), Toast.LENGTH_SHORT).show();

                    e1.printStackTrace();
                }
                progressDoalog.dismiss();
                Toast.makeText(context, "Something went wrong please try after sometime" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }


            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                Log.e("msg", "strEventname=" + strEventname);
                Log.e("msg", "strOrganize=" + strOrganize);
                Log.e("msg", "strMobileno=" + strMobileno);
                Log.e("msg", "strDate=" + strDate);
                Log.e("msg", "strTime=" + strTime);
                Log.e("msg", "strVenue=" + strVenue);
                Log.e("msg", "strCharges=" + strCharges);
                Log.e("msg", "strDescription=" + strDescription);
                Log.e("msg", "strLogo=" + strLogo);
                params.put("NameOfEvent", strEventname);
                params.put("Organiser", strOrganize);
                params.put("OrganiserContactNo", strMobileno);
                params.put("EventDate", strDate + " " + strTime);
                params.put("Venue", strVenue);
                params.put("Charges", strCharges);
                params.put("Description", strDescription);
                params.put("imageUpload", strLogo);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        jsonObjRequest.setRetryPolicy
                (
                        new
                                DefaultRetryPolicy
                                (500000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                                )
                );

        Log.e("jsonObjRequest", jsonObjRequest + "");
        requestQueue.add(jsonObjRequest);


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

}
