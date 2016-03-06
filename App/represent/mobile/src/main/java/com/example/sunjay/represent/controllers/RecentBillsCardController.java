package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.models.BillItem;

import java.util.List;

public class RecentBillsCardController {
  private TextView text;

  private Context context;

  private RecentBillsCardController(Context context, ViewGroup layout) {
    text = (TextView) layout.findViewById(R.id.recent_bills_card_text);

    this.context = context;
  }

  public static RecentBillsCardController takeControl(Context context, ViewGroup layout) {
    return new RecentBillsCardController(context, layout);
  }

  public void configureWithDataItem(final List<BillItem> billItems) {
    if (billItems.size() == 0) {
      return;
    }

    SpannableStringBuilder recentBillsSpannableStringBuilder = new SpannableStringBuilder();
    int start = 0;

    for (BillItem billItem : billItems) {
      recentBillsSpannableStringBuilder.append(billItem.name);
      start += billItem.name.length();

      recentBillsSpannableStringBuilder.append(" ");
      start += 1;

      recentBillsSpannableStringBuilder.append(billItem.date);
      recentBillsSpannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_medium_gray)), start, start + billItem.date.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      recentBillsSpannableStringBuilder.setSpan(new RelativeSizeSpan(0.83f), start, start + billItem.date.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      start += billItem.date.length();

      if (billItems.indexOf(billItem) != billItems.size() - 1) {
        recentBillsSpannableStringBuilder.append("\n");
        start += 1;
      }
    }
    text.setText(recentBillsSpannableStringBuilder);
    recentBillsSpannableStringBuilder.clear();
  }
}
