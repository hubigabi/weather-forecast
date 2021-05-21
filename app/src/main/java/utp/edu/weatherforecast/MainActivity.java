package utp.edu.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

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
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button refreshButton;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        refreshButton = findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(v -> refresh());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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