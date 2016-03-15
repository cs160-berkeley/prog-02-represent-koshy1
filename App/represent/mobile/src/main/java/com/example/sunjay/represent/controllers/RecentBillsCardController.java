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
import com.example.sunjay.represent.shared.models.sunlightmodels.BillItem;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

public class RecentBillsCardController {
  private static final int MAX_BILLS_TO_DISPLAY = 5;

  private TextView text;

  private Context context;

  private RecentBillsCardController(Context context, ViewGroup layout) {
    text = (TextView) layout.findViewById(R.id.recent_bills_card_text);

    this.context = context;
  }

  public static RecentBillsCardController takeControl(Context context, ViewGroup layout) {
    return new RecentBillsCardController(context, layout);
  }

  public void configureWithDataItem(List<BillItem> billItems) {
    if (billItems.size() == 0) {
      return;
    } else if (billItems.size() > MAX_BILLS_TO_DISPLAY) {
      billItems = billItems.subList(0, MAX_BILLS_TO_DISPLAY);
    }

    SpannableStringBuilder recentBillsSpannableStringBuilder = new SpannableStringBuilder();
    int start = 0;

    for (BillItem billItem : billItems) {
      recentBillsSpannableStringBuilder.append(billItem.getCommonName());
      start += billItem.getCommonName().length();

      recentBillsSpannableStringBuilder.append(" ");
      start += 1;

      DateTime introducedOnDateTime = DateTime.parse(billItem.introduced_on, DateTimeFormat.forPattern("yyy-MM-dd"));
      String introducedOn = DateTimeFormat.forPattern("MMMM d, yyyy").print(introducedOnDateTime);

      recentBillsSpannableStringBuilder.append(introducedOn);
      recentBillsSpannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_medium_gray)), start, start + introducedOn.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      recentBillsSpannableStringBuilder.setSpan(new RelativeSizeSpan(0.83f), start, start + introducedOn.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      start += introducedOn.length();

      if (billItems.indexOf(billItem) != billItems.size() - 1) {
        recentBillsSpannableStringBuilder.append("\n");
        start += 1;
      }
    }
    text.setText(recentBillsSpannableStringBuilder);
    recentBillsSpannableStringBuilder.clear();
  }
}
