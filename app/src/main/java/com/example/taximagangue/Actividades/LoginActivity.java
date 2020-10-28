package com.example.taximagangue.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.taximagangue.Actividades.Clientes.MapClienteActivity;
import com.example.taximagangue.Actividades.Conductores.MapConductoresActivity;
import com.example.taximagangue.R;
import com.example.taximagangue.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText eTextImputEmail;
    TextInputEditText eTextImputContra;
    Button btnIngresar;
    FirebaseAuth tAuth;
    DatabaseReference tDatabase;
    SharedPreferences tPref;
    AlertDialog tDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // agragando el toolbar en la actividad .
        MyToolbar.show(this, "Login de Usuario",true);

        tPref= getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        tDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Espere un momento").build();

        eTextImputEmail = (TextInputEditText)findViewById(R.id.EditEmail);
        eTextImputContra =(TextInputEditText)findViewById(R.id.Editpassword);
        btnIngresar = (Button)findViewById(R.id.btnGotoLogin);
        tAuth = FirebaseAuth.getInstance();
        tDatabase = FirebaseDatabase.getInstance().getReference();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    private void Login() {
        String email = eTextImputEmail.getText().toString();
        String Pass = eTextImputContra.getText().toString();

        if (!email.isEmpty() && !Pass.isEmpty()){
            if (Pass.length()>=6){
                tDialog.show();
                tAuth.signInWithEmailAndPassword(email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String user = tPref.getString("user", "");
                            if (user.equals("client")){
                                Intent intent = new Intent(LoginActivity.this, MapClienteActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(LoginActivity.this, MapConductoresActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "La Contraseña o el Usuario es Incorrecto", Toast.LENGTH_SHORT).show();
                        }
                        tDialog.dismiss();
                    }
                });

            }
            else {
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "La contraseña y el email son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}