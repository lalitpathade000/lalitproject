package cityxpress.cbt.com.cityxpress.activitys.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.activity.AllLocalStation;
import cityxpress.cbt.com.cityxpress.activitys.activity.RoutesActivity;

public class FragmentLocalTwo extends Fragment {


    TextView from, to;

    public FragmentLocalTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        from.setText(MyApplication.selectedStaionFrom);
        to.setText(MyApplication.selectedStaionTo);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragmentlocaltwo_layout, container, false);
        from = view.findViewById(R.id.ftwo_from);
        to = view.findViewById(R.id.ftwo_to);
        Button find = view.findViewById(R.id.ftwo_find);


        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AllLocalStation.class));
                MyApplication.selectedStaion = 0;

            }
        });
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AllLocalStation.class));
                MyApplication.selectedStaion = 1;
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strFrom = from.getText().toString();
                String strTo = to.getText().toString();
                if (strFrom.equals(strTo)) {
                    Toast.makeText(getContext(), "Both Station are Same", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        // databse.addHistory(frm+" To "+to);

                        MyApplication.fromstr = strFrom;
                        MyApplication.tostr = strTo;
                        Intent i = new Intent(getContext(), RoutesActivity.class);
                        i.putExtra("From", strFrom);
                        i.putExtra("To", strTo);

                        startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        return view;
    }

}