package cityxpress.cbt.com.cityxpress.activitys.adapter;

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

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.activity.MyCitySubSubMenuActivity;
import cityxpress.cbt.com.cityxpress.activitys.model.SubCategoryPojo;

/**
 * Created by admin on 15-Aug-18.
 */

public class MyCitySubMenuAdapter extends RecyclerView.Adapter<MyCitySubMenuAdapter.MyViewHolder>
{
    Context context;
    ArrayList<SubCategoryPojo> sublist,newlist;
    public MyCitySubMenuAdapter(Context context, ArrayList<SubCategoryPojo> sublist) {

        this.context=context;
        this.sublist=sublist;
    }

    @NonNull
    @Override
    public MyCitySubMenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_submenu,parent,false);
        int height = parent.getMeasuredHeight() / 16;

        view.setMinimumHeight(height);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCitySubMenuAdapter.MyViewHolder holder, int position) {

        SubCategoryPojo m=sublist.get(position);
        holder.id.setText(m.getsubmenuid());
        holder.submenuname.setText(m.getsubmenuname());
        Log.e("aaa","img="+"http://www.cityxpress.in" + m.getsubmenuimg());
        try {
            Picasso.with(context).load("http://www.cityxpress.in" + m.getsubmenuimg())
                    .placeholder(R.drawable.ic_train).error(R.drawable.ic_logo)

                    .into(holder.submenuimg);
        } catch (Exception E) {
            Log.e("errorshow", E.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return sublist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView submenuname,id;
        ImageView submenuimg;
        public MyViewHolder(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.rv_mycitysubmenu_menuid);
            submenuname=itemView.findViewById(R.id.rv_mycitysubmenu_menutext);
            submenuimg=itemView.findViewById(R.id.rv_mycitysubmenu_menuimg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView getid=v.findViewById(R.id.rv_mycitysubmenu_menuid);
                    TextView getname=v.findViewById(R.id.rv_mycitysubmenu_menutext);
                    MyApplication.subcat_select_id=getid.getText().toString();
                    MyApplication.subcat_select_name=getname.getText().toString();
                    context.startActivity(new Intent(context, MyCitySubSubMenuActivity.class));

                    Log.e("msg","seleced menu id="+MyApplication.subcat_select_id) ;
                    Log.e("msg","seleced menu name="+MyApplication.subcat_select_name) ;
                }
            });
        }
    }

    public void updateList( ArrayList<SubCategoryPojo> list){
        sublist = list;
        notifyDataSetChanged();
    }

}
