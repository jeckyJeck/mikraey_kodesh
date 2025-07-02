package com.JK.mikraey_kodesh;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BookmarkActivity extends Dialog
{
	public static final int NAME = 0;
	public static final int BOOK = 1;
	public static final int CHAPTER = 2;
	public static final int SCROLL = 3;
	public static final int FONTSIZE = 4;

	static SharedPreferences mPrefs;
//	SharedPreferences.Editor shPrefEditor;
	public ListView bookmarksListView = null;
	public ArrayList<String> listBookmarksNames = new ArrayList<String>();
	public String Bookmarks;
	String[] listOfBookmarks;
	public static final String PREFS_NAME = "MyPrefsFile";
	public static int[] book_chapter = new int[2];
	MyAdapter adapter;
	SharedPreferences.Editor shPrefEditor;
	Button buttonDeleteAll;
	LinearLayout linearLayout;
	public int black_white_swich;
	public int blackBackground;
    TextView headerTextView;
	Context context;

	public BookmarkActivity(@NonNull Context context) {
		super(context);
		this.context = context;
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarks);
		bookmarksListView = (ListView) findViewById(R.id.Bookmarkslist);
		buttonDeleteAll = (Button) findViewById(R.id.buttonDeleteAll);


		headerTextView = (TextView) findViewById(R.id.headerTextView);
		linearLayout = findViewById(R.id.bookmarksLayout);
		mPrefs = context.getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();
		Bookmarks = mPrefs.getString("Bookmarks", "");
		listOfBookmarks = Bookmarks.split(";");
		blackBackground = mPrefs.getInt("BlackBackground", 0);


		if (blackBackground == 1) {
			linearLayout.setBackgroundColor(Color.parseColor("#424242"));
			bookmarksListView.setBackgroundColor(Color.parseColor("#424242"));
			black_white_swich = (Color.BLACK);
			headerTextView.setTextColor(Color.WHITE);
			headerTextView.setBackgroundColor(Color.parseColor("#424242"));
			bookmarksListView.setDivider(new ColorDrawable(Color.parseColor("#FF424242")));
		}
		else {
			linearLayout.setBackgroundColor(Color.WHITE);
			bookmarksListView.setBackgroundColor(Color.WHITE);
			black_white_swich = (Color.WHITE);
			headerTextView.setTextColor(Color.BLACK);
			headerTextView.setBackground(context.getResources().getDrawable(R.color.app_theme_color));
			bookmarksListView.setDivider(new ColorDrawable(Color.LTGRAY));
		}
		bookmarksListView.setDividerHeight(2);
		fillBookmarksNames();
		showBookmarksList();

	}

	public void performItemClick(int position){

		try
		{
			Class ourClass = Class.forName("com.JK.mikraey_kodesh.textMain");
			Intent ourIntent = new Intent(context,ourClass);
			int bookmarkScrollY, fontSize;

			String currentBookmark = listOfBookmarks[position];
			String[] bookmark = currentBookmark.split("@");

			/*book and chapter*/
			book_chapter[0] = Integer.parseInt(bookmark[BOOK]);
			book_chapter[1] = Integer.parseInt(bookmark[CHAPTER]);
			ourIntent.putExtra("book_chapter", book_chapter);

			/*scroll*/
			//book_chapter[1] = Integer.parseInt(bookmark[CHAPTER]);
			bookmarkScrollY = Integer.parseInt(bookmark[SCROLL]);
			ourIntent.putExtra("bookmarkScrollY", bookmarkScrollY);

			/*font size*/
			fontSize = Integer.parseInt(bookmark[FONTSIZE]);
			shPrefEditor.putInt("fontSize", fontSize);
			shPrefEditor.commit();

			ourIntent.putExtra("fromBookmarks", 1);
			context.startActivity(ourIntent);
			dismiss();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}


	public void onDeleteButtonClick(int position){
		AlertDialog.Builder adb=new AlertDialog.Builder(context ,R.style.bookmarkDeleteAlert);
		adb.setTitle("למחוק?");
		adb.setCustomTitle(null);
		adb.setMessage("למחוק את הסימניה?");
		final int positionToRemove = position;
		//adb.setContentView(R.layout.my_alert_dialog);
		adb.setNegativeButton("בטל", null);
		adb.setPositiveButton("אישור", new AlertDialog.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				int index = 0, index_end = 0;
				String newBookmark = "";
				listBookmarksNames.remove(positionToRemove);
				adapter.notifyDataSetChanged();
				/*remove the bookmark details from Bookmark variable*/
				for (int i=0; i< listOfBookmarks.length; i++){
					if (i != positionToRemove)
						newBookmark = newBookmark + listOfBookmarks[i] + ";";
				}
				if (newBookmark != "")
					newBookmark = newBookmark.substring(0, newBookmark.length()-1); //to remove the last ";"

				Bookmarks = newBookmark;
				listOfBookmarks = Bookmarks.split(";");
				shPrefEditor.putString("Bookmarks", Bookmarks);
				shPrefEditor.commit();
			}
		});
		adb.show();
	}


	private void fillBookmarksNames()
	{
		for (int i=0; i<listOfBookmarks.length; i++){
			String[] bookmark = listOfBookmarks[i].split("@");
			Log.d("aaa", "name: "+bookmark[NAME]+"!");
			if (!bookmark[NAME].equals(""))
				listBookmarksNames.add(bookmark[NAME]);
		}

	}
	
	public void showBookmarksList()
	{
		adapter = new MyAdapter(listBookmarksNames, context){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View superView = super.getView(position, convertView, parent);
				TextView listItemText = (TextView) superView.findViewById(R.id.list_item_string);
				listItemText.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						performItemClick(position);
					}
				});
				ImageButton deleteBtn = (ImageButton) superView.findViewById(R.id.delete_btn);
				deleteBtn.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						onDeleteButtonClick(position);
					}
				});

				return superView;
			}
		};

		bookmarksListView.setAdapter(adapter);
	}
}
