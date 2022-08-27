package com.example.exclusive;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalInfo extends AppCompatDialogFragment {
    private EditText UserFirstName; private EditText UserLastName;
    private PersonalInfoListener personalInfoListener;
    private FirebaseAuth mAuth;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.personal_info,null);
        builder.setView(view).setTitle("Personal Information").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setPositiveButton("apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String FirstName = UserFirstName.getText().toString()+" ";
                        String LastName = UserLastName.getText().toString();
                        String fullName = FirstName + LastName;
                        personalInfoListener.applyTexts(FirstName,LastName);
                        //change the first and last name of user in database so that when app is restarted new name is saved
                        User user = new User(fullName, currentUser.getEmail());
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                    }
                });
        //set username from fragment to Account setting layout
        UserFirstName = view.findViewById(R.id.Personal_Info_FirstName);
        UserLastName = view.findViewById(R.id.Personal_Info_LastName);

        //initialize mAuth
        mAuth = FirebaseAuth.getInstance();

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            personalInfoListener =(PersonalInfoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement PersonalInfoListener");
        }
    }


    public interface PersonalInfoListener{
         void applyTexts(String FirstName,String LastName);
    }
}
