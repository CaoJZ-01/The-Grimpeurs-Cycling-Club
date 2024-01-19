package com.example.thegrimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.bumptech.glide.*;

public class CyclingClub_Main extends AppCompatActivity {
    ImageView logo;
    int RESULT_LOAD_IMAGE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycling_club_main);

        logo = (ImageView) findViewById(R.id.logo);
        FirebaseStorage storageInstance = FirebaseStorage.getInstance();
        StorageReference storageReference = storageInstance.getReferenceFromUrl("gs://the-grimpeurs-cycling-cl-38857.appspot.com");

        Bundle extras = getIntent().getExtras();
        String userid = extras.getString("userid");
        String imagepath = userid + "_logo.jpg";

        try{
            storageReference.child(imagepath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getApplicationContext()).load(uri.toString()).into(logo);
                }
            });
        }
        catch(Exception e){
            //use default logo
        }

    }

    public void OnClickUpdateProfile(View view){
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");
        Intent intent=new Intent(CyclingClub_Main.this, UpdateProfile.class);
        intent.putExtra("userid",userId);
        startActivity(intent,extra);
    }
    public void OnClickCreateEvent(View view){
        Intent intent=new Intent(CyclingClub_Main.this, SelectEventType.class);
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");
        intent.putExtra("userid",userId);
        startActivity(intent,extra);
    }
    public void OnClickDeleteEvent(View view){
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");
        Intent intent=new Intent(CyclingClub_Main.this, DeleteEvent.class);
        intent.putExtra("userid",userId);
        startActivity(intent,extra);
    }
    public void OnClickUpdateEvent(View view){
        Bundle extra=getIntent().getExtras();
        String userId=extra.getString("userid");
        Intent intent=new Intent(CyclingClub_Main.this, SelectEvent.class);
        intent.putExtra("userid",userId);
        startActivity(intent,extra);
    }

    public void OnClickChooseLogo(View view){
        // Launches photo picker in single-select mode.
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), RESULT_LOAD_IMAGE);
    }

    // onActivityResult() handles callbacks from the photo picker.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImageURI = data.getData();
            logo.setImageURI(selectedImageURI);
            //Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageURI);
            uploadImage(selectedImageURI);
        }
    }

    public void onClickSignOff(View view){
        Intent intent=new Intent(CyclingClub_Main.this, MainActivity.class);
        startActivity(intent);
    }

    void uploadImage(Uri filepath){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://the-grimpeurs-cycling-cl-38857.appspot.com");

        Bundle extras = getIntent().getExtras();
        String uid = extras.getString("userid");
        String imageName = uid + "_logo.jpg";
        StorageReference childRef = storageRef.child(imageName);

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading logo...");
        pd.show();

        UploadTask task = (UploadTask) childRef.putFile(filepath)
                .addOnSuccessListener(
                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                        // Image uploaded successfully
                        // Dismiss dialog
                        pd.dismiss();
                        Toast
                                .makeText(CyclingClub_Main.this,
                                        "Logo Uploaded!!",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Error, Image not uploaded
                        pd.dismiss();
                        Toast.makeText(CyclingClub_Main.this,
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                             .show();
                    }
                });

    }

}