package com.example.taximagangue.Actividades.Clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.taximagangue.R;
import com.example.taximagangue.includes.MyToolbar;
import com.example.taximagangue.models.Client;
import com.example.taximagangue.provider.AuthProvider;
import com.example.taximagangue.provider.ClientProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarActivity extends AppCompatActivity {
    AuthProvider tAuthProvider;
    ClientProvider tClient;
    Button btn_Registrar;
    TextInputEditText text_Email, text_Nombre, text_telefono, text_Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        MyToolbar.show(this, "Registro de Usuario",true);

        tAuthProvider = new AuthProvider();
        tClient = new ClientProvider();


        btn_Registrar = (Button)findViewById(R.id.btnRegistrar);
        text_Nombre =(TextInputEditText)findViewById(R.id.EditNombre);
        text_Email = (TextInputEditText)findViewById(R.id.EditEmail);
        text_Pass = (TextInputEditText)findViewById(R.id.EditPass) ;
        text_telefono=(TextInputEditText)findViewById(R.id.EditTelefono);

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

        if(!name.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !telefono.isEmpty()){
            if (pass.length() >= 6){
                //este metodo recibe un email y password
                register(name, email, pass);
            }else{
                Toast.makeText(RegistrarActivity.this, "Ingrese Una Contraseña de 6 ó Mas Carateres", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Ingrese Todos Los Campos", Toast.LENGTH_SHORT).show();
        }

    }

    void register(final String name, final String email, String pass){
        tAuthProvider.Registrar(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Client client = new Client(id, name, email);
                    create(client);
                }else{
                    Toast.makeText(RegistrarActivity.this, "No Se Pudo Registrar El Usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create (Client client){
        tClient.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegistrarActivity.this, "El Registro Se Realizo Exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistrarActivity.this, "No Se Pudo Registrar El Cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*
    public void saveUser(String id, String nombre , String email){
        String selecteUser = tPref.getString("user","");
        User user = new User();
        user.setEmail(email);
        user.setNombre(nombre);

        if (selecteUser.equals("conduct")){
            //referenciamos al nodo principal en firebase , que dentro nos creara un nodo llamado User y dentro de ese nodo , otro nodo
            //llamado conductores e igual para cliente.
            // el metodo push nos crea un id como identificador unico

            tDatabase.child("Users").child("conductores").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistrarActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegistrarActivity.this, "Fallo el Registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if (selecteUser.equals("client")){
            tDatabase.child("Users").child("clients").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistrarActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegistrarActivity.this, "Fallo el Registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    */

}