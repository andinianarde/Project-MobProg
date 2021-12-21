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

public class RegisterActivity extends AppCompatActivity {

    // Membuat variable untuk menampung nilai dari activity_register
    TextView haveAcc;
    EditText inputEmail, inputPw1, inputPw2;
    String email,pw1, pw2;
    Button btnReg;

    // Memanggil Firebase untuk Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Menginisiasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Menampung nilai dari activity_main
        inputEmail = findViewById(R.id.email);
        inputPw1 = findViewById(R.id.password);
        inputPw2 = findViewById(R.id.confirmPw);

        btnReg = findViewById(R.id.btnReg);

        //Jika tombol login diklik
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memanggil method untuk cek login
                registration();
            }
        });

        // Menampung nilai variable dari activity_register
        haveAcc = (TextView)findViewById(R.id.haveAcc);
        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

    private void registration() {
        email = inputEmail.getText().toString();
        pw1 = inputPw1.getText().toString();
        pw2 = inputPw2.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,pw2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Registration Succesfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Registration Failed, Check again your Email/Password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}