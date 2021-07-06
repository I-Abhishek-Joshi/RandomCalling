package com.example.randomcalling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class verifyPhoneNumber extends AppCompatActivity {

    EditText sms;
    Button verify;
    ProgressBar progressBar;
    String PhoneNo, codeBySystem, Name, Password, Email;

    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;
    ArrayList<String>exp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        sms = findViewById(R.id.smsCode);
        verify = findViewById(R.id.btn_verify);
        progressBar = findViewById(R.id.progressBar);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Users");

        PhoneNo = "+91" + getIntent().getStringExtra("userPhone");
        Name = getIntent().getStringExtra("userName");
        Password = getIntent().getStringExtra("userPassword");
        Email = getIntent().getStringExtra("userEmail");
        exp = getIntent().getStringArrayListExtra("list");


        progressBar.setVisibility(View.VISIBLE);
        sendVerificationCodeToUser(PhoneNo);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumberWithCode(codeBySystem, sms.getText().toString().trim());
            }
        });

    }

    private void sendVerificationCodeToUser(String phoneNo) {
       PhoneAuthProvider.getInstance().verifyPhoneNumber(
               PhoneNo,
               60,
               TimeUnit.SECONDS,
               this,
               new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                   @Override
                   public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                       progressBar.setVisibility(View.GONE);
                       signInWithPhoneAuthCredential(phoneAuthCredential);
                   }

                   @Override
                   public void onVerificationFailed(@NonNull FirebaseException e) {
                       progressBar.setVisibility(View.GONE);
                       Toast.makeText(verifyPhoneNumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                       progressBar.setVisibility(View.GONE);
                       codeBySystem = s;
                   }
               });
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(phoneAuthCredential);
    }
    void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential){
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(mFirebaseAuth.getCurrentUser().getUid(), Name, PhoneNo, Email, Password, "False", exp);
                    mDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).setValue(user);
                    Intent intent = new Intent(verifyPhoneNumber.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(verifyPhoneNumber.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}