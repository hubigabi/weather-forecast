package utp.edu.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import utp.edu.weatherforecast.service.WeatherClient;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int INTERNET_REQUEST_CODE = 1;
    private static final int LOCATION_REQUEST_CODE = 2;
    private static final String TAG = MainActivity.class.getSimpleName();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FusedLocationProviderClient fusedLocationClient;
    private Geocoder geocoder;
    private Button refreshButton;
    private Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        refreshButton = findViewById(R.id.refresh_button);
        locationButton = findViewById(R.id.location_button);
        refreshButton.setOnClickListener(v -> refresh());
        locationButton.setOnClickListener(v -> location());
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(LOCATION_REQUEST_CODE)
    private void location() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            System.out.println(location);

                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                Toast.makeText(this, String.format("City: %s, Lat: %s, Lon: %s",
                                        addresses.get(0).getLocality(), location.getLatitude(), location.getLongitude()),
                                        Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } else {
            Log.e(TAG, getString(R.string.location_permission));
            EasyPermissions.requestPermissions(this, getString(R.string.location_permission),
                    LOCATION_REQUEST_CODE, perms);
        }
    }

    @AfterPermissionGranted(INTERNET_REQUEST_CODE)
    private void refresh() {
        String[] perms = {Manifest.permission.INTERNET};
        if (EasyPermissions.hasPermissions(this, perms)) {

            Disposable disposable = WeatherClient.getWeatherService()
                    .getWeather(35.6577, 139.294)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherData -> {
                        System.out.println(weatherData);
                    }, throwable -> {
                        Log.e(TAG, "Unable to get data", throwable);
                        Toast.makeText(this, "Unable to get data", Toast.LENGTH_SHORT).show();
                    });
            compositeDisposable.add(disposable);

        } else {
            Log.e(TAG, getString(R.string.internet_permission));
            EasyPermissions.requestPermissions(this, getString(R.string.internet_permission),
                    INTERNET_REQUEST_CODE, perms);
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

    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

}