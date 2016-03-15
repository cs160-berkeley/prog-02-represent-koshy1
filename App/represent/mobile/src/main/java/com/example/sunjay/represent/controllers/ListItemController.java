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

public class ListItemController {
  private ListItemControllerListener listener;

  private ImageView profile;
  private TextView name;
  private TextView role;
  private TextView party;
  private ImageView email;
  private ImageView website;
  private View cardView;

  private Context context;

  private ListItemController(Context context, ViewGroup layout, final ListItemController.ListItemControllerListener listener) {
    cardView = layout.findViewById(R.id.list_item_card_view);
    profile = (ImageView) layout.findViewById(R.id.list_item_representative_image);
    name = (TextView) layout.findViewById(R.id.list_item_representative_name);
    role = (TextView) layout.findViewById(R.id.list_item_representative_position);
    party = (TextView) layout.findViewById(R.id.list_item_representative_party);
    email = (ImageView) layout.findViewById(R.id.list_item_email);
    website = (ImageView) layout.findViewById(R.id.list_item_website);

    this.context = context;
    this.listener = listener;
  }

  public static ListItemController takeControl(Context context, ViewGroup layout, final ListItemController.ListItemControllerListener listener) {
    return new ListItemController(context, layout, listener);
  }

  public void configureWithDataItem(final CongressPerson congressPerson, final int position) {
    Picasso.with(context)
      .load(congressPerson.profile_url)
      .transform(new CropCircleTransformation())
      .into(profile);
    name.setText(congressPerson.getFullName());
    role.setText(congressPerson.getFullPosition());
    party.setText(congressPerson.getFullParty());

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

    cardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.onCardClick(position, ListItemController.this);
        }
      }
    });
  }

  public interface ListItemControllerListener {
    void onCardClick(int position, ListItemController listItemController);
  }
}
