package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainRoutModel;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by admin on 8/13/2018.
 */

public class TrainRoutesAdapter extends RecyclerView.Adapter<TrainRoutesAdapter.MyViewHolder> {
    ArrayList<TrainRoutModel> rtlist;
    Boolean flag = false;
    String str = MyApplication.fromstationname.toLowerCase();
    String fromStationName, toStationName, currentStationNAme;
    int c = 0;
    int def = 5;
    int preposion = 0, postposition = 0;


    public TrainRoutesAdapter(ArrayList<TrainRoutModel> rtlist) {
        this.rtlist = rtlist;

    }

    @NonNull
    @Override
    public TrainRoutesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_train_route, parent, false);
        //  Fresco.initialize(AddToCart.this);
        MyViewHolder holder = new MyViewHolder(itemLayoutView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrainRoutesAdapter.MyViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        Log.e("position", "position=" + position);


        TrainRoutModel trm = rtlist.get(position);
        holder.arrtrainname.setText(trm.getTrainATime());
        holder.namroute.setText(trm.getTrainName());
        holder.latitude.setText(trm.getTrainLat());
        holder.longitude.setText(trm.getTrainLon());
        if (position >= MyApplication.fromindex && position <= MyApplication.toindex) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#AD8B9E"));
        }
    }

    @Override
    public int getItemCount() {
        return rtlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView arrtrainname, latitude, longitude, namroute;
        LinearLayout linearLayout;

        public MyViewHolder(View v) {
            super(v);
            arrtrainname = (TextView) v.findViewById(R.id.trainroute_timeroutr); // title
            latitude = (TextView) v.findViewById(R.id.trainroute_lat); // title
            longitude = (TextView) v.findViewById(R.id.trainroute_lon); // title
            namroute = (TextView) v.findViewById(R.id.trainroute_namroute); // title
            linearLayout = (LinearLayout) v.findViewById(R.id.trainroute_layout);
        }
    }
}
