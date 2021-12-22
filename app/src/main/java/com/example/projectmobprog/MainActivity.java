package com.example.projectmobprog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    // Membuat variable untuk menampung nilai dari activity_main
    TextView createAcc;
    EditText inputEmail, inputPw;
    String email,pw;
    Button btnLogin;
    SignInButton btnGoogle;

    // Mendeklarasikan Google SignIn Client
    private GoogleSignInClient mGoogleSignInClient;

    // Membuat Loading
    private ProgressDialog progressDialog;

    // Memanggil Firebase untuk Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisiasi progress dialog
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait :D");
        progressDialog.setCancelable(false);

        // Menginisiasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Menampung nilai dari activity_main
        inputEmail = findViewById(R.id.email);
        inputPw = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogle = findViewById(R.id.btnGoogle);

        //Jika tombol login diklik
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memanggil method untuk cek login
                checkLogin();
            }
        });

        // Jika tombol Google diklik
        btnGoogle.setOnClickListener(v -> {
            googleSignIn();
        });

        // Jika tombol Create Acc diklik
        createAcc = (TextView)findViewById(R.id.createAcc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        // Konfigurasi Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("719165832919-qdmf0purnm1epnqi80bad4s1vg1k4v4o.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    // =================== Method untuk mengecek apakah akunnya ada atau tidak ====================

    // Untuk pindah ke Dashboard
    private void toDashboard(){
        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
    }

    private void checkLogin() {
        email = inputEmail.getText().toString();
        pw = inputPw.getText().toString();

        // Menampilkan Loading
        progressDialog.show();

        // Authentication
        if(email.length()>0 && pw.length()>=6){
            mAuth.signInWithEmailAndPassword(email,pw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                if(task.getResult() != null){
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(MainActivity.this, "Login Succesfully", Toast.LENGTH_LONG).show();
                                    toDashboard();
                                }else{
                                    Toast.makeText(MainActivity.this, "Login Failed, Check again your Email/Password", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Login Failed, Check again your Email/Password", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "Email/Password must be required", Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
    }

    // ============================== Method untuk SignIn Google ================================
    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GOOGLE SIGN IN", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("GOOGLE SIGN IN", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("GOOGLE SIGN IN", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("GOOGLE SIGN IN", "signInWithCredential:failure", task.getException());
                        }
                        toDashboard();
                    }
                });
    }



}