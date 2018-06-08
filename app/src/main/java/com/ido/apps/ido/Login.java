package com.ido.apps.ido;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
Button takeregister;
Button btnlogin;
EditText txtEmailLogin;
EditText txtPwd;
FirebaseAuth.AuthStateListener mAuthListener;
private FirebaseAuth mAuth;
    private LinearLayout forgot;
FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        takeregister=(Button)findViewById(R.id.btntakeRegister);
        btnlogin=(Button)findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        txtEmailLogin=(EditText)findViewById(R.id.etLoginEmail);
        txtPwd=(EditText)findViewById(R.id.etLoginPassword);
        forgot=(LinearLayout) findViewById(R.id.forgot);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               validate();
            }
        });
        takeregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class
                ));
                finish();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(Login.this ,forgot.class);
                startActivity(x);

            }
        });

    }
    public void validate()
    {
        if(txtEmailLogin.getText().length()<1)
            Toast.makeText(this, "Please Enter A Valid Email ID", Toast.LENGTH_SHORT).show();
        else if(txtPwd.getText().length()<1)
            Toast.makeText(this, "Invalid Password !", Toast.LENGTH_SHORT).show();
        else


            login();



    }
    public void login() {
        final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "Please wait...", "Proccessing...", true);

        (mAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtPwd.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {


                            user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                Intent i = new Intent(Login.this,  MainActivity.class);
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_LONG).show();
                                i.putExtra("Email", mAuth.getCurrentUser().getEmail());
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(Login.this, "Please verify your account", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                            }
                        } else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        finish();

    }
}


