package com.UAS_AKB_IF5_10120205.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.UAS_AKB_IF5_10120205.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    private EditText Email, Password;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        mauth= FirebaseAuth.getInstance();
        Email = findViewById(R.id.reg_mail);
        Password = findViewById(R.id.reg_password);

        btnLogin = findViewById(R.id.login_text);
        btnRegister = findViewById(R.id.register_btn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registers();

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private void Registers() {

        String user= Email.getText().toString().trim();
        String pass= Password.getText().toString().trim();
        if (user.isEmpty()){
            Email.setError("Email tidak boleh kosong");

        }if (pass.isEmpty()){
            Password.setError("Password tidak boleh kosong");
        }
        else{
            mauth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Registration Failed!!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

// 10120205 - Raya Adhary - IF5