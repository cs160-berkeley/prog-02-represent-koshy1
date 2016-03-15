package com.example.sunjay.represent.shared.models.sunlightmodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitteeItem implements Parcelable {
  private static final int MAX_NAME_LENGTH = 40;

  public String name;

  public CommitteeItem() {

  }

  public String getCommonName() {
    return name.length() > MAX_NAME_LENGTH ? name.substring(0, MAX_NAME_LENGTH) + "..." :  name;
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(name);
  }

  public static final Parcelable.Creator<CommitteeItem> CREATOR = new Parcelable.Creator<CommitteeItem>() {
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
