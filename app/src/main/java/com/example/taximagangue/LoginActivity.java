package com.example.taximagangue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText eTextImputEmail;
    TextInputEditText eTextImputContra;
    Button btnIngresar;

    FirebaseAuth tAuth;
    DatabaseReference tDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                tAuth.signInWithEmailAndPassword(email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "El Login se Realizo Exitosamente", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "La Contraseña o el Usuario es Incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }
}