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
import utp.edu.weatherforecast.entity.WeatherDaily;

public class WeatherDailyAdapter extends RecyclerView.Adapter<WeatherDailyAdapter.WeatherViewHolder> {

    private final List<WeatherDaily> weatherDailyList;
    private ItemClickListener itemClickListener;

    public WeatherDailyAdapter(List<WeatherDaily> weatherDailyList) {
        this.weatherDailyList = weatherDailyList;
    }

    @NonNull
    @Override
    public WeatherDailyAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_daily_list_item, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        weatherViewHolder.weatherImageView = view.findViewById(R.id.weather_image_view);
        weatherViewHolder.headerTextView = view.findViewById(R.id.header_text_view);
        weatherViewHolder.windTextView = view.findViewById(R.id.wind_text_view);
        weatherViewHolder.humidityTextView = view.findViewById(R.id.humidity_text_view);
        weatherViewHolder.cloudinessTextView = view.findViewById(R.id.cloudiness_text_view);
        weatherViewHolder.pressureTextView = view.findViewById(R.id.pressure_text_view);
        weatherViewHolder.feelsLikeTextView = view.findViewById(R.id.feels_like_text_view);
        weatherViewHolder.nightTextView = view.findViewById(R.id.night_text_view);
        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDailyAdapter.WeatherViewHolder holder, int position) {
        WeatherDaily weatherDaily = weatherDailyList.get(position);
        try {
            int id = R.drawable.class.getField("w" + weatherDaily.getIconWeather()).getInt(null);
            holder.weatherImageView.setImageResource(id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        holder.headerTextView.setText(getHeader(weatherDaily));
        holder.windTextView.setText(String.format(Locale.getDefault(), weatherDaily.getWindSpeed() + "m/s"));
        holder.humidityTextView.setText(String.format(Locale.getDefault(), "%d%%", weatherDaily.getHumidity()));
        holder.cloudinessTextView.setText(String.format(Locale.getDefault(), "%d%%", weatherDaily.getClouds()));
        holder.pressureTextView.setText(String.format(Locale.getDefault(), "%d hPa", weatherDaily.getPressure()));
        holder.feelsLikeTextView.setText(String.format(Locale.getDefault(), "%d \u2103", Math.round(weatherDaily.getDayFeelsLike())));
        holder.nightTextView.setText(String.format(Locale.getDefault(), "%d \u2103", Math.round(weatherDaily.getNightTemp())));
    }

    @Override
    public int getItemCount() {
        return weatherDailyList.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView weatherImageView;
        public TextView headerTextView;
        public TextView windTextView;
        public TextView humidityTextView;
        public TextView cloudinessTextView;
        public TextView pressureTextView;
        public TextView feelsLikeTextView;
        public TextView nightTextView;

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

    public WeatherDaily getItem(int id) {
        return weatherDailyList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private String getHeader(WeatherDaily weatherDaily) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date date = new Date((long) (weatherDaily.getDt() * 1000L));
        sb.append(dateFormat.format(date)).append(System.lineSeparator());
        sb.append(Math.round(weatherDaily.getDayTemp())).append(" \u2103");
        return sb.toString();
    }

}
