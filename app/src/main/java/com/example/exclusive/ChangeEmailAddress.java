package com.example.exclusive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;


public class ChangeEmailAddress extends DialogFragment {
    private EditText oldEmail; private EditText newEmail;
    FirebaseAuth mAuth;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_email_address,null);
        builder.setView(view).setTitle("Change Email").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                         * when apply is click app will connect to database and change email address of the user
                         * */
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if(oldEmail.length() == 0 || newEmail.length() == 0){
                            Toast.makeText(getContext(), "All fields required", Toast.LENGTH_SHORT).show();
                        }
                        //check if user provided a valid email
                        if(!Patterns.EMAIL_ADDRESS.matcher(oldEmail.getText()).matches() || !Patterns.EMAIL_ADDRESS.matcher(newEmail.getText()).matches()){
                            Toast.makeText(getContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                        }

                        if((oldEmail.getText().toString()).equals(currentUser.getEmail())){
                            currentUser.updateEmail(newEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        currentUser.sendEmailVerification();
                                        User user = new User(currentUser.getDisplayName(), currentUser.getEmail());
                                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(getContext(), "Email change was a success! Check new Email to verify", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        //Toast.makeText(getContext(), "Email Change was a success, check new email for verification", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "There was an error! Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(getContext(), "Current email do not match with database", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
        oldEmail = view.findViewById(R.id.Current_Email);
        newEmail = view.findViewById(R.id.NewEmailAddress);
        return builder.create();
    }


}