package utp.edu.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import utp.edu.weatherforecast.entity.WeatherHourly;

import static utp.edu.weatherforecast.MainActivity.WEATHER_HOURLY_KEY;

public class WeatherHourlyDetailsActivity extends AppCompatActivity {

    private final String TAG = WeatherHourlyDetailsActivity.class.getSimpleName();
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_hourly_details);

        imageView = findViewById(R.id.weather_image_view);
        textView = findViewById(R.id.weather_text_view);

        WeatherHourly weatherHourly = (WeatherHourly) getIntent().getSerializableExtra(WEATHER_HOURLY_KEY);
        if (weatherHourly != null) {
            try {
                int id = R.drawable.class.getField("w" + weatherHourly.getIconWeather()).getInt(null);
                imageView.setImageResource(id);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }

            textView.setText(weatherHourly.toString());
        } else {
            finish();
        }

    }
}