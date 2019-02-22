package com.example.vyspsrivyavasayiadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;

public class menu extends AppCompatActivity {

    Button button , upload , addarea;
    private DatabaseReference db;
    int n = 0;

    @Override
    public void onBackPressed() {
        if(n==0){
            Toast.makeText(this,"Press again to exit from app!",Toast.LENGTH_SHORT).show();
            n++;
        }
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button = (Button) findViewById(R.id.listmembers);
        upload = (Button) findViewById(R.id.uploadimage);
        addarea = (Button) findViewById(R.id.addarea);
        Log.d("STATUS","IN MENU");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = 0;
                startActivity(new Intent(menu.this, ImageUpload.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = 0;
                Intent myIntent = new Intent(menu.this,
                        ListMembers.class);
                startActivity(myIntent);
            }
        });
        addarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = 0;
                startActivity(new Intent(menu.this,AddArea.class));
            }
        });



    }




}











