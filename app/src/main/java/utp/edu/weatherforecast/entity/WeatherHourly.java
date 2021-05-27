package utp.edu.weatherforecast.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utp.edu.weatherforecast.model.WeatherData;

@Entity
@Data
@Builder
@AllArgsConstructor
public class WeatherHourly {

    @PrimaryKey(autoGenerate = true)
    public int id;

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

    private Integer idWeather;
    private String mainWeather;
    private String descriptionWeather;
    private String iconWeather;

}
