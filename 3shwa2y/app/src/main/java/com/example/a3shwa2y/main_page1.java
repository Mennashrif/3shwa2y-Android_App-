package com.example.a3shwa2y;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;

public class main_page1 extends AppCompatActivity  {
    // implements NavigationView.OnNavigationItemSelectedListener {

    // @Override
    User pub;
    Intent myintent;
    private QRCodeReaderView qrCodeReaderView;
    boolean check = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page1);
        Intent intent = getIntent();
        pub = (User) intent.getSerializableExtra("MyClass");
        save s=new save();
        pub.setU_image(s.read(getApplicationContext(),"image",null));
        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_fregmant()).commit();

    }
    public void onBackPressed() {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            check=false;
                            selectedFragment = new home_fregmant();
                            break;
                        case R.id.nav_profile:
                            check=false;
                            selectedFragment = new profile_fregmant(pub);
                            break;
                        case R.id.nav_place:
                            check=false;
                            selectedFragment = new places_fregmant();
                            break;
                        case R.id.nav_QR:
                                check = true;
                                myintent = new Intent(getApplicationContext(), qrandscann_code.class);
                                pub.setU_image(null);
                                myintent.putExtra("User", pub);
                                startActivity(myintent);
                                break;

                    }
                    if (!check)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
    }


