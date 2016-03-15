package com.example.sunjay.represent.services;

import android.content.Context;
import com.example.sunjay.represent.shared.models.ZipCodeItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class API {
  public static final String SUNLIGHT_API_KEY = "56e4b0cab87b46c09f5445bd4861a4d4";
  public static final String TWITTER_KEY = "NMadk7obPJCjz5oPt3hEr50jS";
  public static final String TWITTER_SECRET = "bfY0f2tPabqc1HKM1kO93eQTPnwNAViQtCVftXVtHnXPPojXqw";
  public static final String GOOGLE_KEY = "AIzaSyCsnvr-6H0BrqDnOP2hHAJEBehk3LclzbM";

  private static final String SUNLIGHT_API_BASE_URL = "https://congress.api.sunlightfoundation.com/";
  private static final String GITHUB_API_BASE_URL = "https://raw.githubusercontent.com/";
  private static final String GOOGLE_MAPS_API_BASE_URL = "https://maps.googleapis.com/";

  private static SunlightAPIService sunlightAPISingleton;
  private static ElectionResultsAPIService electionResultsAPISingleton;
  private static GeocoderAPIService geocoderAPIServiceSingleton;
  private static List<ZipCodeItem> zipCodeItems;
  private static Context context;

  public static void initializeWithContext(Context context) {
    API.context = context;
  }

  public static SunlightAPIService getSunlightAPI() {
    if (sunlightAPISingleton == null) {
      Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(SUNLIGHT_API_BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build();

      sunlightAPISingleton = retrofit.create(SunlightAPIService.class);
    }
    return sunlightAPISingleton;
  }

  public static ElectionResultsAPIService getElectionResultsAPI() {
    if (electionResultsAPISingleton == null) {
      Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(GITHUB_API_BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build();

      electionResultsAPISingleton = retrofit.create(ElectionResultsAPIService.class);
    }
    return electionResultsAPISingleton;
  }

  public static GeocoderAPIService getGeocoderAPI() {
    if (geocoderAPIServiceSingleton == null) {
      Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(GOOGLE_MAPS_API_BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build();

      geocoderAPIServiceSingleton = retrofit.create(GeocoderAPIService.class);
    }
    return geocoderAPIServiceSingleton;
  }

  public static List<ZipCodeItem> getAllZipCodes() {
    if (zipCodeItems == null) {
      try {
        StringBuilder buffer = new StringBuilder();
        InputStream json = context.getAssets().open("zip_codes.json");
        BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
          buffer.append(line);
        }
        in.close();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        zipCodeItems = objectMapper.readValue(buffer.toString(), typeFactory.constructCollectionType(List.class, ZipCodeItem.class));
      } catch (Exception ignored) {
      }
    }
    return zipCodeItems;
  }
}
