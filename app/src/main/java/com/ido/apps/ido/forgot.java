package com.ido.apps.ido;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot extends AppCompatActivity {


    private EditText txtEmailLogin;
    private FirebaseAuth firebaseAuth;
    private Button resetp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        txtEmailLogin = (EditText) findViewById(R.id.emailforgot);
        firebaseAuth = FirebaseAuth.getInstance();
        resetp=(Button)findViewById(R.id.resetp);
        resetp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

    }
    public void validate()
    {
        if(txtEmailLogin.getText().length()<1)

            Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();

        else
            reset_Click();

    }
    public void reset_Click() {
        try {
            String emailAddress = txtEmailLogin.getText().toString();

            firebaseAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(forgot.this, "Email sent to your mail address", Toast.LENGTH_SHORT).show();
                                Intent ob = new Intent(forgot.this, Login.class);
                                startActivity(ob);
                            } else {
                                Toast.makeText(forgot.this, "Wrong email", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(forgot.this,forgot.class);
            startActivity(i);
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(forgot.this, Login.class));
        finish();

    }

}