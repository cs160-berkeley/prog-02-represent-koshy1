package com.example.sunjay.represent.services;

import com.example.sunjay.represent.shared.models.sunlightmodels.BillResults;
import com.example.sunjay.represent.shared.models.sunlightmodels.CommitteeResults;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPersonResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SunlightAPIService {
  @GET("/legislators/locate")
  Call<CongressPersonResults> getCongressPeople(@Query("zip") String zipCode, @Query("apikey") String apiKey);

  @GET("/committees")
  Call<CommitteeResults> getCommittees(@Query("member_ids") String memberID, @Query("apikey") String apiKey);

  @GET("/bills")
  Call<BillResults> getBills(@Query("sponsor_id") String sponsorID, @Query("apikey") String apiKey);
}
