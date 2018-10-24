package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
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
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class BussinessDetailsActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    Context context;
    ImageView logo,backtop,backbuttom;
    TextView businessname, personname, category, subcategory, address, cityarea, pincode, state, city, opningtime, closingtime, email, mobileno, webside, descreption;
    AppBarLayout appBarLayout;
    Animation animation_top;
    ProgressDialog progressDoalog;
    LinearLayout linearlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness_details);
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
        displayprogressbar();
        init();

        getData();
        initvalue();

        imgoperation();
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
    private void imgoperation() {
        Log.e("img","image maxheight in px="+logo.getMaxHeight());
        Log.e("img","image height in dp="+pxToDp(logo.getMaxHeight()));
        Log.e("img","image MeasuredHeight in px="+logo.getMeasuredHeight());
        Log.e("img","image MeasuredHeight in dp="+pxToDp(logo.getMeasuredHeight()));
        Log.e("img","image height in px="+pxToDp(logo.getHeight()));
        Log.e("img","image width in px="+logo.getWidth());
        Log.e("img","image width in px="+pxToDp(logo.getWidth()));

    }

    private void init() {


        logo = findViewById(R.id.businessdetails_logo);
        businessname = findViewById(R.id.businessdetails_businessname);
        personname = findViewById(R.id.businessdetails_personname);
        category = findViewById(R.id.businessdetails_category);
        subcategory = findViewById(R.id.businessdetails_subcategory);
        address = findViewById(R.id.businessdetails_address);
        cityarea = findViewById(R.id.businessdetails_cityarea);
        pincode = findViewById(R.id.businessdetails_pincode);
        state = findViewById(R.id.businessdetails_state);
        city = findViewById(R.id.businessdetails_city);
        opningtime = findViewById(R.id.businessdetails_opening_time);
        closingtime = findViewById(R.id.businessdetails_closing_time);
        email = findViewById(R.id.businessdetails_email);
        mobileno = findViewById(R.id.businessdetails_mobileno);
        webside = findViewById(R.id.businessdetails_webside);
        descreption = findViewById(R.id.businessdetails_description);
        backtop=findViewById(R.id.businessdetails_back_top);
        backbuttom=findViewById(R.id.businessdetails_back_buttom);


    }
    private void initvalue() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Log.e("ccc","TotalScrollRange= "+appBarLayout.getTotalScrollRange());



        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
        int devicewidth = displaymetrics.heightPixels ;
        //if you need 4-5-6 anything fix imageview in height
        appBarLayout.getLayoutParams().height=devicewidth/2;








        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                if (verticalOffset == 0)
                {
                    backbuttom.setVisibility(View.GONE);
                    backtop.setVisibility(View.VISIBLE);

                }
                else
                {
                    Log.e("ccc","Not fully expanded or collapsed ");
                    Log.e("ccc","verticalOffset="+verticalOffset);
                    Log.e("ccc","TotalScrollRange= "+appBarLayout.getTotalScrollRange());
                    if(Math.abs(verticalOffset)== Math.abs(appBarLayout.getTotalScrollRange()))
                    {
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
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, MyApplication.URL_GetBusinessDetails + MyApplication.bussinessDetails_Id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("res", "" + response);
                    Log.e("img", "http://www.cityxpress.in" + response.getString("businessUpload"));
                    Picasso.with(context).load("http://www.cityxpress.in" + response.getString("businessUpload"))
                            .placeholder(R.drawable.ic_train).error(R.drawable.ic_logo)
                            .fit()
                            .into(logo);

                    String str;
                    str = response.getString("businessName");
                    if (str.equals("null")) {
                        businessname.setText("");
                    } else {
                        businessname.setText(str);
                    }

                    str = response.getString("contactPersonName");
                    if (str.equals("null")) {
                        personname.setText("");
                    } else {
                        personname.setText(str);
                    }
                    str = response.getString("categoryName");
                    if (str.equals("null")) {
                        category.setText("");
                    } else {
                        category.setText(str);
                    }

                    str = response.getString("subCategoryName");
                    if (str.equals("null")) {
                        subcategory.setText("");
                    } else {
                        subcategory.setText(str);
                    }


                    str = response.getString("address");
                    if (str.equals("null")) {
                        address.setText("");
                    } else {
                        address.setText(str);
                    }


                    str = response.getString("cityAreaName");
                    if (str.equals("null")) {
                        cityarea.setText("");
                    } else {
                        cityarea.setText(str);
                    }

                    str = response.getString("pincode");
                    if (str.equals("null")) {
                        pincode.setText("");
                    } else {
                        pincode.setText(str);
                    }

                    str = response.getString("stateName");
                    if (str.equals("null")) {
                        state.setText("");
                    } else {
                        state.setText(str);
                    }


                    str = response.getString("stateName");
                    if (str.equals("null")) {
                        state.setText("");
                    } else {
                        state.setText(str);
                    }


                    str = response.getString("cityName");
                    if (str.equals("null")) {
                        city.setText("");
                    } else {
                        city.setText(str);
                    }

                    str = response.getString("openingTime");


                    Log.e("aaa","openingTime="+str);
                    if (str.equals("null")) {
                        opningtime.setText("");
                    } else {
                        opningtime.setText(str);
                    }

                    str = response.getString("closingTime");
                    Log.e("aaa","closingTime="+str);

                    if (str.equals("null")) {
                        closingtime.setText("");
                    } else {
                        closingtime.setText(str);
                    }

                    str = response.getString("emailId");
                    if (str.equals("null")) {
                        email.setText("");
                    } else {
                        email.setText(str);
                    }


                    String mob1, mob2, mob3, mobstr = "";
                    mob1 = response.getString("mobileNo");
                    mob2 = response.getString("alternateMobileNo1");
                    mob3 = response.getString("alternateMobileNo2");

                    if (mob1.length() == 10) {
                        mobstr = mob1;
                    }

                    if (mob2.length() == 10) {
                        mobstr = mobstr + "," + mob2;
                    }
                    if (mob3.length() == 10) {
                        mobstr = mobstr + "," +"\n"+ mob3;
                    }
                    mobileno.setText(mobstr);

                    str = response.getString("websiteName");
                    if (str.equals("null")) {
                        webside.setText("");
                    } else {
                        webside.setText(str);
                    }

                    str = response.getString("description");
                    if (str.equals("null")) {
                        descreption.setText("");
                    } else {
                        descreption.setText(str);
                    }
                    progressDoalog.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                    progressDoalog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDoalog.dismiss();

                Toast.makeText(context, "Somthing Went Wrong", Toast.LENGTH_LONG).show();
                ;
            }
        }
        );

        requestQueue.add(stringRequest);


    }
}

/*
 E/res: {"businessRegistrationID":"2558dbf0-f1c1-4449-bc6c-118b231cf83e","contactPersonName":"tctf","businessName":"ysjd","stateID":0,"cityID":"00000000-0000-0000-0000-000000000000","cityAreaID":"00000000-0000-0000-0000-000000000000","pincode":"411011","categoryID":"00000000-0000-0000-0000-000000000000","subcategoryID":"00000000-0000-0000-0000-000000000000","businessUpload":"\/Images\/ProfileImage\/e5eki21e.Jpeg","emailId":"fgh@fgh.fgh","address":"udir","mobileNo":"9923267755","alternateMobileNo1":"1232112355","alternateMobileNo2":"1223212355","description":"tg","websiteName":null,"stateName":"Maharashtra","cityName":"Pune","cityAreaName":"Kothrud","categoryName":"City Education","subCategoryName":"Classes","openingTime":"","closingTime":""}

 */