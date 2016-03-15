package com.example.sunjay.represent.shared.models.sunlightmodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItem implements Parcelable {
  private static final int MAX_TITLE_LENGTH = 30;

  public String short_title;
  public String introduced_on;
  public String official_title;
  public String popular_title;

  public BillItem() {

  }

  public String getCommonName() {
    String title = official_title;
    if (popular_title != null) {
      title = popular_title;
    } else if (short_title != null) {
      title = short_title;
    }
    return title.length() > MAX_TITLE_LENGTH ? title.substring(0, MAX_TITLE_LENGTH) + "..." :  title;
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(short_title);
    out.writeString(introduced_on);
    out.writeString(official_title);
    out.writeString(popular_title);
  }

  public static final Parcelable.Creator<BillItem> CREATOR = new Parcelable.Creator<BillItem>() {
    public BillItem createFromParcel(Parcel in) {
      return new BillItem(in);
    }

    public BillItem[] newArray(int size) {
      return new BillItem[size];
    }
  };

  private BillItem(Parcel in) {
    short_title = in.readString();
    introduced_on = in.readString();
    official_title = in.readString();
    popular_title = in.readString();
  }
}
