package com.example.sunjay.represent.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.adapters.ListAdapter;
import com.example.sunjay.represent.controllers.LoadingScreenController;
import com.example.sunjay.represent.services.API;
import com.example.sunjay.represent.services.PhoneToWatchService;
import com.example.sunjay.represent.shared.models.ZipCodeItem;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPerson;
import com.example.sunjay.represent.shared.models.ElectionResult;
import com.example.sunjay.represent.shared.models.WatchCards;
import com.example.sunjay.represent.shared.models.googlemodels.LocationItem;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPersonResults;
import com.example.sunjay.represent.shared.util.BlockingQueue;
import com.example.sunjay.represent.shared.models.TwitterItem;
import com.example.sunjay.represent.shared.util.IntentUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.ConnectionResult;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends RepresentFragment implements ListAdapter.ListAdapterListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LoadingScreenController.LoadingScreenControllerListener {
  public static final String ZIP_CODE_ITEM_BUNDLE_IDENTIFIER = "com.example.sunjay.represent.fragments.ListFragment.zipCodeItem";
  private ZipCodeItem zipCodeItem;
  private ListView listView;
  private ListFragmentListener listener;
  private ListAdapter adapter;
  private GoogleApiClient googleApiClient;
  private TwitterApiClient twitterApiClient;
  private TextView title;
  private List<CongressPerson> congressPeople;
  private ElectionResult electionResult;
  private BroadcastReceiver synchronizeBroadcastReceiver;
  private LoadingScreenController loadingScreenController;

  public ListFragment() {

  }

  @Override
  public void onResume() {
    super.onResume();
    googleApiClient.connect();
  }

  @Override
  public void onStart() {
    super.onStart();
    googleApiClient.connect();
  }

  @Override
  public void onStop() {
    super.onStop();
    googleApiClient.disconnect();
  }

  @Override
  public void onPause() {
    super.onPause();
    googleApiClient.disconnect();
  }

  @Override
  public void onConnected(Bundle bundle) {
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {

  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ListFragmentListener) {
      listener = (ListFragmentListener) activity;
    }
    Bundle args = getArguments();
    zipCodeItem = args.getParcelable(ZIP_CODE_ITEM_BUNDLE_IDENTIFIER);
    initializeGoogleApi();
    registerBroadcasters();
  }

  private void registerBroadcasters() {
    synchronizeBroadcastReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if (electionResult != null && congressPeople.size() != 0) {
          sendCardsToWatch();
        }
      }
    };
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(synchronizeBroadcastReceiver, new IntentFilter(IntentUtil.RequestIdentifiers.SYNCHRONIZE_WATCH.getValue()));
  }

  private void initializeGoogleApi() {
    if (googleApiClient == null) {
      googleApiClient = new GoogleApiClient.Builder(getActivity())
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(synchronizeBroadcastReceiver);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_list, container, false);
    listView = (ListView) view.findViewById(R.id.fragment_list_listview);
    title = (TextView) view.findViewById(R.id.fragment_list_actionbar_title);
    loadingScreenController = LoadingScreenController.takeControl(getActivity(), (ViewGroup) view.findViewById(R.id.fragment_list_loading_screen), this);
    TwitterAuthConfig authConfig = new TwitterAuthConfig(API.TWITTER_KEY, API.TWITTER_SECRET);
    Fabric.with(getActivity(), new Twitter(authConfig));
    Button backButton = (Button) view.findViewById(R.id.fragment_list_back_button);
    backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    });
    updateZipCode(zipCodeItem);
    return view;
  }

  public void getTwitterApiClient(final BlockingQueue blockingQueue) {
    if (twitterApiClient == null) {
      TwitterCore.getInstance().logInGuest(new com.twitter.sdk.android.core.Callback<AppSession>() {
        @Override
        public void success(Result<AppSession> result) {
          AppSession guestAppSession = result.data;
          twitterApiClient = TwitterCore.getInstance().getApiClient(guestAppSession);
          blockingQueue.down();
        }

        @Override
        public void failure(TwitterException exception) {
          throw exception;
        }
      });
    } else {
        new Thread(new Runnable() {
            @Override
            public void run() {
                blockingQueue.down();
            }
        }).start();
    }
  }

  private void populateListView(final List<CongressPerson> congressPeople, final BlockingQueue blockingQueue) {
    this.congressPeople = congressPeople;
    final BlockingQueue twitterBlockingQueue = new BlockingQueue(congressPeople.size(), new Runnable() {
      @Override
      public void run() {
        if (getActivity() == null) {
          return;
        }
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            adapter = new ListAdapter(getActivity(), new ArrayList<>(congressPeople), ListFragment.this);
            listView.setAdapter(adapter);
          }
        });
        blockingQueue.down();
      }
    });

    for (final CongressPerson person : congressPeople) {
      if (twitterApiClient != null) {
        twitterApiClient.getStatusesService().userTimeline(null, person.twitter_id, 1, null, null, null, null, null, null, new com.twitter.sdk.android.core.Callback<List<Tweet>>() {
          @Override
          public void success(Result<List<Tweet>> result) {
            Tweet tweet = result.data.get(0);
            TwitterItem twitterItem = new TwitterItem();
            twitterItem.text = tweet.text;
            twitterItem.date = tweet.createdAt;
            person.twitter_item = twitterItem;
            person.profile_url = tweet.user.profileImageUrl.replace("_normal", "");
            twitterBlockingQueue.down();
          }

          public void failure(TwitterException exception) {
            loadingScreenController.showError();
          }
        });
      }
    }
  }

  private void makeRequests() {
    loadingScreenController.showLoading();

    final BlockingQueue requestQueue = new BlockingQueue(2, new Runnable() {
      @Override
      public void run() {
        sendCardsToWatch();
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

    Call<CongressPersonResults> congressPeopleCall = API.getSunlightAPI().getCongressPeople(zipCodeItem.zip_code, API.SUNLIGHT_API_KEY);
    congressPeopleCall.enqueue(new Callback<CongressPersonResults>() {
      @Override
      public void onResponse(Call<CongressPersonResults> call, final Response<CongressPersonResults> response) {
        BlockingQueue blockingQueue = new BlockingQueue(1, new Runnable() {
          @Override
          public void run() {
            populateListView(response.body().results, requestQueue);
          }
        });
        getTwitterApiClient(blockingQueue);
      }

      @Override
      public void onFailure(Call<CongressPersonResults> call, Throwable t) {
        loadingScreenController.showError();
      }
    });

    BlockingQueue locationBlockingQueue = new BlockingQueue(1, new Runnable() {
      @Override
      public void run() {
        Call<List<ElectionResult>> electionResultsCall = API.getElectionResultsAPI().getElectionResults();
        electionResultsCall.enqueue(new Callback<List<ElectionResult>>() {
          @Override
          public void onResponse(Call<List<ElectionResult>> call, Response<List<ElectionResult>> response) {
            ElectionResult correctResult = null;
            for (ElectionResult result : response.body()) {
              if (result.county_name.toLowerCase().equals(zipCodeItem.county.toLowerCase()) && result.state_postal.toLowerCase().equals(zipCodeItem.state.toLowerCase())){
                correctResult = result;
              }
            }
            electionResult = correctResult;
            requestQueue.down();
          }

          @Override
          public void onFailure(Call<List<ElectionResult>> call, Throwable t) {
            loadingScreenController.showError();
          }
        });
      }
    });

    getStateCountyFromZip(locationBlockingQueue);
  }

  private void getStateCountyFromZip(final BlockingQueue blockingQueue) {
    if (zipCodeItem.state == null || zipCodeItem.county == null) {
      Call<LocationItem> callLocationItem = API.getGeocoderAPI().getLocationItemFromZip(zipCodeItem.zip_code, API.GOOGLE_KEY);
      callLocationItem.enqueue(new Callback<LocationItem>() {
        @Override
        public void onResponse(Call<LocationItem> call, Response<LocationItem> response) {
          zipCodeItem.state = response.body().results.get(0).getStateName();
          zipCodeItem.county = response.body().results.get(0).getCountyName();
          blockingQueue.down();
        }

        @Override
        public void onFailure(Call<LocationItem> call, Throwable t) {
          loadingScreenController.showError();
        }
      });
    } else {
      new Thread(new Runnable() {
        @Override
        public void run() {
          blockingQueue.down();
        }
      }).start();
    }
  }

  public void updateZipCode(ZipCodeItem zipCodeItem) {
    this.zipCodeItem = zipCodeItem;
    title.setText(zipCodeItem.zip_code);

    if (adapter != null) {
      adapter.clear();
      adapter = null;
    }
    makeRequests();
  }

  private void sendCardsToWatch() {
    WatchCards watchCards = new WatchCards();
    watchCards.profiles = congressPeople;
    watchCards.electionResults = new ArrayList<>();
    watchCards.electionResults.add(electionResult);

    Intent sendIntent = new Intent(getActivity(), PhoneToWatchService.class);
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.CARDS.getValue());
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.DATA.getValue(), watchCards);
    getActivity().startService(sendIntent);
  }

  @Override
  public void onRowClick(int position, ListAdapter listAdapter) {
    if (listener != null) {
      listener.onListItemSelect(adapter.getItem(position), ListFragment.this);
    }
  }

  @Override
  public void onActionButtonClick(LoadingScreenController listItemController) {
    makeRequests();
  }

  public interface ListFragmentListener {
    void onListItemSelect(CongressPerson congressPerson, ListFragment fragment);
  }
}
