package com.example.sunjay.represent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.sunjay.represent.R;
import com.example.sunjay.represent.controllers.ListItemController;
import com.example.sunjay.represent.models.CongressPerson;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<CongressPerson> implements ListItemController.ListItemControllerListener {
  private ListAdapterListener listener;

  public ListAdapter(Context context, ArrayList<CongressPerson> users, ListAdapterListener listener) {
    super(context, 0, users);
    this.listener = listener;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    CongressPerson congressPerson = getItem(position);
    ListItemController controller;

    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
      controller = ListItemController.takeControl(getContext(), (ViewGroup) convertView, this);
      RowViewHolder rowViewHolder = new RowViewHolder();
      rowViewHolder.controller = controller;
      convertView.setTag(rowViewHolder);
    } else {
      controller = ((RowViewHolder) convertView.getTag()).controller;
    }
    controller.configureWithDataItem(congressPerson, position);
    return convertView;
  }

  @Override
  public void onCardClick(int position, ListItemController listItemController) {
    if (listener != null) {
      listener.onRowClick(position, this);
    }
  }

  public interface ListAdapterListener {
    void onRowClick(int position, ListAdapter listAdapter);
  }

  private class RowViewHolder {
    public ListItemController controller;
  }
}
