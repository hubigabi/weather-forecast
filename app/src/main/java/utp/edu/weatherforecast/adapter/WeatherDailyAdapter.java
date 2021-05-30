package utp.edu.weatherforecast.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import utp.edu.weatherforecast.R;
import utp.edu.weatherforecast.entity.WeatherDaily;

public class WeatherDailyAdapter extends RecyclerView.Adapter<WeatherDailyAdapter.WeatherViewHolder> {

    private final List<WeatherDaily> weatherDailyList;

    public WeatherDailyAdapter(List<WeatherDaily> weatherDailyList) {
        this.weatherDailyList = weatherDailyList;
    }

    @NonNull
    @Override
    public WeatherDailyAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_daily_list_item, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        weatherViewHolder.weatherImageView = view.findViewById(R.id.weather_image_view);
        weatherViewHolder.weatherTextView = view.findViewById(R.id.weather_text_view);
        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDailyAdapter.WeatherViewHolder holder, int position) {
        WeatherDaily weatherDaily = weatherDailyList.get(position);
        holder.weatherTextView.setText(weatherDaily.toString());
        try {
            int id = R.drawable.class.getField("w" + weatherDaily.getIconWeather()).getInt(null);
            holder.weatherImageView.setImageResource(id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return weatherDailyList.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        public ImageView weatherImageView;
        public TextView weatherTextView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
