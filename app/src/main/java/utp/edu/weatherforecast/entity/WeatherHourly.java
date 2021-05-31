package utp.edu.weatherforecast.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
public class WeatherHourly implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private Long createdDate;
    private Double lon;
    private Double lat;

    private Double dt;
    private Double temp;
    private Double feelsLike;
    private Integer pressure;
    private Integer humidity;
    private Double dewPoint;
    private Double uvi;
    private Integer clouds;
    private Integer visibility;
    private Double windSpeed;
    private Integer windDeg;
    private Double windGust;
    private Double pop;

    private Integer idWeather;
    private String mainWeather;
    private String descriptionWeather;
    private String iconWeather;

    @NonNull
    @Override
    public String toString() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date date = new Date((long) (dt * 1000L));

        return "Forecast time: " + dateFormat.format(date) + "\n" +
                "Temperature: " + Math.round(temp) + "\u2103\n" +
                "Perceptible temperature: " + Math.round(feelsLike) + "\u2103\n" +
                "Pressure: " + pressure + " hPa\n" +
                "Humidity: " + humidity + "%\n" +
                "Cloudiness: " + clouds + "%\n" +
                "UV index: " + uvi + "\n" +
                "Visibility: " + visibility + " m\n" +
                "Wind speed: " + windSpeed + " m/s\n" +
                "Wind gust: " + windGust + " m/s\n" +
                "Probability of precipitation: " + Math.round(pop * 100) + "%";
    }
}
