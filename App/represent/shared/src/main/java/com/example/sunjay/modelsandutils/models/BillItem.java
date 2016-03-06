package com.example.sunjay.modelsandutils.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BillItem implements Parcelable {
  public String name;
  public String date;

  public BillItem() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(name);
    out.writeString(date);
  }

  public static final Creator<BillItem> CREATOR
    = new Creator<BillItem>() {
    public BillItem createFromParcel(Parcel in) {
      return new BillItem(in);
    }

    public BillItem[] newArray(int size) {
      return new BillItem[size];
    }
  };

  private BillItem(Parcel in) {
    name = in.readString();
    date = in.readString();
  }
}
