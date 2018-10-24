package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.model.StateModel;
import cityxpress.cbt.com.cityxpress.activitys.model.SubCategory;

/**
 * Created by admin on 17-Aug-18.
 */

public class StateListAdapter extends BaseAdapter{
    Context context;
    ArrayList<StateModel> stateList;
    private LayoutInflater inflater = null;

    public StateListAdapter(Context context, ArrayList<StateModel> stateList) {

        this.context=context;
        this.stateList=stateList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return stateList.size();
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
        StateModel c=stateList.get(position);
        String cat=c.getStateName();
        if (cat != null) {
            title2.setText(cat);
        }
        return vi;
    }
}
