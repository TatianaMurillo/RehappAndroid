package com.rehapp.rehappmovil.rehapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

public class CreateTherapy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_therapy);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater li = LayoutInflater.from(this);
        View customView = li.inflate(R.layout.activity_menu_items, null);
        mActionBar.setCustomView(customView);
        mActionBar.setDisplayShowCustomEnabled(true);
        ImageButton leftPage = (ImageButton)    customView.findViewById(R.id.left);
        leftPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ...
            }
        });

        ImageButton rightPage = (ImageButton) customView.findViewById(R.id.right);
        rightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ...
            }
        });





    }
}