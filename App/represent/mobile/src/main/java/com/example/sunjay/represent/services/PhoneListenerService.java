package com.example.sunjay.represent.services;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;

import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPerson;
import com.example.sunjay.represent.shared.util.IntentUtil;
import com.example.sunjay.represent.shared.util.ParcelableUtil;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class PhoneListenerService extends WearableListenerService {
  @Override
  public void onMessageReceived(MessageEvent messageEvent) {
    if(messageEvent.getPath().equalsIgnoreCase(IntentUtil.MessageTypes.SHAKE.getValue()) ) {
      Intent intent = new Intent(IntentUtil.RequestIdentifiers.SHAKE.getValue());
      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    } else if (messageEvent.getPath().equalsIgnoreCase(IntentUtil.MessageTypes.OPEN_DETAIL.getValue())){
      Intent intent = new Intent(IntentUtil.RequestIdentifiers.OPEN_DETAIL.getValue());
      CongressPerson person = ParcelableUtil.unmarshall(messageEvent.getData(), CongressPerson.CREATOR);
      intent.putExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue(), person);
      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    } else if (messageEvent.getPath().equalsIgnoreCase(IntentUtil.MessageTypes.OPEN_URL.getValue())) {
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.setData(ParcelableUtil.unmarshall(messageEvent.getData(), Uri.CREATOR));
      this.startActivity(i);
    } else if (messageEvent.getPath().equalsIgnoreCase(IntentUtil.MessageTypes.SYNCHRONIZE_WATCH.getValue())) {
      Intent intent = new Intent(IntentUtil.RequestIdentifiers.SYNCHRONIZE_WATCH.getValue());
      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    } else {
      super.onMessageReceived(messageEvent);
    }
  }
}
