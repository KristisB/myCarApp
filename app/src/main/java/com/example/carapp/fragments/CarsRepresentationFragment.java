package com.example.carapp.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.carapp.appComponents.CarAppComponent;
import com.example.carapp.appComponents.ContextModule;
import com.example.carapp.appComponents.DaggerCarAppComponent;
import com.example.carapp.databinding.FragmentCarsRepresentationBinding;
import com.example.carapp.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsRepresentationFragment extends Fragment {
    private FragmentCarsRepresentationBinding binding;
    private List<Car> carList = new ArrayList<>();
    private Location mLocation;
    private CarListAdapter carListAdapter;
    private CarAppComponent appComponents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCarsRepresentationBinding.inflate(inflater, container, false);
        binding.carsRecycleView.setHasFixedSize(true);
        appComponents = DaggerCarAppComponent.builder()
                .contextModule(new ContextModule(getContext()))
                .build();

        appComponents.getApiService().getCars().enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {

                carList = response.body();
                carListAdapter = new CarListAdapter(carList, mLocation);
                binding.carsRecycleView.setAdapter(carListAdapter);
                binding.carsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.plateNumberSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        carListAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

                binding.sortByDistanceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                        PackageManager.PERMISSION_GRANTED) {
                            LocationManager locationManager = appComponents.getLocationManager();
                            Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            mLocation = myLocation;

                            if (myLocation != null) {
                                Log.d("myLocation", "onClick: myLocation" + myLocation.getLongitude() + myLocation.getLatitude());
                                carList.sort((car1, car2) -> Double.valueOf(car1.getDistance(myLocation)).compareTo(Double.valueOf(car2.getDistance(myLocation))));
                                carListAdapter = new CarListAdapter(carList, myLocation);
                                binding.carsRecycleView.setAdapter(carListAdapter);
                            } else {
                                Log.d("Location", "onClick: Location is null ");
                                ;
                            }
                        } else {
                            ActivityCompat.requestPermissions(requireActivity(), new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                            Toast.makeText(requireContext(), "Location permission is not granted. Use settings to turn location services on.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Log.d("CarList", "smth wrong - no car list received ");
                Log.d("CarList", Objects.requireNonNull(t.getMessage()));
            }
        });

        return binding.getRoot();
    }

}