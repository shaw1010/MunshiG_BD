package com.munshig.shaw.munshig_business.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.BaseKeyListener;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.BarcodeModel;
import com.munshig.shaw.munshig_business.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class AddBarcode extends AppCompatActivity{

    Button saveButton,cancelButton;
    private EditText itemName, itemPrice, itemPrice2, scannedBarcode,existingStock,itemStock;
    TextView totalStock;
    FloatingActionButton mSaveButton;
    private String mPhoneNumber;
    BarcodeModel barcodeModel = new BarcodeModel();
    BarcodeModel searchedBarcode = new BarcodeModel();
    LinearLayout hiddenLayout;
    List<Long> prices = new ArrayList<>();
    int temp=0;


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
        hiddenLayout = findViewById(R.id.hiddenLayout);


        //GlobalClass
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        hiddenLayout.setVisibility(View.GONE);


        scannedBarcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                for(int i = 0; i < globalClass.getBarcodeList().size(); i++) {
                    Log.i("onFocusChange: ", globalClass.getBarcodeList().get(i).getName());
                    if(globalClass.getBarcodeList().get(i).getBarcode().equals(scannedBarcode.getText().toString())){
                           searchedBarcode = globalClass.getBarcodeList().get(i);
                           hiddenLayout.setVisibility(View.VISIBLE);
                           itemName.setText(searchedBarcode.getName());
                           prices = searchedBarcode.getPrice();
                           itemPrice.setText(String.valueOf(prices.get(0)));
                           itemPrice2.setText(String.valueOf(prices.get(1)));
                           temp = 1;
                    }
                    else
                        hiddenLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        //Listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyform();
                prices.clear();
                barcodeModel.setBarcode(scannedBarcode.getText().toString().trim());
                barcodeModel.setName(itemName.getText().toString().trim());
                prices.add(Long.parseLong(itemPrice.getText().toString()));
                prices.add(Long.parseLong(itemPrice2.getText().toString()));
                barcodeModel.setPrice(prices);
                if (temp==1){
                    barcodeModel.setModified(true);
                }
                else
                    barcodeModel.setModified(false);
                globalClass.AddBarcode(barcodeModel, globalClass.getKiranaSelected().getName());
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


    public void verifyform(){
        if (scannedBarcode.getText().toString().isEmpty()){
            scannedBarcode.setError("Barcode is Must");
            scannedBarcode.requestFocus();
            return;
        }
        if(itemName.getText().toString().isEmpty()){
            itemName.setError("Enter the Name of the Item!");
            itemName.requestFocus();
            return;
        }
        if(itemPrice.getText().toString().isEmpty()){
            itemPrice.setError("Enter the Price of the Item!");
            itemPrice.requestFocus();
            return;
        }

    }

}
