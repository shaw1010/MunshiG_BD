package com.munshig.shaw.munshig_business.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.R;

public class AddKirana extends AppCompatActivity {

    EditText kirana_name_add, vendor_name_add, address_add;
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

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KiranaModel newkirana = new KiranaModel();
                newkirana.setName(kirana_name_add.getText().toString().toLowerCase().trim());
                newkirana.setVendor_name(vendor_name_add.getText().toString().toLowerCase().trim());
                newkirana.setAddress(address_add.getText().toString().toLowerCase().trim());
                newkirana.setSize(size_add.getSelectedItem().toString().toLowerCase().trim());

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
}

