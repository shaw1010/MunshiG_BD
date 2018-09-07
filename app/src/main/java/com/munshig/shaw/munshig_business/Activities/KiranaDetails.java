package com.munshig.shaw.munshig_business.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.Models.MehboobModel;
import com.munshig.shaw.munshig_business.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class KiranaDetails extends AppCompatActivity {

    ImageView kirana_pic;
    TextView kirana_name_details, vendor_name_details, address_details, size_details, co_mehboob_details, co_mehboob_text, address_data;
    Button barcode_add, speech_add;
    List<MehboobModel> co_mehboob;
    KiranaModel kirana_act;
    LinearLayout comehboob_layout;


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
        address_data = findViewById(R.id.address_data);
        comehboob_layout = findViewById(R.id.comehboob_layout);


        //Getting the Details of the Selected Kirana via Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");


        //Global Class initialisation
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        globalClass = (GlobalClass) getApplicationContext();

        //Detting selected Kiranas Data
        for (int i = 0; i < globalClass.getList_kirana().size(); i++) {
            if (globalClass.getList_kirana().get(i).getName().equals(name.toLowerCase()))
                globalClass.setKirana(globalClass.getList_kirana().get(i));
        }
        kirana_act = globalClass.getKirana();
        String url = globalClass.getKirana().getImage_path();


        //Displaying the Image
        Picasso.get().load(url).fit().centerCrop().into(kirana_pic);
        Log.i("onCreategetkirana: ", kirana_act.getName().toString());


        //Initializing MyTask2 : Fetching list of Kirana in Progress
        MyTask2 myTask2 = new MyTask2(KiranaDetails.this, kirana_act.getName(), globalClass, comehboob_layout);
        myTask2.execute();
        getdata(kirana_act);

        //Listeners

        //1. Go to Add Barcode Activity
        barcode_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KiranaDetails.this, AddBarcode.class);
                startActivity(intent);
            }
        });

        //2. Open Google maps for Directions to the address
        final GlobalClass finalGlobalClass = globalClass;
        address_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode(finalGlobalClass.getKirana().getAddress() + ",Udaipur"));

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

//        comehboob_layout.getFocusedChild().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(finalGlobalClass, "jo hua accha hua", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //Setting data in the layout
    private void getdata(KiranaModel kiranaModel)
    {
        kirana_name_details.append("  " + kiranaModel.getName().toUpperCase());
        vendor_name_details.append("  " + kiranaModel.getVendor_name());
        address_data.setText(kiranaModel.getAddress());
        size_details.append("  " + kiranaModel.getSize());

    }


    static class MyTask2 extends AsyncTask<Void, Void, List<MehboobModel>> {
        private Context context;
        String kirananame;
        GlobalClass globalClass;
        LinearLayout comehboob_layout;


        MyTask2(Context context, String kirananame, GlobalClass globalClass, LinearLayout comehboob_layout) {
            this.context = context;
            this.kirananame = kirananame;
            this.globalClass = globalClass;
            this.comehboob_layout = comehboob_layout;
        }


        @Override
        protected List<MehboobModel> doInBackground(Void... voids) {

            if (globalClass.getCo_mehboob().isEmpty()) {
                globalClass.ReadCoMehboobData(kirananame);
            }
            Log.i( "doInBackground:sizeofcoco ", String.valueOf(globalClass.getCo_mehboob().size()));
            return globalClass.getCo_mehboob();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<MehboobModel> aVoid) {

            Log.i("getdata: ", String.valueOf(globalClass.getCo_mehboob().size()));

            TextView[] tv = new TextView[globalClass.getCo_mehboob().size()];
            for (int i = 0; i < globalClass.getCo_mehboob().size(); i++) {

                Log.i("getdata: sizecmmo", String.valueOf(globalClass.getCo_mehboob().size()));
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                comehboob_layout.setOrientation(LinearLayout.HORIZONTAL);
                comehboob_layout.setLayoutParams(lparams1);
                tv[i].setLayoutParams(lparams);
                tv[i].setText(globalClass.getCo_mehboob().get(i).getName());
                tv[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tv[i].setId(i);

                final int finalI = i;


                comehboob_layout.addView(tv[i]);
            }

            super.onPostExecute(aVoid);
        }
    }
}
