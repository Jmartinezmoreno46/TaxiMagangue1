package com.example.taximagangue.provider;

import com.example.taximagangue.models.Conduct;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ConductorProvider {
    DatabaseReference tDatabase;

    public ConductorProvider() {
        tDatabase= FirebaseDatabase.getInstance().getReference().child("User").child("conductores");

    }

    public Task<Void> create(Conduct conduct){

        Map<String, Object> map = new HashMap<>();
        //con el metodo map hacemos referencia de los datos que queremos ver guardados en firebase en los diferentes tipos de nodo usuario o cliente
        map.put("name", conduct.getNombre());
        map.put("emai", conduct.getEmail());
        return tDatabase.child(conduct.getId()).setValue(map);
    }
}
