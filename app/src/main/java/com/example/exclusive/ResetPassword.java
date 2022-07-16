package com.example.exclusive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        emailEditText = (EditText) findViewById(R.id.Email_Field);
        resetButton = (Button) findViewById(R.id.resetButton);
        mAuth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter a valid email address");
            emailEditText.requestFocus();
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPassword.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResetPassword.this, "Please check that you entered the right email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}