package com.example.sunjay.represent.shared.models.sunlightmodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillResults {
  public List<BillItem> results;

  public BillResults() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeList(results);
  }


  public static final Parcelable.Creator<BillResults> CREATOR = new Parcelable.Creator<BillResults>() {
    public BillResults createFromParcel(Parcel in) {
      return new BillResults(in);
    }

    public BillResults[] newArray(int size) {
      return new BillResults[size];
    }
  };

  private BillResults(Parcel in) {
    in.readList(results, CongressPerson.class.getClassLoader());
  }
}
