package com.example.vyspsrivyavasayiadmin;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.Iterator;
import java.util.Map;

public class AddArea extends AppCompatActivity {

    Button new_area, new_unit, add_area, add_unit;
    EditText area_text, unit_text, unit_text1;
    TextView label;
    Spinner area_spinner;
    Animation animI, animO;
    Context context;
    LinearLayout linear1, linear2;
    boolean show_1 = false, show_2 = false;
    DatabaseReference reference;
    FirebaseDatabase database;
    ArrayList<String> area = new ArrayList<>();
    HashMap<Object, Object> object = null;
    ArrayAdapter<String> area_adapter;
    int selected_position = 0;
    String new_area_string, new_unit_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area);
        context = this;
        new_area = findViewById(R.id.add_new_area);
        new_unit = findViewById(R.id.add_new_unit);
        add_area = findViewById(R.id.add_1);
        add_unit = findViewById(R.id.add_2);
        area_text = findViewById(R.id.area_name);
        unit_text = findViewById(R.id.unit_no);
        unit_text1 = findViewById(R.id.unit_2);
        label = findViewById(R.id.area_label);
        area_spinner = findViewById(R.id.area_spinner);
        linear1 = findViewById(R.id.ll1);
        linear2 = findViewById(R.id.ll2);
        animI = AnimationUtils.loadAnimation(context, R.anim.pop_in);
        animO = AnimationUtils.loadAnimation(context, R.anim.pop_out);
        database = FirebaseDatabase.getInstance();

        new_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAreaList();
                if (!show_2) {
                    linear2.setVisibility(View.VISIBLE);
                    linear2.startAnimation(animI);
                    linear1.setVisibility(View.GONE);
                    linear1.startAnimation(animO);
                    show_2 = true;
                    show_1 = false;
                }

            }
        });
        new_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAreaList();
                if (!show_1) {
                    linear1.setVisibility(View.VISIBLE);
                    linear1.startAnimation(animI);
                    linear2.setVisibility(View.GONE);
                    linear2.startAnimation(animO);
                    show_1 = true;
                    show_2 = false;
                }
            }
        });

        add_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_area_string = area_text.getText().toString();
                new_unit_string = unit_text.getText().toString();
                if (TextUtils.isEmpty(new_area_string) || TextUtils.isEmpty(new_unit_string)) {
                    Toast.makeText(context, "Both field shouldn't be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkAndUpdateNewArea();

            }
        });


    }

    void checkAndUpdateNewArea() {
        int flag = 0;
        if (object != null) {
            if (!area.isEmpty()) {
                Iterator<?> it = area.iterator();
                while (it.hasNext()) {
                    String item = (String) it.next();
                    if (item.toLowerCase().equals(new_area_string.toLowerCase())) {
                        new_area_string = item;
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    doUpdateNewUnit();
                }
                else{
                    new_area_string = new_area_string.substring(0,1).toUpperCase() + new_area_string.substring(1);
                    reference = database.getReference("ListArea/"+new_area_string+"/0");
                    reference.setValue(new_unit_string, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            Toast.makeText(context,"New Area updated!",Toast.LENGTH_SHORT).show();
                            getAreaList();
                        }
                    });
                }
            }

        }
    }

    void getAreaList() {
        area = new ArrayList<>();
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
                    area_spinner.setAdapter(area_adapter);
                    setAreaAdapter();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Database read error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setAreaAdapter() {
        area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_area_string = (String) area_spinner.getSelectedItem();
                new_unit_string = unit_text1.getText().toString();
                if (selected_position == 0) {
                    Toast.makeText(context, "Please select an Area to update new Unit!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(new_area_string)) {
                    Toast.makeText(context, "Unit Field can't be empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    doUpdateNewUnit();
            }
        });
    }

    void doUpdateNewUnit() {

        Log.d("new area", String.valueOf(object.get(new_area_string)));
        ArrayList<String> units = (ArrayList<String>) object.get(new_area_string);

        int new_index = units.size();
        Iterator<?> it2 = units.iterator();
        int flag = 0;
        while (it2.hasNext()) {
            String item2 = (String) it2.next();
            if(item2!=null)
            if (item2.toLowerCase().equals(new_unit_string.toLowerCase())) {
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            reference = database.getReference("ListArea/" + new_area_string + '/' + new_index + '/');
            reference.setValue(new_unit_string, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    Toast.makeText(context, "New unit Updated!", Toast.LENGTH_SHORT).show();
                    getAreaList();
                    unit_text1.setText("");
                }
            });
        }
        else
            Toast.makeText(context,new_unit_string + " is already added!",Toast.LENGTH_SHORT).show();



    }
}
