package com.example.sunjay.represent.shared.models;

import android.os.Parcel;
import android.os.Parcelable;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectionResult implements Parcelable {
  public String title;
  @JsonProperty("county-name")
  public String county_name;
  @JsonProperty("state-postal")
  public String state_postal;
  @JsonProperty("obama-percentage")
  public String obama_percentage;
  @JsonProperty("romney-percentage")
  public String romney_percentage;

  public ElectionResult() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(title);
    out.writeString(county_name);
    out.writeString(state_postal);
    out.writeString(obama_percentage);
    out.writeString(romney_percentage);
  }

  public static final Parcelable.Creator<ElectionResult> CREATOR = new Parcelable.Creator<ElectionResult>() {
    public ElectionResult createFromParcel(Parcel in) {
      return new ElectionResult(in);
    }

    public ElectionResult[] newArray(int size) {
      return new ElectionResult[size];
    }
  };

  public final String getLocationString(){
      return county_name + ", " + state_postal;
  }

  private ElectionResult(Parcel in) {
    title = in.readString();
    county_name = in.readString();
    state_postal = in.readString();
    obama_percentage = in.readString();
    romney_percentage = in.readString();
  }
}
