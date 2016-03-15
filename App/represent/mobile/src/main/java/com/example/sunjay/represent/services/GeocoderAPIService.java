package com.example.sunjay.represent.services;

import com.example.sunjay.represent.shared.models.googlemodels.LocationItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocoderAPIService {
    @GET("/maps/api/geocode/json")
    Call<LocationItem> getLocationItemFromLatLng(@Query("latlng") String latlng, @Query("key") String key);

    @GET("/maps/api/geocode/json")
    Call<LocationItem> getLocationItemFromZip(@Query("address") String zip, @Query("key") String key);
}
