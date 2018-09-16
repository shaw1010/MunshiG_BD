package com.munshig.shaw.munshig_business.Mehboob.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.munshig.shaw.munshig_business.AppUtilities.Global.GlobalClass;
import com.munshig.shaw.munshig_business.AppUtilities.Models.BarcodeModel;
import com.munshig.shaw.munshig_business.R;

import java.util.ArrayList;
import java.util.List;

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
    Boolean a;
    LinearLayout stockLayout;

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
        stockLayout = findViewById(R.id.stockLayout);

        //GlobalClass
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        hiddenLayout.setVisibility(View.GONE);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
//        if(!globalClass.getKiranaSelected().getStock_setting()){
//        stockLayout.setVisibility(View.GONE);}


        scannedBarcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){

                    saveButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                    searchBarcode(scannedBarcode.getText().toString(), globalClass);
                    return true;
                }
                return false;
            }
        });

        //Listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = formValidate(globalClass);
                if(a)
                {
                prices.clear();
                barcodeModel.setBarcode(scannedBarcode.getText().toString().trim());
                barcodeModel.setName(itemName.getText().toString().trim());
                if(globalClass.getSearchBarcode() == null) {
                    barcodeModel.setStock(Integer.parseInt(itemStock.getText().toString()));
                }
                else
                    barcodeModel.setStock(Integer.parseInt(itemStock.getText().toString()) + Integer.parseInt(existingStock.getText().toString()));
                if (itemPrice2.getText().toString().isEmpty()){
                    prices.add(Long.parseLong(itemPrice.getText().toString()));
                }
                else {
                    prices.add(Long.parseLong(itemPrice.getText().toString()));
                    prices.add(Long.parseLong(itemPrice2.getText().toString()));
                }
                barcodeModel.setPrice(prices);

                if (globalClass.getSearchBarcode()!=null && globalClass.getSearchBarcode() != barcodeModel){
                    barcodeModel.setModified(true);
                }
                else
                    barcodeModel.setModified(false);

                globalClass.AddBarcode(barcodeModel, globalClass.getKiranaSelected().getName(), scannedBarcode, itemName, itemPrice, itemPrice2, itemStock, totalStock, existingStock, hiddenLayout);

                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBarcode.this, KiranaDetails.class);
                startActivity(intent);
            }
        });

        itemStock.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (globalClass.getSearchBarcode() == null) {
                        totalStock.setText((itemStock.getText().toString()));
                    } else
                        totalStock.setText(Integer.parseInt(itemStock.getText().toString()) + Integer.parseInt(existingStock.getText().toString()));
                    return true;
                }

            return false;
            }
        });


    }


    public Boolean formValidate(GlobalClass globalClass){
        if (scannedBarcode.getText().toString().isEmpty()){
            scannedBarcode.setError("Barcode is Must");
            scannedBarcode.requestFocus();
            return false;
        }
        if(itemName.getText().toString().isEmpty()){
            itemName.setError("Enter the Name of the Item!");
            itemName.requestFocus();
            return false;
        }
        if(itemPrice.getText().toString().isEmpty()){
            itemPrice.setError("Enter the Price of the Item!");
            itemPrice.requestFocus();
            return false;
        }
        if(globalClass.getKiranaSelected().getStock()){
            if(itemStock.getText().toString().isEmpty()){
                itemStock.setError("Stock Settings is turned On!");
                itemStock.requestFocus();
            }
        }

        return true;
    }

    public void searchBarcode(String barcode, GlobalClass globalClass){
        for(int i = 0; i < globalClass.getBarcodeList().size(); i++) {
            Log.i("onFocusChange: ", globalClass.getBarcodeList().get(i).getName());
            if(globalClass.getBarcodeList().get(i).getBarcode().equals(barcode)){
                searchedBarcode = globalClass.getBarcodeList().get(i);
                hiddenLayout.setVisibility(View.VISIBLE);
                itemName.setText(searchedBarcode.getName());
                prices = searchedBarcode.getPrice();
                itemPrice.setText(String.valueOf(prices.get(0)));
                itemPrice2.setText(String.valueOf(prices.get(1)));
                existingStock.setText((int) globalClass.getSearchBarcode().getStock());
            }
            else
                hiddenLayout.setVisibility(View.VISIBLE);
        }
    }

}
