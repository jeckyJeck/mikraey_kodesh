package com.JK.mikraey_kodesh;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ParentLevel extends BaseExpandableListAdapter {

    private Context context;
    public static final int FIRST_LEVEL_COUNT = 1;
    public static final int SECOND_LEVEL_COUNT = 2;
    public static final int THIRD_LEVEL_COUNT = 3;
    private ExpandableListView expandableListView;
    ArrayList<String> grandparentHeader;
    ArrayList<String> parentHeader;
    //HashMap<String, HashMap<String, ArrayList<String>>> grandparent = new HashMap<>();
    ArrayList<ArrayList<ArrayList<String>>> grandparent = new ArrayList<>();
    ArrayList<ArrayList<String>> parentsHeader = new ArrayList<>();
    ArrayList<String> child = new ArrayList<>();
    ExpandableListView.OnChildClickListener grandsonListener;


    public ParentLevel(Context context, ArrayList<ArrayList<ArrayList<String>>> grandparent,
                       ArrayList<String> grandparentHeader, ArrayList<ArrayList<String>> parentsHeader,
                       ExpandableListView.OnChildClickListener grandsonListener)
    {
        this.context = context;
        this.grandparent = grandparent;
        this.grandparentHeader = grandparentHeader;
        this.parentsHeader = parentsHeader;
        this.grandsonListener = grandsonListener;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parentsHeader.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        secondLevelELV.setAdapter(new SecondLevelAdapter(context, grandparent.get(groupPosition) ,parentsHeader.get(childPosition)));
        secondLevelELV.setGroupIndicator(null);
        secondLevelELV.setOnChildClickListener(grandsonListener);
        secondLevelELV.setTag(groupPosition);

        return secondLevelELV;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return grandparentHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return grandparent.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_first, null);
            TextView text = (TextView) convertView.findViewById(R.id.rowParentText);
            text.setText(grandparentHeader.get(groupPosition));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public class SecondLevelExpandableListView extends ExpandableListView {

        public SecondLevelExpandableListView(Context context) {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public class SecondLevelAdapter extends BaseExpandableListAdapter {

        private Context context;
        ArrayList<ArrayList<String>> parent;
        ArrayList<String> parentHeader;

        public SecondLevelAdapter(Context context, ArrayList<ArrayList<String>> parent, ArrayList<String> parentHeader) {
            this.context = context;
            this.parent = parent;
            this.parentHeader = parentHeader;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return parentHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parentHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_second, null);
                TextView text = (TextView) convertView.findViewById(R.id.rowSecondText);
                text.setText(parentHeader.get(groupPosition));
            }
            return convertView;
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return parent.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_third, null);
                TextView text = (TextView) convertView.findViewById(R.id.rowThirdText);
                text.setText(getChild(groupPosition,childPosition));
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return parent.get(groupPosition).size();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

}