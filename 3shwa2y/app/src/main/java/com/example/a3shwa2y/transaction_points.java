package com.example.a3shwa2y;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class transaction_points extends AppCompatDialogFragment {

    private EditText points;
    private User user;
    private int taken_id;

    public transaction_points(User user,int taken_id) {
        this.user=user;
        this.taken_id=taken_id;

    }

    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view=inflater.inflate(R.layout.transaction_points,null);
        points=view.findViewById(R.id.transaction_points);
        builder
                .setView(view).setTitle("Give Points")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    DB_Connection DB=new DB_Connection(view.getContext());
                    DB.transaction(user,taken_id,Integer.parseInt(points.getText().toString()));
                    }
                });
        return builder.create();
    }
}
