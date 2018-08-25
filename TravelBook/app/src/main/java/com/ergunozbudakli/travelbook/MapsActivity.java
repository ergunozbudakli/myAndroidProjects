package com.ergunozbudakli.travelbook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMapLongClickListener{

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    static SQLiteDatabase database;
    boolean a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        Intent intent=getIntent();
        intent.getStringExtra("position");
        String type=intent.getStringExtra("type");
        if(type.equalsIgnoreCase("old")){
            mMap.clear();
            int position=intent.getIntExtra("position",0);
            LatLng location= new LatLng(MainActivity.locations.get(position).latitude,MainActivity.locations.get(position).longitude);
            mMap.addMarker(new MarkerOptions().title(MainActivity.names.get(position)).position(location));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
            mMap.setOnMapLongClickListener(null);
        }
else {

            locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener= new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    SharedPreferences sharedPreferences=MapsActivity.this.getSharedPreferences("com.ergunozbudakli.travelbook",MODE_PRIVATE);
                    boolean firstTimeCheck=sharedPreferences.getBoolean("Not first time",false);
                    if(!firstTimeCheck){

                        LatLng userlocation= new LatLng(location.getLatitude(),location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userlocation,15));
                        sharedPreferences.edit().putBoolean("Not first time",true).apply();
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            if(Build.VERSION.SDK_INT >= 23){
                if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    mMap.clear();
                    Location lastlocation= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    LatLng userLastLocation= new LatLng(lastlocation.getLatitude(),lastlocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().title("Your Location").position(userLastLocation));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation,15));
                }
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                mMap.clear();
                Location lastlocation= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng userLastLocation= new LatLng(lastlocation.getLatitude(),lastlocation.getLongitude());
                mMap.addMarker(new MarkerOptions().title("Your Location").position(userLastLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation,15));
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length>0){
            if(requestCode==1){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                mMap.clear();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

                Location lastlocation= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    LatLng userLastLocation= new LatLng(lastlocation.getLatitude(),lastlocation.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation,15));
                    mMap.addMarker(new MarkerOptions().title("Your Location").position(userLastLocation));


            }}
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        a=false;
        Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());
        String adress="";
        try {
            List<Address> addressList= geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if(addressList.size()>0 && addressList!=null){
                if(addressList.get(0).getThoroughfare()!=null){
                    adress +=addressList.get(0).getThoroughfare();
                    if(addressList.get(0).getSubThoroughfare()!=null){
                        adress +=addressList.get(0).getSubThoroughfare();
                    }
                }
            }else{
                adress="";
            }
            if(adress!=""){

                try {
                    double l1= latLng.latitude;
                    double l2= latLng.longitude;

                    String cord1= String.valueOf(l1);
                    String cord2= String.valueOf(l2);


                    database= this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
                    database.execSQL("CREATE TABLE IF NOT EXISTS places(name VARCHAR, latitude VARCHAR, longitude VARCHAR)");
                        mMap.addMarker(new MarkerOptions().title("Selected Location").position(latLng));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                    ArrayList<String> arrayList=new ArrayList<>();
                    for(int i=0;i<MainActivity.names.size();i++){
                        arrayList.add(MainActivity.names.get(i));
                    }
                    for(int i=0; i<arrayList.size();i++){
                        String dublicate=arrayList.get(i);
                        if(dublicate.equalsIgnoreCase(adress)){
                            a=true;
                        }
                    }
                    if(!a){
                        Toast.makeText(getApplicationContext(),"New Place",Toast.LENGTH_SHORT).show();
                        SQLiteStatement statement=database.compileStatement("INSERT INTO places(name,latitude,longitude) VALUES (?,?,?)");
                        statement.bindString(1,adress);
                        statement.bindString(2,cord1);
                        statement.bindString(3,cord2);
                        statement.execute();

                        MainActivity.locations.add(latLng);
                        MainActivity.arrayAdapter.notifyDataSetChanged();
                        Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                        intent.putExtra("name",adress);
                        startActivity(intent);
                    }
                        else{
                        Toast.makeText(getApplicationContext(),"Already selected this location",Toast.LENGTH_SHORT).show();

                    }








                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            else {
                Toast.makeText(this,"Please select another location",Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
