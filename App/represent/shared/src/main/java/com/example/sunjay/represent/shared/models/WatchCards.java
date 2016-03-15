package com.example.sunjay.represent.shared.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPerson;

import java.util.ArrayList;
import java.util.List;

public class WatchCards implements Parcelable {
  public List<CongressPerson> profiles;
  public List<ElectionResult> electionResults;

  public WatchCards() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeList(profiles);
    out.writeList(electionResults);
  }

  public static final Parcelable.Creator<WatchCards> CREATOR = new Parcelable.Creator<WatchCards>() {
    public WatchCards createFromParcel(Parcel in) {
      return new WatchCards(in);
    }

    public WatchCards[] newArray(int size) {
      return new WatchCards[size];
    }
  };

  private WatchCards(Parcel in) {
    profiles = new ArrayList<>();
    in.readList(profiles, CongressPerson.class.getClassLoader());
    electionResults = new ArrayList<>();
    in.readList(electionResults, ElectionResult.class.getClassLoader());
  }
}
