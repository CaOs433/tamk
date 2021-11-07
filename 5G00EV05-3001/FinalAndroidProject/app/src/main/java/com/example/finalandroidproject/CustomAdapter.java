package com.example.finalandroidproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] stationIdList;
    int[] icons;
    String[] stationNameList;
    Double[] activityLimits;
    Double[] activityValues;
    Map<String, LastData> lastData;
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext,
                         String[] stationIdList,
                         int[] iconList,
                         String[] stationNameList,
                         Double[] activityLimits,
                         Double[] activityValues,
                         Map<String, LastData> lastData) {
        this.context = applicationContext;
        this.stationIdList = stationIdList;
        this.icons = iconList;
        this.stationNameList = stationNameList;
        this.activityLimits = activityLimits;
        this.activityValues = activityValues;
        this.lastData = lastData;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return stationIdList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Log.d("getView", "Main ListView: getView()");

        // Set up the current row in list view

        view = inflater.inflate(R.layout.activity_list_view, null);
        TextView stationIdTextView = (TextView) view.findViewById(R.id.stationIdTextView);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView stationNameTextField = (TextView) view.findViewById(R.id.stationNameTextView);
        TextView stationActivityTextView = (TextView) view.findViewById(R.id.stationActivityTextView);

        double activityVal = activityValues[i];
        String id = stationIdList[i];

        stationIdTextView.setText(id);
        icon.setImageResource((activityVal >= activityLimits[i]) ? R.drawable.active_icon : icons[1]);
        stationNameTextField.setText(stationNameList[i]);
        stationActivityTextView.setText(context.getResources().getString(R.string.activityValue, activityVal));

        return view;
    }

}
