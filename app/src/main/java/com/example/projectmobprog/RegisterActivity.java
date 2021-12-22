package com.example.projectmobprog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    // Membuat variable untuk menampung nilai dari activity_register
    TextView haveAcc;
    EditText inputEmail, inputPw1, inputPw2, inputUsername;
    String email,pw1, pw2, username;
    Button btnReg;

    // Membuat Loading
    private ProgressDialog progressDialog;

    // Memanggil Firebase untuk Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Menginisiasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inisiasi progress dialog
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait :D");
        progressDialog.setCancelable(false);

        // Menampung nilai dari activity_main
        inputEmail = findViewById(R.id.email);
        inputPw1 = findViewById(R.id.password);
        inputPw2 = findViewById(R.id.confirmPw);
        inputUsername = findViewById(R.id.halo);

        btnReg = findViewById(R.id.btnReg);

        //Jika tombol login diklik
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memanggil method untuk cek register
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

    // Method untuk cek register
    private void registration() {
        email = inputEmail.getText().toString();
        pw1 = inputPw1.getText().toString();
        pw2 = inputPw2.getText().toString();
        username = inputUsername.getText().toString();

        // Memanggil Progress Dialog
        progressDialog.show();

        // Authentication
        if(email.length()>0 && pw1.length()>=6 && pw2.length()>=6 && username.length()>=3){
            if(pw1.equals(pw2)){
                if (username.length()<=16){
                    mAuth.createUserWithEmailAndPassword(email,pw2)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful() && task.getResult() != null) {
                                        FirebaseUser firebaseUser = task.getResult().getUser();
                                        if(firebaseUser != null) {
                                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(username)
                                                    .build();

                                            firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    // Sign in success, update UI with the signed-in user's information
                                                    Toast.makeText(RegisterActivity.this, "Registration Succesfully", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                }
                                            });

                                        }else{
                                            Toast.makeText(RegisterActivity.this, "Registration Failed, Check again your Form", Toast.LENGTH_LONG).show();
                                        }

                                    }else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Registration Failed, Check again your Form", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(getApplicationContext(), "Username must be less than 16 character", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Confirm Passowrd must be same with Password", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "All form must be required", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }

}