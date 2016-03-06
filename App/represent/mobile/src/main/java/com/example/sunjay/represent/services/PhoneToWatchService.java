package com.example.sunjay.represent.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import android.os.Parcelable;
import com.example.sunjay.represent.util.IntentUtil;
import com.example.sunjay.represent.util.ParcelableUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class PhoneToWatchService extends Service {
  private GoogleApiClient apiClient;

  @Override
  public void onCreate() {
    super.onCreate();
    apiClient = new GoogleApiClient.Builder(this)
      .addApi(Wearable.API)
      .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle connectionHint) {

        }

        @Override
        public void onConnectionSuspended(int cause) {

        }
      })
      .build();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    apiClient.disconnect();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent != null) {
      final String message = intent.getStringExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue());
      final Parcelable data = intent.getParcelableExtra(IntentUtil.ExtraIdentifiers.DATA.getValue());

      new Thread(new Runnable() {
        @Override
        public void run() {
          apiClient.connect();
          sendMessage(message, data);
        }
      }).start();
    }

    return START_STICKY;
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  private void sendMessage(final String path, final Parcelable data) {
    new Thread( new Runnable() {
      @Override
      public void run() {
      NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(apiClient).await();
      for (Node node : nodes.getNodes()) {
        Wearable.MessageApi.sendMessage(apiClient, node.getId(), path, data != null ? ParcelableUtil.marshall(data) : null).await();
      }
      }
    }).start();
  }
}
