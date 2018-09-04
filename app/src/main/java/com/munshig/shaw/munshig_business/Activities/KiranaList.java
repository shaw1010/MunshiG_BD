package com.munshig.shaw.munshig_business.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.munshig.shaw.munshig_business.Adapters.KiranalistAdapter;
import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.Models.MehboobModel;
import com.munshig.shaw.munshig_business.R;

import java.util.ArrayList;
import java.util.List;

public class KiranaList extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FloatingActionButton add_button;
    SearchView searchView;
    KiranalistAdapter mAdapter;
    List<KiranaModel> shaw;
    KiranaModel kirana;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirana_list);
        shaw = new ArrayList<>();

        add_button = (FloatingActionButton) findViewById(R.id.add_button);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.hasFixedSize();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(KiranaList.this, AddKirana.class);
                startActivity(intent4);
            }
        });

        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        Log.i( "Inuinu: ", String.valueOf(globalClass.getList_kirana().size()));
        mAdapter = new KiranalistAdapter(globalClass.getList_kirana(), KiranaList.this);
        mRecyclerView.setAdapter(mAdapter);



    }
}
