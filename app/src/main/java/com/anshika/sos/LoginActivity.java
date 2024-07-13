package com.raghav.sos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

        EditText mTextUsername;
        EditText mTextPassword;
        Button mButtonLogin;
        TextView mTextViewRegister;
        TextView forgetpassword;
        FirebaseAuth firebaseAuth;
        AlertDialog.Builder reset_alert;
        LayoutInflater inflater;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            firebaseAuth = FirebaseAuth.getInstance();

            reset_alert = new AlertDialog.Builder(this);

            inflater = this.getLayoutInflater();

            mTextUsername = (EditText)findViewById(R.id.edittext_username);
            mTextPassword = (EditText)findViewById(R.id.edittext_password);
            mButtonLogin = (Button)findViewById(R.id.button_login);
            forgetpassword = (TextView) findViewById(R.id.textview_forgot);
            mTextViewRegister = (TextView)findViewById(R.id.textview_register);
            mTextViewRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(registerIntent);
                }
            });
            forgetpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //start alertdialog

                    View view = inflater.inflate(R.layout.reset_pop, null);
                    reset_alert.setTitle("Forget Password ?")
                            .setMessage("Enter your Email to get Password Reset link. ")
                            .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //validate email
                                    EditText email = view.findViewById(R.id.reset_email_pop);
                                    if(email.getText().toString().isEmpty()){
                                        email.setError("Required Field");
                                        email.requestFocus();
                                        return;
                                    }
                                    //send reset link
                                    firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(LoginActivity.this, "Reset Email Sent", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }).setNegativeButton("Cancel",null)
                            .setView(view)
                            .create().show();
                }
            });
            mButtonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //extract / validate

                    if(mTextUsername.getText().toString().isEmpty()){
                        mTextUsername.setError("Email is Missing!");
                        mTextUsername.requestFocus();
                        return;
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(mTextUsername.getText().toString()).matches()) {
                        mTextUsername.setError("Please provide valid email!");
                        mTextUsername.requestFocus();
                        return;
                    }
                    if(mTextPassword.getText().toString().isEmpty()){
                        mTextPassword.setError("Password is Missing!");
                        mTextPassword.requestFocus();
                        return;
                    }
                    //login user

                    firebaseAuth.signInWithEmailAndPassword(mTextUsername.getText().toString(),mTextPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    //Login is successfull
                                    Intent intent = new Intent(LoginActivity.this,Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }
    }
}
