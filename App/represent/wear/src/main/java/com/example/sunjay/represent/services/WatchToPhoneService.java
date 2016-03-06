package com.example.sunjay.represent.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import android.os.Parcelable;
import android.util.Log;
import com.example.sunjay.represent.util.IntentUtil;
import com.example.sunjay.represent.util.ParcelableUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
  private GoogleApiClient watchApiClient;
  private List<Node> nodes = new ArrayList<>();
  private String message;
  private Parcelable data;

  @Override
  public void onCreate() {
    super.onCreate();
    watchApiClient = new GoogleApiClient.Builder(this)
      .addApi(Wearable.API)
      .addConnectionCallbacks(this)
      .build();
  }

  @Override
  public void onDestroy() {
    disconnectFromPhone();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    connectToPhone();
    if (intent != null) {
      this.message = intent.getStringExtra(IntentUtil.ExtraIdentifiers.MESSAGE.getValue());
      this.data = intent.getParcelableExtra(IntentUtil.ExtraIdentifiers.DATA.getValue());
    }
    return START_STICKY;
  }

  private void disconnectFromPhone() {
    if (watchApiClient != null) {
      watchApiClient.disconnect();
    }
  }

  private void connectToPhone() {
    disconnectFromPhone();
    watchApiClient = new GoogleApiClient.Builder(this)
      .addApi(Wearable.API)
      .addConnectionCallbacks(this)
      .addOnConnectionFailedListener(this)
      .build();
    watchApiClient.connect();
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onConnected(Bundle bundle) {
    Wearable.NodeApi.getConnectedNodes(watchApiClient)
      .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
        @Override
        public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
          nodes = getConnectedNodesResult.getNodes();
          sendMessage(message, data);
        }
      });
  }

  @Override
  public void onConnectionSuspended(int i) {}

  private void sendMessage(final String path, final Parcelable data) {
    for (Node node : nodes) {
      Log.d("MESSAGE_SENT", "MESSAGE_SENT");
      Wearable.MessageApi.sendMessage(watchApiClient, node.getId(), path, data != null ? ParcelableUtil.marshall(data) : new byte[0]);
    }
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {

  }
}
