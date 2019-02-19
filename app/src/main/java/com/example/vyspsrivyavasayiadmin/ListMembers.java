package com.example.vyspsrivyavasayiadmin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMembers extends AppCompatActivity {

    SearchView searchView;
    Spinner spinnerarea;
    Button search;
    RecyclerView recyclerView;
    Context context;
    FirebaseDatabase database;
    DatabaseReference reference;
    HashMap<Object, Object> object;
    ArrayList<String> area = new ArrayList<>();
    ArrayAdapter<String> area_adapter;
    String area_selected = null;
    int selected_position = 0;
    ArrayList<UserClass> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_members);
        context = this;
        searchView =  findViewById(R.id.search);
        spinnerarea = findViewById(R.id.spinner2);
        search = findViewById(R.id.button2);
        recyclerView = findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance();
        getAreaList();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area_selected = (String) spinnerarea.getSelectedItem();
                selected_position = spinnerarea.getSelectedItemPosition();
                if(selected_position == 0){
                    Toast.makeText(context,"Please select an area to search!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    getUsers();
                }
            }
        });


    }
    void getAreaList() {
        area.add("Select");
        reference = database.getReference("ListArea");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    object = (HashMap<Object, Object>) dataSnapshot.getValue();
                    for (Map.Entry<Object, Object> entry : object.entrySet()) {
                        String key = (String) entry.getKey();
                        area.add(key);
                    }
                    area_adapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,area);
                    spinnerarea.setAdapter(area_adapter);
                    /*area_code_adapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,
                            (List<String>) object.get(area.get(0)));
                    area_spinner.setAdapter(area_adapter);
                    area_code_spinner.setAdapter(area_code_adapter);
                    setAreaAdapter();
                    setAreaCodeAdapter();*/
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Database read error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getUsers(){

        reference = database.getReference("Area/"+area_selected);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("snapshot", String.valueOf(dataSnapshot));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
