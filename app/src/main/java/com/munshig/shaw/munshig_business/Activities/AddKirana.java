package com.munshig.shaw.munshig_business.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.R;

public class AddKirana extends AppCompatActivity {

    EditText kirana_name_add, vendor_name_add, address_add, city_add;
    Spinner size_add;
    ImageButton capture_image;
    Button add_button;
    ProgressBar progress_upload;
    TextView path_image;
    Uri path,uri;
    static final int PICK_IMAGE_REQUEST = 25;
    StorageReference mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kirana);

        kirana_name_add = findViewById(R.id.kirana_name_add);
        vendor_name_add = findViewById(R.id.vendor_name_add);
        address_add = findViewById(R.id.address_add);
        size_add = findViewById(R.id.size_add);
        capture_image = findViewById(R.id.capture_image);
        progress_upload = findViewById(R.id.progress_upload);
        add_button = findViewById(R.id.add_button);
        path_image = findViewById(R.id.path_image);
        city_add = findViewById(R.id.city_add);

        final GlobalClass globalClass = (GlobalClass) getApplicationContext();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KiranaModel newkirana = new KiranaModel();
                newkirana.setName(kirana_name_add.getText().toString().toLowerCase().trim());
                newkirana.setVendor_name(vendor_name_add.getText().toString().toLowerCase().trim());
                newkirana.setAddress(address_add.getText().toString().toLowerCase().trim());
                newkirana.setSize(size_add.getSelectedItem().toString().toLowerCase().trim());
                newkirana.setCity(city_add.getText().toString().trim().toLowerCase());
                newkirana.setImage_path(path.toString());
                globalClass.AddKirana(newkirana);

                Toast.makeText(AddKirana.this, "Hogaya!", Toast.LENGTH_SHORT).show();
            }
        });

        capture_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            Log.i( "Sold", "Image.....");

            if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null){

                uri = data.getData();
                uploadfile();

            }
            else{
                Toast.makeText(this, "Daya! Kuch toh Gadbad hai!", Toast.LENGTH_SHORT).show();
            }
        }

        private void uploadfile(){
            final ProgressDialog mprogress = new ProgressDialog(AddKirana.this);
            mprogress.setTitle("Uploading");
            mprogress.show();

            Log.i( "Activity", "Image uploading.....");

            final StorageReference filepath = mStorage.child(kirana_name_add.getText().toString().trim()).child("Kirana_pic");

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    path = taskSnapshot.getDownloadUrl();
                    path_image.setText(path.toString());
                    Toast.makeText(AddKirana.this, "Image Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddKirana.this, path + "    Mate", Toast.LENGTH_SHORT).show();
                    mprogress.dismiss();
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(AddKirana.this, "Some Error Occurred, Try Again!", Toast.LENGTH_SHORT).show();
                    progress_upload.setVisibility(View.GONE);
                    mprogress.dismiss();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    //calculating progress percentage
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    //displaying percentage in progress dialog
                    mprogress.setMessage("Uploaded " + ((int) progress) + "%");

                }
            });
        }

        public void verifyform(){
            if(kirana_name_add.getText().toString().isEmpty()){
                kirana_name_add.setError("Kirana Name is required");
                kirana_name_add.requestFocus();
                return;
            }

            if (vendor_name_add.getText().toString().isEmpty()){
                vendor_name_add.setError("Vendor Name of the Kirana is required");
                vendor_name_add.requestFocus();
                return;
            }

            if(address_add.getText().toString().isEmpty()){
                address_add.setError("Address of the Kirana is required");
                address_add.requestFocus();
                return;
            }

            if (size_add.getSelectedItem().toString().isEmpty()){
                size_add.setPrompt("Select Size of the Shop");
                size_add.requestFocus();
                return;
            }

            if(city_add.getText().toString().isEmpty()){
                city_add.setError("Enter the City");
                city_add.requestFocus();
                return;
            }
        }
}

