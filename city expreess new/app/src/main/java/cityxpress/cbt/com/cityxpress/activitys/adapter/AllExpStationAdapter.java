package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;

public class AllExpStationAdapter extends RecyclerView.Adapter<AllExpStationAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> allStation;

    public AllExpStationAdapter(Context context, ArrayList<String> allStation) {
        this.context = context;
        this.allStation = allStation;
    }

    @NonNull
    @Override
    public AllExpStationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_destination, parent, false);
        return new AllExpStationAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AllExpStationAdapter.MyViewHolder holder, final int position) {

        String ss = allStation.get(position);
        String s = ss.substring(0, ss.indexOf("("));

        String upperString = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();

        holder.t1.setText(upperString);


        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApplication.allStationSource == 0) {
                    MyApplication.selectedSorceStation = allStation.get(position);
                    Activity a = (Activity) v.getContext();
                    a.finish();

                }
                if (MyApplication.allStationSource == 1) {
                    MyApplication.selectedDestStation = allStation.get(position);
                    Activity a = (Activity) v.getContext();
                    a.finish();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return allStation.size();
    }

    public void updateList(ArrayList<String> newlist) {
        allStation = new ArrayList<>(newlist);
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
