package com.munshig.shaw.munshig_business.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.BarcodeModel;
import com.munshig.shaw.munshig_business.R;

public class AddBarcode extends AppCompatActivity{

    Button saveButton,cancelButton;
    private EditText itemName, itemPrice, itemPrice2, scannedBarcode,existingStock,itemStock;
    TextView totalStock;
    FloatingActionButton mSaveButton;
    private String mPhoneNumber;
    BarcodeModel barcodeModel = new BarcodeModel();
    GlobalClass globalClass = (GlobalClass) getApplicationContext();
    Boolean a;


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

    public class MyTask4 extends AsyncTask<Void, Void, BarcodeModel> {
        private Context context;
        GlobalClass globalClass;
        private String barcode, name;

        MyTask4(Context context, String barcode, GlobalClass globalClass, String name){
            this.context = context;
            this.barcode = barcode;
            this.name = name;
            this.globalClass = globalClass;
        }


        @Override
        protected BarcodeModel doInBackground(Void... voids) {

            if (globalClass.getSearch_barcode().getBarcode().isEmpty()) {
                globalClass.SearchBarcode(barcode, name);
            }
            return globalClass.getSearch_barcode();
        }

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(BarcodeModel mehboobModel) {

            itemName.setText(globalClass.getSearch_barcode().getName());
            itemPrice.setText(globalClass.getSearch_barcode().getPrice());
            itemPrice2.setText(globalClass.getSearch_barcode().getPacket());

            super.onPostExecute(mehboobModel);
        }
    }
}
