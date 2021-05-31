package utp.edu.weatherforecast.model;

import lombok.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//https://openweathermap.org/api/one-call-api
@Data
public class WeatherData {

    @SerializedName("lon")
    private Double lon;

    @SerializedName("lat")
    private Double lat;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("timezone_offset")
    private Integer timezoneOffset;

    @SerializedName("current")
    private Current current;

    @SerializedName("hourly")
    private List<Hourly> hourly;

    @SerializedName("daily")
    private List<Daily> daily;

    @Data
    public static class Current {

        @SerializedName("dt")
        private Double dt;

        @SerializedName("sunrise")
        private Integer sunrise;

        @SerializedName("sunset")
        private Integer sunset;

        @SerializedName("temp")
        private Double temp;

        @SerializedName("feels_like")
        private Double feelsLike;

        @SerializedName("pressure")
        private Integer pressure;

        @SerializedName("humidity")
        private Integer humidity;

        @SerializedName("dew_point")
        private Double dewPoint;

        @SerializedName("uvi")
        private Double uvi;

        @SerializedName("clouds")
        private Integer clouds;

        @SerializedName("visibility")
        private Integer visibility;

        @SerializedName("wind_speed")
        private Double windSpeed;

        @SerializedName("wind_deg")
        private Integer windDeg;

        @SerializedName("wind_gust")
        private Double windGust;

        @SerializedName("weather")
        private List<Weather> weather;

    }

    @Data
    public static class Hourly {

        @SerializedName("dt")
        private Double dt;

        @SerializedName("temp")
        private Double temp;

        @SerializedName("feels_like")
        private Double feelsLike;

        @SerializedName("pressure")
        private Integer pressure;

        @SerializedName("humidity")
        private Integer humidity;

        @SerializedName("dew_point")
        private Double dewPoint;

        @SerializedName("uvi")
        private Double uvi;

        @SerializedName("clouds")
        private Integer clouds;

        @SerializedName("visibility")
        private Integer visibility;

        @SerializedName("wind_speed")
        private Double windSpeed;

        @SerializedName("wind_deg")
        private Integer windDeg;

        @SerializedName("wind_gust")
        private Double windGust;

        @SerializedName("pop")
        private Double pop;

        @SerializedName("weather")
        private List<Weather> weather;
    }

    @Data
    public static class Daily {

        @SerializedName("dt")
        private Double dt;

        @SerializedName("sunrise")
        private Integer sunrise;

        @SerializedName("sunset")
        private Integer sunset;

        @SerializedName("moonrise")
        private Integer moonRise;

        @SerializedName("moonset")
        private Integer moonSet;

        @SerializedName("moon_phase")
        private Double moonPhase;

        @SerializedName("pressure")
        private Integer pressure;

        @SerializedName("humidity")
        private Integer humidity;

        @SerializedName("dew_point")
        private Double dewPoint;

        @SerializedName("wind_speed")
        private Double windSpeed;

        @SerializedName("wind_deg")
        private Integer windDeg;

        @SerializedName("wind_gust")
        private Double windGust;

        @SerializedName("clouds")
        private Integer clouds;

        @SerializedName("pop")
        private Double pop;

        @SerializedName("uvi")
        private Double uvi;

        @SerializedName("weather")
        private List<Weather> weather;

        @SerializedName("temp")
        private Temp temp;

        @SerializedName("feels_like")
        private FeelsLike feelsLike;

        @Data
        public static class Temp {

            @SerializedName("day")
            private Double day;

            @SerializedName("min")
            private Double min;

            @SerializedName("max")
            private Double max;

            @SerializedName("night")
            private Double night;

            @SerializedName("eve")
            private Double eve;

            @SerializedName("morn")
            private Double morn;
        }

        @Data
        public static class FeelsLike {

            @SerializedName("day")
            private Double day;

            @SerializedName("night")
            private Double night;

            @SerializedName("eve")
            private Double eve;

            @SerializedName("morn")
            private Double morn;
        }

    }

    @Data
    public static class Weather {
        @SerializedName("id")
        private Integer id;

        @SerializedName("main")
        private String main;

        @SerializedName("description")
        private String description;

        @SerializedName("icon")
        private String icon;
    }


}