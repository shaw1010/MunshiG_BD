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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GlobalClass extends Application {

        KiranaModel kirana;
        String mobile_no;
        String name;
        MehboobModel mehboob;
        FirebaseFirestore fire,lite;
        List<KiranaModel> list_kirana = new ArrayList<>();
        List<String> list_mehboob = new ArrayList<>();
        List<MehboobModel> co_mehboob = new ArrayList<>();
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

    public List<String> getList_mehboob() {
        return list_mehboob;
    }

    public void setList_mehboob(List<String> list_mehboob) {
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


    //To Fetch the User's MehboobModel
    /*Getting user Mehboob's profile data, taking the user's phone number as
    * parameter*/


    public MehboobModel ReadProfileData(String mobile_no){

            fire = FirebaseFirestore.getInstance();

            Log.i("ReadProfileData:", "getMehboob");

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
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GlobalClass.this, "Some Error Occured!" + e.toString(), Toast.LENGTH_LONG).show();
                }
            });

            return mehboob;
        }


    //Fetching Data about Co-Mehboobs
    public List<MehboobModel> ReadCoMehboobData(String kirana_name){


        Log.i( "Reading CoMehboobData: ", "Co_mehboob Data");

        lite = FirebaseFirestore.getInstance();
        fire.collection("Kiranas").document(kirana_name).collection("Barcodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc : task.getResult()){

                        final MehboobModel mehboobModel = new MehboobModel();

                        Log.i( "onCompmehboooobaaa " , doc.getData().get("mehboob").toString());
                            mehboobModel.setName(doc.getData().get("mehboob").toString());
                            list_mehboob.add(doc.getData().get("mehboob").toString());

                            lite.collection("Mehboobs").whereEqualTo("name", doc.getData().get("mehboob").toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot doc1 : task.getResult()){

                                            Log.i( "onCompmehboooobaaa " , doc1.getData().get("mobile_no").toString());
                                            mehboobModel.setMobile_no(doc1.getData().get("mobile_no").toString());

                                        }
                                    }

                                   co_mehboob.add(mehboobModel);

                                    Log.i( "onComcomehboobsize", String.valueOf(co_mehboob.size()));
                                }
                            });
                        Log.i( "Reading completed: ", "Co_mehboob Data Received");
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

        Log.i( "Reading KiranaList: ", "get Kirana List");
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
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GlobalClass.this, "Some Error Occured!" + e.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        Log.i( "Read KiranaList: ", "Received Kirana List");
        return list_kirana;
    }


    //Adding Data to Firebase Firestore

    //Adding new Barcode to Firebase Firestore
    public void AddBarcode(BarcodeModel newbarcode, String kirana_name){
        long ts  = System.currentTimeMillis();

        Log.i( "AddBarcode: ", "Adding Barcode");
        fire.collection("Kiranas").document(kirana_name).collection("Barcodes").document(String.valueOf(ts)).set(newbarcode).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(GlobalClass.this, "Successfully Added!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GlobalClass.this, "Some Error Occurred!" + e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    //Adding new Kirana to Firebase Firestore
    public void AddKirana(KiranaModel newkirana){

        Log.i( "AddKirana: ", "Adding new Kirana");
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

    public BarcodeModel SearchBarcode(final String barcode, String name){

        Log.i( "SearchBarcode: ", "Searching for Existing Barcode");
        fire.collection("Kiranas").document(name).collection("Barcodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc : task.getResult()){
                        if(doc.getData().get("barcode").toString().equals(barcode)){
                            search_barcode = doc.toObject(BarcodeModel.class);
                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GlobalClass.this, "Sorry Some Error Occurred" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.i( "Searched Barcode: ", "Found an Existing Barcode");
        return search_barcode;
    }

}


