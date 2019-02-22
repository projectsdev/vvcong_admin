package com.example.vyspsrivyavasayiadmin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ImageUpload extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager  mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> mDataset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageupload);

        recyclerView = findViewById(R.id.viewimage);

        mDataset = new ArrayList<>();


        for(int i=0;i<4;i++){
            mDataset.add("Image:" + i);
        }
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MainAdapter(mDataset);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


    }
}
