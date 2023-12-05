package com.example.hotelbooking;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.hotelbooking.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Circle userCircle;
    DatabaseManager dbManagement;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    private GoogleMap mMap;
    Marker marker;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initLocation() {
        // Initialize your location-related functionality here
        // For example, set up the FusedLocationProviderClient
        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        // Now you can use fusedLocationClient to get the user's location
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check if the requested permission is granted
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, proceed with your location-related code
                initLocation();
            } else {
                // Permission is denied, handle this case (show a message, disable features, etc.)
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        dbManagement = new DatabaseManager(this);
        // Check and request location permissions if not granted
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            // Update the circle's center to the user's location
                            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            initializeMap(userLocation);
                            userCircle.setCenter(userLocation);

                            // Move the camera to the updated location
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(userLocation);
                            mMap.moveCamera(cameraUpdate);
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));  // Adjust the zoom level as needed

                        // getting nearby available hotels hotels
                            double radius = 30;
                            List<HotelModel> nearbyHotels = dbManagement.getNearbyHotels(userLocation.latitude, userLocation.longitude, radius);
                            // Add markers for nearby hotels
                            for (HotelModel hotel : nearbyHotels) {
                                LatLng hotelLocation = new LatLng(Double.parseDouble(hotel.getLat()), Double.parseDouble(hotel.getLon()));
                                marker=  mMap.addMarker(new MarkerOptions().position(hotelLocation).title(hotel.getHotelName()));
                                marker.setTag(hotel);
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0f));
                               // clicking on marker
                                mMap.setOnMarkerClickListener(marker -> {
                                    // Get the hotel details associated with the clicked marker
                                    HotelModel selectedHotel = getHotelFromMarker(marker);

                                    // Open a new activity or fragment to display the details
                                    openHotelDetailsActivity(selectedHotel);

                                    // Return true to indicate that the click event has been consumed
                                    return true;
                                });
                            }
//                            clicking on marker
                            mMap.setOnMarkerClickListener(marker -> {
                                // Get the hotel details associated with the clicked marker
                                HotelModel selectedHotel = getHotelFromMarker(marker);

                                // Open a new activity or fragment to display the details
                                openHotelDetailsActivity(selectedHotel);

                                // Return true to indicate that the click event has been consumed
                                return true;
                            });
                       }
                    });
        }
    }

    private void initializeMap(LatLng userLocation) {
        // ...
        if (userCircle != null) {
            userCircle.remove();
        }
        // Add a blue circle representing the user's location
        userCircle = mMap.addCircle(new CircleOptions()
                .center(userLocation)
                .radius(50) // Set the radius of the circle (adjust as needed)
                .strokeColor(Color.BLUE) // Set the stroke color to blue
                .fillColor(Color.argb(70, 0, 0, 255))); // Set the fill color to a semi-transparent blue
        // Move the camera to the user's location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(userLocation);
        mMap.moveCamera(cameraUpdate);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));  // Adjust the zoom level as needed


    }


    private void updateMap(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Update the circle's center to the new location
        userCircle.setCenter(new LatLng(latitude, longitude));

        // Move the camera to the updated location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        mMap.moveCamera(cameraUpdate);
    }
    private HotelModel getHotelFromMarker(Marker marker) {
      return (HotelModel) marker.getTag();
    }
    private void openHotelDetailsActivity(HotelModel hotel) {
        // Create an Intent to start the hotel details activity
        Intent intent = new Intent(this, FirstFragment.class);

        // Pass the hotel details to the next activity
        intent.putExtra("hotelName", hotel.getHotelName());
        intent.putExtra("hotelID", hotel.getId_hotel());

        // Start the activity
        startActivity(intent);
    }
}