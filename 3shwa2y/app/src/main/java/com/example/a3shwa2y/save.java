package com.example.a3shwa2y;

import android.content.Context;
import android.content.SharedPreferences;

public class save {
    public  void save(Context ctx,String name,String value){
        SharedPreferences s=ctx.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor edt= s.edit();
        edt.putString(name,value);
        edt.apply();
    }
    public String read(Context ctx,String name,String defValue){
        SharedPreferences s=ctx.getSharedPreferences("login",Context.MODE_PRIVATE);
        return s.getString(name,defValue);
    }
    public void delete(Context ctx,String name){
        SharedPreferences s=ctx.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor edt=s.edit();
        edt.remove(name);
        edt.apply();
    }

}
