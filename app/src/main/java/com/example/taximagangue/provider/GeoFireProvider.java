package com.example.taximagangue.provider;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeoFireProvider {
    private DatabaseReference tDatabase;
    private GeoFire tGeofire;

    public GeoFireProvider(){
        tDatabase = FirebaseDatabase.getInstance().getReference().child("Conductores_Activos");
        tGeofire = new GeoFire(tDatabase);
    }

    public  void  saveLocation(String idConductor, LatLng latLng){
        tGeofire.setLocation(idConductor, new GeoLocation(latLng.latitude, latLng.longitude));
    }

    public  void removeLocation(String idConductor){

        tGeofire.removeLocation(idConductor);
    }

    public GeoQuery getActivarConductor(LatLng latLng){
        GeoQuery geoQuery = tGeofire.queryAtLocation(new GeoLocation(latLng.latitude,latLng.longitude), 5);
        geoQuery.removeAllListeners();
        return geoQuery;
    }

}
