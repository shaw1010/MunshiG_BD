package com.munshig.shaw.munshig_business.Mehboob.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.munshig.shaw.munshig_business.AppUtilities.Global.GlobalClass;
import com.munshig.shaw.munshig_business.AppUtilities.Models.SpeechModel;
import com.munshig.shaw.munshig_business.Mehboob.Adapters.SpeechListAdapter;
import com.munshig.shaw.munshig_business.R;

import java.util.List;

public class SpeechList extends AppCompatActivity {
    RecyclerView recyclerSpeech;
    List<SpeechModel> speechList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerSpeech = findViewById(R.id.recyclerSpeech);
        recyclerSpeech.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerSpeech.setLayoutManager(mLayoutManager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { }
        });

        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        speechList = globalClass.getSpeechList();
        Log.i("onCreate:SPEECHLIST ", String.valueOf(speechList.size()));
        mAdapter = new SpeechListAdapter(speechList, this);
        recyclerSpeech.setAdapter(mAdapter);
    }

}
