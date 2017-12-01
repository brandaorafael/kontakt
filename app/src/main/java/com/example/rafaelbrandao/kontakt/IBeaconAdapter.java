package com.example.rafaelbrandao.kontakt;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.text.DecimalFormat;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by rafaelbrandao on 17/09/17.
 */

public class IBeaconAdapter extends BaseAdapter {


    private List<IBeaconDevice> beacons;

    private Activity act;

    public IBeaconAdapter(List<IBeaconDevice> beacons, Activity act) {
        this.beacons = beacons;
        this.act = act;
    }

    public void refreshBeacons (List<IBeaconDevice> beacons){
        this.beacons = beacons;

        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return beacons.size();
    }

    @Override
    public Object getItem(int i) {
        return beacons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Inflater inflater = new Inflater();

        view = act.getLayoutInflater().inflate(R.layout.item_beacon, viewGroup, false);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView distance = (TextView) view.findViewById(R.id.distance);


        name.setText("NAME: " + beacons.get(i).getUniqueId());
        distance.setText("DISTANCE: " + String.format("%.2f", beacons.get(i).getDistance()));


        return view;
    }
}
