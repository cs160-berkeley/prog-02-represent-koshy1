package com.example.sunjay.represent.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.adapters.CardPagerAdapter;
import com.example.sunjay.represent.controllers.ElectionResultsCardController;
import com.example.sunjay.represent.controllers.ProfileCardController;
import com.example.sunjay.represent.services.WatchToPhoneService;
import com.example.sunjay.represent.shared.models.ElectionResult;
import com.example.sunjay.represent.shared.models.WatchCards;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPerson;
import com.example.sunjay.represent.shared.util.IntentUtil;

import java.util.ArrayList;

public class ListCardFragment extends Fragment implements ProfileCardController.ProfileCardControllerListener {
  private GridViewPager gridViewPager;
  private View emptyState;

  private ListCardFragmentListener listener;
  private BroadcastReceiver cardsReceiver;

  private ArrayList<View> cards = new ArrayList<>();

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof ListCardFragmentListener) {
      listener = (ListCardFragmentListener) activity;
    }
    cardsReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        processCardIntent(intent);
      }
    };

    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(cardsReceiver, new IntentFilter(IntentUtil.MessageTypes.CARDS.getValue()));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_list_card, container, false);
    emptyState = view.findViewById(R.id.fragment_list_empty_state);
    gridViewPager = (GridViewPager) view.findViewById(R.id.fragment_list_card_pager);
    DotsPageIndicator pageIndicator = (DotsPageIndicator) view.findViewById(R.id.fragment_list_card_page_indicator);
    pageIndicator.setDotFadeWhenIdle(false);
    pageIndicator.setPager(gridViewPager);

    Intent sendIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.SYNCHRONIZE_WATCH.getValue());
    getActivity().startService(sendIntent);

    return view;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(cardsReceiver);
  }

  private void processCardIntent(Intent intent) {
    WatchCards watchCards = intent.getParcelableExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue());
    if (watchCards.electionResults.size() > 0) {
      ElectionResult electionResult = watchCards.electionResults.get(0);
      if (electionResult != null) {
        ViewGroup layout = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.election_results_card, null, false);
        ElectionResultsCardController electionResultsCardController = ElectionResultsCardController.takeControl(getActivity(), layout);
        electionResultsCardController.configureWithDataItem(electionResult);
        cards.add(layout);
      }
    }
    for (CongressPerson profile : watchCards.profiles) {
      ViewGroup layout = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.profile_card, null, false);
      ProfileCardController controller = ProfileCardController.takeControl(getActivity(), layout, this);
      controller.configureWithDataItem(profile);
      cards.add(layout);
    }
    assembleCards();
  }

  private void assembleCards() {
    emptyState.setVisibility(View.GONE);
    CardPagerAdapter adapter = new CardPagerAdapter(getActivity());
    adapter.setItems(new ArrayList<>(cards));
    cards.clear();
    gridViewPager.setAdapter(adapter);
  }

  @Override
  public void onButtonClick(CongressPerson congressPerson, ProfileCardController listItemController) {
    if (listener != null) {
      listener.onCardSelect(congressPerson, this);
    }
  }

  @Override
  public void onEmailClick(CongressPerson congressPerson, ProfileCardController listItemController) {
    if (listener != null) {
      listener.onEmailSelect(congressPerson, this);
    }
  }

  @Override
  public void onWebsiteClick(CongressPerson congressPerson, ProfileCardController listItemController) {
    if (listener != null) {
      listener.onWebsiteSelect(congressPerson, this);
    }
  }

  public interface ListCardFragmentListener {
    void onCardSelect(CongressPerson congressPerson, ListCardFragment fragment);
    void onEmailSelect(CongressPerson congressPerson, ListCardFragment fragment);
    void onWebsiteSelect(CongressPerson congresPerson, ListCardFragment fragment);
  }
}
