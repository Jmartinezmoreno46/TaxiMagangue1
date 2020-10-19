package com.example.taximagangue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionAuthActivity extends AppCompatActivity {

    Toolbar bToolbar;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_auth);
        bToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(bToolbar);
        getSupportActionBar().setTitle("Selecccionar opcion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLogin =(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });

    }

    private void gotoLogin() {
        Intent intent = new Intent(OptionAuthActivity.this , LoginActivity.class);
        startActivity(intent);
    }

}