package com.example.sunjay.modelsandutils.util;

public class IntentUtil {
  public enum RequestIdentifiers {
    OPEN_DETAIL("com.example.sunjay.represent.OPEN_DETAIL"),
    SHAKE("com.example.sunjay.represent.SHAKE");

    public String identifier;

    RequestIdentifiers(String value) {
      identifier = value;
    }

    public String getValue() {
      return identifier;
    }
  }

  public enum ExtraIdentifiers {
    MESSAGE("message"),
    DATA("data"),
    CARD_TYPE("cardType"),
    ITEM("items");

    public String identifier;

    ExtraIdentifiers(String value) {
      identifier = value;
    }

    public String getValue() {
      return identifier;
    }
  }

  public enum CardTypes {
    CONGRESS_PROFILE("congressProfile"),
    ELECTION_RESULTS("electionResults");

    public String identifier;

    CardTypes(String value) {
      this.identifier = value;
    }

    public String getValue() {
      return identifier;
    }

    public static CardTypes getCardType(String value) {
      switch (value) {
        case "congressProfile":
          return CONGRESS_PROFILE;
        case "electionResults":
          return ELECTION_RESULTS;
      }
      return null;
    }
  }

  public enum MessageTypes {
    OPEN_DETAIL("/detail"),
    SHAKE("/shake"),
    PROFILE("/profile"),
    ELECTION_RESULTS("/election_results"),
    PROFILE_NOTIFICATION_COUNT("/profile_notification_count");

    public String identifier;

    MessageTypes(String value) {
      identifier = value;
    }

    public String getValue() {
      return identifier;
    }
  }
}
