package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.models.ElectionResult;

public class ElectionResultsCardController {
  private TextView title;
  private TextView location;
  private TextView republicanCandidateName;
  private TextView republicanVote;
  private TextView democratCandidateName;
  private TextView democratVote;

  private Context context;

  private ElectionResultsCardController(Context context, ViewGroup layout) {
    title = (TextView) layout.findViewById(R.id.election_results_card_title);
    location = (TextView) layout.findViewById(R.id.election_results_location);
    republicanCandidateName = (TextView) layout.findViewById(R.id.election_results_republican_candidate_name);
    republicanVote = (TextView) layout.findViewById(R.id.election_results_republican_vote);
    democratCandidateName = (TextView) layout.findViewById(R.id.election_results_democrat_candidate_name);
    democratVote = (TextView) layout.findViewById(R.id.election_results_democrat_vote);

    this.context = context;
  }

  public static ElectionResultsCardController takeControl(Context context, ViewGroup layout) {
    return new ElectionResultsCardController(context, layout);
  }

  public void configureWithDataItem(final ElectionResult electionResult) {
    title.setText(electionResult.title);
    location.setText(electionResult.location);
    republicanCandidateName.setText(electionResult.republican_candidate);
    republicanVote.setText(String.format("%d%%", electionResult.republic_vote));
    democratCandidateName.setText(electionResult.democrat_candidate);
    democratVote.setText(String.format("%d%%", electionResult.democrat_vote));
  }
}
