package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.models.TwitterItem;

public class TwitterCardController {
  private TextView text;
  private TextView timestamp;

  private Context context;

  private TwitterCardController(Context context, ViewGroup layout) {
    text = (TextView) layout.findViewById(R.id.twitter_card_text);
    timestamp = (TextView) layout.findViewById(R.id.twitter_card_timestamp);

    this.context = context;
  }

  public static TwitterCardController getController(Context context, ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(context);
    ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.twitter_card, parent, false);
    return takeControl(context, layout);
  }

  public static TwitterCardController takeControl(Context context, ViewGroup layout) {
    return new TwitterCardController(context, layout);
  }

  public void configureWithDataItem(final TwitterItem twitterItem) {
    text.setText(twitterItem.text);
    timestamp.setText(twitterItem.created_at);
  }
}
