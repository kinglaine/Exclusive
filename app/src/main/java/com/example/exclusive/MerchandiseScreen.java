package com.example.exclusive;

import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MerchandiseScreen extends AppCompatActivity {
    private Button profile; private Button Cart;
    //method to open Account info page
    private <T> void openNewPage(Class<T> className){
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchandise_screen);
        profile =  findViewById(R.id.Account_info);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openNewPage(AccountSetting.class);
                finish();
                overridePendingTransition(0, R.anim.lefttoright);
            }
        });

        Cart = (Button) findViewById(R.id.Access_cart);
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPage(Cart.class);
                finish();
                overridePendingTransition(0, R.anim.lefttoright);
            }
        });

    }
}