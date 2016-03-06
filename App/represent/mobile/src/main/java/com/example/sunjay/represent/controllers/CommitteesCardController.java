package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.models.CommitteeItem;

import java.util.List;

public class CommitteesCardController {
  private TextView text;

  private Context context;

  private CommitteesCardController(Context context, ViewGroup layout) {
    text = (TextView) layout.findViewById(R.id.committees_card_text);

    this.context = context;
  }

  public static CommitteesCardController takeControl(Context context, ViewGroup layout) {
    return new CommitteesCardController(context, layout);
  }

  public void configureWithDataItem(final List<CommitteeItem> committeeItems) {
    if (committeeItems.size() == 0) {
      return;
    }

    StringBuilder committeeFormattedStringBuilder = new StringBuilder();
    for (int i = 0; i < committeeItems.size() - 1; i++) {
      committeeFormattedStringBuilder.append(committeeItems.get(i).name);
      committeeFormattedStringBuilder.append("\n");
    }
    committeeFormattedStringBuilder.append(committeeItems.get(committeeItems.size() - 1).name);
    text.setText(committeeFormattedStringBuilder.toString());
    committeeFormattedStringBuilder.setLength(0);
  }
}
