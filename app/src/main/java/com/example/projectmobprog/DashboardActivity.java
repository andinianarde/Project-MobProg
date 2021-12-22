package com.example.projectmobprog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private TextView textName;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnLogout = findViewById(R.id.btnLogout);
        textName = findViewById(R.id.username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            textName.setText(firebaseUser.getDisplayName());
        }else{
            textName.setText("Login Failed !");
        }

        btnLogout.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

    }
}