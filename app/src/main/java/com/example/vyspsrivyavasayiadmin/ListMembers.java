package com.example.vyspsrivyavasayiadmin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_members);
        context = this;
        searchView = findViewById(R.id.search);
        spinnerarea = findViewById(R.id.spinner2);

        recyclerView = findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance();
        getAreaList();
        searchView.setQueryHint("Name/Phone/Unit");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
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
                    area_adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, area);
                    spinnerarea.setAdapter(area_adapter);
                    setAreaAdapter();
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

    void setAreaAdapter() {
        spinnerarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    area_selected = (String) spinnerarea.getSelectedItem();
                    getUsers();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void getUsers() {
        reference = database.getReference("Area/" + area_selected);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<Object, Object> object = (HashMap<Object, Object>) dataSnapshot.getValue();
                Log.d("snapshot", String.valueOf(object));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
