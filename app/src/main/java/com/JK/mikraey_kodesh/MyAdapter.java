package com.JK.mikraey_kodesh;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import androidx.core.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public Resources resources;
    static SharedPreferences mPrefs;
//	SharedPreferences.Editor shPrefEditor;
    public int blackBackground;
    public static final String PREFS_NAME = "MyPrefsFile";


    public MyAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_simple_list_item, null);
        }
        mPrefs = context.getSharedPreferences(PREFS_NAME, 0);
        blackBackground = mPrefs.getInt("BlackBackground", 0);
        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));
        if (blackBackground == 1)
            listItemText.setTextColor(Color.WHITE);
        else
            listItemText.setTextColor(Color.BLACK);


        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_btn);
        resources = context.getResources();
        if (blackBackground == 1)
            deleteBtn.setBackground(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_close_white_24,null));
        else
            deleteBtn.setBackground(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_close_black_24,null));


        return view;
    }
}
