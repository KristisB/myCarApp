package com.example.carapp.fragments;

import android.graphics.Bitmap;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.carapp.databinding.FragmentCarsListItemBinding;
import com.example.carapp.model.Car;
import com.example.carapp.utils.DownloadImageTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarListViewHolder> implements Filterable {
    private List<Car> carsList = new ArrayList<Car>();
    private List<Car> allCarsList;
    private Location myLocation;

    public CarListAdapter(List<Car> carsList, Location myLocation) {
        this.carsList = carsList;
        this.allCarsList = new ArrayList<>(carsList);
        this.myLocation = myLocation;
    }

    @NonNull
    @Override
    public CarListAdapter.CarListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentCarsListItemBinding binding = FragmentCarsListItemBinding.inflate(inflater, parent, false);

        return new CarListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarListViewHolder holder, int position) {
        holder.bind(carsList.get(position));
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }



    @Override
    public Filter getFilter() {

        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Car> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(allCarsList);
            } else {
                for (Car car : allCarsList) {
                    if (car.getPlateNumber().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(car);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            carsList.clear();
            carsList.addAll((Collection<? extends Car>) results.values);
            notifyDataSetChanged();

        }
    };


    public class CarListViewHolder extends RecyclerView.ViewHolder {
        private FragmentCarsListItemBinding binding;


        public CarListViewHolder(@NonNull FragmentCarsListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Car car) {
            binding.carModelText.setText(car.getModel().getTitle());
            binding.carPlate.setText(car.getPlateNumber());
            binding.carLocation.setText(car.getLocation().getAddress());
            String remainingBatery = "Remaining batery: " + car.getBatteryPercentage() + "%";
            binding.remainingBatery.setText(remainingBatery);
            if (myLocation != null) {

                binding.carDistance.setText("Distance to car " + String.format("%.2f",car.getDistance(myLocation))+" km");
            } else {
                binding.carDistance.setText("unable to calculate distance");
            }

            //binding carIcons
            DownloadImageTask task = new DownloadImageTask();
            Bitmap carIcon;
            try {
                carIcon = task.execute(car.getModel().getPhotoUrl()).get();
                binding.carPicture.setImageBitmap(carIcon);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
