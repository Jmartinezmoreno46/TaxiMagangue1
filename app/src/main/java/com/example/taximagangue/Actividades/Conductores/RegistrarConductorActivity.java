package com.example.taximagangue.Actividades.Conductores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.taximagangue.Actividades.Clientes.RegistrarActivity;
import com.example.taximagangue.R;
import com.example.taximagangue.includes.MyToolbar;
import com.example.taximagangue.models.Client;
import com.example.taximagangue.models.Conduct;
import com.example.taximagangue.provider.AuthProvider;
import com.example.taximagangue.provider.ConductorProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarConductorActivity extends AppCompatActivity {
    AuthProvider tAuthProvider;
    ConductorProvider tConduct;
    Button btn_Registrar;
    TextInputEditText text_Email, text_Nombre, text_telefono, text_Pass , text_Marca_Vehiculo, text_Placa_Vehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_conductor);

        MyToolbar.show(this, "Registro de Conductor",true);

        tAuthProvider = new AuthProvider();
        tConduct = new ConductorProvider();



        btn_Registrar = (Button)findViewById(R.id.btnRegistrar);
        text_Nombre =(TextInputEditText)findViewById(R.id.EditNombre);
        text_Email = (TextInputEditText)findViewById(R.id.EditEmail);
        text_Pass = (TextInputEditText)findViewById(R.id.EditPass) ;
        text_telefono=(TextInputEditText)findViewById(R.id.EditTelefono);
        text_Marca_Vehiculo=(TextInputEditText)findViewById(R.id.EditMarca);
        text_Placa_Vehiculo=(TextInputEditText)findViewById(R.id.EditPlaca);

        btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClicRegistrar();
            }
        });
    }

    public void ClicRegistrar() {

        final String name = text_Nombre.getText().toString();
        final String email = text_Email.getText().toString();
        final String pass = text_Pass.getText().toString();
        final String telefono = text_telefono.getText().toString();
        final String marca = text_Marca_Vehiculo.getText().toString();
        final String placa = text_Placa_Vehiculo.getText().toString();

        if(!name.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !telefono.isEmpty() && !marca.isEmpty() && !placa.isEmpty()){
            if (pass.length() >= 6){
                //este metodo recibe un email y password
                register(name, email, pass , marca ,placa);
            }else{
                Toast.makeText(RegistrarConductorActivity.this, "Ingrese Una Contraseña de 6 ó Mas Carateres", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Ingrese Todos Los Campos", Toast.LENGTH_SHORT).show();
        }

    }

    void register(final String name, final String email, String pass , final String marca, final String placa){
        tAuthProvider.Registrar(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Conduct conduct = new Conduct(id, name, email, marca, placa);
                    create(conduct);
                }else{
                    Toast.makeText(RegistrarConductorActivity.this, "No Se Pudo Registrar El Usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create (Conduct conduct){
        tConduct.create(conduct).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegistrarConductorActivity.this, "El Registro Se Realizo Exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistrarConductorActivity.this, "No Se Pudo Registrar El Conductor", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}