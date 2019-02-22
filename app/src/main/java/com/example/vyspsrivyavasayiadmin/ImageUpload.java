package com.example.vyspsrivyavasayiadmin;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ImageUpload extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager  mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> mDataset,linkList;
    DatabaseReference db;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageupload);
        context = this;
        mDataset = new ArrayList<>();
        recyclerView = findViewById(R.id.viewimage);
        db = FirebaseDatabase.getInstance().getReference("Slider-Images/carousal");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> dataReceived = (ArrayList<String>) dataSnapshot.getValue();
                Iterator iterator = dataReceived.iterator();
                linkList = new ArrayList<>();
                while (iterator.hasNext()){
                    String value;
                    value = (String) iterator.next();
                    if(value!=null){
                        linkList.add(value);
                    }
                }
                for(int i=0;i<4;i++){
                    mDataset.add("Image:" + i);
                }
                mLayoutManager = new LinearLayoutManager(context);
                recyclerView.setHasFixedSize(true);
                mAdapter = new ImageAdapter(mDataset,linkList);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                Log.d("List", String.valueOf(linkList));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
