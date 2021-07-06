package com.example.randomcalling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        EditText email = findViewById(R.id.sEmail);
        EditText password = findViewById(R.id.sPassword);
        Button signUp = findViewById(R.id.btn_signup);
        TextView tv = findViewById(R.id.tv_signup);
        EditText name = findViewById(R.id.sName);
        EditText phone = findViewById(R.id.sPhone);
        CheckBox c1 = findViewById(R.id.Checkbox4);
        CheckBox c2 = findViewById(R.id.Checkbox2);
        CheckBox c3 = findViewById(R.id.Checkbox3);
        CheckBox c4 = findViewById(R.id.Checkbox4);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userName = name.getText().toString().trim();
                String userPhone = phone.getText().toString().trim();
                ArrayList<String>exp = new ArrayList<>();
                if(c1.isChecked()) exp.add(getString(R.string.two));
                if(c2.isChecked()) exp.add(getString(R.string.three));
                if(c3.isChecked()) exp.add(getString(R.string.four));
                if(c4.isChecked()) exp.add(getString(R.string.five));

                if(userEmail.length() > 0 && userPassword.length() > 0 && userName.length() > 0 && userPhone.length() == 10){
                    Intent intent = new Intent(SignUp.this, verifyPhoneNumber.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("userPassword", userPassword);
                    intent.putExtra("userEmail", userEmail);
                    intent.putExtra("userPhone", userPhone);
                    intent.putStringArrayListExtra("list", exp);
                    startActivity(intent);
                }
            }
        });
    }

}