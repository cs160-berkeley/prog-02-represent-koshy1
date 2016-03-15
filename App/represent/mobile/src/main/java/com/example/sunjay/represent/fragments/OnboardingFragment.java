package com.example.sunjay.represent.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.controllers.BlockTouchController;
import com.example.sunjay.represent.services.API;
import com.example.sunjay.represent.shared.models.ZipCodeItem;
import com.example.sunjay.represent.shared.models.googlemodels.LocationItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnboardingFragment extends RepresentFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
  private OnboardingFragmentListener listener;

  private EditText zipCodeEntry;
  private GoogleApiClient googleApiClient;
  private View focusableView;
  private Button locationButton;
  private ProgressBar spinner;

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
    if (activity instanceof OnboardingFragmentListener) {
      listener = (OnboardingFragmentListener) activity;
    }
    initializeGoogleApi();
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
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_onboarding, container, false);

    focusableView = view.findViewById(R.id.fragment_onboarding_focusable_view);
    zipCodeEntry = (EditText) view.findViewById(R.id.fragment_onboarding_zip_code_entry);
    spinner = (ProgressBar) view.findViewById(R.id.fragment_onboarding_progress_bar);
    spinner.setIndeterminate(true);
    spinner.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);

    final Button submitButton = (Button) view.findViewById(R.id.fragment_onboarding_submit_button);
    locationButton = (Button) view.findViewById(R.id.fragment_onboarding_location_button);

    locationButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Location[] lastLocation = new Location[1];
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions((getActivity()), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
        if (listener != null) {
          listener.onRunBlockIfLocationEnabled(new Runnable() {
            @Override
            public void run() {
              showSpinner();
              if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                lastLocation[0] = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                String latitude = "";
                String longitude = "";
                if (lastLocation[0] != null) {
                  latitude = String.format("%.8g", lastLocation[0].getLatitude());
                  longitude = String.format("%.8g", lastLocation[0].getLongitude());
                }
                String coordinates = latitude + "," + longitude;
                getZipFromLocation(coordinates);
              }
            }
          }, OnboardingFragment.this);
        }
      }
    });

    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(zipCodeEntry.getWindowToken(), 0);

        showSpinner();

        BlockTouchController.disableTouch();
        if (!validateZipCodeFormat()) {
          hideSpinner();
          BlockTouchController.enableTouch();
          showZipCodeError();
          return;
        }

        Call<LocationItem> callLocationItem = API.getGeocoderAPI().getLocationItemFromZip(zipCodeEntry.getText().toString(), API.GOOGLE_KEY);
        callLocationItem.enqueue(new Callback<LocationItem>() {
          @Override
          public void onResponse(Call<LocationItem> call, Response<LocationItem> response) {
            BlockTouchController.enableTouch();
            hideSpinner();
            if (response.body().results.size() == 0) {
              showZipCodeError();
            } else {
              submitZipCode();
            }
          }

          @Override
          public void onFailure(Call<LocationItem> call, Throwable t) {
            BlockTouchController.enableTouch();
            hideSpinner();
            showZipCodeError();
          }
        });
      }
    });
    return view;
  }

  private void showZipCodeError() {
    new AlertDialog.Builder(getActivity())
      .setTitle(getActivity().getResources().getString(R.string.fragment_onboarding_zip_code_error_title))
      .setMessage(getActivity().getResources().getString(R.string.fragment_onboarding_zip_code_error_message))
      .setPositiveButton(android.R.string.ok, null)
      .show();
  }

  private void submitZipCode() {
    zipCodeEntry.clearFocus();
    focusableView.requestFocus();

    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(zipCodeEntry.getWindowToken(), 0);

    if (listener != null) {
      ZipCodeItem zipCodeItem = new ZipCodeItem();
      zipCodeItem.zip_code = zipCodeEntry.getText().toString();
      listener.onSubmitButtonClick(zipCodeItem, OnboardingFragment.this);
    }
  }

  private void getZipFromLocation(String coordinates) {
    Call<LocationItem> callLocationItem = API.getGeocoderAPI().getLocationItemFromLatLng(coordinates, API.GOOGLE_KEY);
    callLocationItem.enqueue(new Callback<LocationItem>() {
      @Override
      public void onResponse(Call<LocationItem> call, Response<LocationItem> response) {
        zipCodeEntry.setText(response.body().results.get(0).getZipCode());
        hideSpinner();
      }

      @Override
      public void onFailure(Call<LocationItem> call, Throwable t) {

      }
    });
  }

  private void hideSpinner() {
    locationButton.setVisibility(View.VISIBLE);
    spinner.setVisibility(View.GONE);
  }

  private void showSpinner() {
    locationButton.setVisibility(View.GONE);
    spinner.setVisibility(View.VISIBLE);
  }

  private boolean validateZipCodeFormat() {
    String regex = "\\d{5}([ \\-]\\d{4})?";
    Matcher matcher = Pattern.compile(regex).matcher(zipCodeEntry.getText().toString());
    return matcher.matches();
  }

  public interface OnboardingFragmentListener {
    void onSubmitButtonClick(ZipCodeItem zipCodeItem, OnboardingFragment fragment);
    void onRunBlockIfLocationEnabled(Runnable runnable, OnboardingFragment fragment);
  }
}
