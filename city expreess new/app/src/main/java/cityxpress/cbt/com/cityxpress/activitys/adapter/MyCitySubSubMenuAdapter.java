package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.activity.BussinessDetailsActivity;
import cityxpress.cbt.com.cityxpress.activitys.model.BussinessListModel;

/**
 * Created by admin on 16-Aug-18.
 */

public class MyCitySubSubMenuAdapter extends RecyclerView.Adapter<MyCitySubSubMenuAdapter.MyViewHolder>
{
    Context context;
    ArrayList<BussinessListModel> businesslist;

    public MyCitySubSubMenuAdapter(Context context, ArrayList<BussinessListModel> businesslist) {

        this.context=context;
        this.businesslist=businesslist;
    }

    @NonNull
    @Override
    public MyCitySubSubMenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_subsubmenu,parent,false);
        //int height = parent.getMeasuredHeight() / 8;
        //view.setMinimumHeight(height);
        return new MyCitySubSubMenuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCitySubSubMenuAdapter.MyViewHolder holder, int position) {



/*
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
        int devicewidth = displaymetrics.widthPixels / 3;

        //if you need 4-5-6 anything fix imageview in height
        float deviceheight = ((((displaymetrics.widthPixels-(10*(context.getResources().getDisplayMetrics().density)))/2)*4)/3.25f);



        //holder.contImage.setImageResource(UrlUtils.IMAGE_BASE_URL+visibleObject.get(position).getContinentImage());
        Glide.with(context).load(UrlUtils.IMAGE_BASE_URL+visibleObject.get(position).getContinentImage()).into(holder.contImage);
        holder.contName.setText(visibleObject.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SelectCountryActivity.class);
                intent.putExtra("ContinentId",""+visibleObject.get(position).getId());
                context.startActivity(intent);
                sharedPreferences.edit().putString(SharedPreferConst.USER_SEL_CONTINENT,""+visibleObject.get(position).getName()).apply();
            }
        });
        // holder.cardView.getLayoutParams().width = devicewidth;

        //if you need same height as width you can set devicewidth in holder.image_view.getLayoutParams().height
        Log.e(TAG, "onBindViewHolder: "+ deviceheight+","+displaymetrics.widthPixels);
        holder.cardView.getLayoutParams().height = (int) deviceheight;


       */


        BussinessListModel m=businesslist.get(position);
        holder.bussiness_id.setText(m.getBusinessRegistrationID());
        holder.bussiness_name.setText(m.getBusinessName());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
        int devicewidth = displaymetrics.widthPixels / 2;
        //if you need 4-5-6 anything fix imageview in height
        int deviceheight = displaymetrics.heightPixels / 3;
        holder.bussiness_img.getLayoutParams().width = devicewidth;
        //if you need same height as width you can set devicewidth in holder.image_view.getLayoutParams().height
        holder.bussiness_img.getLayoutParams().height = deviceheight;


        try {
            Picasso.with(context).load("http://www.cityxpress.in" + m.getBusinessImage())
                    .placeholder(R.drawable.ic_train)
                    .error(R.drawable.ic_logo)
                    .fit()
                    .into(holder.bussiness_img);
        } catch (Exception E) {
            Log.e("errorshow", E.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return businesslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView bussiness_name,bussiness_id;
        ImageView bussiness_img;
        public MyViewHolder(View itemView) {
            super(itemView);
            bussiness_id=itemView.findViewById(R.id.rv_mycitysubsubmenu_id);
            bussiness_name=itemView.findViewById(R.id.rv_mycitysubsubmenu_businessname);
            bussiness_img=itemView.findViewById(R.id.rv_mycitysubsubmenu_menuimg);
           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView getid=v.findViewById(R.id.rv_mycitysubsubmenu_id);
                    TextView getname=v.findViewById(R.id.rv_mycitysubsubmenu_businessname);
                    MyApplication.bussinessDetails_Id=getid.getText().toString();
                    context.startActivity(new Intent(context, BussinessDetailsActivity.class));

                    Log.e("msg","seleced business id="+MyApplication.subcat_bus_id) ;
                    Log.e("msg","seleced business name="+MyApplication.subcat_bus_name) ;
                }
            });
        }
    }

    public void updateList( ArrayList<BussinessListModel> list){
        businesslist = list;
        notifyDataSetChanged();
    }
}
