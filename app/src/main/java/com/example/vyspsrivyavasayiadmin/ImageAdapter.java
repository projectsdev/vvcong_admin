package com.example.vyspsrivyavasayiadmin;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    Context context;
    ArrayList<String> mDataset, linkList;

    public ImageAdapter(ArrayList<String> mDataset, ArrayList<String> linkList) {
        this.mDataset = mDataset;
        this.linkList = linkList;

    }


        public ImageAdapter.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
            context = parent.getContext();
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder (@NonNull ImageAdapter.ViewHolder viewHolder,int position){
            viewHolder.textView.setText(mDataset.get(position));
            Picasso.with(context).load(linkList.get(position)).into(viewHolder.imageView);
//        viewHolder.imageView.setIm
        }

        @Override
        public int getItemCount () {
            return linkList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView;
            ImageView imageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.rowtext);
                imageView = itemView.findViewById(R.id.imageView3);

            }
        }
    }
