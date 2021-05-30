package utp.edu.weatherforecast.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import utp.edu.weatherforecast.R;
import utp.edu.weatherforecast.entity.Weather;
import utp.edu.weatherforecast.entity.WeatherDaily;
import utp.edu.weatherforecast.entity.WeatherHourly;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private final Weather weather;
    private final int positionOnSegment;

    public WeatherAdapter(Weather weather, int positionOnSegment) {
        this.weather = weather;
        this.positionOnSegment = positionOnSegment;
    }

    @NonNull
    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_list_item, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        weatherViewHolder.weatherImageView = view.findViewById(R.id.weather_image_view);
        weatherViewHolder.weatherTextView = view.findViewById(R.id.weather_text_view);

        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.WeatherViewHolder holder, int position) {
        if (positionOnSegment == WeatherType.DAILY.getValue()) {
            WeatherDaily weatherDaily = weather.getWeatherDailyList().get(position);
            holder.weatherTextView.setText(weatherDaily.toString());
            try {
                int id = R.drawable.class.getField("w" + weatherDaily.getIconWeather()).getInt(null);
                holder.weatherImageView.setImageResource(id);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        } else {
            WeatherHourly weatherHourly = weather.getWeatherHourlyList().get(position);
            holder.weatherTextView.setText(weatherHourly.toString());
            try {
                int id = R.drawable.class.getField("w" + weatherHourly.getIconWeather()).getInt(null);
                holder.weatherImageView.setImageResource(id);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (positionOnSegment == WeatherType.DAILY.getValue()) {
            return weather.getWeatherDailyList().size();
        } else {
            return weather.getWeatherHourlyList().size();
        }
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        public ImageView weatherImageView;
        public TextView weatherTextView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
