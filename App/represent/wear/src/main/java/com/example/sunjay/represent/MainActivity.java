package com.example.sunjay.represent;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import com.example.sunjay.represent.fragments.ListCardFragment;
import com.example.sunjay.represent.listeners.WatchShakeListener;
import com.example.sunjay.represent.services.WatchToPhoneService;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPerson;
import com.example.sunjay.represent.shared.util.IntentUtil;

public class MainActivity extends Activity implements ListCardFragment.ListCardFragmentListener {
  private WatchShakeListener shakeDetector;
  private SensorManager sensorManager;
  private Sensor accelerometer;
  private ListCardFragment cardFragment;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    shakeDetector = new WatchShakeListener(new WatchShakeListener.OnShakeListener() {
      @Override
      public void onShake() {
      Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
      sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.SHAKE.getValue());
      startService(sendIntent);
      }
    });

    cardFragment = new ListCardFragment();

    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.replace(R.id.activity_main_fragment_container1, cardFragment);
    transaction.commit();
  }

  @Override
  protected void onResume() {
    super.onResume();
    sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
  }

  @Override
  protected void onPause() {
    sensorManager.unregisterListener(shakeDetector);
    super.onPause();
  }

  @Override
  public void onCardSelect(CongressPerson congressPerson, ListCardFragment fragment) {
    Intent sendIntent = new Intent(this, WatchToPhoneService.class);
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.OPEN_DETAIL.getValue());
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.DATA.getValue(), congressPerson);
    startService(sendIntent);
  }

  @Override
  public void onEmailSelect(CongressPerson congressPerson, ListCardFragment fragment) {
    Intent sendIntent = new Intent(this, WatchToPhoneService.class);
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.OPEN_URL.getValue());
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.DATA.getValue(), Uri.parse(congressPerson.getEmailLink()));
    startService(sendIntent);
  }

  @Override
  public void onWebsiteSelect(CongressPerson congressPerson, ListCardFragment fragment) {
    Intent sendIntent = new Intent(this, WatchToPhoneService.class);
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.OPEN_URL.getValue());
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.DATA.getValue(), Uri.parse(congressPerson.website));
    startService(sendIntent);
  }
}
