package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainDedatils;

/**
 * Created by admin on 8/13/2018.
 */

public class TrainListAdpater extends BaseAdapter {

    private Activity activity;
    private ArrayList<TrainDedatils> trainlist;
    private static LayoutInflater inflater = null;
    SimpleDateFormat simpleDateFormat;
    Calendar calander;
    ImageView gotomap;
    String str;
    TextView tv;
    LinearLayout layout;

    public TrainListAdpater(Activity a, ArrayList<TrainDedatils> trainlist) {

        activity = a;

        this.trainlist = trainlist;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return trainlist.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {



        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.listview_trainlist, null);
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = (simpleDateFormat.format(calander.getTime()));
        LinearLayout ll = (LinearLayout) vi.findViewById(R.id.linearlayout);

        TrainDedatils td = trainlist.get(position);

        tv = (TextView) vi.findViewById(R.id.trainlist_train_no); // title
        str = td.getTrainNo();
        if (str != null) {
            tv.setText(str);
        }

        tv = (TextView) vi.findViewById(R.id.trainlist_name); // title
        str = td.getTrainName();
        if (str != null) {
            tv.setText(str);
        }
        tv = (TextView) vi.findViewById(R.id.trainlist_departtime); // title
        str = td.getTrainDTime();
        if (str != null) {
            tv.setText(str);
        }

        tv = (TextView) vi.findViewById(R.id.trainlist_arrivale); // title
        str = td.getTrainATime();
        if (str != null) {
            tv.setText(str);
        }

        tv = (TextView) vi.findViewById(R.id.trainlist_trainlat); // title
        str = td.getTrainLat();
        if (str != null) {
            tv.setText(str);
        }
        tv = (TextView) vi.findViewById(R.id.trainlist_trainlon); // title
        str = td.getTrainLon();
        if (str != null) {
            tv.setText(str);
        }
        tv = (TextView) vi.findViewById(R.id.trainlist_trainTostnlat); // title
        str = td.getTrainTOLat();
        if (str != null) {
            tv.setText(str);
        }

        tv = (TextView) vi.findViewById(R.id.trainlist_trainTostnlon); // title
        str = td.getTrainTOLon();
        if (str != null) {
            tv.setText(str);
        }


        tv = (TextView) vi.findViewById(R.id.trainlist_traval); // title
        str = td.getTrainTravel();
        if (str != null) {
            tv.setText(str);
        }
        return vi;


}
}