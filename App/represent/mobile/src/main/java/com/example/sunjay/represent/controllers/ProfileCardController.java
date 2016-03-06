package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.models.CongressPerson;

public class ProfileCardController {
  private ImageView profile;
  private TextView name;
  private TextView role;
  private TextView party;
  private TextView termEndDate;
  private TextView email;
  private TextView website;

  private Context context;

  private ProfileCardController(Context context, ViewGroup layout) {
    profile = (ImageView) layout.findViewById(R.id.profile_card_image);
    name = (TextView) layout.findViewById(R.id.profile_card_name);
    role = (TextView) layout.findViewById(R.id.profile_card_position);
    party = (TextView) layout.findViewById(R.id.profile_card_party);
    termEndDate = (TextView) layout.findViewById(R.id.profile_card_end_date);
    email = (TextView) layout.findViewById(R.id.profile_card_email);
    website = (TextView) layout.findViewById(R.id.profile_card_website);

    this.context = context;
  }

  public static ProfileCardController takeControl(Context context, ViewGroup layout) {
    return new ProfileCardController(context, layout);
  }

  public void configureWithDataItem(final CongressPerson congressPerson) {
    profile.setImageDrawable(ContextCompat.getDrawable(context, congressPerson.profile_resource_id));
    name.setText(congressPerson.name);
    role.setText(congressPerson.position);
    party.setText(congressPerson.party);
    termEndDate.setText(congressPerson.endDate);

    email.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(congressPerson.email));
        context.startActivity(i);
      }
    });
    website.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(congressPerson.website_url));
        context.startActivity(i);
      }
    });
  }
}
