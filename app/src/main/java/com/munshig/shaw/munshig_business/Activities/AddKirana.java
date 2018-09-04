package com.munshig.shaw.munshig_business.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.munshig.shaw.munshig_business.R;

public class AddKirana extends AppCompatActivity {

    EditText kirana_name_add, vendor_name_add, address_add;
    Spinner size_add;
    ImageButton capture_image;
    Button add_button;
    ProgressBar progress_upload;
    TextView path_image;
    static final int PICK_IMAGE_REQUEST = 25;

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
}
