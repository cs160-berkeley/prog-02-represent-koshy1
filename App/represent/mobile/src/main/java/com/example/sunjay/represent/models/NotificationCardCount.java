package com.example.sunjay.represent.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationCardCount implements Parcelable {
  public int count;

  public NotificationCardCount() {

  }

  public NotificationCardCount(int count) {
    this.count = count;
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(count);
  }

  public static final Parcelable.Creator<NotificationCardCount> CREATOR
    = new Parcelable.Creator<NotificationCardCount>() {
    public NotificationCardCount createFromParcel(Parcel in) {
      return new NotificationCardCount(in);
    }

    public NotificationCardCount[] newArray(int size) {
      return new NotificationCardCount[size];
    }
  };

  private NotificationCardCount(Parcel in) {
    count = in.readInt();
  }
}
