package com.example.sunjay.represent;

import com.example.sunjay.represent.services.API;
import com.example.sunjay.represent.shared.util.SharedApplication;

public class MyApplication extends SharedApplication {
  @Override
  public void onCreate() {
    super.onCreate();
    API.initializeWithContext(this);
  }
}
