package utp.edu.weatherforecast.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Weather {

    public List<WeatherHourly> weatherHourlyList;
    public List<WeatherDaily> weatherDailyList;

}
