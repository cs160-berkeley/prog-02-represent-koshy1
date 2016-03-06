package com.example.sunjay.represent.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.adapters.ListAdapter;
import com.example.sunjay.represent.models.CongressPerson;
import com.example.sunjay.represent.models.ElectionResult;
import com.example.sunjay.represent.models.NotificationCardCount;
import com.example.sunjay.represent.services.API;
import com.example.sunjay.represent.services.PhoneToWatchService;
import com.example.sunjay.represent.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends RepresentFragment implements ListAdapter.ListAdapterListener {

  public static final String ZIP_CODE_BUNDLE_IDENTIFIER = "com.example.sunjay.represent.fragments.ListFragment.zipCode";

  private ListView listView;
  private ListFragmentListener listener;
  private ListAdapter adapter;
  private TextView title;
  private String zipCode;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof ListFragmentListener) {
      listener = (ListFragmentListener) activity;
    }

    Bundle args = getArguments();
    zipCode = args.getString(ZIP_CODE_BUNDLE_IDENTIFIER);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_list, container, false);
    listView = (ListView) view.findViewById(R.id.fragment_list_listview);
    title = (TextView) view.findViewById(R.id.fragment_list_actionbar_title);

    Button backButton = (Button) view.findViewById(R.id.fragment_list_back_button);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });

    updateZipCode(zipCode);
    return view;
  }

  public void updateZipCode(String zipCode) {
    this.zipCode = zipCode;
    title.setText(zipCode);

    if (adapter != null) {
      adapter.clear();
      adapter = null;
    }

    API.getAPI().getCongressPeople(zipCode, new API.APIResponseListener<List<CongressPerson>>() {
      @Override
      public void onSuccess(List<CongressPerson> data) {
        for (CongressPerson person : data) {
          Intent sendIntent = new Intent(getActivity(), PhoneToWatchService.class);
          sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.PROFILE.getValue());
          sendIntent.putExtra(IntentUtil.ExtraIdentifiers.DATA.getValue(), person);
          getActivity().startService(sendIntent);
        }

        Intent sendIntent = new Intent(getActivity(), PhoneToWatchService.class);
        sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.PROFILE_NOTIFICATION_COUNT.getValue());
        sendIntent.putExtra(IntentUtil.ExtraIdentifiers.DATA.getValue(), new NotificationCardCount(data.size() + 1));
        getActivity().startService(sendIntent);

        adapter = new ListAdapter(getActivity(), new ArrayList<>(data), ListFragment.this);
        listView.setAdapter(adapter);
      }
    });

    API.getAPI().getElectionResults(zipCode, new API.APIResponseListener<ElectionResult>() {
      @Override
      public void onSuccess(ElectionResult data) {
        Intent sendIntent = new Intent(getActivity(), PhoneToWatchService.class);
        sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.ELECTION_RESULTS.getValue());
        sendIntent.putExtra(IntentUtil.ExtraIdentifiers.DATA.getValue(), data);
        getActivity().startService(sendIntent);
      }
    });
  }

  public String getZipCode() {
    return zipCode;
  }

  @Override
  public void onRowClick(int position, ListAdapter listAdapter) {
    if (listener != null) {
      listener.onListItemSelect(adapter.getItem(position), ListFragment.this);
    }
  }

  public interface ListFragmentListener {
    void onListItemSelect(CongressPerson congressPerson, ListFragment fragment);
  }
}
