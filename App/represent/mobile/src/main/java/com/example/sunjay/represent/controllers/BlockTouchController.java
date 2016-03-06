package com.example.sunjay.represent.controllers;

import android.view.View;

public class BlockTouchController {
  private static View touchInterceptor;
  
  public static void setTouchInterceptor(View touchInterceptor) {
    BlockTouchController.touchInterceptor = touchInterceptor;
  }

  public static void enableTouch() {
    if (touchInterceptor != null) {
      touchInterceptor.setClickable(false);
    }
  }

  public static void disableTouch() {
    if (touchInterceptor != null) {
      touchInterceptor.setClickable(true);
    }
  }
}
