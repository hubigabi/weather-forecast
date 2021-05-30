package utp.edu.weatherforecast.adapter;

public enum WeatherType {
    HOURLY(0),
    DAILY(1);

    private final int value;

    WeatherType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
