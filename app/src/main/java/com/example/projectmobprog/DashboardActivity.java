package com.example.projectmobprog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        // Untuk Logout
        btnLogout = findViewById(R.id.btnLogout);
        // Untuk username
        textName = findViewById(R.id.username);

        // Mengambil dari Firebase
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Pengkondisian untuk user
        if (firebaseUser != null){
            textName.setText(firebaseUser.getDisplayName());
        }else{
            textName.setText("Login Failed !");
        }

        // Event dari tombol Logout
        btnLogout.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });


    }
}