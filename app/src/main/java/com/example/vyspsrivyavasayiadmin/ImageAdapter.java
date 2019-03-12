package com.example.vyspsrivyavasayiadmin;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    Context context;
    ArrayList<Integer> linkList;
    HashMap<Integer,String> urlMap;
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView no_images;

    public ImageAdapter(ArrayList<Integer> linkList,HashMap<Integer,String> urlMap,TextView no_images) {
        this.urlMap = urlMap;
        this.linkList = linkList;
        this.no_images = no_images;

    }


    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.image_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder viewHolder, final int position) {
        Picasso.with(context).load(urlMap.get(linkList.get(position))).into(viewHolder.imageView);
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLink(position,linkList.get(position));
            }
        });

//        viewHolder.imageView.setIm
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        Button remove;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remove = itemView.findViewById(R.id.remove);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    void deleteLink(final int position, int index){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Slider-Images/carousal/"+index);
        reference.setValue(null, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(context,"Image Removed!",Toast.LENGTH_SHORT).show();
//                urlMap.remove(mapIndex);
                linkList.remove(position);
                Log.d("removedMap", String.valueOf(urlMap));
                notifyDataSetChanged();
                if(linkList.isEmpty())
                    no_images.setVisibility(View.VISIBLE);
            }
        });

    }
}
