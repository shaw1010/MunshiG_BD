package com.munshig.shaw.munshig_business.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
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
    KiranaModel kirana_act;
    LinearLayout comehboob_layout;
    ImageButton addressMap;
    ProgressBar progressBar;
    static List<MehboobModel> Co_Mehboob;
    FirebaseFirestore fire, lite;
    List<String> MehboobList = new ArrayList<>();


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
        progressBar = findViewById(R.id.progressBar);
        addressMap = findViewById(R.id.addressMap);


        fire = FirebaseFirestore.getInstance();
        lite = FirebaseFirestore.getInstance();
        Co_Mehboob = new ArrayList<>();


        //Global Class initialisation
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        globalClass.ReadKirana(globalClass.getKiranaList(), globalClass.getKiranaName());


        MehboobList = globalClass.getKiranaSelected().getMehboobs();
        Log.i("onCreate:MehboobList ", String.valueOf(MehboobList.size()));


        //MyTask
        MyTask2 myTask2 = new MyTask2(KiranaDetails.this, globalClass.getKiranaName(), globalClass, comehboob_layout, progressBar);
        myTask2.execute();


        //Onclick Listeners

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
        addressMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode(finalGlobalClass.getKiranaSelected().getAddress() + ",Udaipur"));

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

//        fire.collection("Kiranas").document(globalClass.getKiranaSelected().getName()).collection("Barcodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if(task.isSuccessful()){
//
//                    Log.i( "comehboob-onComplete: ", String.valueOf(task.getResult().getDocuments().size()));
//                    for(DocumentSnapshot doc : task.getResult()){
//
//                        final MehboobModel mehboobModel = new MehboobModel();
//
//                        Log.i( "+++ " , doc.getData().get("mehboob").toString());
//                        mehboobModel.setName(doc.getData().get("mehboob").toString());
//
//                        lite.collection("Mehboobs").whereEqualTo("name", doc.getData().get("mehboob").toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                                if(task.isSuccessful()){
//                                    for(DocumentSnapshot doc1 : task.getResult()){
//
//                                        Log.i( "onCompmehboooobaaa " , doc1.getData().get("mobile_no").toString());
//                                        mehboobModel.setMobile_no(doc1.getData().get("mobile_no").toString());
//                                        Co_Mehboob.add(mehboobModel);
//                                    }
//                                }
//                                Log.i( "onComcomehboobsize", String.valueOf(Co_Mehboob.size()));
//                            }
//                        });
//
//
//                    }
//                }
//            }
//
//        });


    }


    //Setting data in the layout
    private void getdata(KiranaModel kiranaModel, final GlobalClass globalClass, final List<MehboobModel> Co_Mehboob) {
        kirana_name_details.append("  " + kiranaModel.getName().toUpperCase());
        vendor_name_details.append("  " + kiranaModel.getVendor_name());
        address_data.setText(kiranaModel.getAddress());
        size_details.append("  " + kiranaModel.getSize());
//        Picasso.with(this).load((kiranaModel.getImage_path())).fit().into(kirana_pic);

        Log.i("getdata: aslimazza", String.valueOf(Co_Mehboob.size()));
        for ( int i = 0; i < Co_Mehboob.size(); i++) {
            final TextView tv = new TextView(this);
            Log.i("getdata: sizecmmo", String.valueOf(Co_Mehboob.size()));
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            tv.setLayoutParams(lparams);
            tv.setText(Co_Mehboob.get(i).getName().toUpperCase());
            tv.setPadding(5, 5, 5, 5);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            tv.setId(i);

            final int finalI1 = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + Co_Mehboob.get(finalI1).getMobile_no()));
                        if (ActivityCompat.checkSelfPermission(KiranaDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(globalClass, "Call Permission not Given: " + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            final int finalI = i;

            comehboob_layout.addView(tv);
        }
    }


    class MyTask2 extends AsyncTask<Void, Void, List<MehboobModel>> {
        Context context;
        String kirananame;
        GlobalClass globalClass;
        LinearLayout comehboob_layout;
        ProgressBar progressBar;


        MyTask2(Context context, String kirananame, GlobalClass globalClass, LinearLayout comehboob_layout, ProgressBar progressBar) {
            this.context = context;
            this.kirananame = kirananame;
            this.globalClass = globalClass;
            this.comehboob_layout = comehboob_layout;
            this.progressBar = progressBar;
        }


        @Override
        protected List<MehboobModel> doInBackground(Void... voids) {

            for(int i = 0; i < globalClass.getCoMehboobList().size(); i++) {
                for (int j = 0; j < MehboobList.size(); j++) {
                    if (globalClass.getCoMehboobList().get(i).getName().equals(MehboobList.get(j))) {
                        Co_Mehboob.add(globalClass.getCoMehboobList().get(i));
                        Log.i( "onCo-Mehboob*static ", String.valueOf(Co_Mehboob.size()));
                    }
                }
            }
            Log.i( "doInBack:sizeofcoco ", String.valueOf(globalClass.getCoMehboobList()));
            return Co_Mehboob;
        }

        @Override
        protected void onPreExecute() {

            comehboob_layout.setEnabled(false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<MehboobModel> golo) {

            GlobalClass globalClass = (GlobalClass) context.getApplicationContext();
            Log.i("getdata: ", String.valueOf(Co_Mehboob.size()));
            getdata(globalClass.getKiranaSelected(), globalClass, Co_Mehboob);
            super.onPostExecute(golo);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            comehboob_layout.setEnabled(true);
            super.onProgressUpdate(values);
        }
    }
}
