package com.JK.mikraey_kodesh;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> 
{
    private int hidingItemIndex;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, List<String> objects, int hidingItemIndex) 
    {
        super(context, textViewResourceId, objects);
        this.hidingItemIndex = hidingItemIndex;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) 
    {
        View v = null;
        if (position == hidingItemIndex) 
        {
            TextView tv = new TextView(getContext());
            tv.setVisibility(View.GONE);
            v = tv;
        } 
        else 
        {
            v = super.getDropDownView(position, null, parent);
        }
        return v;
    }
}