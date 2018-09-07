package com.munshig.shaw.munshig_business.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.MehboobModel;

public class MyTask1 extends AsyncTask<Void, Void, MehboobModel> {
    private Context context;
    GlobalClass globalClass;
    private String phonenumber;
    private ProgressBar progressBar;

    MyTask1(Context context, String phonenumber, GlobalClass globalClass){
        this.context = context;
        this.phonenumber = phonenumber;
        this.globalClass = globalClass;
    }


    @Override
    protected MehboobModel doInBackground(Void... voids) {

        if (globalClass.getMehboob().getKirana_progress().isEmpty()) {
            globalClass.ReadProfileData(phonenumber);
        }
        return globalClass.getMehboob();
    }

    @Override
    protected void onPreExecute() {

        progressBar = new ProgressBar(context);
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(MehboobModel mehboobModel) {

        progressBar.setVisibility(View.GONE);

        super.onPostExecute(mehboobModel);
    }
}