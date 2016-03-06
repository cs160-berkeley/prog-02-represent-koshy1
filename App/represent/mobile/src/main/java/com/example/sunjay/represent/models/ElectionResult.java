package com.example.sunjay.represent.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ElectionResult implements Parcelable {
  public String title;
  public String location;
  public String republican_candidate;
  public int republic_vote;
  public String democrat_candidate;
  public int democrat_vote;

  public ElectionResult() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(title);
    out.writeString(location);
    out.writeString(republican_candidate);
    out.writeInt(republic_vote);
    out.writeString(democrat_candidate);
    out.writeInt(democrat_vote);
  }

  public static final Parcelable.Creator<ElectionResult> CREATOR
    = new Parcelable.Creator<ElectionResult>() {
    public ElectionResult createFromParcel(Parcel in) {
      return new ElectionResult(in);
    }

    public ElectionResult[] newArray(int size) {
      return new ElectionResult[size];
    }
  };

  private ElectionResult(Parcel in) {
    title = in.readString();
    location = in.readString();
    republican_candidate = in.readString();
    republic_vote = in.readInt();
    democrat_candidate = in.readString();
    democrat_vote = in.readInt();
  }
}
