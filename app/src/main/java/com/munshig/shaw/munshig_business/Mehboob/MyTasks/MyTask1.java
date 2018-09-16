package com.munshig.shaw.munshig_business.Mehboob.MyTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.munshig.shaw.munshig_business.AppUtilities.Global.GlobalClass;
import com.munshig.shaw.munshig_business.AppUtilities.Models.KiranaModel;

import java.util.List;

public class MyTask1 extends AsyncTask<Void, Void, List<KiranaModel>> {
    private Context context;
    GlobalClass globalClass;
    private String phonenumber;
    ProgressBar progressBar;
    Button kirana_button;
    Button profile_button;
    TextView alert;

    public MyTask1(Context context, String phonenumber, GlobalClass globalClass, ProgressBar progressBar, Button kirana_button, Button profile_button, TextView alert){
        this.context = context;
        this.phonenumber = phonenumber;
        this.globalClass = globalClass;
        this.kirana_button = kirana_button;
        this.profile_button = profile_button;
        this.progressBar = progressBar;
        this.alert = alert;
    }


    @Override
    protected List<KiranaModel> doInBackground(Void... voids) {

        synchronized (this) {

            if (globalClass.getUserMehboob() == null && globalClass.getBarcodeList().isEmpty() && globalClass.getCoMehboobList().isEmpty() && globalClass.getKiranaList().isEmpty()) {
                globalClass.ReadProfileData(phonenumber, kirana_button, profile_button, alert);
                globalClass.getUserMehboob();
                publishProgress();
                globalClass.ReadAllMehboobsData();
            }

            globalClass.ReadAllBarcode(kirana_button, profile_button, progressBar);

        }

        return globalClass.getKiranaList();

    }


    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    @Override
    protected void onPostExecute(List<KiranaModel> mehboobModel) {
        Log.i( "onPostExecute: ", String.valueOf(globalClass.getKiranaList().size()));
    }
}