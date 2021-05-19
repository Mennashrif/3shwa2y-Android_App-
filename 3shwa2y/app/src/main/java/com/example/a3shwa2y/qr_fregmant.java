package com.example.a3shwa2y;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class qr_fregmant extends Fragment {

    private User user;
    public qr_fregmant(User user) {
        this.user=user;
    }
    /*public void onViewCreated(View view, Bundle savedInstanceState) {
        ImageView qrImage=view.findViewById(R.id.Qr_image);
        try{
            byte[] decoder= Base64.decode(imageString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(decoder,0,decoder.length);
            qrImage.setImageBitmap(bitmap);
        }
        catch(Exception e){
            e.getMessage();

        }
    }*/

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.qr_fragment,container,false);
        ImageView qrImage=v.findViewById(R.id.Qr_image);
        try{
            byte[] decoder= Base64.decode(user.getEncodedImage(),Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(decoder,0,decoder.length);
            qrImage.setImageBitmap(bitmap);
        }
        catch(Exception e){
            e.getMessage();

        }

        return v;
    }
}
