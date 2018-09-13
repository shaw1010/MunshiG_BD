package com.munshig.shaw.munshig_business.Global;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
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

        KiranaModel KiranaSelected;     //Kirana details of the selected kirana from the list, where BarcodeData needs to be added/modified
        String phonenumber;             //Phonenumber of User used during Login
        String serial;              //Name of Selected Kirana
        MehboobModel UserMehboob;       //User Mehboob's Profile Information
        FirebaseFirestore fire,lite;    //Firestore Objects
        List<KiranaModel> KiranaList = new ArrayList<>();   //Details of all the Kiranas required by the User
        List<String> MehboobList = new ArrayList<>();
        List<MehboobModel> CoMehboobList = new ArrayList<>();   //List of other Mehboobs working in the same Kirana Store, contains only Name and Contact Info
        BarcodeModel SearchBarcode;     //The details of the barcode that already exists in the database which has been reentered
        List<BarcodeModel> BarcodeList = new ArrayList<>();


//------Constructors------------------------------------------------------------------------------//

    //Constructor with Parameters
    public GlobalClass(KiranaModel KiranaSelected, String phonenumber, String serial, MehboobModel UserMehboob) {
        this.KiranaSelected = KiranaSelected;
        this.phonenumber = phonenumber;
        this.serial = serial;
        this.UserMehboob = UserMehboob;
    }

    //Constructor empty, Best Constructor
    public GlobalClass() {
    }


//---------Getters and Setters for all the Variables----------------------------------------------//


    public List<BarcodeModel> getBarcodeList() {
        return BarcodeList;
    }

    public void setBarcodeList(List<BarcodeModel> barcodeList) {
        BarcodeList = barcodeList;
    }

    public KiranaModel getKiranaSelected() {
        return KiranaSelected;
    }

    public void setKiranaSelected(KiranaModel kiranaSelected) {
        KiranaSelected = kiranaSelected;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public MehboobModel getUserMehboob() {
        return UserMehboob;
    }

    public void setUserMehboob(MehboobModel userMehboob) {
        UserMehboob = userMehboob;
    }

    public List<KiranaModel> getKiranaList() {
        return KiranaList;
    }

    public void setKiranaList(List<KiranaModel> kiranaList) {
        KiranaList = kiranaList;
    }

    public List<String> getMehboobList() {
        return MehboobList;
    }

    public void setMehboobList(List<String> mehboobList) {
        MehboobList = mehboobList;
    }

    public List<MehboobModel> getCoMehboobList() {
        return CoMehboobList;
    }

    public void setCoMehboobList(List<MehboobModel> coMehboobList) {
        CoMehboobList = coMehboobList;
    }

    public BarcodeModel getSearchBarcode() {
        return SearchBarcode;
    }

    public void setSearchBarcode(BarcodeModel searchBarcode) {
        SearchBarcode = searchBarcode;
    }


//------------------------------------------------------------------------------------------------//


    //GLOBALCLASS FUNCTIONS:
    /*These functions would be called to Write or Read data to/from the Firebase Firestore database when
    * required and the data will be stored in the variables of this class and the data from these variables
      * will be fetched using Getters, this variables will then be used to display data or to update our
      * SQLite Database or some other Storage*/



//``A. Fetching Data from the Firestore Database


    /*A.1. Fetching the User's MehboobModel, Getting user Mehboob's profile data, taking the
    user's phone number as parameter. The function ReadKiranaList is called after completion.*/

    public MehboobModel ReadProfileData(String mobile_no, final Button kirana_button, final Button profile_button, final TextView alert){

        fire = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        fire.setFirestoreSettings(settings);


        Log.i("ReadProfileData:", "getMehboob");


        fire.collection("Mehboobs").whereEqualTo("mobile_no", mobile_no).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e!=null){
                    Toast.makeText(GlobalClass.this, e.toString(), Toast.LENGTH_SHORT).show();
                    kirana_button.setEnabled(false);
                    profile_button.setEnabled(false);
                    alert.setText("Request Pending:");
                    alert.setVisibility(View.VISIBLE);
                }

                for(DocumentChange doc : documentSnapshots.getDocumentChanges()){

                    switch(doc.getType()){
                        case ADDED:
                            UserMehboob = doc.getDocument().toObject(MehboobModel.class);
                            Log.i( "onEvent:ReadProfileData: ", "Added");
                            ReadKiranaList(UserMehboob.getKirana_progress());
                            break;

                        case MODIFIED:
                            UserMehboob = doc.getDocument().toObject(MehboobModel.class);
                            ReadKiranaList(UserMehboob.getKirana_progress());
                            Log.i( "onEvent:ReadProfileData: " , "Modified");
                            break;

                        case REMOVED:
                            UserMehboob = null;
                            kirana_button.setEnabled(false);
                            profile_button.setEnabled(false);
                            alert.setText("Request Pending: Aage bado aage bado!");
                            break;
                    }
                }

                String source = documentSnapshots.getMetadata().isFromCache() ?
                        "local cache" : "server";
                Log.d("ReadProfileData: Data fetched from " , source);
//                if(source.equals("local cache")){
//
//                }
            }
        });


            return UserMehboob;
        }



    /*A.2. Fetch the Data of all The Kiranas in Progress from the data of the user, thus we need to first load the
    user's profile data then use the kirana_progress as a parameter to fetch the required data.
    Importantly we need to update the data from the database for any changes especially in kirana_progress field */

    public List<KiranaModel> ReadKiranaList(List<String> kirana_progress) {
        KiranaList.clear();
        Log.i( "ReadKiranaList: " , String.valueOf(kirana_progress.size()));
        for(int i=0;i< kirana_progress.size() ;i++) {
            final int finalI = i;

            fire.collection("Kiranas").whereEqualTo("serial", kirana_progress.get(i)).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if(e!=null){
                        Toast.makeText(GlobalClass.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                    }

                    Log.i( "onEvent:shayad yehi hai pyaar ", "ReadKiranaList");
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()){
                        KiranaModel kiranaModel = documentChange.getDocument().toObject(KiranaModel.class);

                        switch (documentChange.getType()){
                            case ADDED:
                                KiranaList.add(kiranaModel);
                                Log.i( "onEvent:ReadKiranaList: ", "ADDED");
                                break;
                            case REMOVED:
                                kiranaModel = documentChange.getDocument().toObject(KiranaModel.class);
                                KiranaList.remove(kiranaModel);
                                break;
                            case MODIFIED:
                                kiranaModel = documentChange.getDocument().toObject(KiranaModel.class);
                                Log.i( "onEvent:ReadKiranaList: ", "MODIFIED");
                                kiranaModel.setMehboobs((ArrayList) documentChange.getDocument().getData().get("mehboobs"));
                                for(int i=0;i<KiranaList.size();i++){
                                    if(KiranaList.get(i).getSerial() == kiranaModel.getSerial()) {
                                        KiranaList.set(i, kiranaModel);
                                    }
                                }
                                break;
                        }
                    }
                }
            });
        }

        Log.i( "Read KiranaList: ", String.valueOf(getKiranaList().size()));
        return KiranaList;
    }



    /*A.3. We are passing the selected kirana name as parameter to fetch the kirana details from the
    kiranas' list. So for this we must have to load the data of the Kirana list first.
    */

    public KiranaModel ReadKirana(List<KiranaModel> list_kirana, String serial){

        for (int i = 0; i < list_kirana.size(); i++) {
            if (list_kirana.get(i).getSerial().equals(serial))
                KiranaSelected = list_kirana.get(i);
        }

        return KiranaSelected;
    }



    /*A.4. Fetching Data of all Mehboobs
     */

    public List<MehboobModel> ReadAllMehboobsData(){


        CoMehboobList.clear();

        Log.i( "Reading CoMehboobData: ", "Co_mehboob Data");

        //Initializing FirebaseFirestore variables: getting Instance and Enable Offline Data
        lite = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.
                Builder().setPersistenceEnabled(true).build();
        lite.setFirestoreSettings(settings);


        /*Query for fetching Co-Mehbooblist by accessing the mehboob_name in the Barcodes collection
         in the Selected Kirana and then fetching the mobile_no of the mehboob fetched, then adding
         the object of the comehboob into a list */

        fire.collection("Mehboobs").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e!=null){
                    Toast.makeText(GlobalClass.this, "Some Error Occurred" + e.toString(), Toast.LENGTH_SHORT).show();
                }

                for(DocumentChange doc : documentSnapshots.getDocumentChanges() ){
                    switch(doc.getType()) {

                        case ADDED:
                            CoMehboobList.add(doc.getDocument().toObject(MehboobModel.class));
                            Log.i( "onEvent:ReadAllMehboobsData", "ADDED");
                            break;
                        case REMOVED:
                            CoMehboobList.remove(doc.getDocument().toObject(MehboobModel.class));

                            break;
                        case MODIFIED:
                            for(int i=0; i<CoMehboobList.size();i++) {
                                if (CoMehboobList.get(i).getSerial().equals(doc.getDocument().getString("serial"))) {
                                    CoMehboobList.set(i, doc.getDocument().toObject(MehboobModel.class));
                                }
                            }
                            Log.i( "onEvent:ReadAllMehboobsData", "MODIFIED");
                    }
                }
            }
        });

        Log.i("Reading completed: ", String.valueOf(CoMehboobList.size()));
        return CoMehboobList;
    }



    /*A.5. This function fetches the data of all Barcodes entries and stores them in a list of
    BarcodeModel objects this in turn would be used to Search for the Barcode Data newly entered * */

    public BarcodeModel ReadAllBarcode(final Button kirana_button, final Button profile_button, final ProgressBar progressBar){

        Log.i( "SearchBarcode: ", "Searching for Existing Barcode");
        fire.collection("Kiranas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot doc : documentSnapshots){
                    doc.getReference().collection("Barcodes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                            if(e!=null){
                                Toast.makeText(GlobalClass.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                BarcodeModel barcodeModel = new BarcodeModel();
                                switch (doc.getType()){
                                    case REMOVED:
                                        BarcodeList.remove(doc.getDocument().toObject(BarcodeModel.class));
                                        kirana_button.setEnabled(true);
                                        profile_button.setEnabled(true);
                                        Log.i("onEvent:ReadAllBarcode" , "REMOVED");
                                        progressBar.setVisibility(View.GONE);
                                        break;
                                    case ADDED:
                                        barcodeModel.setName(doc.getDocument().get("name").toString());
                                        barcodeModel.setBarcode(doc.getDocument().get("barcode").toString());
                                        barcodeModel.setPrice((ArrayList) doc.getDocument().get("price"));
                                        barcodeModel.setModified(false);
                                        BarcodeList.add(barcodeModel);
                                        kirana_button.setEnabled(true);
                                        profile_button.setEnabled(true);
                                        progressBar.setVisibility(View.GONE);
                                        Log.i("onEvent:ReadAllBarcode" , "ADDED");
                                        break;
                                    case MODIFIED:
                                        barcodeModel.setName(doc.getDocument().get("name").toString());
                                        barcodeModel.setBarcode(doc.getDocument().get("barcode").toString());
                                        barcodeModel.setPrice((ArrayList) doc.getDocument().get("price"));
                                        barcodeModel.setModified(true);
                                        BarcodeList.add(barcodeModel);
                                        kirana_button.setEnabled(true);
                                        Log.i("onEvent:ReadAllBarcode" , "MODIFIED");
                                        profile_button.setEnabled(true);
                                        progressBar.setVisibility(View.GONE);
                                        break;
                                }
                            }
                        }
                    });
                }
            }
        });

        Log.i( "Searched Barcode: ", "Found an Existing Barcode");
        return SearchBarcode;
    }



//``B. Adding Data to Firebase Firestore from AddBarcode and AddKirana Activity


    //B.1. Adding new Barcode to Firebase Firestore
    public void AddBarcode(BarcodeModel newbarcode, String kirana_name, final EditText scannedBarcode, final EditText itemName, final EditText itemPrice, final EditText itemPrice2, final EditText itemStock, final TextView totalStock, final EditText existingStock, final LinearLayout hiddenLayout){
        long ts  = System.currentTimeMillis();

        Log.i( "AddBarcode: ", "Adding Barcode");
        fire.collection("Kiranas").document(kirana_name).collection("Barcodes").document(String.valueOf(ts)).set(newbarcode).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(GlobalClass.this, "Successfully Added!", Toast.LENGTH_SHORT).show();
                scannedBarcode.setText("");itemName.setText("");itemPrice.setText("");itemPrice2.setText("");
                totalStock.setText("");existingStock.setText("");itemStock.setText("");
                scannedBarcode.requestFocus();hiddenLayout.setVisibility(View.GONE);
                Log.i( "onSuccess:AddBarcode", "Barcode Added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GlobalClass.this, "Some Error Occurred!" + e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }



    //B.2. Adding new Kirana to Firebase Firestore
    public void AddKirana(KiranaModel newkirana){

        Log.i( "AddKirana: ", "Adding new Kirana");
        fire.collection("Kiranas").document(newkirana.getName()).set(newkirana).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(GlobalClass.this, "Successfully Added!", Toast.LENGTH_SHORT).show();
                Log.i("onSuccess:AddKirana ", "Kirana Added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GlobalClass.this, "Some Error Occured!" + e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}


//-----------------------------xxxxxxxxxx The End xxxxxxxxxx--------------------------------more to come soon------//


