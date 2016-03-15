package com.example.sunjay.represent.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sunjay.represent.R;
import com.example.sunjay.represent.controllers.*;
import com.example.sunjay.represent.services.API;
import com.example.sunjay.represent.shared.models.sunlightmodels.BillResults;
import com.example.sunjay.represent.shared.models.sunlightmodels.CommitteeResults;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPerson;

import com.example.sunjay.represent.shared.util.BlockingQueue;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends RepresentFragment implements LoadingScreenController.LoadingScreenControllerListener {
  public static final String CONGRESS_PERSON = "com.example.sunjay.represent.fragments.DetailFragment.congressPerson";
  private ProfileCardController profileCardController;
  private TwitterCardController twitterCardController;
  private CommitteesCardController committeesCardController;
  private RecentBillsCardController recentBillsCardController;
  private LoadingScreenController loadingScreenController;

  private CongressPerson congressPerson;
  private View layout;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    Bundle args = getArguments();
    congressPerson = args.getParcelable(CONGRESS_PERSON);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    layout = inflater.inflate(R.layout.fragment_detail, container, false);
    loadingScreenController = LoadingScreenController.takeControl(getActivity(), (ViewGroup) layout.findViewById(R.id.fragment_detail_loading_screen), this);
    updateCongressPerson(congressPerson);
    return layout;
  }

  private void makeRequests() {
    loadingScreenController.showLoading();
    final BlockingQueue requestQueue = new BlockingQueue(2, new Runnable() {
      @Override
      public void run() {
        if (getActivity() == null) {
          return;
        }
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            loadingScreenController.hide();
          }
        });
      }
    });

    Call<CommitteeResults> committeesCall = API.getSunlightAPI().getCommittees(congressPerson.bioguide_id, API.SUNLIGHT_API_KEY);
    committeesCall.enqueue(new Callback<CommitteeResults>() {
      @Override
      public void onResponse(Call<CommitteeResults> call, Response<CommitteeResults> response) {
        if (committeesCardController == null) {
          committeesCardController = CommitteesCardController.takeControl(getActivity(), (ViewGroup) layout.findViewById(R.id.fragment_detail_committees_card));
        }
        committeesCardController.configureWithDataItem(response.body().results);
        requestQueue.down();
      }

      @Override
      public void onFailure(Call<CommitteeResults> call, Throwable t) {
        loadingScreenController.showError();
      }
    });

    Call<BillResults> recentBillsCall = API.getSunlightAPI().getBills(congressPerson.bioguide_id, API.SUNLIGHT_API_KEY);
    recentBillsCall.enqueue(new Callback<BillResults>() {
      @Override
      public void onResponse(Call<BillResults> call, final Response<BillResults> response) {
        if (getActivity() == null) {
          return;
        }
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (recentBillsCardController == null) {
              recentBillsCardController = RecentBillsCardController.takeControl(getActivity(), (ViewGroup) layout.findViewById(R.id.fragment_detail_recent_bills_card));
            }
            recentBillsCardController.configureWithDataItem(response.body().results);
          }
        });
        requestQueue.down();
      }

      @Override
      public void onFailure(Call<BillResults> call, Throwable t) {
        loadingScreenController.showError();
      }
    });

    if (twitterCardController == null) {
      twitterCardController = TwitterCardController.takeControl(getActivity(), (ViewGroup) layout.findViewById(R.id.fragment_detail_twitter_card));
    }
    twitterCardController.configureWithDataItem(congressPerson.twitter_item);
  }

  public void updateCongressPerson(CongressPerson congressPerson) {
    this.congressPerson = congressPerson;
    TextView title = (TextView) layout.findViewById(R.id.fragment_detail_actionbar_title);
    title.setText(congressPerson.getFullName());

    Button backButton = (Button) layout.findViewById(R.id.fragment_detail_back_button);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });

    if (profileCardController == null) {
      profileCardController = ProfileCardController.takeControl(getActivity(), (ViewGroup) layout.findViewById(R.id.fragment_detail_profile_card));
    }
    profileCardController.configureWithDataItem(congressPerson);
    makeRequests();
  }

  @Override
  public void onActionButtonClick(LoadingScreenController listItemController) {
    makeRequests();
  }
}
