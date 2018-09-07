package com.munshig.shaw.munshig_business.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.MehboobModel;
import com.munshig.shaw.munshig_business.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView title_text;
    ImageView logo_image;
    Button profile_button;
    Button kirana_button;
    MehboobModel user_mehboob;
    List<String> kirana;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaring Layouts and Views
        title_text = findViewById(R.id.title_text);
        logo_image = findViewById(R.id.logo_image);
        profile_button = findViewById(R.id.profile_button);
        kirana_button = findViewById(R.id.kirana_button);
        kirana = new ArrayList<>();

        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        MyTask3 myTask3 = new MyTask3(MainActivity.this, "+919011752453", globalClass);
        myTask3.execute();

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

}
