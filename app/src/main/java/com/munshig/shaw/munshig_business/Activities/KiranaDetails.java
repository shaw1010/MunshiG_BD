package com.munshig.shaw.munshig_business.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.Models.MehboobModel;
import com.munshig.shaw.munshig_business.R;

import java.util.ArrayList;
import java.util.List;

public class KiranaDetails extends AppCompatActivity {

    ImageView kirana_pic;
    TextView kirana_name_details, vendor_name_details, address_details, size_details, co_mehboob_details,co_mehboob_text;
    Button barcode_add, speech_add;
    List<String> co_mehboob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirana_details);

        kirana_pic = findViewById(R.id.kirana_pic);
        kirana_name_details = findViewById(R.id.kirana_name_details);
        vendor_name_details = findViewById(R.id.vendor_name_details);
        address_details = findViewById(R.id.address_details);
        size_details = findViewById(R.id.size_details);
        barcode_add = findViewById(R.id.barcode_add);
        speech_add = findViewById(R.id.speech_add);
        co_mehboob_details = findViewById(R.id.co_mehboob_details);
        co_mehboob_text = findViewById(R.id.co_mehboob_text);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        globalClass.ReadKiranaData(name);
        globalClass.getKirana();
        globalClass.ReadCoMehboobData(globalClass.getKirana().getName());
        co_mehboob = globalClass.getCo_mehboob();

        barcode_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KiranaDetails.this, AddBarcode.class);
                startActivity(intent);
            }
        });
    }


    private void getdata(KiranaModel kiranaModel){
        kirana_name_details.append("  "+kiranaModel.getName());
        vendor_name_details.append("  "+kiranaModel.getVendor_name());
        address_details.append("  "+kiranaModel.getAddress());
        size_details.append("  " + kiranaModel.getSize());
        co_mehboob_details.setText(co_mehboob.toString());
    }
}
