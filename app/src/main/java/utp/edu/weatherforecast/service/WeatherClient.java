package utp.edu.weatherforecast.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import utp.edu.weatherforecast.BuildConfig;

public class WeatherClient {

    private static WeatherService weatherService;

    private final static String headerApiKeyName = "x-api-key";
    private final static String apiKey = BuildConfig.API_KEY;;

    public static WeatherService getWeatherService() {
        if (weatherService == null) {
            synchronized (WeatherClient.class) {
                OkHttpClient httpClient = new OkHttpClient.Builder()
                        .addInterceptor(
                                chain -> {
                                    Request request = chain.request()
                                            .newBuilder()
                                            .addHeader(headerApiKeyName, apiKey)
                                            .build();
                                    return chain.proceed(request);
                                }).build();

                Gson gson = new GsonBuilder()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.openweathermap.org/data/2.5/")
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                weatherService = retrofit.create(WeatherService.class);
            }
        }
        return weatherService;
    }

}
