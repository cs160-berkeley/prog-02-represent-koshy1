package com.example.sunjay.represent.shared.models.sunlightmodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CongressPersonResults {
  public List<CongressPerson> results;

  public CongressPersonResults() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeList(results);
  }


  public static final Parcelable.Creator<CongressPersonResults> CREATOR = new Parcelable.Creator<CongressPersonResults>() {
    public CongressPersonResults createFromParcel(Parcel in) {
      return new CongressPersonResults(in);
    }

    public CongressPersonResults[] newArray(int size) {
      return new CongressPersonResults[size];
    }
  };

  private CongressPersonResults(Parcel in) {
    in.readList(results, CongressPerson.class.getClassLoader());
  }
}
