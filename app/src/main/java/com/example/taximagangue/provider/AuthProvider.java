package com.example.taximagangue.provider;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthProvider {

    FirebaseAuth tAuth;

    public AuthProvider() {
        tAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> Registrar(String email, String pass){
        return  tAuth.createUserWithEmailAndPassword(email,pass);
    }

    public Task<AuthResult> Login(String email, String pass){
        return  tAuth.signInWithEmailAndPassword(email,pass);
    }


    public void logout(){
        tAuth.signOut();
    }

}
