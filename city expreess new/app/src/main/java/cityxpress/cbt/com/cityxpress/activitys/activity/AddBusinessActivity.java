package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.adapter.CategoryListAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.CityAreaListAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.CityListAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.CityServiceListAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.StateListAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.SubCategoryListAdapter;
import cityxpress.cbt.com.cityxpress.activitys.internet.CheckNetwork;
import cityxpress.cbt.com.cityxpress.activitys.model.CategoryModel;
import cityxpress.cbt.com.cityxpress.activitys.model.CityAreaModel;
import cityxpress.cbt.com.cityxpress.activitys.model.CityModel;
import cityxpress.cbt.com.cityxpress.activitys.model.CityServiceModel;
import cityxpress.cbt.com.cityxpress.activitys.model.StateModel;
import cityxpress.cbt.com.cityxpress.activitys.model.SubCategory;

public class AddBusinessActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ImageView back, imglogo;
    TextView title, businesslogo;
    EditText name, mobileno, businessname, address, emailid, starttime, endtime, mobaltno1, mobaltno2, website, amount, description, pincode;
    Spinner category, subcatogory, state, city, cityarea;
    EditText service;
    Button addbusiness;

    String strDescription = "", strAmount = "", strPincode = "", strStartTime = "", strEndTime = "", strLogo = "", strName = "", strMobileno = "", strBusinessName = "", strAddress = "", strEmailid = "", strMobileNo1 = "", strMobileNo2 = "", strWebSide = "";
    LinearLayout linearlayout;
    int mHour;
    int mMinute, sec;
    String selectedIndex = "";
    String selectedServices = "";

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ArrayList<CategoryModel> categoryList;
    ArrayList<SubCategory> subCategoryList;
    ArrayList<StateModel> stateList;
    ArrayList<CityModel> cityList;
    ArrayList<CityAreaModel> cityAreaList;
    ArrayList<CityServiceModel> cityServiceList;
    CategoryListAdapter categoryAdapter;
    SubCategoryListAdapter subCategoryAdapter;
    StateListAdapter statelistadapter;
    CityListAdapter cityListAdapter;
    CityAreaListAdapter cityAreaListAdapter;
    CityServiceListAdapter cityServiceListAdapter;
    ArrayList<String> servicelist;
    ArrayList<Integer> selectedItems;

    ProgressDialog progressDoalog;

    String selectedcategoryid = "", selectedSubcategoryid = "", selectedstateid = "", selectedcityid = "", selectedcityareaid = "", selectedcityserviceid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        context = this;


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
        title.setText("Add Business");

        init();
        initvalue();
        getCategory();
        getSubCategory();
        getStates();
        getCity();
    }


    private void init() {

        linearlayout = findViewById(R.id.addbusiness_layout);
        name = findViewById(R.id.addbusiness_name_et);
        mobileno = findViewById(R.id.addbusiness_mobileno);
        businessname = findViewById(R.id.addbusiness_business_name_et);
        address = findViewById(R.id.addbusiness_business_address);
        businesslogo = findViewById(R.id.addbusiness_business_logo);
        imglogo = findViewById(R.id.addbusiness_business_logo_iv);
        emailid = findViewById(R.id.addbusiness_business_emailid);
        starttime = findViewById(R.id.addbusiness_business_starttime);
        endtime = findViewById(R.id.addbusiness_business_endtime);
        mobaltno1 = findViewById(R.id.addbusiness_mobileno_alternative1);
        mobaltno2 = findViewById(R.id.addbusiness_mobileno_alternative2);
        website = findViewById(R.id.addbusiness_website);
        amount = findViewById(R.id.addbusiness_amount);
        description = findViewById(R.id.addbusiness_description);
        pincode = findViewById(R.id.addbusiness_pincode);

        category = findViewById(R.id.addbusiness_category_sp);
        subcatogory = findViewById(R.id.addbusiness_subcategory_sp);
        city = findViewById(R.id.addbusiness_city_sp);
        cityarea = findViewById(R.id.addbusiness_cityarea_sp);
        service = findViewById(R.id.addbusiness_services_sp);
        state = findViewById(R.id.addbusiness_state_sp);
        addbusiness = findViewById(R.id.addbusiness_addbusiness_btn);
        servicelist = new ArrayList<>();
        selectedItems = new ArrayList<>();
    }

    private void initvalue() {
        categoryList = new ArrayList<>();
        subCategoryList = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();
        cityAreaList = new ArrayList<>();
        cityServiceList = new ArrayList<>();
        addbusiness.setOnClickListener(this);
        businesslogo.setOnClickListener(this);
        starttime.setOnClickListener(this);
        endtime.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ExpressCity.class));
                finish();
            }
        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CategoryModel sc = categoryList.get(position);
                selectedcategoryid = sc.getCategoryid();
                getSubCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        subcatogory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SubCategory sc = subCategoryList.get(position);
                selectedSubcategoryid = sc.getSubCategoryID();
                getService();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                StateModel ss = stateList.get(position);
                selectedstateid = ss.getStateID();
                getCity();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CityModel cs = cityList.get(position);
                selectedcityid = cs.getCityID();
                getCityArea();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cityarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CityAreaModel ca = cityAreaList.get(position);
                selectedcityareaid = ca.getCityAreaID();
                getPincode();
                getAmount();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedIndex = "";
                selectedServices = "";


                final CharSequence[] dialogList = servicelist.toArray(new CharSequence[servicelist.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                selectedItems = new ArrayList<Integer>();
                // set the dialog title
                boolean[] itemChecked = new boolean[selectedItems.size()];

                builder.setMultiChoiceItems(dialogList, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if (isChecked) {
                            // if the user checked the item, add it to the selected items
                            selectedItems.add(which);
                        } else if (selectedItems.contains(which)) {
                            // else if the item is already in the array, remove it
                            selectedItems.remove(Integer.valueOf(which));
                        }

                        // you can also add other codes here,
                        // for example a tool tip that gives user an idea of what he is selecting
                        // showToast("Just an example description.");
                    }

                })
                        // Set the action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                int j = 0;
                                for (Integer i : selectedItems) {
                                    if (j == 0) {
                                        selectedServices = selectedServices + cityServiceList.get(i).getCityServiceName();
                                        selectedIndex += cityServiceList.get(i).getCityServiceID();
                                    } else {
                                        selectedIndex = selectedIndex + " , " + cityServiceList.get(i).getCityServiceID();
                                        selectedServices = selectedServices + " , " + cityServiceList.get(i).getCityServiceName();
                                    }
                                    j++;
                                }
                                Log.e("addbussiness", "id==" + selectedIndex);
                                Log.e("addbussiness", "service==" + selectedServices);
                                service.setText(selectedServices);
                                //showToast("Selected index: " + selectedIndex);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // removes the AlertDialog in the screen
                            }
                        })

                        .setTitle("Select Services")
                        .show();


            }
        });


    }

    private void getCategory() {
        categoryList.add(new CategoryModel("0", "-- Select Category --"));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyApplication.URL_GetCAtegory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray js = new JSONArray(response);
                    for (int i = 0; i < js.length(); i++) {
                        JSONObject t = js.getJSONObject(i);
                        categoryList.add(new CategoryModel(t.getString("categoryID"), t.getString("categoryName")));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        }
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

        categoryAdapter = new CategoryListAdapter(context, categoryList);
        category.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }


    public void getSubCategory() {
        subCategoryList.clear();
        subCategoryList.add(new SubCategory("0", "-- Select SubCategory --"));
        if (selectedcategoryid != "") {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, MyApplication.URL_GetSubCAtegory + "=" + selectedcategoryid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray js = new JSONArray(response);
                        for (int i = 0; i < js.length(); i++) {
                            JSONObject t = js.getJSONObject(i);
                            subCategoryList.add(new SubCategory(t.getString("subCategoryID"), t.getString("subCategoryName")));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    try {
                        String respone = new String(error.networkResponse.data, "utf-8");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }
            );
            requestQueue.add(stringRequest);
        }
        subCategoryAdapter = new SubCategoryListAdapter(context, subCategoryList);
        subCategoryAdapter.notifyDataSetChanged();
        subcatogory.setAdapter(subCategoryAdapter);

    }

    private void getStates() {


        stateList.clear();
        stateList.add(new StateModel("0", "-- Select State --"));

        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringreq = new StringRequest(Request.Method.GET, MyApplication.URL_GetState, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonarry = new JSONArray(response);

                    for (int j = 0; j < jsonarry.length(); j++) {
                        JSONObject jobj = jsonarry.getJSONObject(j);

                        stateList.add(new StateModel(jobj.getString("stateID"), jobj.getString("stateName")));

                    }
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {

                    String respone = new String(error.networkResponse.data, "utf-8");
                } catch (Exception e1) {

                    e1.printStackTrace();
                }


            }
        });
        stringreq.setRetryPolicy
                (new DefaultRetryPolicy
                        (
                                500000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                        )
                );
        requestqueue.add(stringreq);
        statelistadapter = new StateListAdapter(context, stateList);
        state.setAdapter(statelistadapter);
        statelistadapter.notifyDataSetChanged();

    }

    private void getCity() {

        if (selectedstateid != "") {
            cityList.clear();
            cityList.add(new CityModel("0", "-- Select City --"));
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, MyApplication.URL_GetCity + selectedstateid
                    , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONArray js = new JSONArray(response);
                        for (int i = 0; i < js.length(); i++) {

                            JSONObject t = js.getJSONObject(i);

                            cityList.add(new CityModel(t.getString("cityID"), t.getString("cityName")));

                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    try {

                        String respone = new String(error.networkResponse.data, "utf-8");
                    } catch (Exception e1) {

                        e1.printStackTrace();
                    }

                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("stateID", "3");
                    return params;
                }
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Content-Type", "application/json; charset=utf-8");
                return param;
            }*/

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        }
        cityListAdapter = new CityListAdapter(context, cityList);
        city.setAdapter(cityListAdapter);
        cityListAdapter.notifyDataSetChanged();
    }

    private void getCityArea() {

        cityAreaList.clear();
        cityAreaList.add(new CityAreaModel("0", "-- Select City Area --"));

        if (selectedcityid != "") {
            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringreq = new StringRequest(Request.Method.GET, MyApplication.URL_GetCityArea + selectedcityid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //   Toast.makeText(AddBuissness.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray jsonarry = new JSONArray(response);

                        for (int j = 0; j < jsonarry.length(); j++) {
                            JSONObject jobj = jsonarry.getJSONObject(j);
                            cityAreaList.add(new CityAreaModel(jobj.getString("cityAreaID"), jobj.getString("cityAreaName")));

                        }
                    } catch (Exception e) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    try {

                        String respone = new String(error.networkResponse.data, "utf-8");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
            stringreq.setRetryPolicy(new DefaultRetryPolicy(
                    500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestqueue.add(stringreq);
        }
        cityAreaListAdapter = new CityAreaListAdapter(context, cityAreaList);
        cityarea.setAdapter(cityAreaListAdapter);
        cityAreaListAdapter.notifyDataSetChanged();

    }

    private void getService() {
        cityServiceList.clear();
        servicelist.clear();
        if (selectedSubcategoryid != "") {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, MyApplication.URL_GetServices + selectedSubcategoryid
                    , new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    // Toast.makeText(AddBuissness.this, "Success" + "http://www.cityxpress.in/api/BusinessApi/GetServiceBySubCategoryID?subCategoryID=" + MyApplication.subcatids, Toast.LENGTH_SHORT).show();

                    try {
                        Log.e("serviceres", "serviceres=" + response);
                        //  Toast.makeText(RegisterAcitivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        JSONArray js = new JSONArray(response);
                        //  Toast.makeText(AddBuissness.this, ""+response, Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < js.length(); i++) {

                            JSONObject t = js.getJSONObject(i);
                            cityServiceList.add(new CityServiceModel(t.getString("servicesID"), t.getString("serviceName")));
                            servicelist.add(t.getString("serviceName"));
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    try {

                        String respone = new String(error.networkResponse.data, "utf-8");
                    } catch (Exception e1) {

                        e1.printStackTrace();
                    }
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("subCategoryID", "3e891393-e75a-4217-b61f-44658242de83");
                    return params;
                }


            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        }


    }


    private void getAmount() {

        if (selectedcityareaid != "") {
            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringreq = new StringRequest(Request.Method.GET, MyApplication.URL_Amount + selectedcityareaid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //Toast.makeText(AddBuissness.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        //  JSONArray jsonarry = new JSONArray(response);

                        JSONObject obj = new JSONObject(response);
                        strAmount = obj.getString("Amount");

                        amount.setText(strAmount);

                    } catch (Exception e) {
                        // Toast.makeText(AddBuissness.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    try {

                        String respone = new String(error.networkResponse.data, "utf-8");
                        // JSONObject json = new JSONObject(respone);
                        // Toast.makeText(getApplicationContext(), "response1sub" + respone.toString(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e1) {
                        //Toast.makeText(getApplicationContext(), "JSONException" + e1.getMessage(), Toast.LENGTH_SHORT).show();

                        e1.printStackTrace();
                    }

                    //  Toast.makeText(AddBuissness.this, "response" + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            stringreq.setRetryPolicy(new DefaultRetryPolicy(
                    500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestqueue.add(stringreq);
        }

    }

    private void getPincode() {
        if (selectedcityareaid != "") {
            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringreq = new StringRequest(Request.Method.GET, MyApplication.URL_GetPincode + selectedcityareaid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //Toast.makeText(AddBuissness.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        //  JSONArray jsonarry = new JSONArray(response);

                        JSONObject obj = new JSONObject(response);
                        strPincode = obj.getString("PinCode");

                        pincode.setText(strPincode);

                    } catch (Exception e) {
                        // Toast.makeText(AddBuissness.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    try {

                        String respone = new String(error.networkResponse.data, "utf-8");
                        // JSONObject json = new JSONObject(respone);
                    } catch (Exception e1) {

                        e1.printStackTrace();
                    }
                }
            });
            stringreq.setRetryPolicy(new DefaultRetryPolicy(
                    500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestqueue.add(stringreq);
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
                        starttime.setText(hourOfDay + ":" + minute + ":" + "00");
                        strStartTime = starttime.getText().toString().trim();
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void endtiemPicker() {
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
                        endtime.setText(hourOfDay + ":" + minute + ":" + "00");
                        strEndTime = endtime.getText().toString().trim();

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.addbusiness_business_starttime) {
            starttiemPicker();
        } else if (v.getId() == R.id.addbusiness_business_endtime) {
            endtiemPicker();
        } else if (v.getId() == R.id.addbusiness_business_logo) {



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



        } else if (v.getId() == R.id.addbusiness_addbusiness_btn) {
            strName = name.getText().toString().trim();
            strMobileno = mobileno.getText().toString().trim();
            strBusinessName = businessname.getText().toString().trim();
            strAddress = address.getText().toString().trim();
            strEmailid = emailid.getText().toString().trim();
            strMobileNo1 = mobaltno1.getText().toString().trim();
            strMobileNo2 = mobaltno2.getText().toString().trim();
            strWebSide = website.getText().toString().trim();
            strName = name.getText().toString().trim();
            strDescription = description.getText().toString().trim();


            if (CheckNetwork.isInternetAvailable(context)) //returns true if internet available
            {


                if (strName.length() == 0) {
                    name.setError("Enter Name");
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Enter Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    name.setFocusable(true);
                } else if (strMobileno.length() == 0) {
                    mobileno.setError("Enter Mobile No");
                    mobileno.setFocusable(true);
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Enter Mobile No", Snackbar.LENGTH_LONG);
                    snackbar.show();


                } else if (strBusinessName.length() == 0) {
                    businessname.setError("Enter Business Name");
                    businessname.setFocusable(true);
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Enter Business Name", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (strAddress.length() == 0) {
                    address.setError("Enter Address");
                    address.setFocusable(true);
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Enter Address", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (strLogo.length() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Please Select Logo", Snackbar.LENGTH_LONG);
                    snackbar.show();


                } else if (strEmailid.length() == 0) {
                    emailid.setError("Enter EmailID");
                    emailid.setFocusable(true);
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Enter EmailID", Snackbar.LENGTH_LONG);
                    snackbar.show();


                } else if (!strEmailid.matches(emailPattern)) {
                    emailid.setError("Invalid EmailID");
                    emailid.setFocusable(true);
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Invalid EmailID", Snackbar.LENGTH_LONG);
                    snackbar.show();


                } else if (strAmount.length() == 0) {
                    amount.setError("Enter Amount");
                    amount.setFocusable(true);


                } else if (pincode.length() == 0) {
                    pincode.setError("Enter Pincode");
                    pincode.setFocusable(true);


                } else if (selectedcategoryid.length() == 0 || selectedcategoryid.equals("0")) {
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Please Select Category", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (selectedSubcategoryid.length() == 0 || selectedSubcategoryid.equals("0")) {
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Please Select Sub Category", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (selectedstateid.length() == 0 || selectedstateid.equals("0")) {
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Please Select State", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (selectedcityid.length() == 0 || selectedcityid.equals("0")) {
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Please Select City", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (selectedcityareaid.length() == 0 || selectedcityareaid.equals("0")) {
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Please Select City Area", Snackbar.LENGTH_LONG);
                    snackbar.show();


                } else {

                    Log.e("token", "tokan=" + MyApplication.accesstoken);
                    addBuissness();

                }
            } else {
                Snackbar snackbar = Snackbar
                        .make(linearlayout, "No Internet Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
                // Toast.makeText(context,"No Internet Connection",1000).show();
            }

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap bp = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    imglogo.setImageBitmap(bp);
                    //imglogo.setVisibility(View.INVISIBLE);
                    BitmapDrawable drawable = (BitmapDrawable) imglogo.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data1 = baos.toByteArray();
                    ImageView image = new ImageView(this);
                    // image.setImageBitmap(bp);
                    businesslogo.setText("file attached");
                    strLogo = Base64.encodeToString(data1, Base64.DEFAULT);

                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imglogo.setImageURI(selectedImage);
                    businesslogo.setText("file attached");

                    BitmapDrawable drawable = (BitmapDrawable) imglogo.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data1 = baos.toByteArray();
                    strLogo = Base64.encodeToString(data1, Base64.DEFAULT);

                }

        }
    }


    private void addBuissness() {

        displayprogressbar();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                MyApplication.URL_AddBusiness,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(AddBuissness.this, ""+response, Toast.LENGTH_SHORT).show();
                        if (!response.equals("")) {
                            //startActivity(new Intent(context, ExploreCity.class));

                            progressDoalog.dismiss();
                            Toast.makeText(context, "Buissness Added Successfully", Toast.LENGTH_SHORT).show();


                        } else if (response.equals("{status:false}")) {
                            progressDoalog.dismiss();
                            Snackbar snackbar = Snackbar
                                    .make(linearlayout, "Already Registered", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            progressDoalog.dismiss();
                            Snackbar snackbar = Snackbar
                                    .make(linearlayout, "Something Went Wrong", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    progressDoalog.dismiss();
                    String respone = new String(error.networkResponse.data, "utf-8");
                    JSONObject json = new JSONObject(respone);
                    Log.e("aaa", error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

                } catch (Exception e1) {
                    progressDoalog.dismiss();

                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    e1.printStackTrace();
                }
                progressDoalog.dismiss();
                error.printStackTrace();
                Snackbar snackbar = Snackbar
                        .make(linearlayout, "Something Went Wrong", Snackbar.LENGTH_LONG);
                snackbar.show();

            }

        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> param = new HashMap<String, String>();
                param.put("Authorization", "bearer " + MyApplication.accesstoken);
                return param;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                // strDescription = "", strAmount = "", strPincode = "", strStartTime = "", strEndTime = "", strLogo = "",  = "", strMobileno = "", strBusinessName = "", strAddress = "", strEmailid = "", strMobileNo1 = "", strMobileNo2 = "", strWebSide = "";
                //String  = "",  = "",  = "",  = "",  = "",  = "";

                Map<String, String> params = new HashMap<String, String>();
                params.put("ContactPersonName", strName);
                params.put("BusinessName", strBusinessName);
                params.put("StateID", selectedstateid);
                params.put("CityID", selectedcityid);
                params.put("CityAreaID", selectedcityareaid);
                params.put("Pincode", strPincode);
                params.put("EmailId", strEmailid);
                params.put("CategoryID", selectedcategoryid);
                params.put("SubCategoryID", selectedSubcategoryid);

                params.put("BusinessUpload", strLogo);
                params.put("Address", strAddress);
                params.put("MobileNo", strMobileno);
                params.put("Description", strDescription);
                params.put("AlternateMobileNo1", strMobileNo1);
                params.put("AlternateMobileNo2", strMobileNo2);
                params.put("WebsiteName", strWebSide);
                params.put("Amount", strAmount);
                Log.e("aaaa", "strStartTime=" + strStartTime);
                params.put("StartTime", strStartTime);
                Log.e("aaaa", "strEndTime=" + strEndTime);
                params.put("EndTime", strEndTime);
                params.put("ServiceIdList", selectedIndex);
                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
