package com.munshig.shaw.munshig_business.Activities;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.BarcodeModel;
import com.munshig.shaw.munshig_business.R;

public class AddBarcode extends AppCompatActivity {

    Button saveButton,cancelButton;
    private EditText itemName, itemPrice, itemPrice2, scannedBarcode,existingStock,itemStock;
    TextView totalStock;
    FloatingActionButton mSaveButton;
    private String mPhoneNumber;
    BarcodeModel barcodeModel = new BarcodeModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barcode);

        //Declaring Layouts and Variable
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        itemPrice2 = findViewById(R.id.itemPrice2);
        scannedBarcode = findViewById(R.id.scannedBarcode);
        existingStock = findViewById(R.id.scannedBarcode);
        itemStock = findViewById(R.id.itemStock);
        totalStock = findViewById(R.id.totalStock);

        //GlobalClass
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();

        //Listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                barcodeModel.setBarcode(scannedBarcode.getText().toString().trim());
                barcodeModel.setName(itemName.getText().toString().trim());
                barcodeModel.setPrice(itemPrice.getText().toString().trim());
                barcodeModel.setPacket(itemPrice2.getText().toString().trim());

                globalClass.AddBarcode(barcodeModel, globalClass.getKirana().getName());
                Toast.makeText(AddBarcode.this, "Hogaya!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBarcode.this, KiranaDetails.class);
                startActivity(intent);
            }
        });



    }
}
