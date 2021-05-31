package utp.edu.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import utp.edu.weatherforecast.entity.WeatherDaily;

import static utp.edu.weatherforecast.MainActivity.WEATHER_DAILY_KEY;

public class WeatherDailyDetailsActivity extends AppCompatActivity {

    private final String TAG = WeatherDailyDetailsActivity.class.getSimpleName();
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_daily_details);

        imageView = findViewById(R.id.weather_image_view);
        textView = findViewById(R.id.weather_text_view);

        WeatherDaily weatherDaily = (WeatherDaily) getIntent().getSerializableExtra(WEATHER_DAILY_KEY);
        if (weatherDaily != null) {
            try {
                int id = R.drawable.class.getField("w" + weatherDaily.getIconWeather()).getInt(null);
                imageView.setImageResource(id);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }

            textView.setText(weatherDaily.toString());
        } else {
            finish();
        }

    }
}