package com.example.sunjay.represent.services;

import com.example.sunjay.represent.shared.models.ElectionResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ElectionResultsAPIService {
  @GET("/cs160-sp16/voting-data/master/election-county-2012.json")
  Call<List<ElectionResult>> getElectionResults();
}
