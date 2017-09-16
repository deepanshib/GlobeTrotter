package com.deepanshi.globetrotter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.height;
import static android.R.attr.width;
import static android.R.id.list;
/**
 * Created by Deepanshi Bansal on 06-08-2017.
 */
public class restrolist extends RecyclerView.Adapter<restrolist.CardViewHolder>  {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> addr = new ArrayList<>();
    ArrayList<String> votes = new ArrayList<>();
    ArrayList<String> photo = new ArrayList<>();

    public restrolist(ArrayList<String> names,ArrayList<String> addr,ArrayList<String> votes,ArrayList<String> photo) {
        this.names=names;
        this.addr=addr;
        this.votes=votes;
        this.photo=photo;

    }

    @Override
    public restrolist.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View linearLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        CardViewHolder cv = new CardViewHolder(linearLayout);
        return cv;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
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
            vote.setText(votes.get(position));
//            Picasso.with(img.getContext())
//                    .load(photo.get(position))
//                    .resize(50,50).into(img);

        }
        }
    }

