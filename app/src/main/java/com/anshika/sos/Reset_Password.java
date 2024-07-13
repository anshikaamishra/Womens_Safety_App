package com.anshika.sos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Reset_Password extends AppCompatActivity {
    EditText userPass, newConfirmPass;
    Button save;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset1);

        userPass = findViewById(R.id.newPass);
        newConfirmPass = findViewById(R.id.newConfirmPass);

        user = FirebaseAuth.getInstance().getCurrentUser();

        save = findViewById(R.id.resetPasswordBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPass.getText().toString().isEmpty()){
                    userPass.setError("Required Field");
                    userPass.requestFocus();
                    return;
                }
                if(newConfirmPass.getText().toString().isEmpty()){
                    newConfirmPass.setError("Required Field");
                    newConfirmPass.requestFocus();
                    return;
                }
                if(!userPass.getText().toString().equals(newConfirmPass.getText().toString())){
                    newConfirmPass.setError("Password do not match");
                    newConfirmPass.requestFocus();
                    return;
                }

                user.updatePassword(userPass.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Reset_Password.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Account_settings.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Reset_Password.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
