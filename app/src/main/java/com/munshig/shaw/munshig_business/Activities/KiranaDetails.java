package com.munshig.shaw.munshig_business.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.Models.MehboobModel;
import com.munshig.shaw.munshig_business.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class KiranaDetails extends AppCompatActivity {

    ImageView kirana_pic;
    TextView kirana_name_details, vendor_name_details, address_details, size_details, co_mehboob_details,co_mehboob_text;
    Button barcode_add, speech_add;
    List<MehboobModel> co_mehboob;
    LinearLayout comehboob_layout;
    MapView address_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirana_details);

        //Initializing Layouts
        kirana_pic = findViewById(R.id.kirana_pic);
        kirana_name_details = findViewById(R.id.kirana_name_details);
        vendor_name_details = findViewById(R.id.vendor_name_details);
        address_details = findViewById(R.id.address_details);
        size_details = findViewById(R.id.size_details);
        barcode_add = findViewById(R.id.barcode_add);
        speech_add = findViewById(R.id.speech_add);
        co_mehboob_text = findViewById(R.id.co_mehboob_text);
        comehboob_layout = findViewById(R.id.comehboob_layout);
        address_map = findViewById(R.id.address_map);

        //Getting the Details of the Selected Kirana
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        globalClass.ReadKiranaData(name);
        getdata(globalClass.getKirana());
        globalClass.ReadCoMehboobData(globalClass.getKirana().getName());
        co_mehboob = globalClass.getCo_mehboob();


        //Listeners
        barcode_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KiranaDetails.this, AddBarcode.class);
                startActivity(intent);
            }
        });

        address_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode(globalClass.getKirana().getAddress()));

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
    }

    //Setting data in the layout
    private void getdata(KiranaModel kiranaModel){
        kirana_name_details.append("  "+kiranaModel.getName());
        vendor_name_details.append("  "+kiranaModel.getVendor_name());
        address_details.append("  "+kiranaModel.getAddress());
        size_details.append("  " + kiranaModel.getSize());

        //Creating TextView for each Mehboob's Details
        for(int i=0; i<co_mehboob.size();i++) {

            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(this);

            tv.setLayoutParams(lparams);

            tv.setText(co_mehboob.get(i).getName());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tv.setId(i);
            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/noto_sans_italic.ttf");
            tv.setTypeface(face);
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse(co_mehboob.get(finalI).getMobile_no()));
                    startActivity(intent);
                }
            });

            this.comehboob_layout.addView(tv);
        }
    }
}
