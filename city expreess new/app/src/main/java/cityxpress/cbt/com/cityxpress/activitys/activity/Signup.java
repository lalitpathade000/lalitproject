package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.adapter.CityAdapter;
import cityxpress.cbt.com.cityxpress.activitys.internet.CheckNetwork;
import cityxpress.cbt.com.cityxpress.activitys.model.SignupcityModel;

import static com.android.volley.Request.Method.GET;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    Context context;
    EditText name, number, email, bdate, password, cpassword;
    RadioButton rb;
    RadioGroup rgroup;
    AutoCompleteTextView city;
    Button signup;
    TextView signin;
    int mYear, mMonth, mDay;
    ArrayList<SignupcityModel> citylist;
    ArrayAdapter cityAdapter;
    String cityidstr = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ArrayList<String> cityliststr;
    LinearLayout linearlayout;
    boolean flag_focus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Glide.with(this).load(R.drawable.backimg).asBitmap().placeholder(R.drawable.backimg).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    linearlayout.setBackground(drawable);
                }
            }
        });

        context = this;
        init();
        initvalue();
        getcitysignupdata();
    }


    private void init() {
        name = findViewById(R.id.signup_name_et);
        number = findViewById(R.id.signup_phoneno_et);
        email = findViewById(R.id.signup_emailid_et);
        bdate = findViewById(R.id.signup_birthdate_et);
        password = findViewById(R.id.signup_password_et);
        cpassword = findViewById(R.id.signup_cpassword_et);
        city = findViewById(R.id.signup_city_sp);
        signup = findViewById(R.id.signup_signup_btn);
        signin = findViewById(R.id.signup_signin_tv);
        rgroup = findViewById(R.id.signup_radiogroup);

        linearlayout = findViewById(R.id.signup_mainlayout);
        citylist = new ArrayList<>();
        cityliststr = new ArrayList<>();
        citylist.add(new SignupcityModel("000", "--Select City--"));



    }

    private void initvalue() {

        signup.setOnClickListener(this);
        signin.setOnClickListener(this);
        bdate.setOnClickListener(this);
        // cityAdapter = new CityAdapter(context, citylist);
        cityAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, cityliststr);
        city.setAdapter(cityAdapter);
        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    Log.e("aaa", "focuse on");

                } else {
                    Log.e("aaa", "focuse off");

                    String selected_city = city.getText().toString().trim();

                    if (checkcity(selected_city)) {

                    } else {
                        city.setError("Invalid City Name");

                    }


                }
            }
        });

    }

    private boolean checkcity(String selected_city) {

        if (cityliststr.contains(selected_city)) {
            Log.e("aaa", "city present");
            for (int i = 0; i < citylist.size(); i++) {
                SignupcityModel s = citylist.get(i);
                if (selected_city.equals(s.getCity())) {
                    cityidstr = s.getId();
                }
            }
            Log.e("aaa", "city id=" + cityidstr);
            return true;
        } else {

            return false;

        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.signup_signup_btn) {

            if (CheckNetwork.isInternetAvailable(context)) //returns true if internet available
            {

                getValidation();
            } else {
                Snackbar snackbar = Snackbar
                        .make(linearlayout, "No Internet Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }
        if (v.getId() == R.id.signup_signin_tv) {
            startActivity(new Intent(context, Login.class));
            finish();
        }
        if (v.getId() == R.id.signup_birthdate_et) {

            setdatepicker();
        }


    }

    /*

    private void checkmobileno() {
        final String mobilenostr = number.getText().toString().trim();

        try {

            String url = "http://www.cityxpress.in/api/UserApi/CheckMobileNo?phoneNumber=" + mobilenostr;
            Log.e("aaa", "url=" + url);
            StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            Log.e("aaa", "Number is exist");


                            Log.e("aaa", "check mobile res=" + response);
                            *//*
                            //Toast.makeText(getApplicationContext(), "" + response.toString(), Toast.LENGTH_SHORT).show();
                            if (response.toString().equals("true")) {
                                Snackbar snackbar = Snackbar
                                        .make(linearlayout, "User Already Registered", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                Log.d("res", "already register response" + response);
                                // Toast.makeText(RegisterAcitivity.this, "already registered" + response, Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("res", "successfully registration=" + response);
                                Snackbar snackbar = Snackbar
                                        .make(linearlayout, "Registered Successfully", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                Log.e("aaa", "REgistration Successfully");
                                // startActivity(new Intent(RegisterAcitivity.this, sendMessage.class));
                                // Toast.makeText(RegisterAcitivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            }*//*

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {


                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                        }
                    }
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "User Already Registered", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Log.e("aaa", "in vooly");
                    Log.e("eee", "in vooly error=" + error);
                    Log.e("eee", "in vooly error msg =" + error.getMessage());

                    //Toast.makeText(RegisterAcitivity.this, "Already Account Register", Toast.LENGTH_SHORT).show();
                }


            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    900000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjRequest);
        } catch (Exception e) {

            Snackbar snackbar = Snackbar
                    .make(linearlayout, "Something went Wrong", Snackbar.LENGTH_LONG);
            snackbar.show();
            Log.d("aaa", "error Something went Wrong in try catch=" + e.getMessage());

            // Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
*/
    private void getValidation() {

        final String mobilenostr = number.getText().toString().trim();
        final String namestr = name.getText().toString().trim();
        final String emialstr = email.getText().toString().trim();
        final String bdatestr = bdate.getText().toString().trim();
        final String passwordstr = password.getText().toString().trim();
        String cpasswordstr = cpassword.getText().toString().trim();
        String citystr = city.getText().toString().trim();

        int selectedId = rgroup.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(selectedId);
        final String genderstr = rb.getText().toString();
        Log.e("aaa", "gender=" + genderstr);
        final String gendervalue = getGenderValue(genderstr);
        Log.e("aaa", "gender value=" + gendervalue);
        Boolean flag = false;
        if (namestr.length() == 0) {
            name.setError("Enter Name");
        } else if (mobilenostr.length() == 0) {
            number.setError("Enter Mobile no");
        } else if (mobilenostr.length() != 10) {
            number.setError("Enter Invalid Mobile");
        } else if (emialstr.length() == 0) {
            email.setError("Enter Email Id");
        } else if (!emialstr.matches(emailPattern)) {
            email.setError("Invalid Email Id");
        } else if (bdatestr.length() == 0) {
            bdate.setError("Enter Birthdate");
        } else if (passwordstr.length() == 0) {
            password.setError("Enter password");
        } else if (cpasswordstr.length() == 0) {
            cpassword.setError("Enter conform password");
        } else if (!passwordstr.equals(cpasswordstr)) {
            password.setError(" Password Not Match");
        } else if (citystr.equals("0")) {
            Snackbar snackbar = Snackbar
                    .make(linearlayout, "Please Select City", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (citystr.length() == 0) {

            city.setError("Invalid City Name");
        } else {


            Log.e("aaa", "true");

            if (checkcity(citystr)) {

                Log.e("aaa", "in on click cityid=" + cityidstr);

                try {

                    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                            MyApplication.URL_Registration,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    //Toast.makeText(getApplicationContext(), "" + response.toString(), Toast.LENGTH_SHORT).show();
                                    if (response.toString().equals("true")) {
                                        Snackbar snackbar = Snackbar
                                                .make(linearlayout, "User Already Registered", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        Log.d("res", "already register response" + response);
                                        // Toast.makeText(RegisterAcitivity.this, "already registered" + response, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("res", "successfully registration=" + response);
                                        Snackbar snackbar = Snackbar
                                                .make(linearlayout, "Registered Successfully", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();

                                        Log.e("aaa", "Registration Successfully");
                                        startActivity(new Intent(context, Login.class));
                                        // Toast.makeText(RegisterAcitivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {


                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data

                                } catch (UnsupportedEncodingException e1) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                    Log.e("eee", e1.getMessage());
                                }
                            }
                            Snackbar snackbar = Snackbar
                                    .make(linearlayout, "User Already Registered", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            Log.e("aaa", "in vooly");
                            Log.e("eee", "in vooly error=" + error);
                            Log.e("eee", "in vooly error msg =" + error.getMessage());

                            //Toast.makeText(RegisterAcitivity.this, "Already Account Register", Toast.LENGTH_SHORT).show();
                        }


                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded; charset=utf-8";
                        }

                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("PhoneNumber", mobilenostr);
                            params.put("Name", namestr);
                            params.put("Gender", gendervalue);
                            params.put("Email", emialstr);
                            params.put("CityID", cityidstr);
                            params.put("DateOfBirth", bdatestr);
                            params.put("Password", passwordstr);

                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(this);

                    jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                            900000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    requestQueue.add(jsonObjRequest);
                } catch (Exception e) {

                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Something went Wrong", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Log.d("aaa", "error Something went Wrong in try catch=" + e.getMessage());

                    // Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            } else {
                city.setError("Invalid City Name");
                Log.e("aaa", "in sign in btn");
            }
        }


    }


    private void setdatepicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        bdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        c.add(Calendar.DATE, 1);
        Date newDate = c.getTime();
        datePickerDialog.getDatePicker().setMaxDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
        datePickerDialog.show();
    }

    private void getcitysignupdata() {

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(GET, "http://www.cityxpress.in/api/UserApi/CityList", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        //  Toast.makeText(RegisterAcitivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        Log.e("res", "city responce=" + response);
                        JSONArray js = new JSONArray(response);
                        for (int i = 0; i < js.length(); i++) {

                            JSONObject t = js.getJSONObject(i);


                            citylist.add(new SignupcityModel(t.getString("cityID"), t.getString("cityName")));
                            cityliststr.add(t.getString("cityName"));

                            //  arrsubCityids.add(t.getString("cityID"));
                            // arrsubcitynms.add(t.getString("cityName"));

                         /*   cityAdapters = new CityAdapters(RegisterAcitivity.this, arrsubCityids, arrsubcitynms);

                            spinner.setAdapter(cityAdapters);*/
                        }
                        cityAdapter.notifyDataSetChanged();

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("aaa", "error=" + error);
                }
            }
            );
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.getMessage();
        }
    }
    public String getGenderValue(String str) {
        if (str.equals("Male")) {
            return "true";
        } else {
            return "false";
        }
    }
}
