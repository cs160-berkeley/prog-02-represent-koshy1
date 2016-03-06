package com.example.sunjay.represent.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.controllers.CommitteesCardController;
import com.example.sunjay.represent.controllers.ProfileCardController;
import com.example.sunjay.represent.controllers.RecentBillsCardController;
import com.example.sunjay.represent.controllers.TwitterCardController;
import com.example.sunjay.represent.models.CongressPerson;

public class DetailFragment extends RepresentFragment {

  public static final String CONGRESS_PERSON = "com.example.sunjay.represent.fragments.DetailFragment.congressPerson";

  private CongressPerson congressPerson;
  private View layout;
  
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    Bundle args = getArguments();
    congressPerson = args.getParcelable(CONGRESS_PERSON);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    layout = inflater.inflate(R.layout.fragment_detail, container, false);
    setCongressPerson(congressPerson);
    return layout;
  }
  
  public void setCongressPerson(CongressPerson congressPerson) {
    this.congressPerson = congressPerson;
    TextView title = (TextView) layout.findViewById(R.id.fragment_detail_actionbar_title);
    title.setText(congressPerson.name);

    Button backButton = (Button) layout.findViewById(R.id.fragment_detail_back_button);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });

    ProfileCardController profileCardController = ProfileCardController.takeControl(getActivity(), (ViewGroup) layout.findViewById(R.id.fragment_detail_profile_card));
    profileCardController.configureWithDataItem(congressPerson);

    TwitterCardController twitterCardController = TwitterCardController.takeControl(getActivity(), (ViewGroup) layout.findViewById(R.id.fragment_detail_twitter_card));
    twitterCardController.configureWithDataItem(congressPerson.recent_twitter_item);

    CommitteesCardController committeesCardController = CommitteesCardController.takeControl(getActivity(), (ViewGroup) layout.findViewById(R.id.fragment_detail_committees_card));
    committeesCardController.configureWithDataItem(congressPerson.committees);

    RecentBillsCardController recentBillsCardController = RecentBillsCardController.takeControl(getActivity(), (ViewGroup) layout.findViewById(R.id.fragment_detail_recent_bills_card));
    recentBillsCardController.configureWithDataItem(congressPerson.bills);
  }
}
