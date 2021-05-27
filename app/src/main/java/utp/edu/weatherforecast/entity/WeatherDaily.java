package utp.edu.weatherforecast.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
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

    private Integer idWeather;
    private String mainWeather;
    private String descriptionWeather;
    private String iconWeather;

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

}
