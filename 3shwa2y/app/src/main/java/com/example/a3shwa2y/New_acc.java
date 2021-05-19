package com.example.a3shwa2y;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.WriterException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;

public class New_acc extends AppCompatActivity {


    private CircleImageView profileImage;
    private static final int PICK_IMAGE = 1;
    private static final int Camera_IMAGE = 0;
    Uri imageUri;
    User user=new User();
     @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acc);
        final EditText User_name=(EditText)findViewById(R.id.U_name);
        final EditText User_email=(EditText)findViewById(R.id.U_email);
        final EditText User_password=(EditText)findViewById(R.id.U_password);
        final Button Reg_done=(Button)findViewById(R.id.reg_Done);
        final Button profile_Button=(Button)findViewById(R.id.profile_button);
         profileImage=findViewById(R.id.user_image);
        Reg_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
             if(User_name.length()==0){
                 User_name.setError("This is Not option");
             }
              if(User_email.length()==0){
                 User_email.setError("This is Not option");
             }
              if(User_password.length()==0){
                 User_password.setError("This is Not option");
             }
              else {
                  user.setU_name(User_name.getText().toString());
                  user.setU_Email(User_email.getText().toString());
                  user.setU_password(User_password.getText().toString());
                 //  user = new User(User_name.getText().toString(), User_email.getText().toString(), User_password.getText().toString());
                  DB_Connection db = new DB_Connection(getApplicationContext(), user);
                  db.get_next_id();


              }


            }

        });
        profile_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items={"Camera","Gallery","Cancel"};
                AlertDialog.Builder builder=new AlertDialog.Builder(New_acc.this);
                builder.setTitle("Add Image");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if(items[which].equals("Camera")){
                           Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                          startActivityForResult(intent,Camera_IMAGE);

                       }else if (items[which].equals(("Gallery"))){
                            Intent gallery=new Intent();
                            gallery.setType("image/*");
                            gallery.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(gallery,"Select Picture"), PICK_IMAGE);
                        }else if (items[which].equals("Cancel")){
                               dialog.dismiss();
                        }
                    }
                });

                builder.show();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE && resultCode==RESULT_OK){
            imageUri=data.getData();

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                profileImage.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                user.setU_image(encodedImage);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else if(requestCode== Camera_IMAGE && resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            final Bitmap bitmap=(Bitmap)bundle.get("data");
            profileImage.setImageBitmap(bitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            user.setU_image(encodedImage);
        }

    }
}
