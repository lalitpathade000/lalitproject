package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;

public class FlashScreen extends AppCompatActivity {

    Context context;
    TextView text_tv;
    LinearLayout main_layout;
    ImageView logo_imgv;
    Animation animation_top, animation_transint;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        context = this;

        main_layout = findViewById(R.id.flash_screen_main_layout);

        Glide.with(this).load(R.drawable.backimg).asBitmap().placeholder(R.drawable.backimg).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    main_layout.setBackground(drawable);
                }
            }
        });

        text_tv = findViewById(R.id.flash_screen_logo_tv);

        logo_imgv = findViewById(R.id.flash_screen_logo_iv);

        setAnimation();

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        MyApplication.accesstoken = "" + prefs.getString("AccessToken", "");
        MyApplication.phonenumber = "" + prefs.getString("phonenumber", "");
        MyApplication.name = "" + prefs.getString("name", "");
        MyApplication.UserID = "" + prefs.getString("UserID", "");
        MyApplication.CityID = "" + prefs.getString("CityID", "");
        MyApplication.fromstnsave = "" + prefs.getString("frm", "");
        MyApplication.tostnsave = "" + prefs.getString("to", "");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                String user_id = MyApplication.accesstoken;
                if (user_id.isEmpty()) {
                    Intent i = new Intent(context, Login.class);
                    finish();
                    startActivity(i);

                } else {
                    Intent i = new Intent(context, ExpressCity.class);
                    finish();
                    startActivity(i);
                }

            }
        }, 3000);

    }

    private void setAnimation() {
        animation_top = AnimationUtils.loadAnimation(this, R.anim.top);
        animation_transint = AnimationUtils.loadAnimation(this, R.anim.transit);
        text_tv.setAnimation(animation_transint);
        logo_imgv.setAnimation(animation_top);
    }
}
