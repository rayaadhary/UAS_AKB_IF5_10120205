package com.UAS_AKB_IF5_10120205.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.UAS_AKB_IF5_10120205.R;
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

public class LoginActivity extends AppCompatActivity {
// 10120205 - Raya Adhary - IF5

    private FirebaseAuth mauth;

    private EditText Email, Password;
    private Button btnLogin, btnRegister;

    private GoogleSignInClient mGoogleSignInClient;

    private SignInButton btnGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mauth= FirebaseAuth.getInstance();
        Email = findViewById(R.id.login_mail);
        Password = findViewById(R.id.login_password);

        btnLogin = findViewById(R.id.login_btn);
        btnRegister = findViewById(R.id.register_text);

        btnGoogle = findViewById(R.id.btn_google);

        getSupportActionBar().hide();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlesignIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void googlesignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 2);
    }

    private void login() {
        String user= Email.getText().toString().trim();
        String pass= Password.getText().toString().trim();
        if (user.isEmpty()){
            Email.setError("Email tidak boleh kosong");

        }if (pass.isEmpty()){
            Password.setError("Password tidak boleh kosong");
        }
        else
        {
            mauth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "Login Successfully..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));


                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Login Failed!!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void reload()
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 2) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GOOGLE SIGN IN", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAGFailed", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAGsigninsuccess", "signInWithCredential:success");
                            FirebaseUser user = mauth.getCurrentUser();
                            reload();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAGsoigninfail", "signInWithCredential:failure", task.getException());

                        }
                        reload();

                    }
                });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mauth.getCurrentUser();
        if(currentUser != null)
        {
            reload();
        }
    }

}

// 10120205 - Raya Adhary - IF5
