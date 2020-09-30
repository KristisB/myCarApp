package com.example.carapp.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.carapp.MainActivity;
import com.example.carapp.R;
import com.example.carapp.databinding.FragmentCarsRepresentationBinding;
import com.example.carapp.model.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsRepresentationFragment extends Fragment {
    private FragmentCarsRepresentationBinding binding;
    List<Car> carList = new ArrayList<>();
    Location mLocation;
    MainActivity mainActivity;
    CarListAdapter carListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCarsRepresentationBinding.inflate(inflater, container, false);
        binding.carsRecycleView.setHasFixedSize(true);
        mainActivity = (MainActivity) requireActivity();

        mainActivity.getService().getCars().enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {

                carList = response.body();
                Log.d("CarList", "Car list received. List size " + carList.size());
                carListAdapter = new CarListAdapter(carList,mLocation);
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
                        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                        PackageManager.PERMISSION_GRANTED) {
                            Location myLocation = mainActivity.getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            mLocation=myLocation;

                            if (myLocation != null) {
                                Log.d("myLocation", "onClick: myLocation"+ myLocation.getLongitude()+myLocation.getLatitude());
                                carList.sort((car1, car2) -> new Double(car1.getDistance(myLocation)).compareTo(new Double(car2.getDistance(myLocation))));

                                carListAdapter = new CarListAdapter(carList,myLocation);
                                binding.carsRecycleView.setAdapter(carListAdapter);
                            } else {
                                Log.d("Location", "onClick: Location is null ");
                                ;
                            }
                        } else {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Log.d("CarList", "smth wrong - no car list received ");
                Log.d("CarList", t.getMessage());
            }
        });

        return binding.getRoot();
    }

}