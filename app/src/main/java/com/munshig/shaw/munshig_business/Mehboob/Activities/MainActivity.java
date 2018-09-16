package com.munshig.shaw.munshig_business.Mehboob.Activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.munshig.shaw.munshig_business.BDTeam.Activities.KiranaBD;
import com.munshig.shaw.munshig_business.BDTeam.Activities.MehboobBD;
import com.munshig.shaw.munshig_business.AppUtilities.Global.GlobalClass;
import com.munshig.shaw.munshig_business.Mehboob.MyTasks.MyTask1;
import com.munshig.shaw.munshig_business.R;
import com.munshig.shaw.munshig_business.AppUtilities.SaveSharedPreference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView title_text;
    ImageView logo_image;
    Button profile_button;
    Button kirana_button;
    List<String> kirana;
    LinearLayout buttonLayout, button_bd;
    ProgressBar progressBar;
    TextView alert;
    Button kirana_bd, mehboob_bd;
    Toolbar toolbarMain;
    StringBuilder khul_ja_simsim = new StringBuilder();
    LinearLayout left_chasma, right_chashma;
    MediaPlayer mMediaPlayer = null,eMediaPlayer = null;
    FirebaseFirestore pattern;
    String pattern_bd;
    RelativeLayout parent_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Declaring Layouts and Views
        title_text = findViewById(R.id.title_text);
        logo_image = findViewById(R.id.logo_image);
        profile_button = findViewById(R.id.profile_button);
        kirana_button = findViewById(R.id.kirana_button);
        buttonLayout = findViewById(R.id.buttonLayout);
        progressBar = findViewById(R.id.progressBar);
        alert = findViewById(R.id.alert);
//        toolbarMain = findViewById(R.id.toolbarMain);
        left_chasma = findViewById(R.id.left_chashma);
        right_chashma = findViewById(R.id.right_chashma);
        pattern = FirebaseFirestore.getInstance();
        parent_layout = findViewById(R.id.parent_layout);
        button_bd = findViewById(R.id.button_bd);
        kirana_bd = findViewById(R.id.kirana_bd);
        mehboob_bd = findViewById(R.id.mehboob_bd);

        kirana = new ArrayList<>();

        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        MyTask1 myTask1 = new MyTask1(MainActivity.this, globalClass.getPhonenumber(), globalClass, progressBar, kirana_button, profile_button, alert);
        myTask1.execute();
        kirana_button.setEnabled(false);
        profile_button.setEnabled(false);
        khul_ja_simsim.setLength(0);


        //Listeners
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                khul_ja_simsim.setLength(0);
                Intent intent2 = new Intent(MainActivity.this, Profile.class);
                startActivity(intent2);
            }
        });

        kirana_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                khul_ja_simsim.setLength(0);
                Intent intent3 = new Intent(MainActivity.this, KiranaList.class);
                startActivity(intent3);
            }
        });

        left_chasma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                khul_ja_simsim.append("a");
                Log.i( "onClick: ", String.valueOf(khul_ja_simsim));
                CheckCode(khul_ja_simsim.toString(), pattern_bd);
            }
        });

        right_chashma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khul_ja_simsim.append("b");
                CheckCode(khul_ja_simsim.toString(), pattern_bd);
            }
        });

        kirana_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KiranaBD.class);
                startActivity(intent);
            }
        });

        mehboob_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MehboobBD.class);
                startActivity(intent);
            }
        });

    }


    public void ChangeToBD(){
        logo_image.setImageResource(R.drawable.munshi_blue);
        button_bd.setVisibility(View.VISIBLE);
        parent_layout.setBackgroundResource(R.drawable.back_layout);
    }

    public void CheckCode(String khul_ja_simsim, String pattern_bd){
        if(khul_ja_simsim.equals(pattern_bd))
        {
            Log.i( "onClick: ", "Welcome!");
            Toast.makeText(MainActivity.this, "Khazaana", Toast.LENGTH_SHORT).show();
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.fox);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.start();

            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.dimAmount = 0.02f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
            profile_button.setVisibility(View.GONE);
            kirana_button.setVisibility(View.GONE);

            parent_layout.setBackgroundResource(R.drawable.fancy_back);
            logo_image.setImageResource(R.drawable.munshi_green);

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Log.i("tag", "This'll run 300 milliseconds later");


                            ChangeToBD();

                        }
                    },
                    10000);
            SaveSharedPreference sharedPreference = (SaveSharedPreference) getApplicationContext();
            sharedPreference.setUserType(this, true);

        }
        else {
            if(khul_ja_simsim.length()>=pattern_bd.length() && !khul_ja_simsim.equals(pattern_bd))
            {
                logo_image.setImageResource( R.drawable.munshi_red);
                eMediaPlayer = new MediaPlayer();
                eMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.aand);
                eMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                eMediaPlayer.start();
            }
        }
    }
}
