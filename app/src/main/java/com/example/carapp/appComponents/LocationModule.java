package com.example.carapp.appComponents;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class LocationModule {
    @Provides
    @Singleton
    public LocationManager getLocationManager(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Log.d("LocationModule", "location manager" + locationManager.toString());
       return locationManager;
   }

}
