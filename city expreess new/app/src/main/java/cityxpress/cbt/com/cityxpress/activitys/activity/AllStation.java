package cityxpress.cbt.com.cityxpress.activitys.activity;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.adapter.AllExpStationAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.AllStationAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.DestinationAdapter;

public class AllStation extends AppCompatActivity {

    RecyclerView recyclerView;
    Context context;
    AllExpStationAdapter adapter;
    LinearLayout linearlayout;
    EditText searchview;
    ArrayList<String> newlist;
    ImageView back;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_station2);
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
        init();
        initvalue();
    }

    private void init() {
        recyclerView = findViewById(R.id.allexpstation_rv);
        searchview = findViewById(R.id.allexpstation_searchview);
        newlist = new ArrayList<>();


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.ab_searchtrain);
        View view = getSupportActionBar().getCustomView();
        back = view.findViewById(R.id.ab_searchtrain_back);
        title = view.findViewById(R.id.ab_searchtrain_title);
        title.setText("All Station");


    }

    private void initvalue() {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Log.e("leanth", "leanth of MyApplication.stationArrayList" + MyApplication.stationArrayList.size());
        adapter = new AllExpStationAdapter(context, MyApplication.stationArrayList);
        recyclerView.setAdapter(adapter);
        linearlayout = findViewById(R.id.back);


        searchview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                newlist.clear();
                for (String d : MyApplication.stationArrayList) {
                    if (d.toLowerCase().contains(s.toString().toLowerCase())) {
                        newlist.add(d);
                    }
                }
                adapter.updateList(newlist);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
