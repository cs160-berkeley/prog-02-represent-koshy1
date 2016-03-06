package com.example.sunjay.represent.services;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.sunjay.represent.R;
import com.example.sunjay.represent.models.*;

import java.util.ArrayList;
import java.util.List;

public class API {
  private static API apiSingleton;
  private Context context;

  private static ArrayList<CongressPerson> placeholderCongressPeople95014;
  private static ArrayList<CongressPerson> placeholderCongressPeopleOther;
  private static ElectionResult electionResults;

  public API(Context context) {
    this.context = context;
    generateMockData();
  }

  private void generateMockData() {
    {
      BillItem bill1 = new BillItem();
      bill1.name = "S. 2552";
      bill1.date = "February 11, 2016";

      BillItem bill2 = new BillItem();
      bill2.name = "S. 2533";
      bill2.date = "February 10, 2016";

      BillItem bill3 = new BillItem();
      bill3.name = "S. 2442";
      bill3.date = "January 12, 2016";

      BillItem bill4 = new BillItem();
      bill4.name = "S. 2522";
      bill4.date = "December 18, 2015";

      BillItem bill5 = new BillItem();
      bill5.name = "S. 2337";
      bill5.date = "December 1, 2015";

      BillItem bill6 = new BillItem();
      bill6.name = "S. Res. 299";
      bill6.date = "October 28, 2015";

      ArrayList<BillItem> bills = new ArrayList<>();
      bills.add(bill1);
      bills.add(bill2);
      bills.add(bill3);
      bills.add(bill4);
      bills.add(bill5);
      bills.add(bill6);

      CommitteeItem committee1 = new CommitteeItem();
      committee1.name = "Senate Intelligence Committee";

      CommitteeItem committee2 = new CommitteeItem();
      committee2.name = "Senate Narcotics Committee";

      CommitteeItem committee3 = new CommitteeItem();
      committee3.name = "Senate Judiciary Committee";

      CommitteeItem committee4 = new CommitteeItem();
      committee4.name = "Senate Committee on Appropriations";

      ArrayList<CommitteeItem> committees = new ArrayList<>();
      committees.add(committee1);
      committees.add(committee2);
      committees.add(committee3);
      committees.add(committee4);

      placeholderCongressPeople95014 = new ArrayList<>();
      CongressPerson congressPerson1 = new CongressPerson();
      congressPerson1.name = "Barbara Lee";
      congressPerson1.position = "House Representative";
      congressPerson1.party = "Democratic";
      congressPerson1.profile_resource_id = R.drawable.barbaralee;
      congressPerson1.email = "https://lee.house.gov/contact-the-office/email-me";
      congressPerson1.website_url = "https://lee.house.gov/";
      congressPerson1.endDate = "January 3, 2016";
      congressPerson1.bills = bills;
      congressPerson1.committees = committees;

      TwitterItem tweet = new TwitterItem();
      tweet.text = "Can’t think of a better way to kick off #WomensHistoryMonth than with champions @FLOTUS &amp; @DrBiden! #WomenSucceed";
      tweet.created_at = "Twitter - 7h";
      congressPerson1.recent_twitter_item = tweet;

      CongressPerson congressPerson2 = new CongressPerson();
      congressPerson2.name = "Barbara Boxer";
      congressPerson2.position = "Senator";
      congressPerson2.party = "Democratic";
      congressPerson2.profile_resource_id = R.drawable.barbaraboxer;
      congressPerson2.email = "mailto:senator@boxer.senate.gov";
      congressPerson2.website_url = "https://www.boxer.senate.gov/";
      congressPerson2.endDate = "January 3, 2016";
      congressPerson2.bills = bills;
      congressPerson2.committees = committees;

      TwitterItem tweet2 = new TwitterItem();
      tweet2.text = "So happy my grandkids could join me on stage at #cadem16 today!";
      tweet2.created_at = "Twitter - 6h";
      congressPerson2.recent_twitter_item = tweet2;

      CongressPerson congressPerson3 = new CongressPerson();
      congressPerson3.name = "Dianne Feinstein";
      congressPerson3.position = "Senator";
      congressPerson3.party = "Democratic";
      congressPerson3.profile_resource_id = R.drawable.diannefeinstein;
      congressPerson3.email = "mailto:senator@feinstein.senate.gov";
      congressPerson3.website_url = "https://www.feinstein.senate.gov/";
      congressPerson3.endDate = "January 3, 2018";
      congressPerson3.bills = bills;
      congressPerson3.committees = committees;

      TwitterItem tweet3 = new TwitterItem();
      tweet3.text = "The federal government needs authority to aggressively pursue transnational criminal organizations to reduce flow of drugs into our country.";
      tweet3.created_at = "Twitter - 3h";
      congressPerson3.recent_twitter_item = tweet3;

      placeholderCongressPeople95014.add(congressPerson1);
      placeholderCongressPeople95014.add(congressPerson2);
      placeholderCongressPeople95014.add(congressPerson3);
    }

    {
      BillItem bill1 = new BillItem();
      bill1.name = "S. 2552";
      bill1.date = "February 11, 2016";

      BillItem bill2 = new BillItem();
      bill2.name = "S. 2533";
      bill2.date = "February 10, 2016";

      BillItem bill3 = new BillItem();
      bill3.name = "S. 2442";
      bill3.date = "January 12, 2016";

      BillItem bill4 = new BillItem();
      bill4.name = "S. 2522";
      bill4.date = "December 18, 2015";

      BillItem bill5 = new BillItem();
      bill5.name = "S. 2337";
      bill5.date = "December 1, 2015";

      BillItem bill6 = new BillItem();
      bill6.name = "S. Res. 299";
      bill6.date = "October 28, 2015";

      ArrayList<BillItem> bills = new ArrayList<>();
      bills.add(bill1);
      bills.add(bill2);
      bills.add(bill3);
      bills.add(bill4);
      bills.add(bill5);
      bills.add(bill6);

      CommitteeItem committee1 = new CommitteeItem();
      committee1.name = "Senate Intelligence Committee";

      CommitteeItem committee2 = new CommitteeItem();
      committee2.name = "Senate Narcotics Committee";

      CommitteeItem committee3 = new CommitteeItem();
      committee3.name = "Senate Judiciary Committee";

      CommitteeItem committee4 = new CommitteeItem();
      committee4.name = "Senate Committee on Appropriations";

      ArrayList<CommitteeItem> committees = new ArrayList<>();
      committees.add(committee1);
      committees.add(committee2);
      committees.add(committee3);
      committees.add(committee4);

      placeholderCongressPeopleOther = new ArrayList<>();
      CongressPerson congressPerson4 = new CongressPerson();
      congressPerson4.name = "Rodney Davis";
      congressPerson4.position = "House Representative";
      congressPerson4.party = "Republican";
      congressPerson4.profile_resource_id = R.drawable.rodneydavis;
      congressPerson4.email = "https://rodneydavis.house.gov/contact/";
      congressPerson4.website_url = "https://rodneydavis.house.gov";
      congressPerson4.endDate = "January 3, 2017";
      congressPerson4.bills = bills;
      congressPerson4.committees = committees;

      TwitterItem tweet4 = new TwitterItem();
      tweet4.text = "#RareDiseases affect our nation's youngest. 30% of kids w/ diseases won't  see their 5th bday. #CuresNow #TeamJonny";
      tweet4.created_at = "Twitter - 10h";
      congressPerson4.recent_twitter_item = tweet4;

      CongressPerson congressPerson5 = new CongressPerson();
      congressPerson5.name = "Mark Kirk";
      congressPerson5.position = "Senator";
      congressPerson5.party = "Republican";
      congressPerson5.profile_resource_id = R.drawable.markkirk;
      congressPerson5.email = "mailto:senator@kirk.senate.gov";
      congressPerson5.website_url = "https://www.bkirk.senate.gov/";
      congressPerson5.endDate = "January 3, 2017";
      congressPerson5.bills = bills;
      congressPerson5.committees = committees;

      TwitterItem tweet5 = new TwitterItem();
      tweet5.text = "@GovRauner joined me at a briefing for @NGA_GEOINT officials today to announce $115 mil. in incentives for bringing @nga2scottafb in #IL.";
      tweet5.created_at = "Twitter - 9h";
      congressPerson5.recent_twitter_item = tweet5;

      CongressPerson congressPerson6 = new CongressPerson();
      congressPerson6.name = "Dick Durbin";
      congressPerson6.position = "Senator";
      congressPerson6.party = "Democratic";
      congressPerson6.profile_resource_id = R.drawable.dickdurbin;
      congressPerson6.email = "mailto:senator@durbin.senate.gov";
      congressPerson6.website_url = "https://www.durbin.senate.gov/";
      congressPerson6.endDate = "January 3, 2021";
      congressPerson6.bills = bills;
      congressPerson6.committees = committees;

      TwitterItem tweet6 = new TwitterItem();
      tweet6.text = "In St. Clair County this afternoon discussing Illinois’ bid to house @NGA_GEOINT";
      tweet6.created_at = "Twitter - 12h";
      congressPerson6.recent_twitter_item = tweet6;

      placeholderCongressPeopleOther.add(congressPerson4);
      placeholderCongressPeopleOther.add(congressPerson5);
      placeholderCongressPeopleOther.add(congressPerson6);
    }
  }

  private ElectionResult getMockElectionResults(String zipCode) {
    electionResults = new ElectionResult();
    electionResults.republic_vote = 48;
    electionResults.democrat_vote = 50;
    electionResults.republican_candidate = "Mitt Romney";
    electionResults.democrat_candidate = "Barack Obama";
    electionResults.title = "2012 Election Results";
    electionResults.location = zipCode;
    return electionResults;
  }

  public static void initializeAPI(Context context) {
    apiSingleton = new API(context);
  }

  public static API getAPI() {
    return apiSingleton;
  }

  public void getCongressPeople(String zipCode, APIResponseListener<List<CongressPerson>> responseListener) {
    responseListener.onSuccess(getMockDataForZipCode(zipCode));
  }

  public void getElectionResults(String zipCode, APIResponseListener<ElectionResult> responseListener) {
    responseListener.onSuccess(getMockElectionResults(zipCode));
  }

  private ArrayList<CongressPerson> getMockDataForZipCode(String zipCode) {
    if (zipCode.contains("95014")) {
      return placeholderCongressPeople95014;
    }
    return placeholderCongressPeopleOther;
  }

  public static class APIResponseListener<T> {
    public void onSuccess(T data) {

    }
  }
}
