package com.munshig.shaw.munshig_business.Mehboob.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.munshig.shaw.munshig_business.AppUtilities.Global.GlobalClass;
import com.munshig.shaw.munshig_business.AppUtilities.Models.MehboobModel;
import com.munshig.shaw.munshig_business.R;

public class Profile extends AppCompatActivity {

    TextView profile_text, name_text, contact_text, totalkiranas_text, scans_text, date_text, score_text;
    TextView serial_text;
    MehboobModel mehboob = new MehboobModel();

    @Override
    protected void onStart() {
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        globalClass.getUserMehboob();

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_text = findViewById(R.id.profile_text);
        serial_text = findViewById(R.id.serial_text);
        name_text = findViewById(R.id.name_text);
        contact_text = findViewById(R.id.contact_text);
        totalkiranas_text = findViewById(R.id.totalkiranas_text);
        scans_text = findViewById(R.id.scans_text);
        date_text = findViewById(R.id.date_text);
        score_text = findViewById(R.id.score_text);

        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        getdata(globalClass.getUserMehboob());

    }

    private void getdata(MehboobModel mehboob){
        serial_text.append(": " +mehboob.getSerial().trim());
        name_text.append(": "+mehboob.getName().trim());
        contact_text.append(": "+mehboob.getMobile_no().trim());
        totalkiranas_text.append(": "+mehboob.getTotal_kiranas());
        scans_text.append(": "+mehboob.getTotal_scans());
        date_text.append(": "+mehboob.getJoining_date().trim());
        score_text.append(": "+mehboob.getScore());
    }

    //This Task is for Loading Mehboob's Profile


}
