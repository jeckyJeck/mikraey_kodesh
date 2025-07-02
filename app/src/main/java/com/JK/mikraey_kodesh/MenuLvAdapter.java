package com.JK.mikraey_kodesh;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuLvAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public Resources resources;
    static SharedPreferences mPrefs;
    public int blackBackground;
    public static final String PREFS_NAME = "MyPrefsFile";
    dataManage dm = new dataManage();

    public MenuLvAdapter(ArrayList<String> list, Context context) {
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
        final int[] lastGroupExpanded = {-1};
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.menu_list_item, null);
        }
        mPrefs = context.getSharedPreferences(PREFS_NAME, 0);
        blackBackground = mPrefs.getInt("BlackBackground", 0);
        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.MLI_TextView);
        listItemText.setText(list.get(position));
        if (blackBackground == 1)
            listItemText.setTextColor(Color.WHITE);
        else
            listItemText.setTextColor(Color.BLACK);




        ExpandableListView expList = (ExpandableListView) view.findViewById(R.id.MLI_ExpList);
        List<String> list = new ArrayList<String>();
        list.add(dm.getPartName(position, -1));
        for (int i = 0; i < dm.partsOfBook[position].length; i++)
            list.add(dm.getPartName(position, dm.partsOfBook[position][i]));
        HashMap<String, List<String>> hashMap = new HashMap<>();
        int temp_j = 0;
        for (int i = 0; i < list.size(); i++){
            List<String> tempList = new ArrayList<>();
            if (i == list.size()-1){
                for (int j = temp_j; j > dm.lastChapterOfBook[position]; j++) {
                    tempList.add(dm.getChapterName(position, j));
                    temp_j = j;
                }
            } else {
                for (int j = temp_j; j > dm.partsOfBook[position][i]; j++) {
                    tempList.add(dm.getChapterName(position, j));
                    temp_j = j;
                }
            }
            hashMap.put(list.get(i), tempList);
        }
        ExpandableListAdapter expAdapter = new ExpandableListAdapter(context, list, hashMap);
        expList.setAdapter(expAdapter);

        expList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastGroupExpanded[0] != -1)
                    expList.collapseGroup(lastGroupExpanded[0]);
                lastGroupExpanded[0] = groupPosition;
            }
        });

        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expList.getVisibility() == View.VISIBLE)
                    expList.setVisibility(View.GONE);
                else
                    expList.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }

}
