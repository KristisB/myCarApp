package com.example.carapp.model;

import android.location.Location;

public class Car {
private int id;
private String plateNumber;
private CarLocation location;
private CarModel model;
private int batteryPercentage;
private double batteryEstimatedDistance;
private boolean isCharging;
private int servicePlusEGoPoints;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public CarLocation getLocation() {
        return location;
    }

    public void setLocation(CarLocation location) {
        this.location = location;
    }

    public CarModel getModel() {
        return model;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public double getBatteryEstimatedDistance() {
        return batteryEstimatedDistance;
    }

    public void setBatteryEstimatedDistance(double batteryEstimatedDistance) {
        this.batteryEstimatedDistance = batteryEstimatedDistance;
    }

    public boolean isCharging() {
        return isCharging;
    }

    public void setCharging(boolean charging) {
        isCharging = charging;
    }

    public int getServicePlusEGoPoints() {
        return servicePlusEGoPoints;
    }

    public void setServicePlusEGoPoints(int servicePlusEGoPoints) {
        this.servicePlusEGoPoints = servicePlusEGoPoints;
    }

    public double getDistance(Location toLocation){
        Location currLocation = new Location("");
        currLocation.setLongitude(location.getLongitude());
        currLocation.setLatitude(location.getLatitude());
        return currLocation.distanceTo(toLocation)/1000;
    }

}
