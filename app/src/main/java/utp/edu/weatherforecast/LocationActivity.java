package utp.edu.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static utp.edu.weatherforecast.MainActivity.CURRENT_LAT_KEY;
import static utp.edu.weatherforecast.MainActivity.CURRENT_LON_KEY;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button chosenLocationButton;
    private Geocoder geocoder;
    private GoogleMap googleMap;
    private LatLng chosenLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        chosenLocationButton = findViewById(R.id.location_chosen_button);
        chosenLocationButton.setOnClickListener(v -> chosenLocation());

        geocoder = new Geocoder(this, Locale.getDefault());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        chosenLocation = new LatLng(getIntent().getDoubleExtra(CURRENT_LAT_KEY, 0),
                getIntent().getDoubleExtra(CURRENT_LON_KEY, 0));
    }

    private void chosenLocation() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(CURRENT_LAT_KEY, chosenLocation.latitude);
        returnIntent.putExtra(CURRENT_LON_KEY, chosenLocation.longitude);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chosenLocation, 12.0f));

        googleMap.addMarker(new MarkerOptions()
                .position(chosenLocation)
                .title("Your current location"));

        googleMap.setOnMapClickListener(latLng -> {
            chosenLocation = latLng;
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Your new location"));

            try {
                List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                String text = (addresses.get(0).getLocality() != null)
                        ? String.format("City: %s", addresses.get(0).getLocality())
                        : String.format(Locale.getDefault(), "Lat: %.2f, Lon: %.2f",
                        addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}