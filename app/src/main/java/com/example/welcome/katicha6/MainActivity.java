package com.example.welcome.katicha6;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.Task;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    private GoogleMap mMap;

    LocationManager locationManager;
    String GOOGLE_API_KEY = "AIzaSyCOWFNmeAi-xjXpDwv8n6bUz8BnyLPN55s";
    double mylatitude;
    double mylongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.map_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mAuth = FirebaseAuth.getInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);

            //for map
            SupportMapFragment mapFragment = (SupportMapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            mapFragment.getMapAsync(this);//OnMapReadyCallback is interface
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            mMap.setMyLocationEnabled(true);

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //get latitude
                        double latitude = location.getLatitude();
                        //get longitude
                        double longitude = location.getLongitude();
                        //instantiate the class, Latlng
                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String str = addressList.get(0).getLocality();
                            str += addressList.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mylatitude = latitude;
                        mylongitude = longitude;
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //get latitude
                        double latitude = location.getLatitude();
                        //get longitude
                        double longitude = location.getLongitude();
                        //instantiate the class, Latlng
                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String str = addressList.get(0).getLocality();
                            str += addressList.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mylatitude = latitude;
                        mylongitude = longitude;
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            }


        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

                break;
            case R.id.nav_maps:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapsFragment()).commit();
                break;
            case R.id.nav_fuelstation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FuelStationFragment()).commit();
                break;
            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
                break;
            case R.id.nav_aboutus:
                Toast.makeText(this, "about us toast", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                sendToStart();
                break;
        }


        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToStart(); //updte ui aafai hunna so affai banauna parxa
        }
    }

    private void sendToStart() {
        Intent i = new Intent(MainActivity.this, StartActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //for device location
   /* private void getDeviceLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {

            com.google.android.gms.tasks.Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task task) {
                    if (task.isSuccessful()) {
                        LatLng latLng;
                        Toast.makeText(MainActivity.this, "location found ", Toast.LENGTH_SHORT).show();
                        Location currentlocation = (Location) task.getResult();

                        moveCamera(new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude()), 15);

                    } else {
                        Toast.makeText(MainActivity.this, "location not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (SecurityException e) {
            Toast.makeText(this, "location service is off", Toast.LENGTH_SHORT).show();
        }
    }  */

    public void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;



        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + "27.673" + "," + "85.359");
        googlePlacesUrl.append("&radius=2000");
        googlePlacesUrl.append("&type=" + "gas_station");
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

        RequestQueue requestqueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest sr = new StringRequest(Request.Method.GET, googlePlacesUrl.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj1 = new JSONObject(s);

                    JSONArray arr1 = obj1.getJSONArray("results");
                    for (int i = 0; i < arr1.length(); i++) {
                        JSONObject obj2 = arr1.getJSONObject(i);
                        JSONObject obj3 = obj2.getJSONObject("geometry");
                        JSONObject obj4 = obj3.getJSONObject("location");
                        double lat = obj4.getDouble("lat");
                        double lng = obj4.getDouble("lng");
                        LatLng[] newLatLng = new LatLng[arr1.length()];
                        newLatLng[i] = new LatLng(lat, lng);

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng[i], 15));
                        mMap.addMarker(new MarkerOptions().position(newLatLng[i]));


                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "No Address Found", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();


            }
        });

        requestqueue.add(sr);



    }



}

