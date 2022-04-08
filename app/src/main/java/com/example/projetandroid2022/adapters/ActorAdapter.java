package com.example.projetandroid2022.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroid2022.R;
import com.example.projetandroid2022.entities.Actor;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.MyViewHolder>{
    Context context;
    List<Actor> mData;
    ResourceItemClickListener resourceItemClickListener;
    public ActorAdapter(Context context, List<Actor> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ActorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.actor_item,viewGroup,false);
        return new ActorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.TvTitle.setText(mData.get(i).getNom());
        myViewHolder.role.setText(mData.get(i).getPersonnage());

        String url = mData.get(i).getUrl();
        if(url != null) {
            Picasso.get().load(mData.get(i).getUrl()).into(myViewHolder.ImgMovie);
        } else {
            myViewHolder.ImgMovie.setImageResource(R.drawable.default_poster);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TvTitle;
        private final TextView role;

        private final ImageView ImgMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.actor_name);
            ImgMovie = itemView.findViewById(R.id.actor_item_img);
            role= itemView.findViewById(R.id.actor_job);

        }
    }
}
