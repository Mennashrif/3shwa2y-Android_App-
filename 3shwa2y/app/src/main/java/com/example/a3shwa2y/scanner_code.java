package com.example.a3shwa2y;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scanner_code extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
         user=(User)intent.getSerializableExtra("User");
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);

    }


    @Override
    public void handleResult(Result result) {
        Toast.makeText(this,result.getText(),Toast.LENGTH_LONG).show();
        transaction_points t=new transaction_points(user,Integer.parseInt(result.getText()));
        t.show(getSupportFragmentManager(),"Give Points");

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
