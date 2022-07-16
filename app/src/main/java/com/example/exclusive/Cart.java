package com.example.exclusive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cart extends AppCompatActivity {
    private Button Home; private Button Profile;
    private <T> void openNewPage(Class<T> className){
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_screen);
        Home = (Button) findViewById(R.id.home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPage(MerchandiseScreen.class);
                overridePendingTransition(0, R.anim.righttoleft);
            }
        });

        Profile = (Button) findViewById(R.id.Account_info);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPage(AccountSetting.class);
                overridePendingTransition(0, R.anim.lefttoright);
            }
        });
    }
}