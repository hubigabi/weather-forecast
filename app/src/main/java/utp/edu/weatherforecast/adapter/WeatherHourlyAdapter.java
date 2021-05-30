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
import utp.edu.weatherforecast.entity.WeatherHourly;

public class WeatherHourlyAdapter extends RecyclerView.Adapter<WeatherHourlyAdapter.WeatherViewHolder> {

    private final List<WeatherHourly> weatherHourlyList;

    public WeatherHourlyAdapter(List<WeatherHourly> weatherHourlyList) {
        this.weatherHourlyList = weatherHourlyList;
    }

    @NonNull
    @Override
    public WeatherHourlyAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_hourly_list_item, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        weatherViewHolder.weatherImageView = view.findViewById(R.id.weather_image_view);
        weatherViewHolder.weatherTextView = view.findViewById(R.id.weather_text_view);
        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHourlyAdapter.WeatherViewHolder holder, int position) {
        WeatherHourly weatherHourly = weatherHourlyList.get(position);
        holder.weatherTextView.setText(weatherHourly.toString());
        try {
            int id = R.drawable.class.getField("w" + weatherHourly.getIconWeather()).getInt(null);
            holder.weatherImageView.setImageResource(id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return weatherHourlyList.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        public ImageView weatherImageView;
        public TextView weatherTextView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
