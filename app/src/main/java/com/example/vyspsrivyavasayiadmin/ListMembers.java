package com.example.vyspsrivyavasayiadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ListMembers extends AppCompatActivity {

    EditText info;
    Spinner spinnerarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_members);

        info = (EditText) findViewById(R.id.search);
        Toast toast = Toast.makeText(getApplicationContext(),"Please Select Area",Toast.LENGTH_SHORT);
        toast.show();


        //Spinner


        spinnerarea = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.area, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerarea.setAdapter(adapter2);

    }
}
