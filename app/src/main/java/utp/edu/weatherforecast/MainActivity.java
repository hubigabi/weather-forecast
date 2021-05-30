package utp.edu.weatherforecast;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.techhelper.segment.OnSegmentCheckedChangeListener;
import com.techhelper.segment.Segment;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import utp.edu.weatherforecast.adapter.WeatherAdapter;
import utp.edu.weatherforecast.db.WeatherDatabase;
import utp.edu.weatherforecast.entity.Weather;
import utp.edu.weatherforecast.mapper.WeatherMapper;
import utp.edu.weatherforecast.service.WeatherClient;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        OnSegmentCheckedChangeListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private final String[] perms = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION};
    private final int PERMISSIONS_REQUEST_CODE = 1000;
    public static final String CURRENT_LAT_KEY = "lat";
    public static final String CURRENT_LON_KEY = "lon";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private ActivityResultLauncher<Intent> locationActivityResultLauncher;
    private Geocoder geocoder;
    private Button refreshButton;
    private Button chooseLocationButton;
    private Segment segment;
    private RecyclerView weatherRecyclerView;
    private Weather weather;
    private LatLng currentLatLng = new LatLng(0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateValuesFromBundle(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        refreshButton = findViewById(R.id.refresh_button);
        chooseLocationButton = findViewById(R.id.choose_location_button);
        segment = (Segment) findViewById(R.id.segments);
        weatherRecyclerView = (RecyclerView) findViewById(R.id.weather_recycler_view);
        refreshButton.setOnClickListener(v -> refresh(currentLatLng.latitude, currentLatLng.longitude));
        chooseLocationButton.setOnClickListener(v -> chooseLocation());

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    Log.i(TAG, String.format(Locale.getDefault(), "Current location lat: %.2f, lon: %.2f",
                            currentLatLng.latitude, currentLatLng.longitude));
                }
            }
        };

        locationActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String text;
                        if (intent != null) {
                            double lat = intent.getDoubleExtra(CURRENT_LAT_KEY, 0);
                            double lon = intent.getDoubleExtra(CURRENT_LON_KEY, 0);

                            text = String.format(Locale.getDefault(), "Chosen location" + System.lineSeparator()
                                    + "Lat: %.2f, Lon: %.2f", lat, lon);
                            refresh(lat, lon);
                        } else {
                            text = "Location has not been chosen";
                        }
                        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                    }
                });

        setupSegment();
        setLocation();
    }

    private void requestPermissions() {
        if (!EasyPermissions.hasPermissions(this, perms)) {
            Log.e(TAG, getString(R.string.permission_text));
            EasyPermissions.requestPermissions(this, getString(R.string.permission_text),
                    PERMISSIONS_REQUEST_CODE, perms);
        }
    }

    private void setupSegment() {
        List<String> segmentTitles = Arrays.asList("Hourly", "Daily");
        segment.setTitles(segmentTitles);
        segment.setOnSegmentCheckedChangedListener(this);
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(PERMISSIONS_REQUEST_CODE)
    private void setLocation() {
        if (EasyPermissions.hasPermissions(this, perms)) {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                String text = (addresses.get(0).getLocality() != null)
                                        ? String.format("Current location: %s", addresses.get(0).getLocality())
                                        : String.format(Locale.getDefault(), "Lat: %.2f, Lon: %.2f",
                                        addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } else {
            Log.e(TAG, getString(R.string.permission_text));
            requestPermissions();
        }
    }

    private void refresh(double latitude, double longitude) {
        if (EasyPermissions.hasPermissions(this, perms)) {

            Disposable disposable = WeatherClient.getWeatherService()
                    .getWeather(latitude, longitude)
                    .subscribeOn(Schedulers.io())
                    .map(WeatherMapper::mapToWeather)
                    .doOnNext(weather -> {
                        Disposable d1 = WeatherDatabase.getInstance(this).weatherHourlyDao()
                                .insertAll(weather.getWeatherHourlyList())
                                .subscribe(() -> Log.i(TAG, "Data from hourly weather inserted to db"));

                        Disposable d2 = WeatherDatabase.getInstance(this).weatherDailyDao()
                                .insertAll(weather.getWeatherDailyList())
                                .subscribe(() -> Log.i(TAG, "Data from daily weather inserted to db"));
                        compositeDisposable.addAll(d1, d2);
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weather -> {
                        this.weather = weather;
                        segment.setSegmentChecked(0, true);
                    }, throwable -> {
                        Log.e(TAG, "Unable to get data", throwable);
                        Toast.makeText(this, "Unable to get data", Toast.LENGTH_SHORT).show();
                    });
            compositeDisposable.add(disposable);

        } else {
            Log.e(TAG, getString(R.string.permission_text));
            requestPermissions();
        }
    }

    private void chooseLocation() {
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra(CURRENT_LAT_KEY, currentLatLng.latitude);
        intent.putExtra(CURRENT_LON_KEY, currentLatLng.longitude);
        locationActivityResultLauncher.launch(intent);
    }

    @Override
    public void onSegmentCheckedChanged(int position, CompoundButton buttonView, boolean isChecked) {
        if (weather != null) {
            weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            weatherRecyclerView.setItemAnimator(new DefaultItemAnimator());
            weatherRecyclerView.setAdapter(new WeatherAdapter(weather, position));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.i(TAG, "Permissions granted");
        Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (currentLatLng != null) {
            outState.putDouble(CURRENT_LAT_KEY, currentLatLng.latitude);
            outState.putDouble(CURRENT_LON_KEY, currentLatLng.longitude);
        }
        super.onSaveInstanceState(outState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }

        if (savedInstanceState.keySet().contains(CURRENT_LAT_KEY)
                && savedInstanceState.keySet().contains(CURRENT_LON_KEY)) {
            currentLatLng = new LatLng(savedInstanceState.getDouble(CURRENT_LAT_KEY),
                    savedInstanceState.getDouble(CURRENT_LON_KEY));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(PERMISSIONS_REQUEST_CODE)
    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(1000);

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

}