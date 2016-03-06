package com.example.sunjay.represent.cards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.ViewGroup;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.controllers.ElectionResultsCardController;
import com.example.sunjay.represent.controllers.ProfileCardController;
import com.example.sunjay.represent.listeners.WatchShakeListener;
import com.example.sunjay.represent.models.CongressPerson;
import com.example.sunjay.represent.models.ElectionResult;
import com.example.sunjay.represent.util.IntentUtil;
import com.example.sunjay.represent.services.WatchToPhoneService;

public class NotificationCard extends Activity implements ProfileCardController.ProfileCardControllerListener {
  private WatchShakeListener shakeDetector;
  private SensorManager sensorManager;
  private Sensor accelerometer;

  public void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.profile_card);
    super.onCreate(savedInstanceState);
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

    Intent i = getIntent();
    IntentUtil.CardTypes cardType = IntentUtil.CardTypes.getCardType(i.getStringExtra(IntentUtil.ExtraIdentifiers.CARD_TYPE.getValue()));

    switch(cardType) {
      case CONGRESS_PROFILE: {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        ProfileCardController profileCardController = ProfileCardController.takeControl(this, viewGroup, this);
        CongressPerson congressPerson = i.getParcelableExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue());
        profileCardController.configureWithDataItem(congressPerson);
        break;
      }
      case ELECTION_RESULTS: {
        setContentView(R.layout.election_results_card);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        ElectionResultsCardController electionResultsCardController = ElectionResultsCardController.takeControl(this, viewGroup);
        ElectionResult electionResult = i.getParcelableExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue());
        electionResultsCardController.configureWithDataItem(electionResult);
        break;
      }
    }
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
  public void onButtonClick(CongressPerson congressPerson, ProfileCardController listItemController) {
    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue(), IntentUtil.MessageTypes.OPEN_DETAIL.getValue());
    sendIntent.putExtra(IntentUtil.ExtraIdentifiers.DATA.getValue(), congressPerson);
    startService(sendIntent);
  }
}
