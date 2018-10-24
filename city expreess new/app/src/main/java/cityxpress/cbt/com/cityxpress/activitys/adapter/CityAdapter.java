package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.model.SignupcityModel;

/**
 * Created by admin on 8/8/2018.
 */

public class CityAdapter extends BaseAdapter {
    Context context;
    ArrayList<SignupcityModel> citylist;
    private static LayoutInflater layoutInflater = null;

    public CityAdapter(Context context, ArrayList<SignupcityModel> citylist) {

        this.context = context;
        this.citylist = citylist;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return citylist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = layoutInflater.inflate(R.layout.city_show_list, null);
        }
        SignupcityModel c = citylist.get(position);

        TextView idtv = (TextView) vi.findViewById(R.id.cityadapter_id);
        String id = c.getId();
        idtv.setText(id);

        TextView nametv = (TextView) vi.findViewById(R.id.cityadapter_cityname);
        String name = c.getCity();
        nametv.setText(name);


        return vi;

    }
}
