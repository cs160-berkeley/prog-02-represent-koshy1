package com.example.sunjay.represent.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.example.sunjay.represent.shared.models.WatchCards;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPerson;
import com.example.sunjay.represent.shared.models.ElectionResult;
import com.example.sunjay.represent.shared.util.IntentUtil;
import com.example.sunjay.represent.shared.util.ParcelableUtil;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class WatchListenerService extends WearableListenerService {
  @Override
  public void onMessageReceived(MessageEvent messageEvent) {
    if(messageEvent.getPath().equals(IntentUtil.MessageTypes.PROFILE.getValue())) {
      Intent intent = new Intent(messageEvent.getPath());
      intent.putExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue(), ParcelableUtil.unmarshall(messageEvent.getData(), CongressPerson.CREATOR));
      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    } else if (messageEvent.getPath().equals(IntentUtil.MessageTypes.ELECTION_RESULTS.getValue())) {
      Intent intent = new Intent(messageEvent.getPath());
      intent.putExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue(), ParcelableUtil.unmarshall(messageEvent.getData(), ElectionResult.CREATOR));
      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    } else if (messageEvent.getPath().equals(IntentUtil.MessageTypes.CARDS.getValue())) {
      Intent intent = new Intent(messageEvent.getPath());
      intent.putExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue(), ParcelableUtil.unmarshall(messageEvent.getData(), WatchCards.CREATOR));
      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    } else {
      super.onMessageReceived(messageEvent);
    }
  }
}
