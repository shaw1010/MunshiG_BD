package com.munshig.shaw.munshig_business.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

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
    List<KiranaModel> kiranas;
    KiranaModel kirana;
    private RecyclerView.LayoutManager mLayoutManager;
    EditText search_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirana_list);

        //Declarations
        add_button = (FloatingActionButton) findViewById(R.id.add_button);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        search_edit = findViewById(R.id.search_edit);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.hasFixedSize();


        //Listeners
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(KiranaList.this, AddKirana.class);
                startActivity(intent4);
            }
        });

        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        GlobalClass globalClass = (GlobalClass) getApplicationContext();

        Log.i( "Inuinu: ", String.valueOf(globalClass.getList_kirana().size()));
        kiranas = globalClass.getList_kirana();
        mAdapter = new KiranalistAdapter(kiranas, KiranaList.this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void filter(String text){
        List<KiranaModel> filteredlist = new ArrayList<>();

        for(KiranaModel kirana : kiranas){
            if(kirana.getName().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(kirana);
            }
        }
        mAdapter.filterList(filteredlist);
    }

}
