package com.example.sunjay.represent.shared.models.googlemodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressComponent implements Parcelable {

  public String short_name;
  public List<String> types;

  public AddressComponent() {

  }

  public int describeContents() {
        return 0;
    }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(short_name);
    out.writeList(types);
  }

  public static final Parcelable.Creator<AddressComponent> CREATOR = new Parcelable.Creator<AddressComponent>() {
    public AddressComponent createFromParcel(Parcel in) {
        return new AddressComponent(in);
    }

    public AddressComponent[] newArray(int size) {
      return new AddressComponent[size];
    }
  };

  public String getType(){
    return types.get(0);
  }

  public String getShortName(){
    return short_name;
  }

  private AddressComponent(Parcel in) {
    short_name = in.readString();
    in.readList(types, null);
  }
}