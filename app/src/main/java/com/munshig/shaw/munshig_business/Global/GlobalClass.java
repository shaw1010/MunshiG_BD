package com.munshig.shaw.munshig_business.Global;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.munshig.shaw.munshig_business.Models.BarcodeModel;
import com.munshig.shaw.munshig_business.Models.KiranaModel;
import com.munshig.shaw.munshig_business.Models.MehboobModel;

import java.lang.reflect.Array;
import java.security.Timestamp;
import java.sql.Date;
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
        List<KiranaModel> list_kirana;
        List<MehboobModel> list_mehboob;
        List<MehboobModel> co_mehboob;
        BarcodeModel search_barcode;

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

    public List<MehboobModel> getCo_mehboob() {
        return co_mehboob;
    }

    public BarcodeModel getSearch_barcode() {
        return search_barcode;
    }

    public void setSearch_barcode(BarcodeModel search_barcode) {
        this.search_barcode = search_barcode;
    }

    public void setCo_mehboob(List<MehboobModel> co_mehboob) {
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


    //Fetching Data about Co-Mehboobs
    public List<MehboobModel> ReadCoMehboobData(String kirana_name){

        co_mehboob = new ArrayList<>();list_mehboob=new ArrayList<>();
        fire.collection("Kiranas").whereEqualTo("name", kirana_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final DocumentSnapshot doc : task.getResult()){
                        doc.getReference().collection("Barcodes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                for (DocumentSnapshot doc : documentSnapshots){
                                    final MehboobModel mehboobModel = new MehboobModel();
                                    if(!list_mehboob.contains(doc.get("mehboob"))) {
                                        mehboobModel.setName(doc.get("mehboob").toString().trim());
                                        fire.collection("Mehboobs").whereEqualTo("name" , doc.get("mehboob").toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot documentSnapshots) {
                                                for (DocumentSnapshot doc1 : documentSnapshots){
                                                    mehboobModel.setMobile_no(doc1.getData().get("mobile_no").toString().trim());
                                                }
                                            }
                                        });

                                        co_mehboob.add(mehboobModel);
                                    }
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


    //Fetch the Datas of all The Kirana in Progress
    public List<KiranaModel> ReadKiranaList(String kirana_progress)
    {
        list_kirana=new ArrayList<>();
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


    //Adding Data to Firebase Firestore

    //Adding new Barcode to Firebase Firestore
    public void AddBarcode(BarcodeModel newbarcode, String kirana_name){
        Date date = new Date();
        Timestamp(Date date);
        fire.collection("Kiranas").document(kirana_name).collection("Barcodes").document(String.valueOf(FieldValue.serverTimestamp())).set(newbarcode).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(GlobalClass.this, "Successfully Added!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GlobalClass.this, "Some Error Occured!" + e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    //Adding new Kirana to Firebase Firestore
    public void AddKirana(KiranaModel newkirana){
        fire.collection("Kiranas").document(kirana.getName()).set(newkirana).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(GlobalClass.this, "Successfully Added!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GlobalClass.this, "Some Error Occured!" + e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public BarcodeModel SearchBarcode(String barcode){


        return search_barcode;
    }

}


