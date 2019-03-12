package com.example.vyspsrivyavasayiadmin;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ImageUpload extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager  mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<Integer> linkList;
    DatabaseReference db;
    Context context;
    TextView no_images;
    EditText urlField;
    ProgressBar bar;
    Button upload;
    HashMap<Integer,String> urlMap = new HashMap<>();
    String path = "Slider-Images/carousal/";
    String url = "";
    boolean updateNow = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageupload);
        context = this;
        recyclerView = findViewById(R.id.viewimage);
        no_images = findViewById(R.id.no_images);
        urlField = findViewById(R.id.url_field);
        upload = findViewById(R.id.upload);
        bar = findViewById(R.id.bar);

        getMyImages();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(urlField.getText())){
                    Toast.makeText(context,"Please enter an url!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(updateNow)
                  updateUrl();
             }
        });
    }
    void getMyImages(){

        updateNow = false;

        bar.setVisibility(View.VISIBLE);
        no_images.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        linkList = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference(path);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bar.setVisibility(View.GONE);
                updateNow = true;
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        int key = Integer.parseInt(snapshot.getKey());
                        String url = (String) snapshot.getValue();
                        linkList.add(key);
                        urlMap.put(key, url);
                    }
                /*List<String> dataReceived = (List<String>) dataSnapshot.getValue();
                Iterator iterator = dataReceived.iterator();
                linkList = new ArrayList<>();
                int index = 0;
                int mapIdex = 0;
                while (iterator.hasNext()){
                    String url = (String) iterator.next();
                    if(url!=null){
                        linkList.add(url);
                        urlMap.put(mapIdex,index);
                        mapIdex++;
                    }
                    index++;
                }*/
//                    Log.d("value", String.valueOf(urlMap));
                    mLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setHasFixedSize(true);
                    mAdapter = new ImageAdapter(linkList, urlMap, no_images);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else {
                    no_images.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    void updateUrl(){
        ArrayList<Integer> integers = linkList;
        int index = 0;
        Log.d("newMap", String.valueOf(urlMap));
        //Log.d("newArray", String.valueOf(linkList));
        db = FirebaseDatabase.getInstance().getReference();
        if(integers.isEmpty()){
            db = FirebaseDatabase.getInstance().getReference(path+index);
        }
        else{
            Collections.sort(integers);
            index = integers.get(integers.size()-1)+1;
            db = FirebaseDatabase.getInstance().getReference(path+index);
        }
        url = urlField.getText().toString();
        db.setValue(url, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                urlField.setText("");
                getMyImages();
            }
        });
    }
}
