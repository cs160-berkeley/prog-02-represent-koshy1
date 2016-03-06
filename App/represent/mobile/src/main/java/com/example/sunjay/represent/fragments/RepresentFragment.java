package com.example.sunjay.represent.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import com.example.sunjay.represent.controllers.BlockTouchController;

public class RepresentFragment extends Fragment {

  @Override
  public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
    final int animatorId = (enter) ? android.R.animator.fade_in : android.R.animator.fade_out;
    final Animator animator = AnimatorInflater.loadAnimator(getActivity(), animatorId);

    animator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationStart(Animator animation) {
        BlockTouchController.disableTouch();
      }

      @Override
      public void onAnimationEnd(Animator animation) {
        BlockTouchController.enableTouch();
      }
    });

    return animator;
  }
}
