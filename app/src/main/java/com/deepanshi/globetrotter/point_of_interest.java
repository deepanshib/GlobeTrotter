package com.deepanshi.globetrotter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Deepanshi Bansal on 27-08-2017.
 */

public class point_of_interest extends RecyclerView.Adapter<point_of_interest.CardViewHolder>{
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> addr = new ArrayList<>();
    ArrayList<String> votes = new ArrayList<>();
    ArrayList<String> photo = new ArrayList<>();
    ArrayList<String> placeid = new ArrayList<>();
Context mactivity;
    public point_of_interest(Context act,ArrayList<String> names, ArrayList<String> addr, ArrayList<String> photo, ArrayList<String> placeid) {
        this.names=names;
        this.addr=addr;
        this.votes=votes;
        this.photo=photo;
this.mactivity=act;
this.placeid=placeid;
    }

    @Override
    public point_of_interest.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View linearLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        point_of_interest.CardViewHolder cv = new point_of_interest.CardViewHolder(linearLayout);
        return cv;
    }

    @Override
    public void onBindViewHolder(point_of_interest.CardViewHolder holder, final int position) {
        holder.setText(position);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mactivity, poiPage.class);
                intent.putExtra("name",names.get(position));
                intent.putExtra("placeid",placeid.get(position));

                mactivity.startActivity(intent);
            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }



    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView name, address,vote;
        ImageView img;
        public CardViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            vote = (TextView) itemView.findViewById(R.id.votes);
            img=itemView.findViewById(R.id.imageView);
        }
        public void setText(int position) {
            name.setText(names.get(position));
            address.setText(addr.get(position));

        }
    }
}

