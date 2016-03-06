package com.example.sunjay.represent.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CommitteeItem implements Parcelable {
  public String name;

  public CommitteeItem() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(name);
  }

  public static final Parcelable.Creator<CommitteeItem> CREATOR
    = new Parcelable.Creator<CommitteeItem>() {
    public CommitteeItem createFromParcel(Parcel in) {
      return new CommitteeItem(in);
    }

    public CommitteeItem[] newArray(int size) {
      return new CommitteeItem[size];
    }
  };

  private CommitteeItem(Parcel in) {
    name = in.readString();
  }
}
