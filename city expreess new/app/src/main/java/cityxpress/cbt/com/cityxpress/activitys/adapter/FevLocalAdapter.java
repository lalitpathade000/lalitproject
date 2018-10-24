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
import cityxpress.cbt.com.cityxpress.activitys.activity.RoutesActivity;

public class FevLocalAdapter extends RecyclerView.Adapter<FevLocalAdapter.MyViewHolder> {

    ArrayList<String> fevLocalList = new ArrayList<>();

    public FevLocalAdapter(ArrayList<String> fevLocalList) {
        this.fevLocalList = fevLocalList;

    }

    @NonNull
    @Override
    public FevLocalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_fevlocalrow, parent, false);
        FevLocalAdapter.MyViewHolder ss = new FevLocalAdapter.MyViewHolder(view);
        ss.setIsRecyclable(false);
        return ss;

    }

    @Override
    public void onBindViewHolder(@NonNull FevLocalAdapter.MyViewHolder holder, final int position) {

        String s = "";
        boolean f = false;
        for (int i = 0; i < fevLocalList.get(position).length(); i++) {
            char ch = fevLocalList.get(position).charAt(i);
            if (ch == '(') {
                f = true;
            }
            if (ch == ')') {
                f = false;
            } else if (!f) {

                s = s + ch;
                Log.e("fff", "s=" + s);
            }
            Log.e("fff", "in loop");
        }

        holder.textView.setText(s);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String str = fevLocalList.get(position);
                String s[] = str.split(" To ");
                String ss = s[0].trim();
                String dd = s[1].trim();
                Log.e("local", "source=" + ss);
                Log.e("local", "destination=" + dd);
                Intent i = new Intent(v.getContext(), RoutesActivity.class);
                i.putExtra("From", ss);
                i.putExtra("To", dd);
                MyApplication.fromstr = ss;
                MyApplication.tostr = dd;
                v.getContext().startActivity(i);


            }

        });

    }

    @Override
    public int getItemCount() {
        return fevLocalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rv_fevlocal);

        }
    }
}
