package cityxpress.cbt.com.cityxpress.activitys.Fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.adapter.FevLocalAdapter;
import cityxpress.cbt.com.cityxpress.activitys.adapter.FragmentOneAdapter;
import cityxpress.cbt.com.cityxpress.activitys.database.SQLiteDatabse;
import cityxpress.cbt.com.cityxpress.activitys.model.SubCategoryPojo;

public class FragmentLocalOne extends Fragment {

    ArrayList<String> selectedList = new ArrayList<>();
    ArrayList<String> newlist = new ArrayList<>();
    SQLiteDatabse databse;

    public FragmentLocalOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        databse = new SQLiteDatabse(getContext());

        final View view = inflater.inflate(R.layout.fragmentlocalone_layout, container, false);
        LinearLayout fone_fevlocal = view.findViewById(R.id.fone_fevlocal);
        fone_fevlocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_fev_local);
                RecyclerView recyclerView = dialog.findViewById(R.id.dailog_fevlist_rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                ArrayList<String> fevLocalList = new ArrayList<>();

                Cursor c = databse.getFevLocal();
                fevLocalList.clear();
                if (c.moveToFirst()) {
                    do {
                        fevLocalList.add(c.getString(1));
                        Log.e("local", "fev local=" + c.getString(1));


                    } while (c.moveToNext());
                }

                recyclerView.setAdapter(new FevLocalAdapter(fevLocalList));
                dialog.setTitle("Favorite Route");
                dialog.show();

            }
        });


        List<String> centralline = Arrays.asList(getResources().getStringArray(R.array.centralline));
        List<String> westernline = Arrays.asList(getResources().getStringArray(R.array.westernline));
        List<String> harbourline = Arrays.asList(getResources().getStringArray(R.array.harbourline));
        List<String> trainsline = Arrays.asList(getResources().getStringArray(R.array.trainsline));
        List<String> line1 = Arrays.asList(getResources().getStringArray(R.array.fifthline));

        List<String> allline = new ArrayList<>();

        allline.addAll(centralline);
        allline.addAll(westernline);
        allline.addAll(harbourline);
        allline.addAll(trainsline);
        allline.addAll(line1);


// add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(allline);
        allline.clear();
        allline.addAll(hs);


        final ArrayList<String> allstation = new ArrayList<>(allline);
        Collections.sort(allstation);
        Log.e("station", "all station leanth=" + allline.size());

        final ArrayList<String> centrallinestation = new ArrayList<>(centralline);
        final ArrayList<String> westernlinestation = new ArrayList<>(westernline);
        final ArrayList<String> harbourlinestation = new ArrayList<>(harbourline);
        final ArrayList<String> trainslinestation = new ArrayList<>(trainsline);

        selectedList = allstation;

        MyApplication.allStation = allstation;
        final RecyclerView menu_rv = view.findViewById(R.id.fone_routelist_rv);
        menu_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        final FragmentOneAdapter adapter = new FragmentOneAdapter(allstation, databse);
        menu_rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        final EditText search = view.findViewById(R.id.fone_searchview);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                newlist.clear();
                for (String d : selectedList) {
                    if (d.toLowerCase().contains(s.toString().toLowerCase())) {
                        newlist.add(d);
                    }
                }
                adapter.updateList(newlist);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        final TextView allt1 = view.findViewById(R.id.fone_all_t1);
        final TextView allt2 = view.findViewById(R.id.fone_all_t2);
        final TextView wt1 = view.findViewById(R.id.fone_w_t1);
        final TextView wt2 = view.findViewById(R.id.fone_w_t2);
        final TextView ct1 = view.findViewById(R.id.fone_c_t1);
        final TextView ct2 = view.findViewById(R.id.fone_c_t2);
        final TextView ht1 = view.findViewById(R.id.fone_h_t1);
        final TextView ht2 = view.findViewById(R.id.fone_h_t2);
        final TextView tt1 = view.findViewById(R.id.fone_t_t1);
        final TextView tt2 = view.findViewById(R.id.fone_t_t2);
        final LinearLayout all = view.findViewById(R.id.fone_all);
        LinearLayout w = view.findViewById(R.id.fone_w);
        LinearLayout c = view.findViewById(R.id.fone_c);
        LinearLayout h = view.findViewById(R.id.fone_h);
        LinearLayout t = view.findViewById(R.id.fone_t);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allt1.setTextColor(Color.parseColor("#ffffff"));
                allt2.setTextColor(Color.parseColor("#ffffff"));

                wt1.setTextColor(Color.parseColor("#AD8B9E"));
                wt2.setTextColor(Color.parseColor("#AD8B9E"));
                ct1.setTextColor(Color.parseColor("#AD8B9E"));
                ct2.setTextColor(Color.parseColor("#AD8B9E"));
                ht1.setTextColor(Color.parseColor("#AD8B9E"));
                ht2.setTextColor(Color.parseColor("#AD8B9E"));
                tt1.setTextColor(Color.parseColor("#AD8B9E"));
                tt2.setTextColor(Color.parseColor("#AD8B9E"));

                final FragmentOneAdapter adapter = new FragmentOneAdapter(allstation, databse);
                menu_rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                selectedList = allstation;
                final EditText search = view.findViewById(R.id.fone_searchview);
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        newlist.clear();
                        for (String d : selectedList) {
                            if (d.toLowerCase().contains(s.toString().toLowerCase())) {
                                newlist.add(d);
                            }
                        }
                        adapter.updateList(newlist);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wt1.setTextColor(Color.parseColor("#ffffff"));
                wt2.setTextColor(Color.parseColor("#ffffff"));

                allt1.setTextColor(Color.parseColor("#AD8B9E"));
                allt2.setTextColor(Color.parseColor("#AD8B9E"));
                ct1.setTextColor(Color.parseColor("#AD8B9E"));
                ct2.setTextColor(Color.parseColor("#AD8B9E"));
                ht1.setTextColor(Color.parseColor("#AD8B9E"));
                ht2.setTextColor(Color.parseColor("#AD8B9E"));
                tt1.setTextColor(Color.parseColor("#AD8B9E"));
                tt2.setTextColor(Color.parseColor("#AD8B9E"));
                final FragmentOneAdapter adapter = new FragmentOneAdapter(westernlinestation, databse);
                menu_rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                selectedList = westernlinestation;

                final EditText search = view.findViewById(R.id.fone_searchview);
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        newlist.clear();
                        for (String d : selectedList) {
                            if (d.toLowerCase().contains(s.toString().toLowerCase())) {
                                newlist.add(d);
                            }
                        }
                        adapter.updateList(newlist);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ct1.setTextColor(Color.parseColor("#ffffff"));
                ct2.setTextColor(Color.parseColor("#ffffff"));

                wt1.setTextColor(Color.parseColor("#AD8B9E"));
                wt2.setTextColor(Color.parseColor("#AD8B9E"));
                allt1.setTextColor(Color.parseColor("#AD8B9E"));
                allt2.setTextColor(Color.parseColor("#AD8B9E"));
                ht1.setTextColor(Color.parseColor("#AD8B9E"));
                ht2.setTextColor(Color.parseColor("#AD8B9E"));
                tt1.setTextColor(Color.parseColor("#AD8B9E"));
                tt2.setTextColor(Color.parseColor("#AD8B9E"));

                final FragmentOneAdapter adapter = new FragmentOneAdapter(centrallinestation, databse);
                menu_rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                selectedList = centrallinestation;


                final EditText search = view.findViewById(R.id.fone_searchview);
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        newlist.clear();
                        for (String d : selectedList) {
                            if (d.toLowerCase().contains(s.toString().toLowerCase())) {
                                newlist.add(d);
                            }
                        }
                        adapter.updateList(newlist);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ht1.setTextColor(Color.parseColor("#ffffff"));
                ht2.setTextColor(Color.parseColor("#ffffff"));

                ct1.setTextColor(Color.parseColor("#AD8B9E"));
                ct2.setTextColor(Color.parseColor("#AD8B9E"));
                wt1.setTextColor(Color.parseColor("#AD8B9E"));
                wt2.setTextColor(Color.parseColor("#AD8B9E"));
                allt1.setTextColor(Color.parseColor("#AD8B9E"));
                allt2.setTextColor(Color.parseColor("#AD8B9E"));
                tt1.setTextColor(Color.parseColor("#AD8B9E"));
                tt2.setTextColor(Color.parseColor("#AD8B9E"));
                final FragmentOneAdapter adapter = new FragmentOneAdapter(harbourlinestation, databse);
                menu_rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                selectedList = harbourlinestation;

                final EditText search = view.findViewById(R.id.fone_searchview);
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        newlist.clear();
                        for (String d : selectedList) {
                            if (d.toLowerCase().contains(s.toString().toLowerCase())) {
                                newlist.add(d);
                            }
                        }
                        adapter.updateList(newlist);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tt1.setTextColor(Color.parseColor("#ffffff"));
                tt2.setTextColor(Color.parseColor("#ffffff"));

                ht1.setTextColor(Color.parseColor("#AD8B9E"));
                ht2.setTextColor(Color.parseColor("#AD8B9E"));
                ct1.setTextColor(Color.parseColor("#AD8B9E"));
                ct2.setTextColor(Color.parseColor("#AD8B9E"));
                wt1.setTextColor(Color.parseColor("#AD8B9E"));
                wt2.setTextColor(Color.parseColor("#AD8B9E"));
                allt1.setTextColor(Color.parseColor("#AD8B9E"));
                allt2.setTextColor(Color.parseColor("#AD8B9E"));
                final FragmentOneAdapter adapter = new FragmentOneAdapter(trainslinestation, databse);
                menu_rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                selectedList = trainslinestation;

                final EditText search = view.findViewById(R.id.fone_searchview);
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        newlist.clear();
                        for (String d : selectedList) {
                            if (d.toLowerCase().contains(s.toString().toLowerCase())) {
                                newlist.add(d);
                            }
                        }
                        adapter.updateList(newlist);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });
        return view;
    }

}