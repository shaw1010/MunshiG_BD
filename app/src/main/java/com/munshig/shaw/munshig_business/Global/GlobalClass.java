package com.munshig.shaw.munshig_business.Global;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.Models.MehboobModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GlobalClass extends Application {

        KiranaModel kirana;
        String mobile_no;
        String name;
        MehboobModel mehboob;
        FirebaseFirestore fire;
        List<KiranaModel> list_kirana = new ArrayList<>();
        List<MehboobModel> list_mehboob = new ArrayList<>();
        List<String> co_mehboob;

    public GlobalClass(KiranaModel kirana, String mobile_no, String name, MehboobModel mehboob) {
        this.kirana = kirana;
        this.mobile_no = mobile_no;
        this.name = name;
        this.mehboob = mehboob;
    }

    public GlobalClass() {
    }

    public List<KiranaModel> getList_kirana() {
        return list_kirana;
    }

    public void setList_kirana(List<KiranaModel> list_kirana) {
        this.list_kirana = list_kirana;
    }

    public List<MehboobModel> getList_mehboob() {
        return list_mehboob;
    }

    public void setList_mehboob(List<MehboobModel> list_mehboob) {
        this.list_mehboob = list_mehboob;
    }

    public KiranaModel getKirana() {
        return kirana;
    }

    public void setKirana(KiranaModel kirana) {
        this.kirana = kirana;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MehboobModel getMehboob() {

        return mehboob;
    }

    public void setMehboob(MehboobModel mehboob) {
        this.mehboob = mehboob;
    }

    public List<String> getCo_mehboob() {
        return co_mehboob;
    }

    public void setCo_mehboob(List<String> co_mehboob) {
        this.co_mehboob = co_mehboob;
    }

    //To get the User's MehboobModel
    public MehboobModel ReadProfileData(String mobile_no){

            fire = FirebaseFirestore.getInstance();

            fire.collection("Mehboobs").whereEqualTo("mobile_no", mobile_no).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc : task.getResult()){
                            mehboob = doc.toObject(MehboobModel.class);
                        }
                    }
                    else{
                        mehboob = null;
                    }
                }
            });

            return mehboob;
        }


    public KiranaModel ReadKiranaData(String name)
    {
        
        return kirana;
    }


    public List<String> ReadCoMehboobData(String kirana_name){

        fire.collection("Kiranas").whereEqualTo("name", kirana_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final DocumentSnapshot doc : task.getResult()){
                        doc.getReference().collection("Barcodes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                for (DocumentSnapshot doc : documentSnapshots){
                                    if(!list_mehboob.contains(doc.get("mehboob")))
                                        co_mehboob.add(doc.get("mehboob").toString());
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }
            }
        });
        return co_mehboob;
    }

    public List<KiranaModel> ReadKiranaList(String kirana_progress)
    {

    //Convert the retrieved String to List
        kirana_progress = kirana_progress.replace("[", "");
        kirana_progress = kirana_progress.replace("]", "");
        final List<String> required = Arrays.asList(kirana_progress.split(","));
        list_kirana.clear();

    //Creating List of Objects of all the Kiranas of Mehboob
        for(int i=0;i< required.size() ;i++) {

            final int finalI = i;
            fire.collection("Kiranas").whereEqualTo("name", required.get(i).toLowerCase().trim()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            //Setting Values for each Kirana into the Object
                            KiranaModel kiranaModel = new KiranaModel();
                            kiranaModel.setName(doc.getData().get("name").toString().trim());
                            kiranaModel.setVendor_name(doc.getData().get("vendor_name").toString().trim());
                            kiranaModel.setAddress(doc.getData().get("address").toString().trim());
                            kiranaModel.setSize(doc.getData().get("size").toString().trim());
                            kiranaModel.setTotal_scanned(doc.getData().get("total_scanned").toString().trim());
                            kiranaModel.setImage_path(doc.getData().get("image").toString().trim());

                            list_kirana.add(kiranaModel);
                        }
                    } else {
                        Log.i("onComplete:Gadbad ", "++++");
                    }
                }
            });
        }

        return list_kirana;
    }
}


