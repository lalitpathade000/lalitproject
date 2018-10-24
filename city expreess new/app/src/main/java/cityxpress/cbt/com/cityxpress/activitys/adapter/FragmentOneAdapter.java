package cityxpress.cbt.com.cityxpress.activitys.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cityxpress.cbt.com.cityxpress.R;
import cityxpress.cbt.com.cityxpress.activitys.Data.MyApplication;
import cityxpress.cbt.com.cityxpress.activitys.activity.Destination;
import cityxpress.cbt.com.cityxpress.activitys.database.SQLiteDatabse;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainInfoModel;

public class FragmentOneAdapter extends RecyclerView.Adapter<FragmentOneAdapter.ViewHolder> {


    ArrayList<String> allstation = new ArrayList<>();
    SQLiteDatabse database;
    ArrayList destlist = new ArrayList();
    String sourceStation;

    public FragmentOneAdapter(ArrayList<String> allstation, SQLiteDatabse database) {
        this.allstation = allstation;
        this.database = database;

    }

    @NonNull
    @Override
    public FragmentOneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_localrow, parent, false);
        FragmentOneAdapter.ViewHolder ss = new FragmentOneAdapter.ViewHolder(view);
        ss.setIsRecyclable(false);
        return ss;

    }

    @Override
    public void onBindViewHolder(@NonNull final FragmentOneAdapter.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        Log.e("train", "position=" + position);
        String ss = allstation.get(position);
        String s = ss.substring(0, ss.indexOf("("));
        holder.t1.setText(s);


        if (MyApplication.fevList.contains(ss)) {
            holder.img.setImageResource(R.drawable.ic_star_selected);

        } else {
            holder.img.setImageResource(R.drawable.ic_star_unselected);

        }

       /* holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyApplication.selectedLocalSource = allstation.get(position);
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_local);
                dialog.setTitle(allstation.get(position));

                MyApplication.fromstr = allstation.get(position);

                RecyclerView rv_fevlist = dialog.findViewById(R.id.dailog_rv_fevlist);
                TextView textView = dialog.findViewById(R.id.dailog_dest);


                Animation anim = AnimationUtils.loadAnimation(v.getContext(),
                        R.anim.blink);
                textView.startAnimation(anim);


                rv_fevlist.setLayoutManager(new LinearLayoutManager(v.getContext()));
                rv_fevlist.setAdapter(new DialogFevListAdapter());


                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("local", "click on desination");
                        v.getContext().startActivity(new Intent(v.getContext(), Destination.class));

                    }
                });
                dialog.show();

            }
        });*/


        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyApplication.selectedLocalSource = allstation.get(position);
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_local);
                dialog.setTitle(allstation.get(position));

                MyApplication.fromstr = allstation.get(position);
                sourceStation = allstation.get(position);

                //// getDestination logic
                final ArrayList<String> westernstation = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.westernstation)));
                final ArrayList<String> centralstation1 = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.centralstation1)));
                final ArrayList<String> centralstation2 = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.centralstation2)));
                final ArrayList<String> harbourstation1 = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.harbourstation1)));
                final ArrayList<String> harbourstation2 = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.centralstation2)));
                final ArrayList<String> trainsstation = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.trainsstation)));
                final ArrayList<String> fifthstation = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.fifthstation)));

                String first, last;
                destlist.clear();

                if (westernstation.contains(sourceStation)) {
                    first = westernstation.get(0);
                    last = westernstation.get(westernstation.size() - 1);
                    Log.e("fev", "first=" + first);
                    Log.e("fev", "last=" + last);
                    if (!destlist.contains(first)) {
                        destlist.add(first);
                    }
                    if (!destlist.contains(last)) {
                        destlist.add(last);

                    }
                }

                if (centralstation1.contains(sourceStation)) {
                    first = centralstation1.get(0);
                    last = centralstation1.get(centralstation1.size() - 1);
                    Log.e("fev", "first=" + first);
                    Log.e("fev", "last=" + last);
                    if (!destlist.contains(first)) {
                        destlist.add(first);
                    }
                    if (!destlist.contains(last)) {
                        destlist.add(last);

                    }
                }

                if (centralstation2.contains(sourceStation)) {

                    first = centralstation2.get(0);
                    last = centralstation2.get(centralstation2.size() - 1);
                    Log.e("fev", "first=" + first);
                    Log.e("fev", "last=" + last);
                    if (!destlist.contains(first)) {
                        destlist.add(first);
                    }
                    if (!destlist.contains(last)) {
                        destlist.add(last);

                    }
                }

                if (harbourstation1.contains(sourceStation)) {

                    first = harbourstation1.get(0);
                    last = harbourstation1.get(harbourstation1.size() - 1);
                    Log.e("fev", "first=" + first);
                    Log.e("fev", "last=" + last);
                    if (!destlist.contains(first)) {
                        destlist.add(first);
                    }
                    if (!destlist.contains(last)) {
                        destlist.add(last);

                    }
                }

                if (harbourstation2.contains(sourceStation)) {
                    first = harbourstation2.get(0);
                    last = harbourstation2.get(harbourstation2.size() - 1);
                    Log.e("fev", "first=" + first);
                    Log.e("fev", "last=" + last);
                    if (!destlist.contains(first)) {
                        destlist.add(first);
                    }
                    if (!destlist.contains(last)) {
                        destlist.add(last);

                    }
                }


                if (trainsstation.contains(sourceStation)) {
                    first = trainsstation.get(0);
                    last = trainsstation.get(trainsstation.size() - 1);
                    Log.e("fev", "first=" + first);
                    Log.e("fev", "last=" + last);
                    if (!destlist.contains(first)) {
                        destlist.add(first);
                    }
                    if (!destlist.contains(last)) {
                        destlist.add(last);

                    }
                }


                if (fifthstation.contains(sourceStation)) {
                    first = fifthstation.get(0);
                    last = fifthstation.get(fifthstation.size() - 1);
                    Log.e("fev", "first=" + first);
                    Log.e("fev", "last=" + last);
                    if (!destlist.contains(first)) {
                        destlist.add(first);
                    }
                    if (!destlist.contains(last)) {
                        destlist.add(last);

                    }
                }


                RecyclerView rv_fevlist = dialog.findViewById(R.id.dailog_rv_fevlist);


                TextView textView = dialog.findViewById(R.id.dailog_dest);
                rv_fevlist.setLayoutManager(new LinearLayoutManager(v.getContext()));


                rv_fevlist.setAdapter(new DialogFevListAdapter(destlist));


                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("local", "click on desination");
                        v.getContext().startActivity(new Intent(v.getContext(), Destination.class));

                    }
                });
                dialog.show();


            }
        });


        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.selectedLocalSource = allstation.get(position);
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_local);
                dialog.setTitle(allstation.get(position));

                MyApplication.fromstr = allstation.get(position);
                sourceStation = allstation.get(position);

                //// getDestination logic
                final ArrayList<String> westernstation = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.westernstation)));
                final ArrayList<String> centralstation1 = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.centralstation1)));
                final ArrayList<String> centralstation2 = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.centralstation2)));
                final ArrayList<String> harbourstation1 = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.harbourstation1)));
                final ArrayList<String> harbourstation2 = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.harbourstation2)));
                final ArrayList<String> harbourstation3 = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.harbourstation3)));
                final ArrayList<String> trainsstation = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.trainsstation)));
                final ArrayList<String> fifthstation = new ArrayList<>(Arrays.asList(v.getContext().getResources().getStringArray(R.array.fifthstation)));


                String first, last;
                destlist.clear();

                if (westernstation.contains(sourceStation)) {

                    first = westernstation.get(0);
                    last = westernstation.get(westernstation.size() - 1);

                    Log.e("fev", " westernstation first=" + first);
                    Log.e("fev", "westernstation last=" + last);


                    if (!sourceStation.equalsIgnoreCase(first)) {

                        if (!destlist.contains(first)) {
                            destlist.add(first);
                        }
                    }
                    if (!sourceStation.equalsIgnoreCase(last)) {
                        if (!destlist.contains(last)) {
                            destlist.add(last);

                        }
                    }


                }
                if (centralstation1.contains(sourceStation)) {
                    first = centralstation1.get(0);
                    last = centralstation1.get(centralstation1.size() - 1);
                    Log.e("fev", "centralstation1 first=" + first);
                    Log.e("fev", "centralstation1 last=" + last);

                    if (!sourceStation.equalsIgnoreCase(first)) {

                        if (!destlist.contains(first)) {
                            destlist.add(first);
                        }
                    }
                    if (!sourceStation.equalsIgnoreCase(last)) {
                        if (!destlist.contains(last)) {
                            destlist.add(last);

                        }
                    }
                }
                if (centralstation2.contains(sourceStation)) {

                    first = centralstation2.get(0);
                    last = centralstation2.get(centralstation2.size() - 1);
                    Log.e("fev", "centralstation2 first=" + first);
                    Log.e("fev", "centralstation2 last=" + last);
                    if (!sourceStation.equalsIgnoreCase(first)) {

                        if (!destlist.contains(first)) {
                            destlist.add(first);
                        }
                    }
                    if (!sourceStation.equalsIgnoreCase(last)) {
                        if (!destlist.contains(last)) {
                            destlist.add(last);

                        }
                    }
                }
                if (harbourstation1.contains(sourceStation)) {

                    first = harbourstation1.get(0);
                    last = harbourstation1.get(harbourstation1.size() - 1);
                    Log.e("fev", "harbourstation1 first=" + first);
                    Log.e("fev", "harbourstation1 last=" + last);


                    if (!sourceStation.equalsIgnoreCase(first)) {

                        if (!destlist.contains(first)) {
                            destlist.add(first);
                        }
                    }
                    if (!sourceStation.equalsIgnoreCase(last)) {
                        if (!destlist.contains(last)) {
                            destlist.add(last);

                        }
                    }
                }
                if (harbourstation2.contains(sourceStation)) {
                    first = harbourstation2.get(0);
                    last = harbourstation2.get(harbourstation2.size() - 1);
                    Log.e("fev", "harbourstation2 first=" + first);
                    Log.e("fev", "harbourstation2 last=" + last);

                    if (!sourceStation.equalsIgnoreCase(first)) {

                        if (!destlist.contains(first)) {
                            destlist.add(first);
                        }
                    }
                    if (!sourceStation.equalsIgnoreCase(last)) {
                        if (!destlist.contains(last)) {
                            destlist.add(last);

                        }
                    }
                }
                if (harbourstation3.contains(sourceStation)) {
                    first = harbourstation3.get(0);
                    last = harbourstation3.get(harbourstation3.size() - 1);
                    Log.e("fev", "harbourstation3 first=" + first);
                    Log.e("fev", "harbourstation3 last=" + last);

                    if (!sourceStation.equalsIgnoreCase(first)) {

                        if (!destlist.contains(first)) {
                            destlist.add(first);
                        }
                    }
                    if (!sourceStation.equalsIgnoreCase(last)) {
                        if (!destlist.contains(last)) {
                            destlist.add(last);

                        }
                    }
                }

                if (trainsstation.contains(sourceStation)) {
                    first = trainsstation.get(0);
                    last = trainsstation.get(trainsstation.size() - 1);
                    Log.e("fev", "trainsstation first=" + first);
                    Log.e("fev", "trainsstation last=" + last);

                    if (!sourceStation.equalsIgnoreCase(first)) {

                        if (!destlist.contains(first)) {
                            destlist.add(first);
                        }
                    }
                    if (!sourceStation.equalsIgnoreCase(last)) {
                        if (!destlist.contains(last)) {
                            destlist.add(last);

                        }
                    }
                }


                if (fifthstation.contains(sourceStation)) {
                    first = fifthstation.get(0);
                    last = fifthstation.get(fifthstation.size() - 1);
                    Log.e("fev", "fifthstation first=" + first);
                    Log.e("fev", "fifthstation last=" + last);

                    if (!sourceStation.equalsIgnoreCase(first)) {

                        if (!destlist.contains(first)) {
                            destlist.add(first);
                        }
                    }
                    if (!sourceStation.equalsIgnoreCase(last)) {
                        if (!destlist.contains(last)) {
                            destlist.add(last);

                        }
                    }
                }


                RecyclerView rv_fevlist = dialog.findViewById(R.id.dailog_rv_fevlist);


                TextView textView = dialog.findViewById(R.id.dailog_dest);
                rv_fevlist.setLayoutManager(new LinearLayoutManager(v.getContext()));
                rv_fevlist.setAdapter(new DialogFevListAdapter(destlist));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("local", "click on desination");
                        v.getContext().startActivity(new Intent(v.getContext(), Destination.class));
                    }
                });
                dialog.show();
            }
        });


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.img.setImageResource(R.drawable.ic_star_unselected);
                if (MyApplication.fevList.size() != 0) {

                    if (MyApplication.fevList.contains(allstation.get(position))) {
                        database.deleteFevStation(allstation.get(position));
                        MyApplication.fevList.remove(allstation.get(position));
                        Log.e("local", "fev list=" + MyApplication.fevList);
                        holder.img.setImageResource(R.drawable.ic_star_unselected);
                    } else {

                        database.addFevStation(allstation.get(position));
                        holder.img.setImageResource(R.drawable.ic_star_selected);
                        MyApplication.fevList.add(allstation.get(position));
                        Log.e("local", "fev list=" + MyApplication.fevList);

                    }


                } else {


                    database.addFevStation(allstation.get(position));
                    holder.img.setImageResource(R.drawable.ic_star_selected);
                    MyApplication.fevList.add(allstation.get(position));
                    Log.e("local", "fev list=" + MyApplication.fevList);

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return allstation.size();
    }

    public void updateList(ArrayList<String> newlist) {
        allstation = newlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView t1;
        ImageView img, img2;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.e("train", "ViewHolder");
            t1 = itemView.findViewById(R.id.rv_fragment_one);
            img = itemView.findViewById(R.id.rv_fragment_fev);
            img2 = itemView.findViewById(R.id.rv_fragment_img);
        }
    }
}
