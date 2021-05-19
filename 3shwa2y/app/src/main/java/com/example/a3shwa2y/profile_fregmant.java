package com.example.a3shwa2y;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class profile_fregmant extends Fragment {
    private  User user;
    private static final int PICK_IMAGE = 1;
    private static final int Camera_IMAGE = 0;
    private CircleImageView circleImageView;
    private Button logOut;
    private Uri imageUri;


    public profile_fregmant(User user) {
        this.user=user;
    }
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        TextView Text = view.findViewById(R.id.profile_theName);
        Text.setText(user.getU_name());
        Text = view.findViewById(R.id.profile_theMail);
        Text.setText(user.getU_Email());
        Text = view.findViewById(R.id.profile_thePoints);
        Text.setText(String.valueOf(user.getU_points()));
        circleImageView = view.findViewById(R.id.pp);
        logOut=view.findViewById(R.id.logout);
        Button image_butoon = view.findViewById(R.id.image_button);
        if (user.getU_image() != null) {
            try {
                byte[] decoder = Base64.decode(user.getU_image(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decoder, 0, decoder.length);
                circleImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.getMessage();

            }
        }
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save s=new save();
                s.delete(view.getContext(),"check");
                s.delete(view.getContext(),"email");
                s.delete(view.getContext(),"password");
                s.delete(view.getContext(),"image");
                Intent intent=new Intent(view.getContext(),MainActivity.class);
                startActivity(intent);
               getActivity().finish();
            }
        });
        image_butoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Add Image");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, Camera_IMAGE);

                        } else if (items[which].equals(("Gallery"))) {
                            Intent gallery = new Intent();
                            gallery.setType("image/*");
                            gallery.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
                        } else if (items[which].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });

                builder.show();
            }
        });
    }
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode== PICK_IMAGE && resultCode==RESULT_OK){
                imageUri=data.getData();

                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
                    circleImageView.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    if(user.getU_image()!=null){
                        DB_Connection db_connection=new DB_Connection(getContext());
                        db_connection.changeImage(user,encodedImage);
                    }


                    user.setU_image(encodedImage);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            else if(requestCode== Camera_IMAGE && resultCode==RESULT_OK){
                Bundle bundle=data.getExtras();
                final Bitmap bitmap=(Bitmap)bundle.get("data");
                circleImageView.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                user.setU_image(encodedImage);
            }
        }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.profile_fragment,container,false);
    }
}
