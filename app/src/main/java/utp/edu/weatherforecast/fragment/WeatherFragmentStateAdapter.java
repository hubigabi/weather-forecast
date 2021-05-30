package utp.edu.weatherforecast.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WeatherFragmentStateAdapter extends FragmentStateAdapter {

    public WeatherFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HourlyFragment();
            case 1:
                return new DailyFragment();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}