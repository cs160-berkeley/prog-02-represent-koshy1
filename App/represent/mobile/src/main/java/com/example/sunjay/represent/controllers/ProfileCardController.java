package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.shared.models.sunlightmodels.CongressPerson;
import com.squareup.picasso.Picasso;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class ProfileCardController {
  private ImageView profile;
  private TextView name;
  private TextView role;
  private TextView party;
  private TextView termEndDate;
  private ImageView email;
  private ImageView website;

  private Context context;

  private ProfileCardController(Context context, ViewGroup layout) {
    profile = (ImageView) layout.findViewById(R.id.profile_card_image);
    name = (TextView) layout.findViewById(R.id.profile_card_name);
    role = (TextView) layout.findViewById(R.id.profile_card_position);
    party = (TextView) layout.findViewById(R.id.profile_card_party);
    termEndDate = (TextView) layout.findViewById(R.id.profile_card_end_date);
    email = (ImageView) layout.findViewById(R.id.profile_card_email);
    website = (ImageView) layout.findViewById(R.id.profile_card_website);

    this.context = context;
  }

  public static ProfileCardController takeControl(Context context, ViewGroup layout) {
    return new ProfileCardController(context, layout);
  }

  public void configureWithDataItem(final CongressPerson congressPerson) {
    Picasso.with(context)
      .load(congressPerson.profile_url)
      .transform(new CropCircleTransformation())
      .into(profile);
    profile.requestLayout();
    name.setText(congressPerson.getFullName());
    role.setText(congressPerson.getFullPosition());
    party.setText(congressPerson.getFullParty());

    DateTime termEndDateTime = DateTime.parse(congressPerson.term_end, DateTimeFormat.forPattern("yyy-MM-dd"));
    termEndDate.setText(DateTimeFormat.forPattern("MMMM d, yyyy").print(termEndDateTime));

    email.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(congressPerson.getEmailLink()));
        context.startActivity(i);
      }
    });
    website.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(congressPerson.website));
        context.startActivity(i);
      }
    });
  }
}
