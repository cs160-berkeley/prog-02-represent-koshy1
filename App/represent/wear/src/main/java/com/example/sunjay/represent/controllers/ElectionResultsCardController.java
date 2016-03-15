package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.shared.models.ElectionResult;

public class ElectionResultsCardController {
  private TextView location;
  private TextView republicanResults;
  private TextView democratResults;

  private Context context;

  private ElectionResultsCardController(Context context, ViewGroup layout) {
    location = (TextView) layout.findViewById(R.id.election_results_location);
    republicanResults = (TextView) layout.findViewById(R.id.election_results_republican_results);
    democratResults = (TextView) layout.findViewById(R.id.election_results_democrat_results);
    this.context = context;
  }

  public static ElectionResultsCardController takeControl(Context context, ViewGroup layout) {
    return new ElectionResultsCardController(context, layout);
  }

  public void configureWithDataItem(final ElectionResult electionResult) {
    location.setText(electionResult.getLocationString());
    republicanResults.setText(String.format("%s %s%%", context.getResources().getString(R.string.republican_candidate_name), electionResult.romney_percentage));
    democratResults.setText(String.format("%s %s%%", context.getResources().getString(R.string.democratic_candidate_name), electionResult.obama_percentage));
  }
}
