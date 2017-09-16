package com.deepanshi.globetrotter;

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

    public point_of_interest(ArrayList<String> names,ArrayList<String> addr,ArrayList<String> photo) {
        this.names=names;
        this.addr=addr;
        this.votes=votes;
        this.photo=photo;

    }

    @Override
    public point_of_interest.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View linearLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        point_of_interest.CardViewHolder cv = new point_of_interest.CardViewHolder(linearLayout);
        return cv;
    }

    @Override
    public void onBindViewHolder(point_of_interest.CardViewHolder holder, int position) {
        holder.setText(position);

//      holder.telephone.setText(places.get(position).getPhoneNumber().toString());
//        holder.website.setText((CharSequence) places.get(position).getWebsiteUri().toString());
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
//            Picasso.with(img.getContext())
//                    .load(photo.get(position))
//                    .resize(50,50).into(img);

        }
    }
}

