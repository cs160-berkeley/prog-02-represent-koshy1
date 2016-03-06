package com.example.sunjay.modelsandutils.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CongressPerson implements Parcelable {
  public String name;
  public String position;
  public String party;
  public String email;
  public String website_url;
  public String endDate;
  public int profile_resource_id;
  public TwitterItem recent_twitter_item;
  public List<CommitteeItem> committees;
  public List<BillItem> bills;

  public CongressPerson() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(name);
    out.writeString(position);
    out.writeString(party);
    out.writeString(email);
    out.writeString(website_url);
    out.writeString(endDate);
    out.writeInt(profile_resource_id);
    out.writeParcelable(recent_twitter_item, 0);
    out.writeList(committees);
    out.writeList(bills);
  }

  public static final Creator<CongressPerson> CREATOR
    = new Creator<CongressPerson>() {
    public CongressPerson createFromParcel(Parcel in) {
      return new CongressPerson(in);
    }

    public CongressPerson[] newArray(int size) {
      return new CongressPerson[size];
    }
  };

  private CongressPerson(Parcel in) {
    name = in.readString();
    position = in.readString();
    party = in.readString();
    email = in.readString();
    website_url = in.readString();
    endDate = in.readString();
    profile_resource_id = in.readInt();
    recent_twitter_item = in.readParcelable(TwitterItem.class.getClassLoader());
    committees = new ArrayList<>();
    in.readList(committees, CommitteeItem.class.getClassLoader());
    bills = new ArrayList<>();
    in.readList(bills, BillItem.class.getClassLoader());
  }
}
