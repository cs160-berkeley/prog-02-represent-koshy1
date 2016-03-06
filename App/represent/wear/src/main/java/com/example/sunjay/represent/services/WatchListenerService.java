package com.example.sunjay.represent.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.sunjay.represent.R;
import com.example.sunjay.represent.cards.NotificationCard;
import com.example.sunjay.represent.models.CongressPerson;
import com.example.sunjay.represent.models.ElectionResult;
import com.example.sunjay.represent.models.NotificationCardCount;
import com.example.sunjay.represent.util.IntentUtil;
import com.example.sunjay.represent.util.ParcelableUtil;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.ArrayList;

public class WatchListenerService extends WearableListenerService {
  private final static String ELECTION_RESULTS_GROUP = "election_results_group";
  private final static String PROFILE_GROUP = "profile_group";
  private static int identifier = 0;
  private static int unique_counter = 0;

  private ArrayList<Notification> profileNotifications = new ArrayList<>();
  private ArrayList<Notification> electionResultNotifications = new ArrayList<>();

  private int totalProfileNotificationsCount = -1;

  @Override
  public void onMessageReceived(MessageEvent messageEvent) {
    if(messageEvent.getPath().equals(IntentUtil.MessageTypes.PROFILE.getValue())) {
      processProfileNotification(messageEvent);
    } else if (messageEvent.getPath().equals(IntentUtil.MessageTypes.ELECTION_RESULTS.getValue())) {
      processElectionResultsNotification(messageEvent);
    } else if (messageEvent.getPath().equals(IntentUtil.MessageTypes.PROFILE_NOTIFICATION_COUNT.getValue())) {
      setTotalProfileNotificationsCount(ParcelableUtil.unmarshall(messageEvent.getData(), NotificationCardCount.CREATOR).count);
      return;
    } else {
      super.onMessageReceived(messageEvent);
      return;
    }
  }

  private void processElectionResultsNotification(MessageEvent messageEvent) {
    Parcelable item = ParcelableUtil.unmarshall(messageEvent.getData(), ElectionResult.CREATOR);
    Intent intent = new Intent(this, NotificationCard.class);
    intent.putExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue(), item);
    intent.putExtra(IntentUtil.ExtraIdentifiers.CARD_TYPE.getValue(), IntentUtil.CardTypes.ELECTION_RESULTS.getValue());
    PendingIntent pendingIntent = PendingIntent.getActivity(this, unique_counter, intent, 0);
    unique_counter++;

    Notification notification = new NotificationCompat.Builder(this)
      .extend(new NotificationCompat.WearableExtender()
        .setDisplayIntent(pendingIntent)
        .setCustomSizePreset(NotificationCompat.WearableExtender.SIZE_FULL_SCREEN)
        .setHintShowBackgroundOnly(true))
      .setSmallIcon(R.drawable.ic_launcher)
      .setOngoing(true)
      .build();

    electionResultNotifications = new ArrayList<>();
    electionResultNotifications.add(notification);

    if (profileNotifications.size() + electionResultNotifications.size() == totalProfileNotificationsCount) {
      assembleNotifications();
    }
  }

  private void processProfileNotification(MessageEvent messageEvent) {
    Parcelable item = ParcelableUtil.unmarshall(messageEvent.getData(), CongressPerson.CREATOR);
    Intent intent = new Intent(this, NotificationCard.class);
    intent.putExtra(IntentUtil.ExtraIdentifiers.ITEM.getValue(), item);
    intent.putExtra(IntentUtil.ExtraIdentifiers.CARD_TYPE.getValue(), IntentUtil.CardTypes.CONGRESS_PROFILE.getValue());
    PendingIntent pendingIntent = PendingIntent.getActivity(this, unique_counter, intent, 0);
    unique_counter++;

    Notification notification = new NotificationCompat.Builder(this)
      .extend(new NotificationCompat.WearableExtender()
        .setDisplayIntent(pendingIntent)
        .setCustomSizePreset(NotificationCompat.WearableExtender.SIZE_FULL_SCREEN)
        .setHintShowBackgroundOnly(true))
      .setSmallIcon(R.drawable.ic_launcher)
      .setOngoing(true)
      .build();
    profileNotifications.add(notification);

    if (profileNotifications.size() + electionResultNotifications.size() == totalProfileNotificationsCount) {
      assembleNotifications();
    }
  }

  private void setTotalProfileNotificationsCount(int count) {
    this.totalProfileNotificationsCount = count;
    if (profileNotifications.size() + electionResultNotifications.size() == count) {
      assembleNotifications();
    }
  }

  private void assembleNotifications() {
    Notification compiledNotifications =
      new NotificationCompat.Builder(this)
          .extend(new NotificationCompat.WearableExtender()
          .setHintShowBackgroundOnly(true)
          .addPages(profileNotifications)
          .setCustomSizePreset(NotificationCompat.WearableExtender.SIZE_FULL_SCREEN))
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentText(getResources().getString(R.string.notification_profile_card_text))
        .setGroup(PROFILE_GROUP)
        .setOngoing(true)
        .build();

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
    notificationManager.cancelAll();

    notificationManager.notify(identifier, compiledNotifications);
    profileNotifications.clear();
    identifier++;

    compiledNotifications =
      new NotificationCompat.Builder(this)
        .extend(new NotificationCompat.WearableExtender()
          .setHintShowBackgroundOnly(true)
          .addPages(electionResultNotifications)
          .setCustomSizePreset(NotificationCompat.WearableExtender.SIZE_FULL_SCREEN))
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentText(getResources().getString(R.string.notification_election_results_card_text))
        .setGroup(ELECTION_RESULTS_GROUP)
        .setOngoing(true)
        .build();

    notificationManager.notify(identifier, compiledNotifications);
    electionResultNotifications.clear();
    identifier++;
  }
}
