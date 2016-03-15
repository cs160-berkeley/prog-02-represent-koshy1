package com.example.sunjay.represent.shared.models.googlemodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationItem implements Parcelable {
  public List<AddressResultData> results;

  public LocationItem() {

  }

  public int describeContents() {
        return 0;
    }

  public void writeToParcel(Parcel out, int flags) {
    out.writeList(results);
  }

  public static final Parcelable.Creator<LocationItem> CREATOR = new Parcelable.Creator<LocationItem>() {
    public LocationItem createFromParcel(Parcel in) {
        return new LocationItem(in);
    }

    public LocationItem[] newArray(int size) {
        return new LocationItem[size];
    }
  };

  private LocationItem(Parcel in) {
      in.readList(results, null);
  }
}