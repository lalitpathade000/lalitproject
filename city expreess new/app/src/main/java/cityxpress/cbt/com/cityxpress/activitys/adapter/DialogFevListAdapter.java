package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.GSON.Route;
import cityxpress.cbt.com.cityxpress.activitys.activity.RoutesActivity;

public class DialogFevListAdapter extends RecyclerView.Adapter<DialogFevListAdapter.ViewHolder> {


    ArrayList<String> list = new ArrayList<>();


    public DialogFevListAdapter(ArrayList destlist) {
        list = destlist;

    }

    // ArrayList<String> list = new ArrayList<>(MyApplication.fevList);

    @NonNull
    @Override
    public DialogFevListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_dailog_fevlist, parent, false);
        return new DialogFevListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogFevListAdapter.ViewHolder holder, final int position) {

        Log.e("local", "in DialogFevListAdapter list size" + list.size());
        holder.dest.setText(list.get(position));
        holder.dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.tostr = list.get(position);
                v.getContext().startActivity(new Intent(v.getContext(), RoutesActivity.class));

                Log.e("local", "seleced from fev=" + list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dest;

        public ViewHolder(View itemView) {
            super(itemView);
            dest = itemView.findViewById(R.id.rv_dailog_dest);

        }
    }
}
