package com.JK.mikraey_kodesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TwoAndHalfLvlAdapter extends BaseExpandableListAdapter
{

    private Context _context;
    public int BlackBackground=0;
    static SharedPreferences mPrefs;
    public static final String PREFS_NAME = "MyPrefsFile";

    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    List<String[]> nispachim = new ArrayList<>();
    static int textColor = Color.BLACK;
    public LinearLayout listGroupLayout;
    public LinearLayout listChildLayout;
    View.OnClickListener textViewsListener;
    ExpandableListView.OnChildClickListener expListListener;
    //public ImageView ListHeaderIconPlay;
    public TwoAndHalfLvlAdapter(Context context, List<String> listDataHeader,
                                HashMap<String, List<String>> listChildData, List<String[]> nispachim,
                                ExpandableListView.OnChildClickListener expListener)
    {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.nispachim = nispachim;
        expListListener = expListener;

    }

    @Override
    public Object getChild(int groupPosition, int childPositon)
    {
        if ( childPositon == dataManage.chapterOfNispachim[groupPosition])
            return "נספחים";
        else
            return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPositon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (childPosition != dataManage.chapterOfNispachim[groupPosition]){
            final String childText = (String) getChild(groupPosition, childPosition);
            if (true)
            {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.row_second, null);
            }
            TextView txtListChild = (TextView) convertView.findViewById(R.id.rowSecondText);
            //listChildLayout = (LinearLayout) convertView.findViewById(R.id.LLlistChild);

            if (txtListChild != null) {
                txtListChild.setText(childText);

                if (childText.startsWith("*c*")) {
                    txtListChild.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    txtListChild.setText(childText.substring(3));
                } else {
                    txtListChild.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
//                if (textColor == Color.BLACK) {
//                    listChildLayout.setBackgroundColor(Color.WHITE);
//                    txtListChild.setTextColor(Color.BLACK);
//                } else {
//                    listChildLayout.setBackgroundColor(Color.BLACK);
//                    txtListChild.setTextColor(Color.WHITE);
//                }
            }
        } else {
            SecondLevelExpandableListView expLv = new SecondLevelExpandableListView(_context);
//            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.expandable_list_item, null);

//            expLv = convertView.findViewById(R.id.expList);
//            List<String> headList = new ArrayList<>();
//            headList.add("נספחים:");

            expLv.setTag(groupPosition);
            String[] headList = {_context.getString(R.string.nispachim_head)};
            HashMap<String, List<String>> hashMap = new HashMap<>();
            //hashMap.put("נספחים:", nispachim.get(groupPosition));
            //ExpandableListAdapter expAdapter = new ExpandableListAdapter(_context,headList, hashMap);
            expLv.setAdapter(new SecondLevelAdapter(_context, headList, nispachim, groupPosition));
            expLv.setOnChildClickListener(expListListener);

            convertView = expLv;
        }




        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size()+1;

    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_first, null);
        }

        //listGroupLayout = (LinearLayout) convertView.findViewById(R.id.LLlistGroup);
        //ListHeaderIconPlay = (ImageView) convertView.findViewById(R.id.ListHeaderIconPlay);

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.rowParentText);
        //ImageView ListHeaderIconPlay = (ImageView) convertView.findViewById(R.id.ListHeaderIconPlay);
        int a = lblListHeader.getId();
        lblListHeader.setText(headerTitle);
//        if(textColor==Color.BLACK)
//        {
//            listGroupLayout.setBackgroundColor(Color.WHITE);
//            lblListHeader.setTextColor(Color.BLACK);
//        }
//        else
//        {
//            listGroupLayout.setBackgroundColor(Color.BLACK);
//            lblListHeader.setTextColor(Color.WHITE);
//        }
//        listGroupLayout.setGravity(Gravity.RIGHT);

        return convertView;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
    public void setTextColor(int c)
    {
        textColor=c;
    }

    public class SecondLevelExpandableListView extends ExpandableListView {

        public SecondLevelExpandableListView(Context context) {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    public class SecondLevelAdapter extends BaseExpandableListAdapter {

        private Context context;
        int bookId;


        List<String[]> data;

        String[] headers;

        ImageView ivGroupIndicator;


        public SecondLevelAdapter(Context context, String[] headers, List<String[]> data, int bookId) {
            this.context = context;
            this.data = data;
            this.headers = headers;
            this.bookId = bookId;

        }

        @Override
        public Object getGroup(int groupPosition) {

            return headers[groupPosition];
        }

        @Override
        public int getGroupCount() {

            return headers.length;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_second, null);
            TextView text = (TextView) convertView.findViewById(R.id.rowSecondText);

            String groupText = getGroup(groupPosition).toString();
            text.setText(groupText);
            return convertView;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String[] childData;
            childData = data.get(groupPosition);
            return childData[childPosition];
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_third, null);
            TextView textView = (TextView) convertView.findViewById(R.id.rowThirdText);

            String[] childArray = data.get(bookId);
            String text = childArray[childPosition];
            if (text.startsWith("*c*")){
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                text = text.substring(3);
            } else {
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            textView.setText(text);

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return data.get(bookId).length;
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

