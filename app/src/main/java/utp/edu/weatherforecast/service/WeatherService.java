package utp.edu.weatherforecast.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import utp.edu.weatherforecast.model.WeatherData;

public interface WeatherService {

    @GET("onecall?units=metric&exclude=minutely,alerts")
    Observable<WeatherData> getWeather(@Query("lat") Double lat, @Query("lon") Double lon);
}
