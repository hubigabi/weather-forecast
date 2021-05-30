package utp.edu.weatherforecast.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import utp.edu.weatherforecast.R;
import utp.edu.weatherforecast.entity.WeatherHourly;

public class HourlyFragment extends Fragment {

    private final String TAG = HourlyFragment.class.getSimpleName();
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hourly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "View created");
        textView = view.findViewById(R.id.hourly_text_view);
    }

    public void refresh(List<WeatherHourly> weatherHourlyList) {
        textView.setText(weatherHourlyList.toString());
    }

}