package com.munshig.shaw.munshig_business.Mehboob.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.munshig.shaw.munshig_business.Mehboob.Adapters.KiranalistAdapter;
import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.R;

import java.util.ArrayList;
import java.util.List;

public class KiranaList extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FloatingActionButton add_button;
    KiranalistAdapter mAdapter;
    List<KiranaModel> kiranas;
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

        Log.i( "Inuinu: ", String.valueOf(globalClass.getKiranaList().size()));
        Log.i("onCreate:KiranaList ", String.valueOf(globalClass.getKiranaList().size()));

        kiranas = globalClass.getKiranaList();
        mAdapter = new KiranalistAdapter(kiranas, KiranaList.this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void filter(String text){
        List<KiranaModel> filteredlist = new ArrayList<>();

        for(KiranaModel kirana : kiranas){
            if(kirana.getName().toLowerCase().trim().contains(text.toLowerCase().trim()) || kirana.getSerial().toLowerCase().trim().contains(text.toLowerCase().trim())){
                filteredlist.add(kirana);
            }
        }
        mAdapter.filterList(filteredlist);
    }
}
