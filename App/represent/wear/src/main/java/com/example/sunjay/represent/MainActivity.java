package com.example.sunjay.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import com.example.sunjay.represent.listeners.WatchShakeListener;
import com.example.sunjay.represent.util.IntentUtil;
import com.example.sunjay.represent.services.WatchToPhoneService;

public class MainActivity extends Activity {
  private WatchShakeListener shakeDetector;
  private SensorManager sensorManager;
  private Sensor accelerometer;
  
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
}
