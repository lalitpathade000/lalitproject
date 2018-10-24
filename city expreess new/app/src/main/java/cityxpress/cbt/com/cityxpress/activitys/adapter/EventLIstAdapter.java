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
import cityxpress.cbt.com.cityxpress.activitys.activity.EventDetailsActivity;
import cityxpress.cbt.com.cityxpress.activitys.model.EventListModel;

/**
 * Created by admin on 24-Aug-18.
 */

public class EventLIstAdapter extends RecyclerView.Adapter<EventLIstAdapter.MyViewHolder> {

    Context context;
    ArrayList<EventListModel> eventlist;

    public EventLIstAdapter(Context context, ArrayList<EventListModel> eventlist) {
        this.context = context;
        this.eventlist = eventlist;

    }

    @NonNull
    @Override
    public EventLIstAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_eventlist,parent,false);
        return  new EventLIstAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventLIstAdapter.MyViewHolder holder, int position) {

        EventListModel m=eventlist.get(position);
        holder.event_id.setText(m.getEventID());
        holder.event_name.setText(m.getNameOfEvent());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
        int devicewidth = displaymetrics.widthPixels / 2;
        //if you need 4-5-6 anything fix imageview in height
        int deviceheight = displaymetrics.heightPixels / 3;
        holder.event_img.getLayoutParams().width = devicewidth;
        //if you need same height as width you can set devicewidth in holder.image_view.getLayoutParams().height
        holder.event_img.getLayoutParams().height = deviceheight;







        Log.e("aaa","in adapter image=http://www.cityxpress.in"+m.getImageUpload());
        try {
            Picasso.with(context).load("http://www.cityxpress.in" + m.getImageUpload())
                    .placeholder(R.drawable.ic_train)
                    .error(R.drawable.ic_logo)
                    .fit()
                    .into(holder.event_img);
        } catch (Exception E) {
            E.printStackTrace();
            Log.e("errorshow", E.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return eventlist.size();
    }

    public void updateList(ArrayList<EventListModel> newlist) {

            eventlist = newlist;
            notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView event_name,event_id;
        ImageView event_img;
        public MyViewHolder(View itemView) {
            super(itemView);
            event_name=itemView.findViewById(R.id.rv_eventlist_name);
            event_id=itemView.findViewById(R.id.rv_eventlist_id);
            event_img=itemView.findViewById(R.id.rv_eventlist_menuimg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView getid=v.findViewById(R.id.rv_eventlist_id);
                    MyApplication.SelectEvent_Id=getid.getText().toString();
                    context.startActivity(new Intent(context, EventDetailsActivity.class));
                    Log.e("msg","seleced business id="+MyApplication.subcat_bus_id) ;

                }
            });


        }
    }
}
