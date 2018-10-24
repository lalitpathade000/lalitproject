package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ActionBar;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.Fragment.FragmentLocalOne;
import cityxpress.cbt.com.cityxpress.activitys.Fragment.FragmentLocalTwo;
import cityxpress.cbt.com.cityxpress.activitys.database.SQLiteDatabse;

public class LocalTrainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    Context context;
    ImageView back;
    TextView title;
    SQLiteDatabse databse;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_train);
        context = this;

        getSupportActionBar().hide();

        init();
        initvalue();
        getfevstation();

        MyApplication.fevId = 1;
    }

    private void getfevstation() {

        MyApplication.fevList.clear();
        Cursor cursor = databse.getFevStation();
        {
            if (cursor.moveToFirst()) {
                do {

                    MyApplication.fevList.add(cursor.getString(1));
                    Log.e("local", "fev station=" + cursor.getString(1));

                } while (cursor.moveToNext());
            }
        }
        Log.e("local", "fev list on load=" + MyApplication.fevList);

    }

    private void init() {

        layout = findViewById(R.id.back);

        Glide.with(this).load(R.drawable.backimg).asBitmap().placeholder(R.drawable.backimg).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout.setBackground(drawable);
                }
            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        databse = new SQLiteDatabse(context);

    }

    private void initvalue() {


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentLocalOne(), "STATION");
        adapter.addFragment(new FragmentLocalTwo(), "A to B");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
