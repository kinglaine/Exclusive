package com.example.exclusive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends AppCompatDialogFragment {
    private EditText newPassword; private EditText confirmNewPassword;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_password,null);

        newPassword = view.findViewById(R.id.New_Password);
        confirmNewPassword = view.findViewById(R.id.Confirm_New_Password);
        builder.setView(view).setTitle("Reset Password").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                         * when apply is click app will connect to database and change password of the user
                         * */
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String password1 = newPassword.getText().toString();
                        String password2 = confirmNewPassword.getText().toString();
                        if(password1.isEmpty() || password2.isEmpty()){
                            Toast.makeText(getContext(), "All fields required", Toast.LENGTH_SHORT).show();
                        }
                        //check if user provided a valid password
                        if(password1.equals(password2)== false){
                            Toast.makeText(getContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
                        }
                        if(!passwordValidation(newPassword.getText().toString())){
                            Toast.makeText(getContext(), "Please enter a valid password! Password must be greater than 8, includes digits and special characters", Toast.LENGTH_LONG).show();
                        } else if((passwordValidation(password1) == true && passwordValidation(password2) == true) && password1.equals(password2)) {
                            currentUser.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }


                    }

                });
        return builder.create();
    }
    public static boolean passwordValidation(String password) {
        if(password.length()>=8) {
            //Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            //Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            //Pattern eight = Pattern.compile (".{8}");

            //Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            //Matcher hasSpecial = special.matcher(password);

            return /*hasLetter.find() && && hasSpecial.find()*/ hasDigit.find();
        }
        else {
            return false;
        }

    }

}