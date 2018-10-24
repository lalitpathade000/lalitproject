package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.model.CategoryModel;
import cityxpress.cbt.com.cityxpress.activitys.model.CityServiceModel;

/**
 * Created by admin on 18-Aug-18.
 */

public class CityServiceListAdapter extends BaseAdapter {
    Context context;
    ArrayList<CityServiceModel> cityServiceList;
    private LayoutInflater inflater = null;

    public CityServiceListAdapter(Context context, ArrayList<CityServiceModel> cityServiceList) {

        this.context=context;
        this.cityServiceList=cityServiceList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return cityServiceList.size();
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
        if (convertView == null)
            vi = inflater.inflate(R.layout.sp_category_layout, null);
        TextView title2 = (TextView) vi.findViewById(R.id.sp_category_name); // title
        CityServiceModel c=cityServiceList.get(position);
        String cat=c.getCityServiceName();
        if (cat != null) {
            title2.setText(cat);
        }
        return vi;
    }
}
