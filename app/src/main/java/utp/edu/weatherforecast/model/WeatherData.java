package utp.edu.weatherforecast.model;

import lombok.Data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;

//https://openweathermap.org/current
@Data
public class WeatherData {

    @SerializedName("coord")
    private Coord coord;

    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("base")
    private String base;

    @SerializedName("main")
    private Main main;

    @SerializedName("visibility")
    private Integer visibility;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    @JsonAdapter(CloudsDeserializer.class)
    private Integer cloudsAll;

    @SerializedName("dt")
    private Integer dt;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("timezone")
    private Integer timezone;

    @SerializedName("id")
    private Integer cityId;

    @SerializedName("name")
    private String cityName;

    @Data
    public static class Coord {
        @SerializedName("lon")
        private Double lon;

        @SerializedName("lat")
        private Double lat;
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

    @Data
    public static class Main {
        @SerializedName("temp")
        private Double temp;

        @SerializedName("feels_like")
        private Double feelsLike;

        @SerializedName("temp_min")
        private Double tempMin;

        @SerializedName("temp_max")
        private Double tempMax;

        @SerializedName("pressure")
        private Integer pressure;

        @SerializedName("humidity")
        private Integer humidity;
    }

    @Data
    public static class Wind {
        @SerializedName("speed")
        private Double speed;

        @SerializedName("deg")
        private Integer deg;

        @SerializedName("gust")
        private Double gust;
    }

    @Data
    public static class Sys {
        @SerializedName("country")
        private String country;

        @SerializedName("sunrise")
        private Integer sunrise;

        @SerializedName("sunset")
        private Integer sunset;
    }

    public static class CloudsDeserializer implements JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            return json.getAsJsonObject().get("all").getAsInt();
        }
    }

}