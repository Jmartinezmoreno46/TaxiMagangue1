package com.example.taximagangue.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.taximagangue.R;

public class InicioActivity extends AppCompatActivity {
    Button btnCliente;
    Button btnConductor;
    SharedPreferences tPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        tPref= getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        final SharedPreferences.Editor editor = tPref.edit();

        btnConductor =(Button)findViewById(R.id.btnConduct);
        btnCliente =(Button)findViewById(R.id.btnClient);

        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user","client");
                editor.apply();
                gotoSelectAuth();
            }
        });

        btnConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user","conduct");
                editor.apply();
                gotoSelectAuth();
            }
        });

    }

    private void gotoSelectAuth() {
        Intent intent = new Intent(InicioActivity.this, OptionAuthActivity.class);
        startActivity(intent);
    }
}