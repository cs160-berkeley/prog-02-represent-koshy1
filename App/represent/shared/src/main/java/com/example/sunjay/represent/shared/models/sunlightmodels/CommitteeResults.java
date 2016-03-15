package com.example.sunjay.represent.shared.models.sunlightmodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitteeResults {
  public List<CommitteeItem> results;

  public CommitteeResults() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeList(results);
  }


  public static final Parcelable.Creator<CommitteeResults> CREATOR = new Parcelable.Creator<CommitteeResults>() {
    public CommitteeResults createFromParcel(Parcel in) {
      return new CommitteeResults(in);
    }

    public CommitteeResults[] newArray(int size) {
      return new CommitteeResults[size];
    }
  };

  private CommitteeResults(Parcel in) {
    in.readList(results, CongressPerson.class.getClassLoader());
  }
}
