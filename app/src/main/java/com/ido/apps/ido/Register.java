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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private EditText txtEmailAddress;
    private EditText txtPassword;
    private EditText txtconfirmPassword;
    private FirebaseAuth firebaseAuth;
    private Button registerf;
    private Button takelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtEmailAddress = (EditText) findViewById(R.id.etRegisterEmail);
        txtPassword = (EditText) findViewById(R.id.etRegisterPassword);
        txtconfirmPassword=(EditText)findViewById(R.id.etRegisterConfirmPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        registerf=(Button)findViewById(R.id.btnRegister);
        takelogin=(Button)findViewById(R.id.btntakeLogin);
        takelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
        registerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }
    public void validate()
    {
        if(txtEmailAddress.getText().length()<1)

            Toast.makeText(this, "Please Enter A Valid E-Mail ID", Toast.LENGTH_SHORT).show();
        else if(txtPassword.getText().length()<1)
            Toast.makeText(this, "Please Fill in Your Password !", Toast.LENGTH_SHORT).show();
        else if(txtconfirmPassword.getText().length()<1)
            Toast.makeText(this, "Please Fill in Your Confirm Password !", Toast.LENGTH_SHORT).show();
        else if(txtPassword.getText().length()<6)
            Toast.makeText(this, "Password Is Too Weak !" , Toast.LENGTH_SHORT).show();
        else if (!txtconfirmPassword.getText().toString().equals(txtPassword.getText().toString()))
        {
            Toast.makeText(this, "Password is not matching!", Toast.LENGTH_SHORT).show();
        }


        else
            register();

    }


    public void register() {

        final ProgressDialog progressDialog = ProgressDialog.show(Register.this, "Please wait...", "Processing...", true);
        (firebaseAuth.createUserWithEmailAndPassword(txtEmailAddress.getText().toString(), txtPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_LONG).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Register.this, "Email sent please verify", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                            Intent i = new Intent(Register.this, Login.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Register.this, Login.class));
        finish();

    }
    }

