package utp.edu.weatherforecast.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
public class WeatherDaily {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private Double lon;
    private Double lat;

    private Double dt;
    private Integer sunrise;
    private Integer sunset;
    private Integer moonRise;
    private Integer moonSet;
    private Double moonPhase;
    private Integer pressure;
    private Integer humidity;
    private Double dewPoint;
    private Double windSpeed;
    private Integer windDeg;
    private Double windGust;
    private Integer clouds;
    private Double pop;
    private Double uvi;

    private Double dayTemp;
    private Double minTemp;
    private Double maxTemp;
    private Double nightTemp;
    private Double eveTemp;
    private Double mornTemp;

    private Double dayFeelsLike;
    private Double nightFeelsLike;
    private Double eveFeelsLike;
    private Double mornFeelsLike;

    private Integer idWeather;
    private String mainWeather;
    private String descriptionWeather;
    private String iconWeather;

    private String convertTime(int unixTime) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        Date date = new Date(unixTime * 1000L);
        return dateFormat.format(date);
    }

    @NonNull
    @Override
    public String toString() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        Date date = new Date((long) (dt * 1000L));

        return "Forecast time: " + dateFormat.format(date) + "\n" +
                "Sunrise time: " + convertTime(sunrise) + "\n" +
                "Sunset time: " + convertTime(sunset) + "\n" +
                "Moonrise time: " + convertTime(moonRise) + "\n" +
                "Moonset time: " + convertTime(moonSet) + "\n" +
                "Moon phase: " + moonPhase + "\n" +
                "Day temperature: " + Math.round(dayTemp) + "\u2103\n" +
                "Perceptible daily temperature: " + Math.round(dayFeelsLike) + "\u2103\n" +
                "Max daily temperature: " + Math.round(maxTemp) + "\u2103\n" +
                "Min daily temperature: " + Math.round(minTemp) + "\u2103\n" +
                "Night temperature: " + Math.round(nightTemp) + "\u2103\n" +
                "Perceptible night temperature: " + Math.round(nightFeelsLike) + "\u2103\n" +
                "Pressure: " + pressure + " hPa\n" +
                "Humidity: " + humidity + "%\n" +
                "Cloudiness: " + clouds + "%\n" +
                "Max value of UV index: " + uvi + "\n" +
                "Wind speed: " + windSpeed + " m/s\n" +
                "Wind gust: " + windGust + " m/s\n" +
                "Probability of precipitation: " + Math.round(pop * 100) + "%";
    }
}
