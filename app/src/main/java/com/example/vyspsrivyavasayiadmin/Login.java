package com.example.vyspsrivyavasayiadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    EditText user;
    Button button;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(user.getText().toString(),password.getText().toString());
            }
        });

    }

    void validate(String usr,String pas){



        if( usr.equals("admin")  && pas.equals("admin")) {
            Intent intent = new Intent(Login.this,menu.class);
            startActivity(intent);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Access Denied",
                    Toast.LENGTH_SHORT);

            toast.show();
        }
    }
}

