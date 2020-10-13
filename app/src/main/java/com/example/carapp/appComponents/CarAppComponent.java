package com.example.carapp.appComponents;

import android.location.LocationManager;
import com.example.carapp.network.ApiService;
import javax.inject.Singleton;
import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component (modules = {RetrofitModule.class, LocationModule.class})
public interface CarAppComponent {

    ApiService getApiService();

    LocationManager getLocationManager();
}
