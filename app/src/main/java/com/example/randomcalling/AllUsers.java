package com.example.randomcalling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllUsers extends AppCompatActivity {
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    ListView lv;
    ArrayList<User>CompleteUserList;
    ListViewAdapter adapter;
    String userProblem, CurrUserId;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        getSupportActionBar().hide();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Users");
        ListView lv = findViewById(R.id.listView);
        CurrUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        TextView noOne = findViewById(R.id.noOne);

        userProblem = getIntent().getStringExtra("Problem");
        CompleteUserList = new ArrayList<>();
        adapter = new ListViewAdapter(AllUsers.this, CompleteUserList);
        lv.setAdapter(adapter);
        lv.setVisibility(View.INVISIBLE);

        progressDialog = new ProgressDialog(AllUsers.this);
        progressDialog.setTitle("Fetching all active Users");
        progressDialog.setMessage("Wait for a while");
        progressDialog.show();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if(user.getUserKey().equals(CurrUserId) || user.isActive.equals("False")) continue;

                    for(String problem : user.getExp()){
                        if(problem.equals(userProblem)){
                            adapter.add(new User(user.getUserName(), user.getPhoneNo()));
                            lv.setVisibility(View.VISIBLE);
                            noOne.setVisibility(View.INVISIBLE);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        progressDialog.dismiss();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User CurrUser = (User) parent.getItemAtPosition(position);
                String CurrPhoneNo = CurrUser.getPhoneNo();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + CurrPhoneNo));
                startActivity(intent);
            }
        });
    }
}