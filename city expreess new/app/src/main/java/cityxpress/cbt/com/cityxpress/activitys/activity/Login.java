package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
import com.squareup.picasso.Target;

import org.json.JSONObject;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.internet.CheckNetwork;

public class Login extends AppCompatActivity implements View.OnClickListener {


    TextView title, signup;
    EditText phoneno, password;
    TextView signin;
    Context context;
    Typeface face;
    Animation animation_top;
    String accesstoken, phonenumber, name, UserID, CityID;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences.Editor editor;
    LinearLayout linearlayout;
    SharedPreferences pref;
    Dialog progressDoalog;

    private SpringAnimation xAnimation;
    private SpringAnimation yAnimation;
    private float dX;
    private float dY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        init();
        initvalue();
    }


    private void init() {
        linearlayout = findViewById(R.id.login_layout);
        Glide.with(this).load(R.drawable.backimg).asBitmap().placeholder(R.drawable.backimg).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    linearlayout.setBackground(drawable);
                }
            }
        });


        face = Typeface.createFromAsset(getAssets(), "MilkShake.ttf");
        title = findViewById(R.id.login_title_tv);
        phoneno = findViewById(R.id.login_phoneno_et);
        password = findViewById(R.id.login_password_et);
        signin = findViewById(R.id.login_signin_text);
        animation_top = AnimationUtils.loadAnimation(this, R.anim.top);
        signup = findViewById(R.id.login_signup_tv);
        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        title.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        title.setOnTouchListener(touchListener);


    }

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            xAnimation = createSpringAnimation(title, SpringAnimation.X, title.getX(),
                    SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
            yAnimation = createSpringAnimation(title, SpringAnimation.Y, title.getY(),
                    SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        }
    };

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    // capture the difference between view's top left corner and touch point
                    dX = v.getX() - event.getRawX();
                    dY = v.getY() - event.getRawY();
                    // cancel animations
                    xAnimation.cancel();
                    yAnimation.cancel();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //  a different approach would be to change the view's LayoutParams.
                    title.animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
                    break;
                case MotionEvent.ACTION_UP:
                    xAnimation.start();
                    yAnimation.start();
                    break;
            }
            return true;
        }
    };


    public SpringAnimation createSpringAnimation(View view,
                                                 DynamicAnimation.ViewProperty property,
                                                 float finalPosition,
                                                 float stiffness,
                                                 float dampingRatio) {
        SpringAnimation animation = new SpringAnimation(view, property);
        SpringForce springForce = new SpringForce(finalPosition);
        springForce.setStiffness(stiffness);
        springForce.setDampingRatio(dampingRatio);
        animation.setSpring(springForce);
        return animation;
    }


    public SpringAnimation createSpringAnimation(View view,
                                                 DynamicAnimation.ViewProperty property,
                                                 float stiffness,
                                                 float dampingRatio) {
        SpringAnimation animation = new SpringAnimation(view, property);
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(stiffness);
        springForce.setDampingRatio(dampingRatio);
        animation.setSpring(springForce);
        return animation;
    }


    private void initvalue() {
        title.setTypeface(face);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
        title.setAnimation(animation_top);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.login_signin_text) {
            String userid = phoneno.getText().toString().trim();
            String passwordstr = password.getText().toString().trim();
            if (CheckNetwork.isInternetAvailable(context)) //returns true if internet available
            {

                if (userid.length() == 0) {
                    phoneno.setError("Enter  Mobile No");
                } else if (userid.length() != 10) {
                    phoneno.setError("Invalid Mobile No");
                } else if (passwordstr.length() == 0) {
                    password.setError("Enter Password");
                } else {
                    displayprogressbar();
                    login(userid, passwordstr);
                }
            } else {
                Toast.makeText(context, "Internet Connection Not Available", Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.login_signup_tv) {
            startActivity(new Intent(context, Signup.class));
            finish();
        }

    }

    private void displayprogressbar() {
        /*progressDoalog = new ProgressDialog(context);

        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("Please Wait");
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();*/

        progressDoalog = new Dialog(context);

        progressDoalog.setCancelable(false);
        progressDoalog.setContentView(R.layout.dialog_progress);
        progressDoalog.setTitle("Please Wait");
        progressDoalog.show();


    }

    private void login(String userid, String passwordstr) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, MyApplication.URL_Login + userid + "&password=" + passwordstr, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("res", "login responce=" + response);
                    if (response.has("message")) {
                        Log.e("aaa", "msg=" + response.getString("message"));
                        Log.e("aaa", "msg conatain");
                        progressDoalog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(linearlayout, response.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        Log.e("aaa", "msg not conatain");
                        //Log.e("aaa","responce msg="+response.getString("message"));
                        accesstoken = response.getString("access_token");
                        phonenumber = response.getString("phoneNumber");
                        name = response.getString("name");
                        UserID = response.getString("UserID");
                        CityID = response.getString("CityID");

                        Log.e("aaa", "access_token=" + accesstoken);
                        Log.e("aaa", "phonenumber=" + phonenumber);
                        Log.e("aaa", "name=" + name);
                        Log.e("aaa", "UserID=" + UserID);
                        Log.e("aaa", "CityID=" + CityID);
                        if (accesstoken != null) {

                            MyApplication.accesstoken = accesstoken;
                            MyApplication.phonenumber = phonenumber;
                            MyApplication.name = name;
                            MyApplication.UserID = UserID;
                            MyApplication.CityID = CityID;
                            editor = pref.edit();
                            editor.putString("AccessToken", accesstoken);
                            editor.putString("phonenumber", phonenumber);
                            editor.putString("name", name);
                            editor.putString("UserID", UserID);
                            editor.putString("CityID", CityID);

                            editor.commit();
                            progressDoalog.dismiss();
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context, ExpressCity.class));
                            finish();
                            Log.e("aaa", "Login Successfully");
                        }

                        progressDoalog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(linearlayout, "Login successful", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                } catch (Exception e) {
                    progressDoalog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(linearlayout, "Something Went Wrong", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Log.e("eee", e.getStackTrace() + "");
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDoalog.dismiss();

                Snackbar snackbar = Snackbar
                        .make(linearlayout, "Invalid Username and Password", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonobj.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonobj);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
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
