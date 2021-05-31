package utp.edu.weatherforecast.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import utp.edu.weatherforecast.R;
import utp.edu.weatherforecast.entity.WeatherHourly;

public class WeatherHourlyAdapter extends RecyclerView.Adapter<WeatherHourlyAdapter.WeatherViewHolder> {

    private final List<WeatherHourly> weatherHourlyList;
    private ItemClickListener itemClickListener;

    public WeatherHourlyAdapter(List<WeatherHourly> weatherHourlyList) {
        this.weatherHourlyList = weatherHourlyList;
    }

    @NonNull
    @Override
    public WeatherHourlyAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_hourly_list_item, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        weatherViewHolder.weatherImageView = view.findViewById(R.id.weather_image_view);
        weatherViewHolder.headerTextView = view.findViewById(R.id.header_text_view);
        weatherViewHolder.windTextView = view.findViewById(R.id.wind_text_view);
        weatherViewHolder.humidityTextView = view.findViewById(R.id.humidity_text_view);
        weatherViewHolder.cloudinessTextView = view.findViewById(R.id.cloudiness_text_view);
        weatherViewHolder.pressureTextView = view.findViewById(R.id.pressure_text_view);
        weatherViewHolder.feelsLikeTextView = view.findViewById(R.id.feels_like_text_view);
        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHourlyAdapter.WeatherViewHolder holder, int position) {
        WeatherHourly weatherHourly = weatherHourlyList.get(position);
        try {
            int id = R.drawable.class.getField("w" + weatherHourly.getIconWeather()).getInt(null);
            holder.weatherImageView.setImageResource(id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        holder.headerTextView.setText(getHeader(weatherHourly));
        holder.windTextView.setText(String.format(Locale.getDefault(), weatherHourly.getWindSpeed() + "m/s"));
        holder.humidityTextView.setText(String.format(Locale.getDefault(), "%d%%", weatherHourly.getHumidity()));
        holder.cloudinessTextView.setText(String.format(Locale.getDefault(), "%d%%", weatherHourly.getClouds()));
        holder.pressureTextView.setText(String.format(Locale.getDefault(), "%d hPa", weatherHourly.getPressure()));
        holder.feelsLikeTextView.setText(String.format(Locale.getDefault(), "%d \u2103", weatherHourly.getFeelsLike().intValue()));
    }

    @Override
    public int getItemCount() {
        return weatherHourlyList.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView weatherImageView;
        public TextView headerTextView;
        public TextView windTextView;
        public TextView humidityTextView;
        public TextView cloudinessTextView;
        public TextView pressureTextView;
        public TextView feelsLikeTextView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    public WeatherHourly getItem(int id) {
        return weatherHourlyList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private String getHeader(WeatherHourly weatherHourly) {
        StringBuilder sb = new StringBuilder();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date date = new Date((long) (weatherHourly.getDt() * 1000L));
        sb.append(dateFormat.format(date)).append(System.lineSeparator());
        sb.append(weatherHourly.getTemp().intValue()).append(" \u2103");
        return sb.toString();
    }

}
