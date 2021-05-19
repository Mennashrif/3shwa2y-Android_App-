package com.example.a3shwa2y;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class qrandscann_code extends AppCompatActivity {


    User user;
    boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrandscann_code);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("User");
        BottomNavigationView bottomNav = findViewById(R.id.nav_bar1);
        bottomNav.setOnNavigationItemSelectedListener(navListener1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1, new qr_fregmant(user)).commit();

    }
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),main_page1.class);
        intent.putExtra("MyClass",user);
        startActivity(intent);
        finish();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener1 =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch(menuItem.getItemId()){
                        case R.id.QR:
                            check=false;
                            selectedFragment=new qr_fregmant(user);
                            break;
                        case R.id.REPORT:
                            check=false;
                            selectedFragment=new report(user);
                            break;
                        case R.id.SCANNER:
                            check=true;
                            Intent myIntent=new Intent(getApplicationContext(),scanner_code.class);
                            myIntent.putExtra("User",user);
                            startActivity(myIntent);
                            break;



                    }
                    if(!check)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,selectedFragment).commit();
                    return true;

                }
            };
}