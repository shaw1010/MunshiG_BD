package com.munshig.shaw.munshig_business.Mehboob.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Manifest;
import com.munshig.shaw.munshig_business.Models.MehboobModel;
import com.munshig.shaw.munshig_business.Mehboob.MyTasks.MyTask1;
import com.munshig.shaw.munshig_business.R;
import com.munshig.shaw.munshig_business.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView title_text;
    ImageView logo_image;
    Button profile_button;
    Button kirana_button;
    List<String> kirana;
    LinearLayout buttonLayout;
    ProgressBar progressBar;
    TextView alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Declaring Layouts and Views
        title_text = findViewById(R.id.title_text);
        logo_image = findViewById(R.id.logo_image);
        profile_button = findViewById(R.id.profile_button);
        kirana_button = findViewById(R.id.kirana_button);
        buttonLayout = findViewById(R.id.buttonLayout);
        progressBar = findViewById(R.id.progressBar);
        alert = findViewById(R.id.alert);
        kirana = new ArrayList<>();


        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        MyTask1 myTask1 = new MyTask1(MainActivity.this, globalClass.getPhonenumber(), globalClass, progressBar, kirana_button, profile_button, alert);
        myTask1.execute();
        kirana_button.setEnabled(false);
        profile_button.setEnabled(false);



        //Listeners
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(MainActivity.this, Profile.class);
                startActivity(intent2);
            }
        });

        kirana_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(MainActivity.this, KiranaList.class);
                startActivity(intent3);
            }
        });

    }

//
}
