package com.example.sunjay.represent.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.example.sunjay.represent.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnboardingFragment extends RepresentFragment {
  private OnboardingFragmentListener listener;

  private EditText zipCodeEntry;
  private Button submitButton;
  private Button locationButton;
  private View focusableView;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof OnboardingFragmentListener) {
      listener = (OnboardingFragmentListener) activity;
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_onboarding, container, false);

    focusableView = view.findViewById(R.id.fragment_onboarding_focusable_view);
    zipCodeEntry = (EditText) view.findViewById(R.id.fragment_onboarding_zip_code_entry);
    submitButton = (Button) view.findViewById(R.id.fragment_onboarding_submit_button);
    locationButton = (Button) view.findViewById(R.id.fragment_onboarding_location_button);

    locationButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        zipCodeEntry.setText(getResources().getText(R.string.fragment_onboarding_zip_code_default));
      }
    });

    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!validateZipCode()) {
          new AlertDialog.Builder(getActivity())
                  .setTitle(getActivity().getResources().getString(R.string.fragment_onboarding_zip_code_error_title))
                  .setMessage(getActivity().getResources().getString(R.string.fragment_onboarding_zip_code_error_message))
                  .setPositiveButton(android.R.string.ok, null)
                  .show();
          return;
        }

        zipCodeEntry.clearFocus();
        focusableView.requestFocus();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(zipCodeEntry.getWindowToken(), 0);

        if (listener != null) {
          listener.onSubmitButtonClick(zipCodeEntry.getText().toString(), OnboardingFragment.this);
        }
      }
    });
    return view;
  }

  private boolean validateZipCode() {
    String regex = "\\d{5}([ \\-]\\d{4})?";
    Matcher matcher = Pattern.compile(regex).matcher(zipCodeEntry.getText().toString());
    return matcher.matches();
  }

  public interface OnboardingFragmentListener {
    void onSubmitButtonClick(String zipCode, OnboardingFragment fragment);
  }
}
