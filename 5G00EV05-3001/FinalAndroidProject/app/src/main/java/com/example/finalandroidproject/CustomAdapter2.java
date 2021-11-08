package com.example.finalandroidproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class CustomAdapter2 extends BaseAdapter {
    Context context;
    Double[] activityLimits;
    Double[][] data;
    Double current_limit;
    String station_name;
    String station_id;
    Double current_value;
    LayoutInflater inflater;

    public CustomAdapter2(Context applicationContext, Double[] activityLimits, Double[][] data, Double current_limit, String station_name, String station_id, Double current_value) {
        this.context = applicationContext;//context;
        this.activityLimits = activityLimits;
        this.data = data;
        this.current_limit = current_limit;
        this.station_name = station_name;
        this.station_id = station_id;
        this.current_value = current_value;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return data.length;
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
        Log.d("getView", "Main ListView: getView()");

        view = inflater.inflate(R.layout.activity_station_list_view, null);

        ImageView icon = (ImageView) view.findViewById(R.id.station_list_icon);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.station_list_descriptionTextView);
        TextView timeTextField = (TextView) view.findViewById(R.id.station_list_timeTextView);
        TextView valueTextView = (TextView) view.findViewById(R.id.station_list_valueTextView);

        String activityTime = new BigDecimal(data[i][0]).toPlainString();
        double activityVal = (data[i][1] != null) ? data[i][1] : 0d;
        boolean highActivity = activityVal >= current_limit;//activityLimits[i];

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Log.d("TimeStamp: activityTime", activityTime);
        String dateString = formatter.format(new Date(Long.parseLong(activityTime)));

        /*Date in = new Date(Long.parseLong(activityTime));
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(activityTime)), TimeZone.getDefault().toZoneId());
*/

        icon.setImageResource((highActivity) ? R.drawable.active_icon : R.drawable._01n);
        descriptionTextView.setText((highActivity) ? context.getString(R.string.high_activity_no_value) : context.getString(R.string.small_activity_no_value));
        timeTextField.setText(dateString);
        valueTextView.setText(context.getResources().getString(R.string.activityValue, activityVal));

        return view;
    }

}