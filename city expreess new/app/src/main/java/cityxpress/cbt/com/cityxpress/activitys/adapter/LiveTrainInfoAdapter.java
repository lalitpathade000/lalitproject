package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.GSON.Route;
import cityxpress.cbt.com.cityxpress.activitys.GSON.Station;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainInfoModel;

/**
 * Created by admin on 06-Sep-18.
 */

public class LiveTrainInfoAdapter extends RecyclerView.Adapter<LiveTrainInfoAdapter.ViewHolder> {

    Context context;
    ArrayList<TrainInfoModel> list;

    public LiveTrainInfoAdapter(Context context, ArrayList<TrainInfoModel> list) {
        this.context = context;
        this.list = list;
        Log.e("train", "in contractor=" + list.size());

    }

    @NonNull
    @Override
    public LiveTrainInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_livetrainstatus, parent, false);
        return new LiveTrainInfoAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LiveTrainInfoAdapter.ViewHolder holder, int position) {

        Log.e("train", "position=" + position);

        TrainInfoModel r = list.get(position);
        holder.date.setText(r.getDate());
        holder.time.setText(r.getAtime());
        holder.dep.setText(r.getDtime());
        holder.late.setText(r.getLatemin());
        holder.station.setText(r.getStation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, dep, late, station;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.e("train", "ViewHolder");
            date = itemView.findViewById(R.id.rv_traindetails_date);
            time = itemView.findViewById(R.id.rv_traindetails_time);
            dep = itemView.findViewById(R.id.rv_traindetails_dept);
            late = itemView.findViewById(R.id.rv_traindetails_latemin);
            station = itemView.findViewById(R.id.rv_traindetails_stationname);
        }
    }
}
