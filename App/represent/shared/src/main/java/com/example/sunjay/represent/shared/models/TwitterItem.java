package com.example.sunjay.represent.shared.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TwitterItem implements Parcelable {
  public String text;
  public String date;

  public TwitterItem() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(text);
    out.writeString(date);
  }

  public static final Parcelable.Creator<TwitterItem> CREATOR = new Parcelable.Creator<TwitterItem>() {
    public TwitterItem createFromParcel(Parcel in) {
      return new TwitterItem(in);
    }

    public TwitterItem[] newArray(int size) {
      return new TwitterItem[size];
    }
  };

  private TwitterItem(Parcel in) {
    text = in.readString();
    date = in.readString();
  }
}
