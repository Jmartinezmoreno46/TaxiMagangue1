package com.example.taximagangue.provider;

import com.example.taximagangue.models.Client;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClientProvider {
    DatabaseReference tDatabase;

    public ClientProvider() {
        tDatabase= FirebaseDatabase.getInstance().getReference().child("User").child("clients");

    }

    public Task<Void> create(Client client){
        Map<String, Object> map = new HashMap<>();
        //con el metodo map hacemos referencia de los datos que queremos ver guardados en firebase en los diferentes tipos de nodo usuario o cliente
        map.put("name", client.getNombre());
        map.put("emai", client.getEmail());
        return tDatabase.child(client.getId()).setValue(map);
    }
}
