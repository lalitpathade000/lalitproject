package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.activity.RoutesActivity;

import static cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication.selectedStaionTo;

public class AllStationAdapter  extends RecyclerView.Adapter<AllStationAdapter.MyViewHolder> {

    ArrayList<String> list;
    Context context;

    public AllStationAdapter(Context context, ArrayList<String> allStation) {

        this.context = context;
        list = allStation;
    }

    @NonNull
    @Override
    public AllStationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_destination, parent, false);
        return new AllStationAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AllStationAdapter.MyViewHolder holder, final int position) {

        String ss = list.get(position);
        String s = ss.substring(0, ss.indexOf("("));
        holder.t1.setText(s);


       // MyApplication.selectedStaion=0;selectedStaionTo


      holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MyApplication.selectedStaion==0)
                {
                    MyApplication.selectedStaionFrom=list.get(position);
                    AppCompatActivity a= (AppCompatActivity) context;
                    a.finish();
                }
                else if(MyApplication.selectedStaion==1)
                {
                    MyApplication.selectedStaionTo=list.get(position);
                    AppCompatActivity a= (AppCompatActivity) context;
                    a.finish();
                }



            }
        });

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.selectedStaion==0)
                {
                    MyApplication.selectedStaionFrom=list.get(position);
                    AppCompatActivity a= (AppCompatActivity) context;
                    a.finish();
                }
                else if(MyApplication.selectedStaion==1)
                {
                    MyApplication.selectedStaionTo=list.get(position);
                    AppCompatActivity a= (AppCompatActivity) context;
                    a.finish();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(ArrayList<String> newlist) {
        list = new ArrayList<>(newlist);
        notifyDataSetChanged();


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.rv_destination_text);
            imageView = itemView.findViewById(R.id.rv_rv_destination_img);
        }
    }
}
