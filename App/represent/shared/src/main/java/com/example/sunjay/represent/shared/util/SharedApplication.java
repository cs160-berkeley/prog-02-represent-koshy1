package com.example.sunjay.represent.shared.util;

import android.app.Application;
import android.content.Context;

public class SharedApplication extends Application {
  private static SharedApplication instance;

  public SharedApplication() {
    instance = this;
  }

  public static Context getContext() {
    return instance;
  }
}
