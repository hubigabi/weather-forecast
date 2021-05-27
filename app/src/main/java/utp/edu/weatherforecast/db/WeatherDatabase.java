package utp.edu.weatherforecast.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import utp.edu.weatherforecast.dao.WeatherDailyDao;
import utp.edu.weatherforecast.dao.WeatherHourlyDao;
import utp.edu.weatherforecast.entity.WeatherDaily;
import utp.edu.weatherforecast.entity.WeatherHourly;

@Database(entities = {WeatherDaily.class, WeatherHourly.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    private static final String dbName = "weather";

    public abstract WeatherDailyDao weatherDailyDao();

    public abstract WeatherHourlyDao weatherHourlyDao();

    private static volatile WeatherDatabase INSTANCE;

    public static WeatherDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (WeatherDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherDatabase.class, dbName)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}