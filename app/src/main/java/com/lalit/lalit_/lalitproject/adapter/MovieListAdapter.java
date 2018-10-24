package com.lalit.lalit_.lalitproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lalit.lalit_.lalitproject.Comman.MyApplication;
import com.lalit.lalit_.lalitproject.Model.Movies;
import com.lalit.lalit_.lalitproject.R;
import com.lalit.lalit_.lalitproject.activities.MovieDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {
    ArrayList<Movies> list;
    Context context;

    public MovieListAdapter(Context context, ArrayList<Movies> list) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_movielist, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Movies m = list.get(i);
        myViewHolder.id.setText(m.getId());
        myViewHolder.title.setText(m.getTitle());
        myViewHolder.date.setText(m.getDate());
        if (m.getAdult())
            myViewHolder.adult.setText("(U/A)");
        else
            myViewHolder.adult.setText("(A)");
        try {
            Picasso.with(context).load(MyApplication.URL_MovieList + m.getImg())
                    .into(myViewHolder.img);
        } catch (Exception E) {
            Log.e("errorshow", E.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title, date, adult, id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rv_movielist_moviename);
            date = itemView.findViewById(R.id.rv_movielist_releasedate);
            adult = itemView.findViewById(R.id.rv_movielist_adult);
            img = itemView.findViewById(R.id.rv_movielist_poster);
            id = itemView.findViewById(R.id.rv_movielist_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView id = v.findViewById(R.id.rv_movielist_id);
                    MyApplication.selectedMovie = id.getText().toString();
                    context.startActivity(new Intent(context, MovieDetailsActivity.class));
                }
            });

        }
    }
}
