package com.example.sunjay.represent.shared.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZipCodeItem implements Parcelable {
  public String zip_code;
  public String county;
  public String state;

  public ZipCodeItem() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(zip_code);
    out.writeString(county);
    out.writeString(state);
  }

  public static final Parcelable.Creator<ZipCodeItem> CREATOR = new Parcelable.Creator<ZipCodeItem>() {
    public ZipCodeItem createFromParcel(Parcel in) {
      return new ZipCodeItem(in);
    }

    public ZipCodeItem[] newArray(int size) {
      return new ZipCodeItem[size];
    }
  };

  private ZipCodeItem(Parcel in) {
    zip_code = in.readString();
    county = in.readString();
    state = in.readString();
  }
}
