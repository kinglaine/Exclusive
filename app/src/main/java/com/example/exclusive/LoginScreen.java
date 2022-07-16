package com.example.exclusive;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginScreen extends AppCompatActivity {
    private Button Log_in; private Button Sign_Up; private ImageView logo;
    private EditText Password_Field; private EditText Email_Field; private TextView Forgot_Password;
    //Method to open any page with class to go to as parameter
    private <T> void openNewPage(Class<T> className){
        Intent intent = new Intent(this, className);
        startActivity(intent);
        finish();
    }

    public void ScreenTapped(View view){
        openNewPage(com.example.exclusive.MerchandiseScreen.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        logo =(ImageView) findViewById(R.id.logo);
        // create bounce animation object
        Animation logoBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        logo.setAnimation(logoBounce);

        //click sign up button to sign up
        Sign_Up =(Button) findViewById(R.id.Sign_Up);
        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPage(com.example.exclusive.CreateAccountScreen.class);
                overridePendingTransition(R.anim.lefttorightslow, 0);
                finish();
            }
        });

        //add toast message when either email or password field empty
        Log_in = (Button) findViewById(R.id.Log_in);
        Email_Field = (EditText) findViewById(R.id.Email_Field);
        Password_Field = (EditText) findViewById(R.id.Password_Field);
        Log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(Password_Field.getText().toString())){
                    Toast.makeText(LoginScreen.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Email_Field.getText().toString())) {
                    Toast.makeText(LoginScreen.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else{
                    openNewPage(MerchandiseScreen.class);
                }
            }
        });

        //reset password
        Forgot_Password = (TextView) findViewById(R.id.Forgot_Password);
        Forgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPage(ResetPassword.class);
            }
        });
    }


    @Override
    public void onBackPressed() {
        openNewPage(CreateAccountScreen.class);
    }
}