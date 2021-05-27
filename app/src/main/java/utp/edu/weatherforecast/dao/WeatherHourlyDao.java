package utp.edu.weatherforecast.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import utp.edu.weatherforecast.entity.WeatherHourly;

@Dao
public interface WeatherHourlyDao {

    @Query("SELECT * FROM weatherhourly")
    Single<List<WeatherHourly>> getAll();

    @Insert
    Completable insert(WeatherHourly weatherHourly);

    @Insert
    Completable insertAll(List<WeatherHourly> weatherHourlyList);

}
