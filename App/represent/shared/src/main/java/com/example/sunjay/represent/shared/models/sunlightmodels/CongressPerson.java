package com.example.sunjay.represent.shared.models.sunlightmodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.sunjay.represent.shared.R;
import com.example.sunjay.represent.shared.models.TwitterItem;
import com.example.sunjay.represent.shared.util.SharedApplication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CongressPerson implements Parcelable {
  public String bioguide_id;
  public String first_name;
  public String last_name;
  public String oc_email;
  public String website;
  public String term_end;
  public String twitter_id;
  public String party;
  public String title;
  public TwitterItem twitter_item;
  public String profile_url;

  public CongressPerson() {

  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeString(bioguide_id);
    out.writeString(first_name);
    out.writeString(last_name);
    out.writeString(oc_email);
    out.writeString(website);
    out.writeString(term_end);
    out.writeString(twitter_id);
    out.writeString(party);
    out.writeString(title);
    out.writeParcelable(twitter_item, 0);
    out.writeString(profile_url);
  }

  public static final Parcelable.Creator<CongressPerson> CREATOR = new Parcelable.Creator<CongressPerson>() {
    public CongressPerson createFromParcel(Parcel in) {
      return new CongressPerson(in);
    }
    public CongressPerson[] newArray(int size) {
      return new CongressPerson[size];
    }
  };

  public String getFullName(){
      return first_name + " " + last_name;
  }

  public String getFullPosition() {
    if (title.equals("Rep")) {
      return SharedApplication.getContext().getString(R.string.representative);
    } else {
      return SharedApplication.getContext().getString(R.string.senator);
    }
  }

  public String getFullParty() {
    if (party.equals("D")) {
      return SharedApplication.getContext().getString(R.string.democrat);
    } else if (party.equals("R")) {
      return SharedApplication.getContext().getString(R.string.republican);
    } else if (party.equals("I")) {
      return SharedApplication.getContext().getString(R.string.independent);
    } else {
      return SharedApplication.getContext().getString(R.string.other_party);
    }
  }

  public String getEmailLink() {
    return "mailto:" + oc_email;
  }

  private CongressPerson(Parcel in) {
    bioguide_id = in.readString();
    first_name = in.readString();
    last_name = in.readString();
    oc_email = in.readString();
    website = in.readString();
    term_end = in.readString();
    twitter_id = in.readString();
    party = in.readString();
    title = in.readString();
    twitter_item = in.readParcelable(TwitterItem.class.getClassLoader());
    profile_url = in.readString();
  }
}
