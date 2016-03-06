package com.example.sunjay.represent;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import com.example.sunjay.represent.controllers.BlockTouchController;
import com.example.sunjay.represent.fragments.DetailFragment;
import com.example.sunjay.represent.fragments.ListFragment;
import com.example.sunjay.represent.fragments.OnboardingFragment;
import com.example.sunjay.represent.models.CongressPerson;
import com.example.sunjay.represent.services.PhoneListenerService;
import com.example.sunjay.represent.util.IntentUtil;

public class MainActivity extends Activity implements OnboardingFragment.OnboardingFragmentListener, ListFragment.ListFragmentListener {
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
        if (listFragment != null && listFragment.isAdded()) {
          String newZipCode = "95014";
          if (listFragment.getZipCode().equals("95014")) {
            newZipCode = "94087";
          }
          listFragment.updateZipCode(newZipCode);
        } else {
          startListFragment("94087");
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
  public void onSubmitButtonClick(String zipCode, OnboardingFragment fragment) {
    startListFragment(zipCode);
  }

  private void startListFragment(String zipCode) {
    Bundle args = new Bundle();
    args.putString(ListFragment.ZIP_CODE_BUNDLE_IDENTIFIER, zipCode);

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
