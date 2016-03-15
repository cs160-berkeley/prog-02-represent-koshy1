package com.example.sunjay.represent.shared.models.googlemodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResultData implements Parcelable {
  public List<AddressComponent> address_components;

  public AddressResultData() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeList(address_components);
  }

  public static final Parcelable.Creator<AddressResultData> CREATOR = new Parcelable.Creator<AddressResultData>() {
    public AddressResultData createFromParcel(Parcel in) {
      return new AddressResultData(in);
    }

    public AddressResultData[] newArray(int size) {
      return new AddressResultData[size];
    }
  };

  private AddressResultData(Parcel in) {
    in.readList(address_components, AddressComponent.class.getClassLoader());
  }

  public String getCountyName(){
    for (AddressComponent a : address_components){
      if (a.getType().equals("administrative_area_level_2")){
        return a.getShortName().replace(" County", "");
      }
    }
    return "";
  }

  public String getStateName(){
    for (AddressComponent a : address_components){
      if (a.getType().equals("administrative_area_level_1")){
        return a.getShortName();
      }
    }
    return "";
  }

  public String getZipCode(){
    for (AddressComponent a : address_components){
      if (a.getType().equals("postal_code")){
        return a.getShortName();
      }
    }
    return "";
  }
}