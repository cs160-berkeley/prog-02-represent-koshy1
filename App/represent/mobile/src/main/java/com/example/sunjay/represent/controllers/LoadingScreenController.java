package com.example.sunjay.represent.controllers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import com.example.sunjay.represent.R;

public class LoadingScreenController {
  private LoadingScreenControllerListener listener;

  private Context context;
  private Button actionButton;
  private ProgressBar spinner;
  private View errorContainer;
  private ViewGroup layout;

  private LoadingScreenController(Context context, ViewGroup layout, final LoadingScreenController.LoadingScreenControllerListener listener) {
    actionButton = (Button) layout.findViewById(R.id.loading_screen_action_button);
    spinner = (ProgressBar) layout.findViewById(R.id.loading_screen_progress_bar);
    errorContainer = layout.findViewById(R.id.loading_screen_error_container);
    spinner.setIndeterminate(true);
    spinner.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.represent_red), android.graphics.PorterDuff.Mode.MULTIPLY);

    actionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.onActionButtonClick(LoadingScreenController.this);
        }
      }
    });

    this.context = context;
    this.layout = layout;
    this.listener = listener;
  }

  public static LoadingScreenController takeControl(Context context, ViewGroup layout, final LoadingScreenController.LoadingScreenControllerListener listener) {
    return new LoadingScreenController(context, layout, listener);
  }

  public void showLoading() {
    showLayout();
    spinner.setVisibility(View.VISIBLE);
    spinner.setIndeterminate(true);
    errorContainer.setVisibility(View.GONE);
  }

  public void showError() {
    showLayout();
    spinner.setVisibility(View.GONE);
    errorContainer.setVisibility(View.VISIBLE);
  }

  private void showLayout() {
    layout.setClickable(true);
    layout.setVisibility(View.VISIBLE);
  }

  public void hide() {
    layout.setVisibility(View.GONE);
    layout.setClickable(false);
  }

  public interface LoadingScreenControllerListener {
    void onActionButtonClick(LoadingScreenController listItemController);
  }
}
