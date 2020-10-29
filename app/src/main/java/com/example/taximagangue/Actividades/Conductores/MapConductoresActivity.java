package com.example.taximagangue.Actividades.Conductores;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.taximagangue.provider.GeoFireProvider;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.example.taximagangue.R;
import com.example.taximagangue.Actividades.InicioActivity;
import com.example.taximagangue.includes.MyToolbar;
import com.example.taximagangue.provider.AuthProvider;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapConductoresActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap tMap;
    private SupportMapFragment tMapFragment;
    private AuthProvider tAuthProvider;

    private LocationRequest tLocationRequest;
    private FusedLocationProviderClient tFusedLocation;

    private final static int LOCATION_REQUEST_CODE = 1;
    private final static int SETTINGS_REQUEST_CODE = 2;
    private LatLng tCurrentLatLng;
    private Marker tMarker;
    private GeoFireProvider tGeoFireProvider;

    private Button btnConexion;

    private boolean tIsConect = false;

    LocationCallback tLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (getApplicationContext() != null) {

                    tCurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    if (tMarker != null) {
                        tMarker.remove();
                    }
                    tMarker = tMap.addMarker(new MarkerOptions().position(
                            new LatLng(location.getLatitude(), location.getLongitude())
                            ).title("Tu Posicion")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi))
                    );

                    // OBTENER LA LOCALIZACION DEL USUARIO EN TIEMPO REAL
                    tMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .zoom(15f)
                                    .build()
                    ));

                    updateLocation();
                }
            }
        }
    };

    private void updateLocation() {
        if (tAuthProvider.existSesion() && tCurrentLatLng != null) {
            tGeoFireProvider.saveLocation(tAuthProvider.getId(), tCurrentLatLng);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_conductores);
        MyToolbar.show(this, "Conductor", false);

        tAuthProvider = new AuthProvider();
        tGeoFireProvider = new GeoFireProvider();
        tFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        tMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        tMapFragment.getMapAsync(this);

        btnConexion = (Button) findViewById(R.id.btnConectar);
        btnConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tIsConect) {
                    disconnet();
                } else {
                    startLocation();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        tMap = googleMap;
        tMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        tMap.getUiSettings().setZoomControlsEnabled(true);

        tLocationRequest = new LocationRequest();
        tLocationRequest.setInterval(1000);
        tLocationRequest.setFastestInterval(1000);
        tLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        tLocationRequest.setSmallestDisplacement(5);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (gpsActived()) {
                        tFusedLocation.requestLocationUpdates(tLocationRequest, tLocationCallback, Looper.myLooper());
                        tMap.setMyLocationEnabled(true);
                    } else {
                        showAlertDialogNOGPS();
                    }
                } else {
                    checkLocationPermissions();
                }
            } else {
                checkLocationPermissions();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE && gpsActived()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                tFusedLocation.requestLocationUpdates(tLocationRequest, tLocationCallback, Looper.myLooper());
                tMap.setMyLocationEnabled(true);
            }
        }
        else {
            showAlertDialogNOGPS();
        }
    }

    private boolean gpsActived() {
        boolean isActive = false;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isActive = true;
        }
        return isActive;
    }

    private void disconnet(){

        if (tFusedLocation != null){
            btnConexion.setText("Conectarse");
            tIsConect = false;
            tFusedLocation.removeLocationUpdates(tLocationCallback);
            if (tAuthProvider.existSesion()){
                tGeoFireProvider.removeLocation(tAuthProvider.getId());
            }
        }
        else {
            Toast.makeText(MapConductoresActivity.this , "No Te Puedes Desconectar", Toast.LENGTH_SHORT).show();
        }

    }

    private void startLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (gpsActived()) {
                    btnConexion.setText("Desconectarse");
                    tIsConect = true;
                    tFusedLocation.requestLocationUpdates(tLocationRequest, tLocationCallback, Looper.myLooper());
                    tMap.setMyLocationEnabled(true);
                }
                else {
                    showAlertDialogNOGPS();
                }
            }
            else {
                checkLocationPermissions();
            }
        }else {
            if (gpsActived()) {
                tFusedLocation.requestLocationUpdates(tLocationRequest, tLocationCallback, Looper.myLooper());
                tMap.setMyLocationEnabled(true);
            }
            else {
                showAlertDialogNOGPS();
            }
        }
    }

    private void showAlertDialogNOGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Por favor activa tu ubicacion para continuar")
                .setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), SETTINGS_REQUEST_CODE);
                    }
                }).create().show();
    }
    private void checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Proporciona los permisos para continuar")
                        .setMessage("Esta aplicacion requiere de los permisos de ubicacion para poder utilizarse")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapConductoresActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                            }
                        }).create().show();
            }
            else {
                ActivityCompat.requestPermissions(MapConductoresActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.conductor_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    void logout() {
        disconnet();
        tAuthProvider.logout();
        Intent intent = new Intent(MapConductoresActivity.this, InicioActivity.class);
        startActivity(intent);
        finish();
    }
}
