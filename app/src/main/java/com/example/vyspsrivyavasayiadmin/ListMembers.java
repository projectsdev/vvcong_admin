package com.example.vyspsrivyavasayiadmin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.Spinner;
import android.widget.TextView;
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

    Spinner spinnerarea;
    RecyclerView recyclerView;
    Context context;
    TextView no_record;
    FirebaseDatabase database;
    DatabaseReference reference;
    HashMap<Object, Object> object;
    ArrayList<String> area = new ArrayList<>();
    ArrayAdapter<String> area_adapter;
    String area_selected = null;
    int selected_position = 0;
    ArrayList<UserClass> userList = new ArrayList<>();
    UserListAdapter adapter = null;
    ProgressBar bar;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    CoordinatorLayout coordinatorLayout;
    SearchView searchView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    LinearLayout layout;
    boolean expand = false;
    LayoutAnimationController controller;

    void callAppBarListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    collapsingToolbarLayout.setTitle(" ");
                    layout.setVisibility(View.GONE);
                    expand = false;
                } else if (verticalOffset == 0) {
//                    if (searchView != null && searchView.isIconified())
                    layout.setVisibility(View.VISIBLE);
                    expand = true;
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (expand) {
            appBarLayout.setExpanded(false);
            super.onBackPressed();
        } else {
            appBarLayout.setExpanded(true);
            searchView.setIconified(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dotmenu, menu);
        MenuItem item = menu.findItem(R.id.menusearch);
        searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(Html.fromHtml("<font color=#ffffff>Search by Name/Phone/Email..</font>"));

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_position == 0)
                    Toast.makeText(context, "Select area to list users!", Toast.LENGTH_SHORT).show();
                appBarLayout.setExpanded(false); //collapses
                getSupportActionBar().setIcon(R.drawable.logo);

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                appBarLayout.setExpanded(true);  //expands
                getSupportActionBar().setIcon(null);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null)
                    adapter.getFilter().filter(newText.toLowerCase());
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listmembers);
        context = this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        appBarLayout = findViewById(R.id.app_bar_l);
        coordinatorLayout = findViewById(R.id.co_layout);
        collapsingToolbarLayout = findViewById(R.id.collapse_layout);
        layout = findViewById(R.id.layout);
        spinnerarea = findViewById(R.id.spinner2);
        recyclerView = findViewById(R.id.recyclerView);
        bar = findViewById(R.id.progressBar);
        no_record = findViewById(R.id.no_record);
        controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slideup);
        database = FirebaseDatabase.getInstance();
        callAppBarListener();
        getAreaList();
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
                selected_position = position;
                if (position != 0) {
                    no_record.setText("No record found!");
                    no_record.setVisibility(View.GONE);
                    area_selected = (String) spinnerarea.getSelectedItem();
                    getUsers();
                } else {
                    recyclerView.setVisibility(View.GONE);
                    no_record.setText("Select area to list registered users");
                    no_record.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void getUsers() {
        bar.setVisibility(View.VISIBLE);
        userList = new ArrayList<>();
        reference = database.getReference("Area/" + area_selected);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bar.setVisibility(View.GONE);
                if (dataSnapshot.getValue() != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    no_record.setVisibility(View.GONE);
                    parseData(dataSnapshot);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    no_record.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void parseData(DataSnapshot dataSnapshot){
        HashMap<Object, Object> object = (HashMap<Object, Object>) dataSnapshot.getValue();
        Log.d("snapshot", String.valueOf(object));
        String area_code, status, stat, mobile, name, email, address, timestamp, registered;
        for (Map.Entry<Object, Object> obj : object.entrySet()) {
            area_code = (String) obj.getKey();
            Log.d("area_code", area_code);
            HashMap<Object, Object> object2 = (HashMap<Object, Object>) obj.getValue();
            for (Map.Entry<Object, Object> obj2 : object2.entrySet()) {
                stat = (String) obj2.getKey();
                Log.d("status", stat);
                if (stat.equals("members")) {
                    HashMap<Object, Object> object3 = (HashMap<Object, Object>) obj2.getValue();
                    for (Map.Entry<Object, Object> obj3 : object3.entrySet()) {
                        HashMap<Object, Object> details = (HashMap<Object, Object>) obj3.getValue();
                        mobile = (String) obj3.getKey();
                        name = (String) details.get("name");
                        address = (String) details.get("address");
                        email = (String) details.get("email");
                        status = (String) details.get("status");
                        timestamp = (String) details.get("timestamp");
                        UserClass uclass = new UserClass(name, mobile, email, address, area_selected, area_code, status, timestamp, "not_required");
                        userList.add(uclass);
                    }
                } else {
                    HashMap<Object, Object> details = (HashMap<Object, Object>) obj2.getValue();
                    mobile = (String) details.get("phone");
                    name = (String) details.get("name");
                    address = (String) details.get("address");
                    email = (String) details.get("email");
                    timestamp = (String) details.get("timestamp");
                    registered = String.valueOf(details.get("registered"));
                    status = (String) details.get("status");
                    UserClass uclass = new UserClass(name, mobile, email, address, area_selected, area_code, status, timestamp, registered);
                    userList.add(uclass);
                }

            }
        }
        adapter = new UserListAdapter(context, userList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }

}
