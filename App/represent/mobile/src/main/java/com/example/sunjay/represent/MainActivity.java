package com.example.sunjay.represent;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.example.sunjay.represent.controllers.BlockTouchController;
import com.example.sunjay.represent.fragments.DetailFragment;
import com.example.sunjay.represent.fragments.ListFragment;
import com.example.sunjay.represent.fragments.OnboardingFragment;
import com.example.sunjay.represent.services.API;
import com.example.sunjay.represent.services.PhoneListenerService;
import com.example.sunjay.represent.shared.models.ZipCodeItem;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPerson;
import com.example.sunjay.represent.shared.util.IntentUtil;

import java.util.List;
import java.util.Random;

public class MainActivity extends Activity implements OnboardingFragment.OnboardingFragmentListener, ListFragment.ListFragmentListener{
  private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

  private OnboardingFragment onboardingFragment;
  private ListFragment listFragment;
  private DetailFragment detailFragment;
  private BroadcastReceiver shakeBroadcastReceiver;
  private BroadcastReceiver openDetailBroadcastReceiver;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.represent_dark_red));

    API.initializeWithContext(this);
    Intent sendIntent = new Intent(this, PhoneListenerService.class);
    startService(sendIntent);

    BlockTouchController.setTouchInterceptor(this.findViewById(R.id.activity_main_touch_interceptor));
    onboardingFragment = new OnboardingFragment();

    shakeBroadcastReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
      if (detailFragment != null && detailFragment.isAdded()) {
        onBackPressed();
      }

      Random random = new Random();
      List<ZipCodeItem> zipCodes = API.getAllZipCodes();
      ZipCodeItem zipCodeItem = zipCodes.get(random.nextInt(zipCodes.size()));
      if (listFragment != null && listFragment.isAdded()) {
        listFragment.updateZipCode(zipCodeItem);
      } else {
        startListFragment(zipCodeItem);
      }
      }
    };

    openDetailBroadcastReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
      CongressPerson congressPerson = intent.getParcelableExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue());
      if (detailFragment != null && detailFragment.isAdded()) {
        detailFragment.updateCongressPerson(congressPerson);
      } else {
        startDetailFragment(congressPerson);
      }
      }
    };

    LocalBroadcastManager.getInstance(this).registerReceiver(shakeBroadcastReceiver, new IntentFilter(IntentUtil.RequestIdentifiers.SHAKE.getValue()));
    LocalBroadcastManager.getInstance(this).registerReceiver(openDetailBroadcastReceiver, new IntentFilter(IntentUtil.RequestIdentifiers.OPEN_DETAIL.getValue()));

    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.replace(R.id.activity_main_fragment_container1, onboardingFragment);
    transaction.commit();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(shakeBroadcastReceiver);
    LocalBroadcastManager.getInstance(this).unregisterReceiver(openDetailBroadcastReceiver);
  }

  @Override
  public void onListItemSelect(CongressPerson congressPerson, ListFragment fragment) {
    startDetailFragment(congressPerson);
  }

  @Override
  public void onSubmitButtonClick(ZipCodeItem zipCodeItem, OnboardingFragment fragment) {
    startListFragment(zipCodeItem);
  }

  @Override
  public void onRunBlockIfLocationEnabled(Runnable runnable, OnboardingFragment fragment) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
    } else {
      runnable.run();
    }
  }

  private void startListFragment(ZipCodeItem zipCodeItem) {
    Bundle args = new Bundle();
    args.putParcelable(ListFragment.ZIP_CODE_ITEM_BUNDLE_IDENTIFIER, zipCodeItem);
    listFragment = new ListFragment();
    listFragment.setArguments(args);

    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
    transaction.replace(R.id.activity_main_fragment_container2, listFragment);
    transaction.addToBackStack(listFragment.getClass().getName());
    transaction.commit();
  }

  private void startDetailFragment(CongressPerson congressPerson) {
    Bundle args = new Bundle();
    args.putParcelable(DetailFragment.CONGRESS_PERSON, congressPerson);

    detailFragment = new DetailFragment();
    detailFragment.setArguments(args);

    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
    transaction.replace(R.id.activity_main_fragment_container3, detailFragment);
    transaction.addToBackStack(detailFragment.getClass().getName());
    transaction.commit();
  }
}
