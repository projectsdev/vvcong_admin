package com.example.vyspsrivyavasayiadmin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListMembers extends AppCompatActivity {

    EditText info;
    Spinner spinnerarea;
    DatabaseReference db;
    JSONObject listarea;
    ArrayList<String> areas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_members);

        db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ListArea = db.child("ListArea");
        ListArea.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 listarea = new ObjectToJson().objectToJSONObject(dataSnapshot.getValue());
                Log.d("Areas are:", String.valueOf(listarea));
                Iterator<String> x = listarea.keys();

                while(x.hasNext()){
                    areas.add(x.next());
                }
                Log.d("Areas array:", String.valueOf(areas));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        info = (EditText) findViewById(R.id.search);
        Toast toast = Toast.makeText(getApplicationContext(),"Please Select Area",Toast.LENGTH_SHORT);
        toast.show();


        //Spinner


        spinnerarea = (Spinner) findViewById(R.id.spinner2);
        spinnerarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter adapter2 = new ArrayAdapter<String>(getApplicationContext())
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerarea.setAdapter(adapter2);

    }
}
