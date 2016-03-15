package com.example.sunjay.represent.adapters;

import android.content.Context;
import android.support.wearable.view.GridPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CardPagerAdapter extends GridPagerAdapter {
  private List<View> items;
  private Context context;

  public CardPagerAdapter(Context context) {
    this.context = context;
  }

  public void setItems(List<View> items) {
    this.items = items;
    notifyDataSetChanged();
  }

  @Override
  public int getRowCount() {
    return 1;
  }

  @Override
  public int getColumnCount(int i) {
    return items.size();
  }

  @Override
  public Object instantiateItem(ViewGroup viewGroup, int i, int i1) {
    View view = items.get(i1);
    viewGroup.addView(view);
    return items.get(i1);
  }

  @Override
  public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
    viewGroup.removeView(items.get(i1));
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view.equals(object);
  }
}
