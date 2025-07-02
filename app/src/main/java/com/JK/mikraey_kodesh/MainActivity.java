package com.JK.mikraey_kodesh;




import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{


	private static final int HEBREW	 = 0;
	private static final int ENGLISH = 1;
	private static final int RUSSIAN = 2;
	private static final int SPANISH = 3;
	private static final int FRENCH = 4;

	dataManage dm = new dataManage();
	public ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	LinearLayout LinearLayoutListGroup;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	public static final String PREFS_NAME = "MyPrefsFile";
	static SharedPreferences mPrefs;
	SharedPreferences.Editor shPrefEditor;
	public int BlackBackground=0, SleepScreen=1, MyLanguage = -1;
	public MenuInflater inflater;
	public ActionBar ab;
	RelativeLayout background;
	public Menu abMenu=null;
	public EditText TextToDecode;
	public Dialog acronymsDialog;
	String acronymsText;
	public int StartInLastLocation = 1;
	public boolean newVersion = false;
	public Context context;
	Boolean groupExpanded;
	int groupThatOpened;
	//private StorageReference storageRef;
	//private FirebaseStorage storage;
	//private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		context = this;
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();
		BlackBackground = mPrefs.getInt("BlackBackground", 0);
		StartInLastLocation = mPrefs.getInt("StartInLastLocation", 0);
		MyLanguage = mPrefs.getInt("MyLanguage", -1);
		groupExpanded = false;
		groupThatOpened = -1;

		if (BlackBackground == 1)
			setTheme(R.style.AppThemeDark);
		else
			setTheme(R.style.AppThemeLight);

		setContentView(R.layout.activity_main);

		background = findViewById(R.id.background_layout);
		if (BlackBackground == 1)
			background.setBackgroundColor(Color.BLACK);

		ab = getSupportActionBar();
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);


		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener()
		{
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id)
			{
				// TODO Auto-generated method stub

				try
				{
					shPrefEditor.putInt("scrollY", 0);
					shPrefEditor.commit();
					Class ourClass = Class.forName("com.JK.mikraey_kodesh.textMain");
					Intent ourIntent = new Intent(MainActivity.this, ourClass);
					int[] book_chapter = new int[2];
					book_chapter[0] = groupPosition;
					book_chapter[1] = childPosition;
					ourIntent.putExtra("book_chapter", book_chapter);
					ourIntent.putExtra("fromMainActivity", true);
					startActivity(ourIntent);
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}

				return false;
			}
		});


		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int i) {
//				if (groupThatOpened != -1 && groupThatOpened != i) {
//					expListView.collapseGroup(groupThatOpened);
//				}
//				expListView.setSelection(i);
				groupExpanded = true;
				groupThatOpened = i;

			}
		});

		if(StartInLastLocation == 1 && !(mPrefs.getInt("book", 0) == 0 && mPrefs.getInt("chapter", 0) == 0) && newVersion == false)/*check if book and chapter are 0 so this is the first time the user open the application so don't go to the last location*/
		{
			goToLastLocation();
		}
	}//onCreate


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		abMenu = menu;
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater = getMenuInflater();

		if(BlackBackground == 1)
		{
			ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF424242")));
			inflater.inflate(R.menu.tochen_actionbar_black, menu);
			ab.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + "תוכן" + "</font>"));
			listAdapter.setTextColor(Color.WHITE);//to set the list text color

			expListView.setAdapter(listAdapter);//to set the list text color
		}
		else
		{
			ab.setBackgroundDrawable(getResources().getDrawable(R.color.app_theme_color));
			inflater.inflate(R.menu.tochen_actionbar, menu);
			ab.setTitle(Html.fromHtml("<font color=\"black\">" + "תוכן" + "</font>"));
			listAdapter.setTextColor(Color.BLACK);//to set the list text color
			expListView.setAdapter(listAdapter);//to set the list text color
		}

		return true;
	}//onCreateOptionsMenu

	protected void onResume()
	{
		// The activity has become visible (it is now "resumed").
		super.onResume();
		if (BlackBackground != mPrefs.getInt("BlackBackground", 0)) {
			recreate();
		}

		}//onResume

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub
		switch (item.getItemId())
		{
			case R.id.action_search:
				onSearchRequested();
				break;
			case R.id.action_bookmarks:
				Dialog dialog = new BookmarkActivity(this);
				dialog.show();
//				try
//				{
//					Class ourClass = Class.forName("com.JK.mikraey_kodesh.BookmarkActivity");
//					Intent ourIntent = new Intent(MainActivity.this, ourClass);
//					startActivity(ourIntent);
//				}
//				catch (ClassNotFoundException e)
//				{
//					e.printStackTrace();
//				}
				break;
			case R.id.action_place:
				goToLastLocation();
				break;
			case R.id.action_config:
				showPopupMenuSettings(findViewById(R.id.action_config));
				break;
			default:
				break;
		}

		return true;
		//return super.onOptionsItemSelected(item);
	}

	private void showPopupMenuSettings(View v)
	{
		PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
		//popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
//		PopupWindow pw = new PopupWindow(this);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			pw.setElevation(10);
//		}

		String[] configHeaders = new String[4];

			configHeaders[0] = "הגדרות";
			configHeaders[1] = "ראשי תיבות";
			configHeaders[2] = "הסכמות";
			configHeaders[3] = "אודות";


		popupMenu.getMenu().add(0,0,0,configHeaders[0]);//(int groupId, int itemId, int order, int titleRes)
		popupMenu.getMenu().add(0,1,1,configHeaders[1]);
		popupMenu.getMenu().add(0,2,2,configHeaders[2]);
		popupMenu.getMenu().add(0,3,3,configHeaders[3]);


		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				switch (item.getItemId())
				{
					case 0:/*settings*/
						try
						{
							Class ourClass = Class.forName("com.JK.mikraey_kodesh.Settings");
							Intent ourIntent = new Intent(MainActivity.this, ourClass);
							startActivity(ourIntent);
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}

						break;


					case 1:/*acronyms*/
						acronymsDecode();
						break;
					case 2:/*hascamot*/
						try
						{
							Class ourClass = Class.forName("com.JK.mikraey_kodesh.hascamot");
							Intent ourIntent = new Intent(MainActivity.this, ourClass);

							startActivity(ourIntent);
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}
						break;
					case 3: /*about*/
						try{
							Class ourClass = Class.forName("com.JK.mikraey_kodesh.About");
							Intent ourIntent = new Intent(MainActivity.this, ourClass);
							startActivity(ourIntent);
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}

					default:
						break;
				}
				return true;
			}
		});

		popupMenu.show();
	}

	/*Preparing the list data*/
	private void prepareListData()
	{
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		//List<List<String>> listOfLists = new ArrayList<List<String>>();

		int bookId;
		int chapterId;
		for (bookId=0; bookId< dataManage.BOOKS_NUMBER; bookId++) {
			listDataHeader.add(dm.convertBookIdToName(bookId));
			List<String> list = new ArrayList<>();  // Adding child data
			for (chapterId=0; chapterId < dm.lastChapterOfBook[bookId];chapterId++){
				list.add(dm.getChapterName(bookId, chapterId));
			}
			listDataChild.put(listDataHeader.get(bookId), list); // Header, Child data
		}
	}//prepareListData

	void acronymsDecode()
	{
		final Context context = this;

		// custom dialog
		acronymsDialog = new Dialog(context);
		acronymsDialog.setContentView(R.layout.acronyms);
		acronymsDialog.setTitle("פענוח ראשי תיבות");

		Button dialogButtonExit = (Button) acronymsDialog.findViewById(R.id.dialogButtonExit);
		Button dialogButtonDecode = (Button) acronymsDialog.findViewById(R.id.dialogButtonDecode);
		final TextView decodedText = (TextView) acronymsDialog.findViewById(R.id.textViewDecodedText);
		//final byte[] buffer;
		//final int size;

		TextToDecode = (EditText) acronymsDialog.findViewById(R.id.editTextAcronyms );

		// if button is clicked
		dialogButtonExit.setOnClickListener(new OnClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				acronymsDialog.dismiss();
			}
		});

		dialogButtonDecode.setOnClickListener(new OnClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				acronymsText = "\r\n" + /*"י\"א" */TextToDecode.getText().toString() + " - ";
				acronymsText = acronymsText.replace("\"", "");
				acronymsText = acronymsText.replace("'", "");
				InputStream is;
				String r="לא נמצאו תוצאות";
				int index=0, index_end=0, first=1;
				try
				{
					is = getAssets().open("acronyms.txt");
					int size = is.available();
					byte[] buffer = new byte[size];
					is.read(buffer);
					is.close();
					String strText  = new String(buffer);

					while (strText.indexOf(acronymsText, index_end) != -1)
					{
						index = strText.indexOf(acronymsText, index);
						index = strText.indexOf("-", index+1) + 2;
						index_end = strText.indexOf("\r\n", index);
						if(first==1)
						{
							r = strText.substring (index, index_end);
							first=0;
						}
						else
							r += ", " + strText.substring (index, index_end);
					}
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				decodedText.setText(TextToDecode.getText().toString() + " - " + r);

			}
		});
		acronymsDialog.show();
	}

	@Override
	public void onBackPressed() {
		if (!groupExpanded)
			super.onBackPressed();
		else {
			expListView.collapseGroup(groupThatOpened);
			groupExpanded = false;
		}

	}

	void goToLastLocation()
	{
		try
		{
			Class ourClass = Class.forName("com.JK.mikraey_kodesh.textMain");
			Intent ourIntent = new Intent(MainActivity.this, ourClass);
			int[] book_chapter = new int[2];
			book_chapter[0] = 0xFFFF;
			book_chapter[1] = 0xFFFF;
			ourIntent.putExtra("book_chapter", book_chapter);
			startActivity(ourIntent);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void onClickTryActivity(View view)
	{
		try{
			Class ourClass = Class.forName("com.JK.mikraey_kodesh.TryingActivity");
			Intent ourIntent = new Intent(MainActivity.this, ourClass);
			startActivity(ourIntent);
		}catch (ClassNotFoundException e){

		}

	}


	
}
