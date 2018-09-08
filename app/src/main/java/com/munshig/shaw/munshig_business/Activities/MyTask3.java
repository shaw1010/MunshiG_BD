package com.munshig.shaw.munshig_business.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Models.MehboobModel;

public class MyTask3 extends AsyncTask<Void, Void, MehboobModel> {
    private Context context;
    GlobalClass globalClass;
    private String phonenumber;
    private ProgressBar progressBar;

    MyTask3(Context context, String phonenumber, GlobalClass globalClass){
        this.context = context;
        this.phonenumber = phonenumber;
        this.globalClass = globalClass;
    }


    @Override
    protected MehboobModel doInBackground(Void... voids) {

        Log.i( "doInBa?:kiranalist", "Haan haan");
        if (globalClass.getMehboob()==null) {

            globalClass.ReadProfileData(phonenumber);

            while (globalClass.getMehboob()==null)
            {
                if (globalClass.getList_kirana().isEmpty() && globalClass.getMehboob()!=null) {

                    Log.i( "doInBackgroundkirana" , "globalClass.getMehboob()");
                    globalClass.ReadKiranaList(globalClass.getMehboob().getKirana_progress().toString());

                }
            }
        }
        if (globalClass.getList_kirana().isEmpty()) {

            Log.i( "doInBackgroundkirana" , "globalClass.getMehboob()");
            globalClass.ReadKiranaList(globalClass.getMehboob().getKirana_progress().toString());

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
