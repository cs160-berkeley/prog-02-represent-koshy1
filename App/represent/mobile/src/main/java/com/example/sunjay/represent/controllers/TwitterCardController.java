package com.example.sunjay.represent.controllers;

import android.content.Context;
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

  public static TwitterCardController takeControl(Context context, ViewGroup layout) {
    return new TwitterCardController(context, layout);
  }

  public void configureWithDataItem(final TwitterItem twitterItem) {
    text.setText(twitterItem.text);
    timestamp.setText(twitterItem.created_at);
  }
}
