package com.example.taximagangue.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.taximagangue.Actividades.Clientes.RegistrarActivity;
import com.example.taximagangue.Actividades.Conductores.RegistrarConductorActivity;
import com.example.taximagangue.R;
import com.example.taximagangue.includes.MyToolbar;

public class OptionAuthActivity extends AppCompatActivity {
    SharedPreferences tPref;
    Button btnLogin;
    Button btnRegistrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_auth);
        MyToolbar.show(this, "Seleccionar Opcion",true);

        tPref= getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        btnRegistrar= (Button)findViewById(R.id.btnGotoRegistrar);
        btnLogin =(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoLogin();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoResgistrar();
            }
        });

    }

    private void gotoLogin() {
        Intent intent = new Intent(OptionAuthActivity.this , LoginActivity.class);
        startActivity(intent);
    }

    private void gotoResgistrar(){
        String typeUser = tPref.getString("user", "");
        if (typeUser.equals("client")){
            Intent intent = new Intent(OptionAuthActivity.this , RegistrarActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(OptionAuthActivity.this , RegistrarConductorActivity.class);
            startActivity(intent);

        }


    }

}