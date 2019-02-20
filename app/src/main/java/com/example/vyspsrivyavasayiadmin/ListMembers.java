package com.example.vyspsrivyavasayiadmin;

import android.content.Context;
import android.hardware.Camera;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    ArrayList<UserClass> obj = new ArrayList<>();

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_members);
        context = this;
        searchView = findViewById(R.id.search);
        spinnerarea = findViewById(R.id.spinner2);
        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance();
        searchView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
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
        progressBar.setVisibility(View.VISIBLE);
        reference = database.getReference("Area/" + area_selected);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.INVISIBLE);
                searchView.setVisibility(View.VISIBLE);
                HashMap<Object, Object> UnitIDs = (HashMap<Object, Object>) dataSnapshot.getValue();
//                Log.d("snapshot", String.valueOf(AreaMap));

                for (Object unitCode : UnitIDs.keySet()){
                    Log.d("KEYS", String.valueOf(unitCode));
                    HashMap<Object,Object> Members = (HashMap<Object, Object>) UnitIDs.get(unitCode);
                    Log.d("KEY VALUE", String.valueOf(Members));
                    for (Object memberType : Members.keySet()){

                        if(memberType.toString().equals("members")){
//                            Log.d("MEMBERS FOUND?", "MEMBERSSSSS");
                            HashMap<Object,Object> memberIDs = (HashMap<Object, Object>) Members.get(memberType);
                            for(Object memberID : memberIDs.keySet()){
                                ArrayList<String> constructorArgument = new ArrayList<>();
                                HashMap<Object,Object> memberDetails = (HashMap<Object, Object>) memberIDs.get(memberID);
                                for(Object detail : memberDetails.keySet()){
//                                    Log.d("DEBUG:", String.valueOf(memberDetails.get(detail)));
                                    if(detail.toString().equals("name")){
                                        constructorArgument.add(String.valueOf(memberDetails.get(detail)));
                                    }
                                    else if(detail.toString().equals("phone")){
                                        constructorArgument.add(String.valueOf(memberDetails.get(detail)));
                                    }
                                    else if(detail.toString().equals("email")){
                                        constructorArgument.add(String.valueOf(memberDetails.get(detail)));
                                    }
                                    else if(detail.toString().equals("status")){
                                        constructorArgument.add(String.valueOf(memberDetails.get(detail)));
                                    }
                                }
                                constructorArgument.add(area_selected);
                                constructorArgument.add(String.valueOf(unitCode));
                                Log.d("Created List", String.valueOf(constructorArgument));
                                UserClass uclass = new UserClass(constructorArgument);
                                obj.add(uclass);
                            }
                        }
                        else {

                            ArrayList<String> constructorArgument = new ArrayList<>();
                            HashMap<Object, Object> memberDetails = (HashMap<Object, Object>) Members.get(memberType);
                            for (Object detail : memberDetails.keySet()) {

//                                Log.d("DEBUG:", String.valueOf(memberDetails.get(detail)));
                                if (detail.toString().equals("name")) {
                                    constructorArgument.add(String.valueOf(memberDetails.get(detail)));
                                } else if (detail.toString().equals("phone")) {
                                    constructorArgument.add(String.valueOf(memberDetails.get(detail)));
                                } else if (detail.toString().equals("email")) {
                                    constructorArgument.add(String.valueOf(memberDetails.get(detail)));
                                } else if (detail.toString().equals("status")) {
                                    constructorArgument.add(String.valueOf(memberDetails.get(detail)));
                                }
                            }
                            constructorArgument.add(area_selected);
                            constructorArgument.add(String.valueOf(unitCode));
                            Log.d("Created List", String.valueOf(constructorArgument));
                            UserClass uclass = new UserClass(constructorArgument);
                            obj.add(uclass);

                        }

                    }



                }

                Log.d("OBJECT", String.valueOf(obj.get(6).status));

//                Log.d("KV001", String.valueOf(object.get("KV001")));
//                JSONObject userDetails = new ObjectToJson().objectToJSONObject(dataSnapshot.getValue());
//                Log.d("MEMBERS:", String.valueOf(userDetails));

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        
    }

}
