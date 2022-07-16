package com.example.exclusive;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Transition;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountScreen extends AppCompatActivity {
    private Button Log_in; private Button Sign_Up; private Button Forgot_Password; private ImageView logo;
    private EditText User_FullName; private EditText Email_Field; private EditText Password_Field;


    //Method to open any page with class to go to as parameter
    protected  <T> void openNewPage(Class<T> className){
        Intent intent = new Intent(this, className);
        startActivity(intent);
        finish();
    }
    public void ScreenTapped(View view){
        //openNewPage(MerchandiseScreen.class);
    }

    //firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_screen);
        //logo animation
        logo =(ImageView) findViewById(R.id.logo);
        // create bounce animation object
        Animation logoBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        logo.setAnimation(logoBounce);

        //click on login to prompt user login credentials
        Log_in = findViewById(R.id.Log_in);
        Log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPage(LoginScreen.class);
            }
        });

        //add toast message when sign up fields are empty
        Sign_Up = (Button) findViewById(R.id.Sign_Up);
        User_FullName = (EditText) findViewById(R.id.User_FullName);
        Email_Field = (EditText) findViewById(R.id.Email_Field);
        Password_Field = (EditText) findViewById(R.id.Password_Field);
        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(User_FullName.getText().toString()) && TextUtils.isEmpty(Email_Field.getText().toString()) && TextUtils.isEmpty(Password_Field.getText().toString())) {
                    Toast.makeText(CreateAccountScreen.this, "Please enter your info to signup", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(User_FullName.getText().toString())){
                    User_FullName.requestFocus();
                    Toast.makeText(CreateAccountScreen.this, "Name field is required", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Email_Field.getText().toString())) {
                    Email_Field.requestFocus();
                    Toast.makeText(CreateAccountScreen.this, "Email field is required", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Password_Field.getText().toString())){
                    Password_Field.requestFocus();
                    Toast.makeText(CreateAccountScreen.this, "Password field is required", Toast.LENGTH_SHORT).show();
                }else {
                    registerUser();
                }
            }
        });

        //All Firebase Codes starts from here
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onBackPressed() {
        openNewPage(LoginScreen.class);
        overridePendingTransition(R.anim.lefttorightslow, 0);
    }

    private void registerUser(){
        //store user inputs in variables
        String FullName = User_FullName.getText().toString().trim();
        String Email = Email_Field.getText().toString().trim();
        String Password = Password_Field.getText().toString().trim();

        if(FullName.isEmpty()){
            User_FullName.setError("Please enter a name");
            Password_Field.requestFocus();
        }
        //check if user provided a valid email
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            Email_Field.setError("Please enter a valid email address");
            Password_Field.requestFocus();
        }
        //check password length because firebase wont accept password with less than 6 charachters
        if(Password.length()<6){
            Password_Field.setError("Password should be greater than 5 characters!");
            Password_Field.requestFocus();
        }
        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(FullName,Email);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(CreateAccountScreen.this, "Your account has been created successfully!", Toast.LENGTH_SHORT).show();
                                        openNewPage(LoginScreen.class);
                                    }else{
                                        Toast.makeText(CreateAccountScreen.this, "Failed to create account! Try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    //check if email is already in use
                    mAuth.fetchSignInMethodsForEmail(Email_Field.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean check = !task.getResult().getSignInMethods().isEmpty();
                            if(check){
                                Toast.makeText(CreateAccountScreen.this, "Email already in use", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(CreateAccountScreen.this, "Failed to create account! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}