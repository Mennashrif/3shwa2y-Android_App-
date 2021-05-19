package com.example.a3shwa2y;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button Creat_new_acc;
    Button Log_in;
    EditText Email;
    EditText Password;
    save s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s=new save();
        if (s.read(getApplicationContext(),"check","false").equals("true")) {
            User user = new User();
            user.setU_Email(s.read(getApplicationContext(),"email",""));
            user.setU_password(s.read(getApplicationContext(),"password",""));
            DB_Connection db = new DB_Connection(getApplicationContext(), user);
            db.search();
        } else {
            setContentView(R.layout.activity_main);
            Creat_new_acc =findViewById(R.id.Creat_new_account);
            Log_in =findViewById(R.id.Log_in);
            Email =findViewById(R.id.Email_edittext);
            Password =findViewById(R.id.Password_edittext);

            Creat_new_acc.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                    // Start NewActivity.class
                    Intent myIntent = new Intent(getApplicationContext(), New_acc.class);
                    startActivity(myIntent);
                }

            });


            Log_in.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                    // Start NewActivity.class
                    User user = new User();
                    user.setU_Email(Email.getText().toString());
                    user.setU_password(Password.getText().toString());
                    DB_Connection db = new DB_Connection(getApplicationContext(), user);
                    db.search();
                    finish();

                }
            });

        }
    }
}
