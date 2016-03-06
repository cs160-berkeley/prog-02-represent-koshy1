package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sunjay.represent.R;
import com.example.sunjay.represent.models.CongressPerson;

public class ProfileCardController {
  private ProfileCardControllerListener listener;

  private TextView name;
  private TextView role;
  private TextView party;
  private ImageView openOnPhoneButton;

  private Context context;

  private ProfileCardController(Context context, ViewGroup layout, final ProfileCardControllerListener listener) {
    name = (TextView) layout.findViewById(R.id.profile_card_representative_name);
    role = (TextView) layout.findViewById(R.id.profile_card_representative_position);
    party = (TextView) layout.findViewById(R.id.profile_card_representative_party);
    openOnPhoneButton = (ImageView) layout.findViewById(R.id.profile_card_button);

    this.context = context;
    this.listener = listener;
  }

  public static ProfileCardController takeControl(Context context, ViewGroup layout, final ProfileCardControllerListener listener) {
    return new ProfileCardController(context, layout, listener);
  }

  public void configureWithDataItem(final CongressPerson congressPerson) {
    name.setText(congressPerson.name);
    role.setText(congressPerson.position);
    party.setText(congressPerson.party);
    openOnPhoneButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.onButtonClick(congressPerson, ProfileCardController.this);
        }
      }
    });
  }

  public interface ProfileCardControllerListener {
    void onButtonClick(CongressPerson congressPerson, ProfileCardController listItemController);
  }
}
