package utp.edu.weatherforecast.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import utp.edu.weatherforecast.entity.WeatherDaily;

@Dao
public interface WeatherDailyDao {

    @Query("SELECT * FROM weatherdaily")
    Single<List<WeatherDaily>> getAll();

    @Query("SELECT * FROM weatherdaily WHERE createdDate = (SELECT MAX(createdDate) FROM weatherdaily )")
    Single<List<WeatherDaily>> getLatest();

    @Insert
    Completable insert(WeatherDaily weatherDaily);

    @Insert
    Completable insertAll(List<WeatherDaily> weatherDailyList);

}
