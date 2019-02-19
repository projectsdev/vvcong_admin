package com.example.vyspsrivyavasayiadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddArea extends AppCompatActivity {

    String  selected;
    EditText edittxt;
    Button button;
    Spinner spinnerarea , spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area);

        edittxt = (EditText) findViewById(R.id.getarea);
        button = (Button) findViewById(R.id.addarea);


        spinner = (Spinner) findViewById(R.id.unit);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.area, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerarea.setAdapter(adapter1);

        spinnerarea = (Spinner) findViewById(R.id.area);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.area, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerarea.setAdapter(adapter2);
        spinnerarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selected = spinnerarea.getSelectedItem().toString();
               if(selected.equals("Select"))
               {
                   edittxt.setVisibility(View.VISIBLE);
                   button.setVisibility(View.VISIBLE);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                edittxt.setVisibility(View.INVISIBLE);
            }
        });

    }
}
