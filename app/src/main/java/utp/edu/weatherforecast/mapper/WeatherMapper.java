package utp.edu.weatherforecast.mapper;

import java.util.List;
import java.util.stream.Collectors;

import utp.edu.weatherforecast.entity.Weather;
import utp.edu.weatherforecast.entity.WeatherDaily;
import utp.edu.weatherforecast.entity.WeatherHourly;
import utp.edu.weatherforecast.model.WeatherData;

public class WeatherMapper {

    public static Weather mapToWeather(WeatherData weatherData) {
        final Double lat = weatherData.getLat();
        final Double lon = weatherData.getLon();

        List<WeatherHourly> weatherHourlyList = weatherData.getHourly().stream()
                .map(hourly -> mapToWeatherHourly(hourly, lat, lon))
                .collect(Collectors.toList());

        List<WeatherDaily> weatherDailyList = weatherData.getDaily().stream()
                .map(daily -> mapToWeatherDaily(daily, lat, lon))
                .collect(Collectors.toList());

        return new Weather(weatherHourlyList, weatherDailyList);
    }

    private static WeatherHourly mapToWeatherHourly(WeatherData.Hourly hourly, Double lat, Double lon) {
        WeatherHourly weatherHourly = WeatherHourly.builder()
                .lon(lat)
                .lat(lon)
                .dt(hourly.getDt())
                .temp(hourly.getTemp())
                .feelsLike(hourly.getFeelsLike())
                .pressure(hourly.getPressure())
                .humidity(hourly.getHumidity())
                .dewPoint(hourly.getDewPoint())
                .uvi(hourly.getUvi())
                .clouds(hourly.getClouds())
                .visibility(hourly.getVisibility())
                .windSpeed(hourly.getWindSpeed())
                .windDeg(hourly.getWindDeg())
                .windGust(hourly.getWindGust())
                .build();

        List<WeatherData.Weather> weatherList = hourly.getWeather();
        if (weatherList.size() > 0) {
            WeatherData.Weather weather = weatherList.get(0);

            weatherHourly.setIdWeather(weather.getId());
            weatherHourly.setMainWeather(weather.getMain());
            weatherHourly.setDescriptionWeather(weather.getDescription());
            weatherHourly.setIconWeather(weather.getIcon());
        }

        return weatherHourly;
    }

    private static WeatherDaily mapToWeatherDaily(WeatherData.Daily daily, Double lat, Double lon) {
        WeatherDaily weatherDaily = WeatherDaily.builder()
                .lon(lat)
                .lat(lon)
                .dt(daily.getDt())
                .sunrise(daily.getSunrise())
                .sunset(daily.getSunset())
                .moonRise(daily.getMoonRise())
                .moonSet(daily.getMoonSet())
                .moonPhase(daily.getMoonPhase())
                .pressure(daily.getPressure())
                .humidity(daily.getHumidity())
                .dewPoint(daily.getDewPoint())
                .windSpeed(daily.getWindSpeed())
                .windDeg(daily.getWindDeg())
                .windGust(daily.getWindGust())
                .clouds(daily.getClouds())
                .pop(daily.getPop())
                .uvi(daily.getUvi())
                .dayTemp(daily.getTemp().getDay())
                .minTemp(daily.getTemp().getMin())
                .maxTemp(daily.getTemp().getMax())
                .nightTemp(daily.getTemp().getNight())
                .eveTemp(daily.getTemp().getEve())
                .mornTemp(daily.getTemp().getMorn())
                .dayFeelsLike(daily.getFeelsLike().getDay())
                .nightFeelsLike(daily.getFeelsLike().getNight())
                .eveFeelsLike(daily.getFeelsLike().getEve())
                .mornFeelsLike(daily.getFeelsLike().getMorn())
                .build();

        List<WeatherData.Weather> weatherList = daily.getWeather();
        if (weatherList.size() > 0) {
            WeatherData.Weather weather = weatherList.get(0);

            weatherDaily.setIdWeather(weather.getId());
            weatherDaily.setMainWeather(weather.getMain());
            weatherDaily.setDescriptionWeather(weather.getDescription());
            weatherDaily.setIconWeather(weather.getIcon());
        }

        return weatherDaily;
    }


}
