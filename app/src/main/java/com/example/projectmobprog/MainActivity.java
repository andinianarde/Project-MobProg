package com.example.projectmobprog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Membuat variable untuk menampung nilai dari activity_main
    TextView createAcc;
    EditText inputEmail, inputPw;
    String email,pw;
    Button btnLogin;

    // Memanggil Firebase untuk Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menginisiasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Menampung nilai dari activity_main
        inputEmail = findViewById(R.id.email);
        inputPw = findViewById(R.id.password);

        btnLogin = findViewById(R.id.btnLogin);

        //Jika tombol login diklik
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memanggil method untuk cek login
                checkLogin();
            }
        });

        createAcc = (TextView)findViewById(R.id.createAcc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    // Method untuk mengecek apakah akunnya ada atau tidak
    private void checkLogin() {
        email = inputEmail.getText().toString();
        pw = inputPw.getText().toString();

        mAuth.signInWithEmailAndPassword(email,pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Login Succesfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Login Failed, Check again your Email/Password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}