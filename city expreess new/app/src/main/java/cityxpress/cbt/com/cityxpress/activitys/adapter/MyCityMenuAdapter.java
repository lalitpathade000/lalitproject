package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.activity.MyCityActivity;
import cityxpress.cbt.com.cityxpress.activitys.activity.MyCitySubMenuActivity;
import cityxpress.cbt.com.cityxpress.activitys.model.MyCityMenuModel;

/**
 * Created by admin on 14-Aug-18.
 */

public class MyCityMenuAdapter extends RecyclerView.Adapter<MyCityMenuAdapter.MyViewHolder> {

    ArrayList<MyCityMenuModel> menukist;
    Context context;

    public MyCityMenuAdapter(Context context, ArrayList<MyCityMenuModel> menukist) {
        this.menukist = menukist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyCityMenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_mycitymenu, parent, false);
        int height = parent.getMeasuredHeight() / 10;
        view.setMinimumHeight(height);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCityMenuAdapter.MyViewHolder holder, int position) {
        MyCityMenuModel p = menukist.get(position);


        holder.id.setText(p.getId());
        holder.menuname.setText(p.getMenuname());
        /*Picasso.with(context).load("http://www.cityxpress.in" + menukist.get(position))
                .placeholder(R.drawable.ic_logoold).error(R.drawable.ic_logoold).fit()
                .centerCrop()
                .into(draweeView);*/
    }

    @Override
    public int getItemCount() {
        return menukist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView id, menuname;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.rv_mycitymenu_img_iv);
            id = itemView.findViewById(R.id.rv_mycitymenu_id_tv);
            menuname = itemView.findViewById(R.id.rv_mycitymenu_menuname_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView menuid = v.findViewById(R.id.rv_mycitymenu_id_tv);
                    TextView menutitle = v.findViewById(R.id.rv_mycitymenu_menuname_tv);
                    Log.e("ccc", "data=" + menuname.getText().toString());
                    MyApplication.mycitysubcategoruselectedtitle=menutitle.getText().toString();
                    MyApplication.mycitysubcategoruselectedid = menuid.getText().toString();
                    context.startActivity(new Intent(context, MyCitySubMenuActivity.class));
                  //  ((Activity)context).finish();


                }
            });
        }

    }
}
