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

public class restrolist extends RecyclerView.Adapter<restrolist.CardViewHolder>  {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> addr = new ArrayList<>();
    ArrayList<String> votes = new ArrayList<>();
    ArrayList<String> votes2 = new ArrayList<>();
    ArrayList<String> cuisine = new ArrayList<>();
    ArrayList<String> cost = new ArrayList<>();
    ArrayList<String> currency = new ArrayList<>();
    ArrayList<String> photo = new ArrayList<>();

  Context mactivity;
    public restrolist(Context act, ArrayList<String> names, ArrayList<String> addr, ArrayList<String> votes, ArrayList<String> photo,ArrayList<String> votes2,ArrayList<String> cuisine,ArrayList<String> cost,ArrayList<String> currency) {
        this.names=names;
        this.addr=addr;
        this.votes=votes;
        this.photo=photo;
        mactivity=act;
        this.votes2=votes2;
this.cuisine=cuisine;
this.cost=cost;
this.currency=currency;
    }

    @Override
    public restrolist.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View linearLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        CardViewHolder cv = new CardViewHolder(linearLayout);
        return cv;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, final int position) {
        holder.setText(position);

//      holder.telephone.setText(places.get(position).getPhoneNumber().toString());
//        holder.website.setText((CharSequence) places.get(position).getWebsiteUri().toString());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mactivity, individualpage.class);
                intent.putExtra("name",names.get(position));
                intent.putExtra("address",addr.get(position));
                intent.putExtra("rating",votes.get(position));
                intent.putExtra("review",votes2.get(position));
                intent.putExtra("photo",photo.get(position));
                intent.putExtra("cuisine",cuisine.get(position));
                intent.putExtra("cost",cost.get(position));
                intent.putExtra("currency",currency.get(position));

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
            vote.setText(votes.get(position));
            if(photo.get(position).equals("")){
                img.setImageResource( R.drawable.restro);

            }
            else {
                Picasso.with(img.getContext())
                        .load(photo.get(position))
                        .resize(200, 200).into(img);
            }

        }

    }
    }

