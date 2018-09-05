package com.munshig.shaw.munshig_business.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.munshig.shaw.munshig_business.Global.GlobalClass;
import com.munshig.shaw.munshig_business.R;

import java.util.concurrent.TimeUnit;


public class AuthActivity extends AppCompatActivity {

    private LinearLayout mPhoneLayout;
    private LinearLayout mCodeLayout;

    private EditText mPhoneText;
    private EditText mCodeText;
    private ProgressBar mPhoneBar;
    private ProgressBar mCodeBar;

    private Button mSendbtn;

    private int btnType = 0;

    private TextView mErrorText;
    private FirebaseAuth mAuth;
    private static String phoneNumber;
    private String mVerificationId;

    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private final String TAG="fireAuth";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mPhoneLayout = findViewById(R.id.phoneLayout);
        mCodeLayout = findViewById(R.id.codeLayout);

        mPhoneText = findViewById(R.id.phoneEditText);
        mCodeText = findViewById(R.id.codeEditText);

        mPhoneBar = findViewById(R.id.phoneProgress);
        mCodeBar = findViewById(R.id.codeProgress);

        mErrorText = findViewById(R.id.errorText);

        mAuth = FirebaseAuth.getInstance();

        mSendbtn = findViewById(R.id.sendBtn);
        mSendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnType == 0){
                    mPhoneText.setEnabled(false);
                    mPhoneBar.setVisibility(View.VISIBLE);
                    mSendbtn.setEnabled(false);

                    phoneNumber = "+91"+mPhoneText.getText().toString();

                    if(phoneNumber.length()==13){PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,
                            60,
                            TimeUnit.SECONDS,
                            AuthActivity.this,
                            mCallbacks
                    );}
                    else{
                        Toast.makeText(AuthActivity.this, "Phone number does not seem to be valid. Check again", Toast.LENGTH_LONG).show();
                        mPhoneText.setEnabled(true);
                        mPhoneText.selectAll();
                        mPhoneBar.setVisibility(View.INVISIBLE);
                        mSendbtn.setEnabled(true);
                    }

                } else {
                    mSendbtn.setEnabled(false);
                    mCodeBar.setVisibility(View.VISIBLE);

                    String verificationCode = mCodeText.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w(TAG, "onVerificationFailed", e);
                mErrorText.setText("There was some error logging in! "+ e);
                mErrorText.setVisibility(View.VISIBLE);

                mCodeText.requestFocus();
                mCodeText.selectAll();
                mCodeText.setText(null);
                mSendbtn.setEnabled(true);
                mPhoneText.setEnabled(true);
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                btnType = 1;

                mPhoneBar.setVisibility(View.INVISIBLE);
                mCodeLayout.setVisibility(View.VISIBLE);

                mSendbtn.setEnabled(true);
                mSendbtn.setText("Verify Code");

                // ...
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            mErrorText.setText("There was some error logging in!");
                            mErrorText.setVisibility(View.VISIBLE);
                            mCodeText.requestFocus();
                            mCodeText.selectAll();
                            mCodeText.setText(null);
                            mSendbtn.setEnabled(true);
                            mPhoneText.setEnabled(true);
                        }

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                    }
                });
    }
}