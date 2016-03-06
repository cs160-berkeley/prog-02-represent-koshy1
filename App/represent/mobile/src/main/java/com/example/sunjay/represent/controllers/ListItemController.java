package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.models.CongressPerson;

public class ListItemController {
  private ListItemControllerListener listener;

  private ImageView profile;
  private TextView name;
  private TextView role;
  private TextView party;
  private TextView email;
  private TextView website;
  private CardView cardView;

  private Context context;

  private ListItemController(Context context, ViewGroup layout, final ListItemController.ListItemControllerListener listener) {
    cardView = (CardView) layout.findViewById(R.id.list_item_card_view);
    profile = (ImageView) layout.findViewById(R.id.list_item_representative_image);
    name = (TextView) layout.findViewById(R.id.list_item_representative_name);
    role = (TextView) layout.findViewById(R.id.list_item_representative_position);
    party = (TextView) layout.findViewById(R.id.list_item_representative_party);
    email = (TextView) layout.findViewById(R.id.list_item_email);
    website = (TextView) layout.findViewById(R.id.list_item_website);

    this.context = context;
    this.listener = listener;
  }

  public static ListItemController takeControl(Context context, ViewGroup layout, final ListItemController.ListItemControllerListener listener) {
    return new ListItemController(context, layout, listener);
  }

  public void configureWithDataItem(final CongressPerson congressPerson, final int position) {
    profile.setImageDrawable(ContextCompat.getDrawable(context, congressPerson.profile_resource_id));
    name.setText(congressPerson.name);
    role.setText(congressPerson.position);
    party.setText(congressPerson.party);
    
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
