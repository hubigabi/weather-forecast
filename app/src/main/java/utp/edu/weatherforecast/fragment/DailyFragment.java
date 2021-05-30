package utp.edu.weatherforecast.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import utp.edu.weatherforecast.R;
import utp.edu.weatherforecast.entity.WeatherDaily;

public class DailyFragment extends Fragment {

    private final String TAG = DailyFragment.class.getSimpleName();
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daily, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "View created");
        textView = view.findViewById(R.id.daily_text_view);
    }

    public void refresh(List<WeatherDaily> weatherDailyList) {
        textView.setText(weatherDailyList.toString());
    }

}