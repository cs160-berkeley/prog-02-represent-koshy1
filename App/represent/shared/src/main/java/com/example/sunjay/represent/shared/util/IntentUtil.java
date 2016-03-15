package com.example.sunjay.represent.shared.util;

public class IntentUtil {
  public enum RequestIdentifiers {
    OPEN_DETAIL("com.example.sunjay.represent.OPEN_DETAIL"),
    SHAKE("com.example.sunjay.represent.SHAKE"),
    SYNCHRONIZE_WATCH("com.example.sunjay.represent.SYNCRONIZE_WATCH");

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

  public enum MessageTypes {
    OPEN_DETAIL("/detail"),
    OPEN_URL("/open_url"),
    SHAKE("/shake"),
    PROFILE("/profile"),
    SYNCHRONIZE_WATCH("/syncronize_watch"),
    ELECTION_RESULTS("/election_results"),
    CARDS("/cards");

    public String identifier;

    MessageTypes(String value) {
      identifier = value;
    }

    public String getValue() {
      return identifier;
    }
  }
}
