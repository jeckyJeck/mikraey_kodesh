package com.JK.mikraey_kodesh;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.*;

/*jsoup*/




import com.google.android.material.bottomappbar.BottomAppBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@SuppressLint("SetJavaScriptEnabled")
public class textMain extends AppCompatActivity implements View.OnClickListener//, OnGestureListener
{

	// Create a reference from an HTTPS URL
	// Note that in the URL, characters are URL escaped!
	//StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");



	public Dialog dialogModes;

	private static final int HEBREW	 = 0;
	private static final int ENGLISH = 1;
	private static final int RUSSIAN = 2;
	private static final int SPANISH = 3;
	private static final int FRENCH = 4;

	Context context;

	WebView webView2;
	WebView webview;
	WebView webviewNote;
	Object JSInterfaceShow, JSInterfaceHide;
	WebView.FindListener webviewFindListener;
	NestedScrollView nestedScrollView;
	public static int[] book_chapter = new int[2];
	boolean cameFromSearch = false, firstTime = true, ChangeChapter = false, fromMainActivity = false;
	String searchPosition = null, sectionsForToast = null;
	public ImageButton bParagraphs, bNext_sec, bPrevious_sec, bFindNext, bFindPrevious,bFindClose, bIncreaseFont, bDecreaseFont, bBack/*,bAutoScrollSpeed, bPlay, bStop, bNext_page, bPrevious_page, bu*/ ;
	LinearLayout llMainLayout;
	LinearLayout secondLayout;
	LinearLayout separateLayout;
	androidx.appcompat.widget.Toolbar topToolbar;
	BottomAppBar bottomAppBar;
	Toolbar.OnMenuItemClickListener bottomAppBarMenuListener;
	ImageButton bexitnotes, bexitnotesBlack;
	ImageButton increaseScrlBtn, decreaseScrlBtn, closeScrlBtn;
	String stHeadersArr;
	Elements headers;
	String fileName, fileNameOnly, lastFileName = null;
//	String[][] chaptersFiles = new String[BOOKS_NUMBER][31];
//	String[][] chaptersNames = new String[BOOKS_NUMBER][31];
	private LinearLayout lnrOptions, lnrFindOptions, lnrScroll;
	public static final String PREFS_NAME = "MyPrefsFile";
	static SharedPreferences mPrefs;
	SharedPreferences.Editor shPrefEditor;
	int scrollY = 0;
	public int BlackBackground=0, SleepScreen=1, cbFullScreen=1, cbAssistButtons=1, notesState=0;
	boolean bookmark = false;
	Document doc = null;
	static MenuInflater inflater;
	static public ActionBar textActionBar;
	public String query, title;
	public String note_id;
	public String draw_id;
	public String audio_id;
	public String chaper_text;
	public Resources resources;
	static byte fullScreenFlag = 0;
	public static byte rotate = 0;
	public String noteStr = "0";
	public int MyLanguage;
	dataManage dm = new dataManage();

	DrawerLayout mDrawerLayuot;
	ActionBarDrawerToggle actionBarDrawerToggle;
	LinearLayout backToMenuLl;
	ExpandableListView menuExpLv;
	ThreeLevelListAdapter adapter;
	TwoAndHalfLvlAdapter adapter2;
	ExpandableListView.OnChildClickListener expListener;


	/*for bookmarks*/
	public List<String> bookmarks_array_names = new ArrayList<String>();
	public EditText result;
	public Spinner spinner1, spinnerAutoScroll;
	public EditText BookmarkName, TextToSearch, TextToDecode;
	public Dialog bookmarkDialog, innerSearchDialog, acronymsDialog, autoScrollDialog;
	String innerSearchText, acronymsText;
	public String strBookmark, Bookmarks;

	static public boolean jumpToSectionFlag = false;
	public int fontSize;
	public  Toast fontToast;

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("JavascriptInterface")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		context = this;

		loadActivity();


	}//onCreate

	@SuppressLint("JavascriptInterface")
	private void loadActivity()
	{
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();
		cbAssistButtons = mPrefs.getInt("sAssistButtons", 1);
		MyLanguage = mPrefs.getInt("MyLanguage", 0);
		BlackBackground = mPrefs.getInt("BlackBackground", 0);
		notesState = mPrefs.getInt("notesState", 0);
		resources = getResources();

		if (BlackBackground == 1)
			setTheme(R.style.AppThemeDark);
		else
			setTheme(R.style.AppThemeLight);

		setContentView(R.layout.text_main);

		setLayoutContents();
		setWebViews();

		fontToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);

		bParagraphs.setOnClickListener(this);
		bNext_sec.setOnClickListener(this);
		bPrevious_sec.setOnClickListener(this);
		bFindNext.setOnClickListener(this);
		bFindPrevious.setOnClickListener(this);
		bFindClose.setOnClickListener(this);
		bIncreaseFont.setOnClickListener(this);
		bDecreaseFont.setOnClickListener(this);
		bBack.setOnClickListener(this);

		jumpToSectionFlag = false;
		firstTime = true;
		book_chapter[0] = mPrefs.getInt("book", -1);
		book_chapter[1] = mPrefs.getInt("chapter", -1);
		int fromBookmarks = 0;
		SleepScreen = mPrefs.getInt("SleepScreen", 0);
		if(SleepScreen == 0)
		{
			webview.setKeepScreenOn (false);
		}
		else if(SleepScreen == 1)
		{
			webview.setKeepScreenOn (true);
		}


		inflater = getMenuInflater();

		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			cameFromSearch = extras.getBoolean("cameFromSearch",false);
			searchPosition = extras.getString("searchPosition");
			if(extras.getIntArray("book_chapter") != null)
				book_chapter = extras.getIntArray("book_chapter");
			else{
				book_chapter[0] = mPrefs.getInt("book", -1);
				book_chapter[1] = mPrefs.getInt("chapter", -1);
			}
			sectionsForToast = extras.getString("sectionsForToast");
			if(cameFromSearch)
			{
				query = extras.getString("query");
				findBookAndChapter();
				//webview.loadUrl(dm.getURL(book_chapter[0], book_chapter[1]));
				chaper_text = getChapterText(book_chapter[0], book_chapter[1]);
				scrollY = 0;
				lnrFindOptions.setVisibility(View.VISIBLE);
			}
			else
			{
				lnrFindOptions.setVisibility(View.GONE);
				book_chapter = extras.getIntArray("book_chapter");

				fromBookmarks = extras.getInt("fromBookmarks");
				if(fromBookmarks == 1)/*came from bookmarks*/
				{
					//webview.loadUrl(dm.getURL(book_chapter[0], book_chapter[1]));
					chaper_text = getChapterText(book_chapter[0], book_chapter[1]);
					scrollY = extras.getInt("bookmarkScrollY");
				}
				else if(book_chapter != null)
				{
					if(book_chapter[0] == 0xFFFF || book_chapter[1] == 0xFFFF)/*go to the last location*/
					{
						bookmark = true;
						book_chapter[0] = mPrefs.getInt("book", 0);
						book_chapter[1] = mPrefs.getInt("chapter", 0);
						//webview.loadUrl(dm.getURL(book_chapter[0], book_chapter[1]));
						chaper_text = getChapterText(book_chapter[0], book_chapter[1]);
						scrollY = mPrefs.getInt("scrollY", 0);
					}
					else {
						bookmark = false;
						scrollY = 0;
						//webview.loadUrl(dm.getURL(book_chapter[0], book_chapter[1]));
						chaper_text = getChapterText(book_chapter[0], book_chapter[1]);
					}
				}
			}
			webview.loadDataWithBaseURL("file:///android_asset/",chaper_text, "text/html; charset=utf-8", "UTF-8",null);
		}


		if(book_chapter[1] == dm.lastChapterOfBook[book_chapter[0]])
			bNext_sec.setEnabled(false);
		else if(book_chapter[1] == 0)
			bPrevious_sec.setEnabled(false);
		webview.setWebChromeClient(new WebChromeClient()
		{
			@Override
			public void onProgressChanged(WebView view, int progress)
			{
				if ( view.getProgress()==100)
				{
					if(jumpToSectionFlag == false)
						jumpToY( scrollY );
				}
			}
		});

		final WebView wv = new WebView(this);
		wv.post(new Runnable() {
			@Override
			public void run() {
				wv.loadUrl(fileName);
			}
		});

		jumpToY(scrollY);

	}

	public void setLayoutContents(){
		llMainLayout   = (LinearLayout) findViewById(R.id.llMainLayout);
		separateLayout = (LinearLayout) findViewById(R.id.handle);
		bexitnotes     = (ImageButton) findViewById(R.id.exitNoteButton);
		bexitnotesBlack = (ImageButton) findViewById(R.id.exitNoteButtonBlack);
		secondLayout   = (LinearLayout) findViewById(R.id.secondary);
		nestedScrollView = (NestedScrollView) findViewById(R.id.scroll_view);

		lnrOptions     = (LinearLayout) findViewById(R.id.lnrOptions);
		bParagraphs    = (ImageButton) findViewById(R.id.ibChapters);
		bNext_sec      = (ImageButton) findViewById(R.id.ibNext);
		bPrevious_sec  = (ImageButton) findViewById(R.id.ibPrevious);
		bIncreaseFont  = (ImageButton) findViewById(R.id.increaseFont);
		bDecreaseFont  = (ImageButton) findViewById(R.id.decreaseFont);
		bBack	       = (ImageButton) findViewById(R.id.exit);

		topToolbar     = findViewById(R.id.toolbar);
		setSupportActionBar(topToolbar);
		bottomAppBar   = findViewById(R.id.bottom_app_bar);

		lnrFindOptions = (LinearLayout) findViewById(R.id.lnrFindOptions);
		bFindNext      = (ImageButton) findViewById(R.id.ibFindNext);
		bFindPrevious  = (ImageButton) findViewById(R.id.ibFindPrevious);
		bFindClose		= (ImageButton) findViewById(R.id.ibFindClose);

		lnrScroll		= (LinearLayout) findViewById(R.id.scrollLayot);
		closeScrlBtn 	= (ImageButton) findViewById(R.id.stopScrollBtn);
		decreaseScrlBtn 	= (ImageButton) findViewById(R.id.decreaseScrollBtn);
		increaseScrlBtn 	= (ImageButton) findViewById(R.id.increaseScrollBtn);

		mDrawerLayuot = findViewById(R.id.TMDrawer);
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayuot, topToolbar, R.string.nav_open, R.string.nav_close);
		mDrawerLayuot.addDrawerListener(actionBarDrawerToggle);
		actionBarDrawerToggle.syncState();
		if (BlackBackground == 1){
			actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
		} else {
			actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.BLACK);
		}

		menuExpLv = (ExpandableListView) findViewById(R.id.menuExpLv);
		loadAdapter2Data();
		menuExpLv.setOnChildClickListener(expListener);
		menuExpLv.setAdapter(adapter2);
		menuExpLv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			int previousGroup = -1;

			@Override
			public void onGroupExpand(int groupPosition) {
				if (groupPosition != previousGroup)
					menuExpLv.collapseGroup(previousGroup);
				previousGroup = groupPosition;
			}
		});
		menuExpLv.setDivider(null);
		menuExpLv.setGroupIndicator(null);

		//bottomAppBar.inflateMenu(R.menu.text_main_bottom_toolbar);
		setBtmapbrMenuListener();
		bottomAppBar.setForegroundGravity(Gravity.CENTER);


		if(cbAssistButtons==1) { //remove buttons and add them back to bottom
			llMainLayout.removeView(lnrFindOptions);
			llMainLayout.removeView(lnrOptions);
			llMainLayout.addView(lnrOptions);
			llMainLayout.addView(lnrFindOptions);
		}

		//secondLayout.setVisibility(View.GONE);

	}


	@SuppressLint("JavascriptInterface")
	public void setWebViews(){
		webview = (WebView) findViewById(R.id.webView1);
		WebSettings webSettings = webview.getSettings();
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);
		webSettings.setSupportZoom(true);

		webview.requestFocusFromTouch();
		webview.getSettings().setAllowFileAccess(true);
		webview.setWebViewClient(new MyWebViewClient());
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webview.setVerticalScrollBarEnabled(true);

		setWebviewFindListener();

		webView2 = (WebView) findViewById(R.id.webView2);
		final WebSettings webSettings2 = webView2.getSettings();
		webSettings2.setDefaultTextEncodingName("utf-8");
		webSettings2.setJavaScriptEnabled(true);
		webSettings2.setSupportZoom(true);
		webView2.requestFocusFromTouch();
		webView2.getSettings().setAllowFileAccess(true);
		webView2.setWebViewClient(new MyWebViewClient());

		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.note);

		WebSettings webSettingsNote;
		webviewNote = (WebView) dialog.findViewById(R.id.webViewNote1);
		webSettingsNote = webviewNote.getSettings();
		webSettingsNote.setDefaultTextEncodingName("utf-8");
		webviewNote.requestFocusFromTouch();
		webSettingsNote.setJavaScriptEnabled(true);
		webSettingsNote.setDefaultFontSize(fontSize);
		webviewNote.addJavascriptInterface(JSInterfaceShow, "sd");
		webviewNote.addJavascriptInterface(JSInterfaceHide, "cls");

		LinearLayout dialogLayout = dialog.findViewById(R.id.dialogLayout);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		ViewGroup.LayoutParams params = dialogLayout.getLayoutParams();
		params.height = (int) (size.y*0.8);
		dialogLayout.setLayoutParams(params);

		final Runnable runnableNote = new Runnable()
		{
			public void run()
			{
				// your code here
				BlackBackground = mPrefs.getInt("BlackBackground", 0);
				notesState = mPrefs.getInt("notesState", 0);
				String note, content = null;
				int intNoteId;

				intNoteId = Integer.parseInt(note_id)-1000;
				note_id = Integer.toString(intNoteId);

				content =  "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
						"<html><head>"+
						"<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\" />"+
						"<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"+
						"<head/>";
				if(BlackBackground == 0)
					content += "<body>";//White background
				else if(BlackBackground == 1)
					content += "<body style=\"background-color:black;color:white\">";//Black background
				ParseTheDoc();
				headers = doc.select("div#ftn"+note_id);
				note = headers.get(0).html();
				note = note.substring(0, note.indexOf("<a href")) + note.substring(note.indexOf("</a>")+4);//in order to remove the prefix of the note. something like "<a href="#1021" name="_ftn21" title="">[21]</a>"
				//note = note.substring(note_id.length()+3);//in order to remove the prefix of the note. something like [note_id]
				//content += "<p class=\"note\" dir=\"RTL\">" + note + "</p> </body></html>";
				content+=note;

				if (notesState == 1) {  //show notes in resizeable window
					webView2.loadDataWithBaseURL("file:///android_asset/",content, "text/html; charset=utf-8", "UTF-8",null);
					webSettings2.setDefaultFontSize(fontSize);
					if (BlackBackground == 1) {
						separateLayout.setBackground(resources.getDrawable(R.drawable.separate_white));
						bexitnotes.setVisibility(View.GONE);
						bexitnotesBlack.setVisibility(View.VISIBLE);
					}
					else {
						separateLayout.setBackground(resources.getDrawable(R.drawable.separate_black));
						bexitnotesBlack.setVisibility(View.GONE);
						bexitnotes.setVisibility(View.VISIBLE);
					}
					secondLayout.setVisibility(View.VISIBLE);
					Log.d("getChap", "hap");
				}
				else{   //show notes in dialog window

					webSettingsNote.setDefaultFontSize(fontSize);
					webviewNote.loadDataWithBaseURL("file:///android_asset/",content, "text/html; charset=utf-8", "UTF-8",null);
					dialog.show();
					dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
					{
						@Override
						public void onCancel(DialogInterface dialog)
						{
							//do whatever you want the back key to do
							dialog.dismiss();
							scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
						}
					});
				}
			}
		};

		final Runnable runnableShowDraw = new Runnable() {
			@Override
			public void run() {
				String JSFun = "javascript:function show() {" +
						"document.getElementById('cls"+draw_id+"').style.display = 'block';" +
						"document.getElementById('img"+draw_id+"').style.display = 'block'; } show(); ";
				webview.loadUrl(JSFun);
				webView2.loadUrl(JSFun);
				webviewNote.loadUrl(JSFun);
			}
		};
		final Runnable runnableHideDraw = new Runnable() {
			@Override
			public void run() {
				String JSFun = "javascript:function hide() {" +
						"document.getElementById('"+draw_id+"').style.display = 'none';" +
						"document.getElementById('img"+draw_id.substring(3)+"').style.display = 'none'} hide(); ";
				webview.loadUrl(JSFun);
				webView2.loadUrl(JSFun);
				webviewNote.loadUrl(JSFun);
			}
		};

		final Runnable runnableAudio = new Runnable()
		{

			public void run()
			{
				// your code here
				try
				{
					if ((isConnected())) {
						Class ourClass = Class.forName("com.JK.mikraey_kodesh.myAudio");
						Intent ourIntent = new Intent(textMain.this, ourClass);

						ourIntent.putExtra("audio_id", Integer.parseInt(audio_id));
						ourIntent.putExtra("book_id", book_chapter[0]);
						ourIntent.putExtra("chapter_id", book_chapter[1]);
						ourIntent.putExtra("chapter_id", book_chapter[1]);
						ourIntent.putExtra("webLink", dm.getURL(book_chapter[0], book_chapter[1]));
						//ourIntent.putExtra("webLink", localFile.getPath());
						ourIntent.putExtra("scroolY", webview.getScrollY());
						ourIntent.putExtra("hearAndRead", false);
						ourIntent.putExtra("fontSize", fontSize);
						findAllHeaders(ourIntent);
						startActivity(ourIntent);
					}
					else
						Toast.makeText(getApplicationContext(), "אין חיבור אינטרנט", Toast.LENGTH_LONG).show();
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		};

		webview.addJavascriptInterface(new Object()
		{
			@JavascriptInterface
			public void performClick(String id)
			{
				scrollSpeed = 0;
				setNoteId(id);
				runOnUiThread(runnableNote);
			}
		}, "ok");


		JSInterfaceShow = new Object(){
			@JavascriptInterface
			public void performClick(String id)
			{
				scrollSpeed = 0;
				setDrawId(id);
				runOnUiThread(runnableShowDraw);
			}
		};
		JSInterfaceHide = new Object(){
			@JavascriptInterface
			public void performClick(String id)
			{

				setDrawId(id);
				runOnUiThread(runnableHideDraw);
			}
		};

		webView2.addJavascriptInterface(JSInterfaceShow, "sd");
		webView2.addJavascriptInterface(JSInterfaceHide, "cls");
		webview.addJavascriptInterface(JSInterfaceShow, "sd");
		webview.addJavascriptInterface(JSInterfaceHide, "cls");


		webview.addJavascriptInterface(new Object()
		{
			@JavascriptInterface
			public void performClick(String id)
			{

				setDrawId(id);
				runOnUiThread(runnableHideDraw);
			}
		}, "cls");

		webview.addJavascriptInterface(new Object()
		{
			@JavascriptInterface
			public void performClick(String id)
			{
				setAudioId(id);
				runOnUiThread(runnableAudio);
			}
		}, "audio");
		fontSize = mPrefs.getInt("fontSize", 20);
		if(fontSize > 50)
			fontSize = 20;
		webSettings.setDefaultFontSize(fontSize);
	}

	private void setWebviewFindListener() {
		webviewFindListener = new WebView.FindListener() {
			@Override
			public void onFindResultReceived(int i, int i1, boolean b) {

				if (b) {
					if (i1 == 0)//no matches
					{
						webview.loadUrl("javascript:function b() {" +
								"document.getElementsByClassName('notes')[0].style.display = 'none';} b(); ");
						Toast.makeText(textMain.this, "לא נמצאו תוצאות בפרק זה", Toast.LENGTH_SHORT).show();
						webview.clearMatches();
					}
					else {
						if (lnrFindOptions.getVisibility() == View.GONE)
							Toast.makeText(textMain.this,"נמצאו "+ i1 + " תוצאות", Toast.LENGTH_SHORT).show();
						lnrFindOptions.setVisibility(View.VISIBLE);

					}
				}
			}
		};
		webview.setFindListener(webviewFindListener);

	}


	public void  setNoteId(String id)
	{
		note_id=id;
	}
	public void  setDrawId(String id)
	{
		draw_id=id;
	}
	public void  setAudioId(String id)
	{
		audio_id=id;
	}

	boolean isConnected(){

		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if(networkInfo!=null){
			if(networkInfo.isConnected())
				return true;
			else
				return false;
		}else
			return false;

	}
	private void jumpToY ( int yLocation )
	{
		webview.postDelayed( new Runnable ()
		{
			public void run()
			{
				//if(scrollY != 0)
					webview.scrollTo(0, yLocation);
			}
		}, 400);/*how much time to delay*/
	}

	private void finddelay (final String query  )
	{
		webview.postDelayed( new Runnable ()
		{
			public void run()
			{
				doInnerSearch(query);
				try
				{
					Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
					m.invoke(webview, true);
				}
				catch (Throwable ignored){}
			}
		}, 400);/*how much time to delay*/
	}

	private void WhiteTextAfterDelay (  )
	{
		webview.postDelayed( new Runnable ()
		{
			public void run()
			{
				webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"white\";} myFunction(); ");
				webview.findAll(query);
				try
				{
					Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
					m.invoke(webview, true);
				}
				catch (Throwable ignored){}
			}
		}, 400);/*how much time to delay*/
	}
	private Context getContext() {
		return null;
	}


	protected void onStart()
	{
		super.onStart();
		// The activity is about to become visible.

	}//onStart

	protected void onResume()
	{
		// The activity has become visible (it is now "resumed").


		if (BlackBackground != mPrefs.getInt("BlackBackground", 0) ||
				cbAssistButtons !=  mPrefs.getInt("sAssistButtons", 1)||
				SleepScreen !=  mPrefs.getInt("SleepScreen", 0)) {
			recreate();
		}

		super.onResume();
		//setLayoutContents();
		supportInvalidateOptionsMenu();
		invalidateOptionsMenu();



//
//		loadActivity();

	}//onResume

	protected void onPause()
	{
		super.onPause();
		webview.clearMatches();
		lnrFindOptions.setVisibility(View.GONE);
		webview.loadUrl("javascript:function b() {" +
				"document.getElementsByClassName('notes')[0].style.display = 'none';} b(); ");
		scrollY = webview.getScrollY();
		fromMainActivity = false;
		shPrefEditor.putInt("book", book_chapter[0]);
		shPrefEditor.putInt("chapter", book_chapter[1]);
		shPrefEditor.putInt("scrollY", scrollY);
		shPrefEditor.putInt("fontSize", fontSize);
		scrollSpeed = -1;
		shPrefEditor.commit();
	}//onPaused


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub
		BlackBackground = mPrefs.getInt("BlackBackground", 0);

		getMenuInflater().inflate(R.menu.actionbar_textmain, menu);
		title = dm.getPartName(book_chapter[0], book_chapter[1]);

		if(BlackBackground == 1)
		{
			//textActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF424242")));

			//inflater.inflate(R.menu.actionbar_textmain_black, menu);

			webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"white\";var y = document.getElementsByClassName(\"left\"); y[0].style.display = 'none';} myFunction(); ");

			webview.setBackgroundColor(0xFFFFFF);//black
			//textActionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + title + "</font>"));


		}
		else {
//			inflater.inflate(R.menu.actionbar_textmain, menu);
			//lnrOptions.setBackground(ResourcesCompat.getDrawable(resources,R.color.app_theme_color, getTheme()));
			//lnrFindOptions.setBackground(ResourcesCompat.getDrawable(resources,R.color.app_theme_color, getTheme()));
			//textActionBar.setBackgroundDrawable(ResourcesCompat.getDrawable(resources,R.color.app_theme_color, getTheme()));
			webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"black\";} myFunction(); ");
			webview.loadUrl("javascript:function d() {" +
					"document.getElementsByClassName('notes')[0].style.background-color = '##E7E7E7';} d(); ");
			webview.setBackgroundColor(0x000000);//white
			//llMainLayout.setBackgroundColor(Color.WHITE);
			//textActionBar.setTitle(Html.fromHtml("<font color=\"black\">" + title + "</font>"));

		}

		return true;
	}//onCreateOptionsMenu

	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			rotate=2;
		}
		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			rotate=1;
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		if(mPrefs.getInt("sVolumeScroll", 1)==1) {
			int keyCode = event.getKeyCode();
			int keyAction = event.getAction();
			switch (keyCode) {
				case KeyEvent.KEYCODE_VOLUME_UP:
					if (keyAction == KeyEvent.ACTION_UP) {
						webview.pageUp(false);
					}
					return true;
				case KeyEvent.KEYCODE_VOLUME_DOWN:
					if (keyAction == KeyEvent.ACTION_UP) {
						webview.pageDown(false);
					}
					return true;
				default:
					return super.dispatchKeyEvent(event);
			}
		} else
			return super.dispatchKeyEvent(event);
	}

	int scrollSpeed=1;
	private Handler mHandler=new Handler();
	public Runnable mScrollDown = new Runnable()
	{
		public void run()
		{
			if(scrollSpeed == 0) // in case of note opened
			{
				mHandler.postDelayed(this, 200);
			}
			else if(scrollSpeed == -1) // in case that "stop" pressed
			{
				webview.scrollBy(0, 0);
			}
			else
			{
				webview.scrollBy(0, 1);
				mHandler.postDelayed(this, 200-(scrollSpeed*10));
			}
		}
	};



	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		final Context context = this;
		if (actionBarDrawerToggle.onOptionsItemSelected(item))
			return true;

		// TODO Auto-generated method stub
		switch (item.getItemId())
		{
			case R.id.action_search:
				innerSearch();
				break;
			case R.id.action_add_bookmark:
				// custom dialog
				bookmarkDialog = new Dialog(context);
                bookmarkDialog.setContentView(R.layout.add_bookmark);
				bookmarkDialog.setTitle("הוסף סימניה");

				Button dialogButton = (Button) bookmarkDialog.findViewById(R.id.dialogButtonOK);
				spinner1 = (Spinner) bookmarkDialog.findViewById(R.id.spinner1);
				BookmarkName = (EditText) bookmarkDialog.findViewById(R.id.editTextBookmarkName);

				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						int index = 0, index_end = 0;
						String bookmarkText = BookmarkName.getText().toString();
						bookmarkText = bookmarkText.replaceAll("@", "-");/*if the user insert @, replace it with "-"*/
						bookmarkText = bookmarkText.replaceAll(";", ":");
						/*		      bookmark name			book					chapter						scroll							fontSize*/
						strBookmark = bookmarkText + "@" + book_chapter[0] + "@" + book_chapter[1] + "@" + webview.getScrollY() + "@" + (int) (fontSize)/*(webview.getScale()*100)*/;

						Bookmarks = mPrefs.getString("Bookmarks", "");
						if((index = Bookmarks.indexOf(bookmarkText))!=-1)/*if there is already bookmark with the same name override it*/
						{
							index_end = index;
							for(int i=0; i<5; i++)
							{
								if(Bookmarks.indexOf("@", index_end+1) != -1)
									index_end = Bookmarks.indexOf("@", index_end + 1);
								else/*in case that this is the last bookmark*/
									index_end = Bookmarks.length();
							}
							Bookmarks = Bookmarks.substring(0, index) + strBookmark + Bookmarks.substring(index_end, Bookmarks.length());
							if(MyLanguage == ENGLISH)
								Toast.makeText(getApplicationContext(),	"Existing bookmark updated", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == RUSSIAN)
								Toast.makeText(getApplicationContext(),	"Текущая закладка обновлена", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == SPANISH)
								Toast.makeText(getApplicationContext(),	"Marcador existente actualizado", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == FRENCH)
								Toast.makeText(getApplicationContext(),	"Le signet existant est mis à jour", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(getApplicationContext(),	"הסימניה הקיימת עודכנה", Toast.LENGTH_SHORT).show();
						}
						else
						{
							if (Bookmarks.equals(""))
								Bookmarks += strBookmark;
							else
								Bookmarks += ";" + strBookmark;
							if(MyLanguage == ENGLISH)
								Toast.makeText(getApplicationContext(),	"New bookmark created", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == RUSSIAN)
								Toast.makeText(getApplicationContext(),	"Создана новая закладка", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == SPANISH)
								Toast.makeText(getApplicationContext(),	"Nuevo marcador creado", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == FRENCH)
								Toast.makeText(getApplicationContext(),	"Nouveau signet créé", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(getApplicationContext(),	"סימניה חדשה נוצרה", Toast.LENGTH_SHORT).show();
						}
						shPrefEditor.putString("Bookmarks", Bookmarks);
						shPrefEditor.commit();
						bookmarkDialog.dismiss();
					}
				});

				BookmarkName.setText(dm.getFullNameOfChapter(book_chapter[0], book_chapter[1]));

				addItemsOnSpinner();

				spinner1.setOnItemSelectedListener(new OnItemSelectedListener()
				{
					boolean first=true;
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
					{
						if (!first)
							BookmarkName.setText(parent.getItemAtPosition(pos).toString());
						first = false;
					}

					public void onNothingSelected(AdapterView<?> arg0)
					{
						// do nothing
					}
				});

				bookmarkDialog.show();

				break;
			case R.id.action_config:
				showPopupMenuSettings(findViewById(R.id.action_config));
				break;

//			case R.id.play:
//				scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
//				runOnUiThread(mScrollDown);
//				break;
//
//			case R.id.stop:
//				scrollSpeed = -1;
//				break;
//
//			case R.id.autoScrollSpeed:
//				autoScrollSpeedDialog();
//				break;
			default:
				break;
		}

		return true;
		//return super.onOptionsItemSelected(item);
	}

	public void addItemsOnSpinner()
	{
		List<String> list = new ArrayList<String>();
		int i, index = 0, index_end=0;

		list.add("");/*this is for the first item that need to be hidden in order to have the ability to choose the first item*/
		Bookmarks = mPrefs.getString("Bookmarks", "");
		String[] listOfBookMarks = Bookmarks.split(";");

		for (i=0; i<listOfBookMarks.length; i++){
			String[] mBookmark = listOfBookMarks[i].split("@");
			list.add(mBookmark[BookmarkActivity.NAME]);
		}
//		while((index = Bookmarks.indexOf("," , index)) != -1)
//		{
//			index++;
//			index_end = Bookmarks.indexOf("," , index);
//			list.add(Bookmarks.substring(index, index_end));
//			for(i=0;i<4;i++)/*skip all other fields*/
//				index = Bookmarks.indexOf("," , index) + 1;
//		}

		int hidingItemIndex = 0;
		CustomSpinnerAdapter dataAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, list, hidingItemIndex);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner1.setAdapter(dataAdapter);
	}

	private void showPopupMenuSettings(View v)
	{
		PopupMenu popupMenu = new PopupMenu(textMain.this, v);

		String configHeaders[] = new String[3];

		configHeaders[0] = "הגדרות";
		configHeaders[1] = "ראשי תיבות";
		configHeaders[2] = "התחל גלילה";
//		configHeaders[3] = "הפסק גלילה";
//		configHeaders[4] = "הגדר מהירות";
//			configHeaders[5] = "הגדל טקסט";
//			configHeaders[6] = "הקטן טקסט";
//		}

		popupMenu.getMenu().add(0,0,0,configHeaders[0]);//(int groupId, int itemId, int order, int titleRes)
		popupMenu.getMenu().add(0,1,1,configHeaders[1]);
		popupMenu.getMenu().add(0,2,2,configHeaders[2]);


		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				WebSettings webSettings = webview.getSettings();
				fontSize = webSettings.getDefaultFontSize();
				switch (item.getItemId())
				{
					case 0:/*settings*/
						try
						{
							Class ourClass = Class.forName("com.JK.mikraey_kodesh.Settings");
							Intent ourIntent = new Intent(textMain.this, ourClass);
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
					case 2:/*scroll*/
						lnrScroll.setVisibility(View.VISIBLE);
						scrollSpeed = 17;
						runOnUiThread(mScrollDown);
						break;



					default:
						break;
				}
				return true;
			}
		});

		popupMenu.show();
	}//showPopupMenuSettings

	public void ParseTheDoc()
	{
		String prefix;
		InputStream is;
		int size;
		byte[] buffer;
		String input;

		fileName = dm.getURL(book_chapter[0],book_chapter[1],true);
		if ((!fileName.equals(lastFileName)))
		{
			Log.d("getChap",fileName+" # "+fileNameOnly);
			lastFileName = fileName;
			prefix = "file:///android_asset/";
			fileNameOnly = fileName;//.substring(prefix.length());
			try
			{
				is = getAssets().open(fileNameOnly);
				size = is.available();
				buffer = new byte[size];
				is.read(buffer);
				is.close();
				input = new String(buffer);
				doc = Jsoup.parse(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String getChapterText(int book, int chapter){
		String text = "אירעה שגיאה, ניתן לדווח ב";
		StringBuilder stringBuilder = new StringBuilder();
		InputStream is;
		int size;
		BufferedReader buffer;
		try
		{
			is = context.getAssets().open(dm.getURL(book, chapter, true));
			size = is.available();
			buffer = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = buffer.readLine()) != null){
				stringBuilder.append(line);
			}
			text = stringBuilder.toString();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

	private void findHeaders()
	{
		String prefix;
		fileName = dm.getURL(book_chapter[0],book_chapter[1],true);
		prefix = "file:///android_asset/";
		fileNameOnly = fileName;//.substring(prefix.length());
		try
		{
			InputStream is = getAssets().open(fileNameOnly);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String input = new String(buffer);

			Document doc = Jsoup.parse(input);
			headers = doc.select("h2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getTheArrayLocation(String Chapter)
	{
		int perek, seif;
		for (perek = 0; perek < dm.BOOKS_NUMBER; perek++)
		{
			for (seif = 0; seif <= dm.lastChapterOfBook[perek]; seif++)
			{
				if(Chapter.equals(dm.getURL(perek, seif)))
				{
					book_chapter[0] = perek;
					book_chapter[1] = seif;
					return;
				}
			}
		}
	}

	private String getClearUrl()
	{
		String ClearUrl;
		ClearUrl = webview.getUrl();
		Log.d("getChap", ClearUrl);
		ClearUrl = ClearUrl.substring(0, ClearUrl.indexOf(".html")+5);
		ClearUrl = dm.getURL(book_chapter[0],book_chapter[1],true);
		return ClearUrl;
	}

	private void findAllHeaders(Intent ourIntent)
	{
		String prefix, a;
		int j;
		ArrayList<String> sections = new ArrayList<String>();
		ArrayList<String> sections2 = new ArrayList<String>();
		fileName = dm.getURL(book_chapter[0],book_chapter[1],true);
		prefix = "file:///android_asset/";
		fileNameOnly = fileName;//substring(prefix.length());
		fileNameOnly = fileNameOnly.substring(0, fileNameOnly.lastIndexOf("_")+1);

		for(int i=1; i<=dm.lastChapterOfBook[book_chapter[0]]; i++) {
			try {
				InputStream is;
				is = getAssets().open(fileNameOnly+i+".html");
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				String input = new String(buffer);

				Document doc = Jsoup.parse(input);
				headers = doc.select("h2");

				sections.clear();
				for(j = 0; j < headers.size(); j++)
					sections.add(headers.get(j).text());


				String name;
				name = "sections_"+i;

				// Creating a new local copy of the current list.
				ArrayList<String> newList = new ArrayList<>(sections);

				ourIntent.putStringArrayListExtra(name, newList);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void setBtmapbrMenuListener() {
		bottomAppBar.setOnMenuItemClickListener(item -> {
			WebSettings webSettings = webview.getSettings();
			WebSettings webSettings2 = webView2.getSettings();

			fontSize = webSettings.getDefaultFontSize();
			switch (item.getItemId()) {
				case R.id.ibChapters:
					findHeaders();
					//showPopupMenu(findViewById(R.id.ibChapters));
					showHeaderMenu();
					return true;

				case R.id.ibNext:
					book_chapter[1] += 1;
					goToChapter(book_chapter[0], book_chapter[1]);
					return true;

				case R.id.ibPrevious:
					book_chapter[1] -= 1;
					goToChapter(book_chapter[0], book_chapter[1]);
					return true;

				case R.id.increaseFont:
					if(fontSize <= 47) {
						fontSize += 3;
						webSettings.setDefaultFontSize(fontSize);
						webSettings2.setDefaultFontSize(fontSize);
						shPrefEditor.putInt("fontSize", fontSize);
						shPrefEditor.commit();
//					fontToast.setText("גודל גופן - "+fontSize);
//					fontToast.show();
						Toast.makeText(getApplicationContext(),	"גודל גופן - "+fontSize, Toast.LENGTH_SHORT).show();
					}
					else{
						fontToast.setText("גודל גופן מקסימלי");
						fontToast.show();
//					Toast.makeText(getApplicationContext(),	"גודל גופן מקסימלי", Toast.LENGTH_SHORT).show();
					}
					return true;

				case R.id.decreaseFont:
					if(fontSize >= 10 ) {
						fontSize -= 3;
						webSettings.setDefaultFontSize(fontSize);
						webSettings2.setDefaultFontSize(fontSize);
						shPrefEditor.putInt("fontSize", fontSize);
						shPrefEditor.commit();
//					fontToast.setText("גודל גופן - "+fontSize);
//					fontToast.show();
						Toast.makeText(getApplicationContext(),	"גודל גופן - "+fontSize, Toast.LENGTH_SHORT).show();
					}
					else{
						fontToast.setText("גודל גופן מקסימלי");
						fontToast.show();
//					Toast.makeText(getApplicationContext(),	"גודל גופן מקסימלי", Toast.LENGTH_SHORT).show();
					}
					return true;

//			case R.id.ibNextPage:
//				webview.pageDown(false);
//				break;

//			case R.id.ibPreviousPage:
//				webview.pageUp(false);
//				break; (previous/next page)


				case R.id.exit:
					this.finish();
				return true;
				default:
					return false;
			}
		});
	}

	private void showHeaderMenu() {
		final Map<String, String> headers = new HashMap<>();
		Log.d("getChap", "start");

		// Find all the headers in the document and get their positions
		webview.evaluateJavascript("var headers = document.querySelectorAll('h1, h2, h3, h4, h5, h6');" +
				"var positions = [];" +
				"for (var i = 0; i < headers.length; i++) {" +
				"  var header = headers[i];" +
				"  var position = header.getBoundingClientRect();" +
				"  positions.push({text: header.innerText, x: position.left + window.pageXOffset, y: position.top + window.pageYOffset});" +
				"}" +
				"JSON.stringify(positions);", new ValueCallback<String>() {
			@Override
			public void onReceiveValue(String value) {
				Log.d("getChap", "received");
				try {
					JSONArray jsonArray = new JSONArray(value);
					Log.d("getChap", String.valueOf(jsonArray.length()));
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String text = jsonObject.getString("text");
						int x = jsonObject.getInt("x");
						int y = jsonObject.getInt("y");
						headers.put(text, x + "," + y);
					}
					// Show the header menu
					PopupMenu popupMenu = new PopupMenu(textMain.this, findViewById(R.id.ibChapters));
					for (String text : headers.keySet()) {
						popupMenu.getMenu().add(text);
					}
					popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							String positionString = headers.get(item.getTitle().toString());
							String[] parts = positionString.split(",");
							int x = Integer.parseInt(parts[0]);
							int y = Integer.parseInt(parts[1]);
							webview.scrollTo(x, y);
							nestedScrollView.scrollTo(x, y);
							return true;
						}
					});
					popupMenu.show();
				} catch (JSONException e) {
					Log.d("getChap", "exception");
					e.printStackTrace();
				}
			}
		});
	}


	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View view)
	{
		String currentChapter;
		WebSettings webSettings = webview.getSettings();
		WebSettings webSettings2 = webView2.getSettings();

		fontSize = webSettings.getDefaultFontSize();
		// TODO Auto-generated method stub
		switch(view.getId())
		{
			case R.id.ibChapters:
				findHeaders();
				//showPopupMenu(view);
				showHeaderMenu();
				break;

			case R.id.ibNext:
				book_chapter[1] += 1;
				goToChapter(book_chapter[0], book_chapter[1]);
				break;

			case R.id.ibPrevious:
				book_chapter[1] -= 1;
				goToChapter(book_chapter[0], book_chapter[1]);
				break;

			case R.id.increaseFont:
				if(fontSize <= 47) {
					fontSize += 3;
					webSettings.setDefaultFontSize(fontSize);
					webSettings2.setDefaultFontSize(fontSize);
					shPrefEditor.putInt("fontSize", fontSize);
					shPrefEditor.commit();
//					fontToast.setText("גודל גופן - "+fontSize);
//					fontToast.show();
					Toast.makeText(getApplicationContext(),	"גודל גופן - "+fontSize, Toast.LENGTH_SHORT).show();
				}
				else{
					fontToast.setText("גודל גופן מקסימלי");
					fontToast.show();
//					Toast.makeText(getApplicationContext(),	"גודל גופן מקסימלי", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.decreaseFont:
				if(fontSize >= 10 ) {
					fontSize -= 3;
					webSettings.setDefaultFontSize(fontSize);
					webSettings2.setDefaultFontSize(fontSize);
					shPrefEditor.putInt("fontSize", fontSize);
					shPrefEditor.commit();
//					fontToast.setText("גודל גופן - "+fontSize);
//					fontToast.show();
					Toast.makeText(getApplicationContext(),	"גודל גופן - "+fontSize, Toast.LENGTH_SHORT).show();
				}
				else{
					fontToast.setText("גודל גופן מקסימלי");
					fontToast.show();
//					Toast.makeText(getApplicationContext(),	"גודל גופן מקסימלי", Toast.LENGTH_SHORT).show();
				}
				break;

//			case R.id.ibNextPage:
//				webview.pageDown(false);
//				break;

//			case R.id.ibPreviousPage:
//				webview.pageUp(false);
//				break; (previous/next page)

			case R.id.ibFindNext:
				webview.findNext(true);
				break;

			case R.id.ibFindPrevious:
				webview.findNext(false);
				break;

			case R.id.ibFindClose:
				webview.clearMatches();
				lnrFindOptions.setVisibility(View.GONE);
				webview.loadUrl("javascript:function b() {" +
						"document.getElementsByClassName('notes')[0].style.display = 'none';} b(); ");
				break;

			case R.id.exit:
				this.finish();
//			case R.id.play:
//				scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
//				runOnUiThread(mScrollDown);
//				break;
//
//			case R.id.stop:
//				scrollSpeed = -1;
//				break;
//
//			case R.id.autoScrollSpeed:
//				autoScrollSpeedDialog();
//				break; (auto scroll option)
		}

	}//onClick

	public void onClickActinBar(View view){
		switch (view.getId())
		{
			case R.id.action_search:
				innerSearch();
				break;
			case R.id.action_add_bookmark:
				// custom dialog
				bookmarkDialog = new Dialog(context);
				bookmarkDialog.setContentView(R.layout.add_bookmark);
				bookmarkDialog.setTitle("הוסף סימניה");

				Button dialogButton = (Button) bookmarkDialog.findViewById(R.id.dialogButtonOK);
				spinner1 = (Spinner) bookmarkDialog.findViewById(R.id.spinner1);
				BookmarkName = (EditText) bookmarkDialog.findViewById(R.id.editTextBookmarkName);

				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						int index = 0, index_end = 0;
						String bookmarkText = BookmarkName.getText().toString();
						bookmarkText = bookmarkText.replaceAll("@", "-");/*if the user insert comma, replace it with "-"*/
						bookmarkText = bookmarkText.replaceAll(";", ":");
						/*		      bookmark name			book					chapter						scroll							fontSize*/
						strBookmark = bookmarkText + "@" + book_chapter[0] + "@" + book_chapter[1] + "@" + webview.getScrollY() + "@" + (int) (fontSize)/*(webview.getScale()*100)*/;

						Bookmarks = mPrefs.getString("Bookmarks", "");
						if((index = Bookmarks.indexOf(bookmarkText))!=-1)/*if there is already bookmark with the same name override it*/
						{
							index_end = index;
							for(int i=0; i<5; i++)
							{
								if(Bookmarks.indexOf("@", index_end+1) != -1)
									index_end = Bookmarks.indexOf("@", index_end + 1);
								else/*in case that this is the last bookmark*/
									index_end = Bookmarks.length();
							}
							Bookmarks = Bookmarks.substring(0, index) + strBookmark + Bookmarks.substring(index_end, Bookmarks.length());
							if(MyLanguage == ENGLISH)
								Toast.makeText(getApplicationContext(),	"Existing bookmark updated", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == RUSSIAN)
								Toast.makeText(getApplicationContext(),	"Текущая закладка обновлена", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == SPANISH)
								Toast.makeText(getApplicationContext(),	"Marcador existente actualizado", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == FRENCH)
								Toast.makeText(getApplicationContext(),	"Le signet existant est mis à jour", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(getApplicationContext(),	"הסימניה הקיימת עודכנה", Toast.LENGTH_SHORT).show();
						}
						else
						{
							if (Bookmarks.equals(""))
								Bookmarks += strBookmark;
							else
								Bookmarks += ";" + strBookmark;
							if(MyLanguage == ENGLISH)
								Toast.makeText(getApplicationContext(),	"New bookmark created", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == RUSSIAN)
								Toast.makeText(getApplicationContext(),	"Создана новая закладка", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == SPANISH)
								Toast.makeText(getApplicationContext(),	"Nuevo marcador creado", Toast.LENGTH_SHORT).show();
							else if(MyLanguage == FRENCH)
								Toast.makeText(getApplicationContext(),	"Nouveau signet créé", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(getApplicationContext(),	"סימניה חדשה נוצרה", Toast.LENGTH_SHORT).show();
						}
						shPrefEditor.putString("Bookmarks", Bookmarks);
						shPrefEditor.commit();
						bookmarkDialog.dismiss();
					}
				});

				BookmarkName.setText(dm.getFullNameOfChapter(book_chapter[0], book_chapter[1]));

				addItemsOnSpinner();

				spinner1.setOnItemSelectedListener(new OnItemSelectedListener()
				{
					boolean first=true;
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
					{
						if (!first)
							BookmarkName.setText(parent.getItemAtPosition(pos).toString());
						first = false;
					}

					public void onNothingSelected(AdapterView<?> arg0)
					{
						// do nothing
					}
				});

				bookmarkDialog.show();

				break;
			case R.id.action_config:
				showPopupMenuSettings(findViewById(R.id.action_config));
				break;
			case R.id.sideBarBtn:

			default:
				break;
		}
	}

	public void onScrollClick(View view){
		switch (view.getId()){
			case R.id.stopScrollBtn:
				scrollSpeed = -1;
				lnrScroll.setVisibility(View.GONE);
				break;
			case R.id.increaseScrollBtn:
				if (scrollSpeed != 19)
					scrollSpeed +=1;
				runOnUiThread(mScrollDown);
				break;
			case R.id.decreaseScrollBtn:
				scrollSpeed -= 1;

				break;
		}
	}

	public boolean onDown(MotionEvent e) {
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	private class MyWebViewClient extends WebViewClient
	{
		@SuppressLint("NewApi")
		@Override
		public void onPageFinished(WebView view, String url)
		{
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			String strToastSearch;
			WebSettings webSettings = webview.getSettings();


			if(firstTime || ChangeChapter)
			{
				firstTime = false;

				webSettings.setDefaultFontSize(fontSize);

				if(cameFromSearch)
				{
					cameFromSearch = false;
					webview.loadUrl(searchPosition);
					if(noteStr != "0")/*if all the results are in notes*/
					{
						//query = "[" + noteStr+ "]";
						strToastSearch = "תוצאות החיפוש נמצאות בהערות: " + sectionsForToast;
						if(BlackBackground == 1)
							WhiteTextAfterDelay();
						finddelay(query);
					}
					else
					{
						strToastSearch = "תוצאות החיפוש נמצאות גם בהערות: " + sectionsForToast;
						doInnerSearch(query);
					}

//					if(API < 16)
//					{
//						if(BlackBackground == 1)
//							WhiteTextAfterDelay();
//						else
//							finddelay(query);
//					} else {
//
//					}

					if (sectionsForToast.compareTo("") != 0)
						Toast.makeText(getApplicationContext(), strToastSearch, Toast.LENGTH_LONG).show();
				}
				if(BlackBackground == 1)
				{
					//webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"white\";var y = document.getElementsByClassName(\"left_white\"); y[0].style.display = 'none';} myFunction(); ");
					webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"white\";} myFunction(); ");
					llMainLayout.setBackgroundColor(Color.BLACK);
					webview.setBackgroundColor(0xFFFFFF);//black
					//	textActionBar.setTitle(Html.fromHtml("<font color=\"white\">" + title + "</font>"));
				}
				else
				{
					//webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"black\";var y = document.getElementsByClassName(\"left_black\"); y[0].style.display = 'none';} myFunction(); ");
					webview.loadUrl("javascript:function myFunction() {var x = document.body;x.style.color = \"black\";} myFunction(); ");
					llMainLayout.setBackgroundColor(Color.WHITE);
					webview.setBackgroundColor(0x000000);//white
					//	textActionBar.setTitle(Html.fromHtml("<font color=\"black\">" + title + "</font>"));
				}
				invalidateOptionsMenu();
			}
		}
	}

	public void findBookAndChapter()
	{
		String bookAndChapter;

		int length= searchPosition.lastIndexOf("#");
		if(length == -1)/*it means that all the results are in notes*/
		{
			length = searchPosition.lastIndexOf(":");
			noteStr = searchPosition.substring(length+1,searchPosition.length());
			searchPosition = searchPosition.substring(0, length);
			bookAndChapter = searchPosition;
		}
		else
		{
			length= searchPosition.lastIndexOf("#");
			bookAndChapter = searchPosition.substring(0, length);
		}

		book_chapter = new int[2];
		for (int i = 0; i< dataManage.BOOKS_NUMBER; i++)
			for (int j=0; j< dm.lastChapterOfBook[i]; j++)
				if(bookAndChapter.equals(dm.getURL(i, j)))
				{
					book_chapter[0] = i;
					book_chapter[1] = j;
					return;
				}
	}

	void innerSearch()
	{
		final Context context = this;

		// custom dialog
		innerSearchDialog = new Dialog(context);
		innerSearchDialog.setContentView(R.layout.inner_search);
		innerSearchDialog.setTitle("חיפוש בפרק הנוכחי");

		Button dialogButton = (Button) innerSearchDialog.findViewById(R.id.dialogButtonOK);
		TextToSearch = (EditText) innerSearchDialog.findViewById(R.id.editTextTextToSearch );

		// if button is clicked
		dialogButton.setOnClickListener(new OnClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				innerSearchText = TextToSearch.getText().toString();

				innerSearchDialog.dismiss();

//				if(API < 16)
//				{
//					int a=webview.findAll(/*"כל"*/innerSearchText);
//					/*to highlight the searched text*/
//					try
//					{
//						Method m = WebView.class.getMethod("setFindIsUp", Boolean.TYPE);
//						m.invoke(webview, true);
//					}
//					catch (Throwable ignored){}
//				}
//				else
//				{
					doInnerSearch(innerSearchText);
//					webview.loadUrl("javascript:function a() {" +
//							"document.getElementsByClassName('notes')[0].style.display = 'block';} a(); ");
//					webview.findAllAsync(/*"כל"*/innerSearchText);
				//}
//				webview.setFindListener(new WebView.FindListener() {
//					@Override
//					public void onFindResultReceived(int i, int i1, boolean b) {
//
//						if (b) {
//							if (i1 == 0)//no matches
//								Toast.makeText(context, "לא נמצאו תוצאות בפרק זה", Toast.LENGTH_SHORT).show();
//							else {
//								if (lnrFindOptions.getVisibility() == View.GONE)
//									Toast.makeText(context,"נמצאו"+ i1 + "תוצאות", Toast.LENGTH_SHORT).show();
//								lnrFindOptions.setVisibility(View.VISIBLE);
//							}
//						}
//					}
//				});
			}
		});
		innerSearchDialog.show();
	}

	public void doInnerSearch(String query){
		webview.loadUrl("javascript:function a() {" +
				"document.getElementsByClassName('notes')[0].style.display = 'block';} a(); ");
		if (BlackBackground == 1)
			webview.loadUrl("javascript:function c() {" +
					"document.getElementsByClassName('notes')[0].style.backgroundColor = '#353535';} c(); ");
//		webview.setFindListener(new WebView.FindListener() {
//			@Override
//			public void onFindResultReceived(int i, int i1, boolean b) {
//
//				if (b) {
//					if (i1 == 0)//no matches
//					{
//						webview.loadUrl("javascript:function b() {" +
//								"document.getElementsByClassName('notes')[0].style.display = 'none';} b(); ");
//						Toast.makeText(textMain.this, "לא נמצאו תוצאות בפרק זה", Toast.LENGTH_SHORT).show();
//						webview.clearMatches();
//					}
//					else {
//						if (lnrFindOptions.getVisibility() == View.GONE)
//							Toast.makeText(textMain.this,"נמצאו "+ i1 + " תוצאות", Toast.LENGTH_SHORT).show();
//						lnrFindOptions.setVisibility(View.VISIBLE);
//
//					}
//				}
//			}
//		});
		webview.findAllAsync(query);

	}

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
		dialogButtonExit.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				acronymsDialog.dismiss();
			}
		});

		dialogButtonDecode.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				acronymsText = "\r\n" + /*"י\"א" */TextToDecode.getText().toString() + " - ";
				acronymsText = acronymsText.replace("\"", "");
				acronymsText = acronymsText.replace("'", "");
				InputStream is;
				String r = "לא נמצאו תוצאות";
				int index = 0, index_end = 0, first = 1;
				try {
					is = getAssets().open("acronyms.txt");
					int size = is.available();
					byte[] buffer = new byte[size];
					is.read(buffer);
					is.close();
					String strText = new String(buffer);

					while (strText.indexOf(acronymsText, index_end) != -1) {
						index = strText.indexOf(acronymsText, index);
						index = strText.indexOf("-", index + 1) + 2;
						index_end = strText.indexOf("\r\n", index);
						if (first == 1) {
							r = strText.substring(index, index_end);
							first = 0;
						} else
							r += ", " + strText.substring(index, index_end);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				decodedText.setText(TextToDecode.getText().toString() + " - " + r);

			}
		});
		acronymsDialog.show();
	}

	void autoScrollSpeedDialog()
	{
		final Context context = this;

		// custom dialog
		autoScrollDialog = new Dialog(context);
		autoScrollDialog.setContentView(R.layout.auto_scroll);
		autoScrollDialog.setTitle("מהירות גלילה אוטומטית");

		Button dialogButton = (Button) autoScrollDialog.findViewById(R.id.dialogButtonOK);

		// if button is clicked
		dialogButton.setOnClickListener(new OnClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				autoScrollDialog.dismiss();
			}
		});
		spinnerAutoScroll = (Spinner) autoScrollDialog.findViewById(R.id.spinner_auto_scroll);
		scrollSpeed = mPrefs.getInt("scrollSpeed", 2);
		spinnerAutoScroll.setSelection((scrollSpeed / 2) - 1);
		spinnerAutoScroll.setOnItemSelectedListener(new OnItemSelectedListener() {
			boolean first = true;

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				scrollSpeed = (pos + 1) * 2;
				shPrefEditor.putInt("scrollSpeed", scrollSpeed);
				shPrefEditor.commit();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing   
			}
		});
		autoScrollDialog.show();

	}
	private void showPopupMenu(View v)
	{
		PopupMenu popupMenu = new PopupMenu(textMain.this, v);

		//popupMenu.
		for(int i = 0; i < headers.size(); i++)//fill the menu list
		{
			popupMenu.getMenu().add(0,i,i,headers.get(i).text());
		}

		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				int id = item.getItemId()+1;
				String name = String.valueOf(id);
				webview.loadUrl("javascript: function() {" +
						"  var header = document.getElementsByName('2')[0].nextElementSibling;" +
						"  if (header) header.scrollIntoView();" +
						"} function();");;
//				fileName = "file:///android_asset/" + fileName;" +name+"
//
//				String s=fileName+ "#" + id;
//				String s2=fileName+ "#" + (id+1);
//				webview.loadUrl(s2);/*Workaround to fix the bug of jumping to same anchor twice*/
//				webview.loadUrl(s);
				jumpToSectionFlag = true;
				return true;
			}
		});

		popupMenu.show();
	}

	public void loadAdapter2Data(){
		ArrayList<String> listDataHeader = new ArrayList<String>();
		HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
		List<String[]> listOfNispachim = new ArrayList<>();

		int bookId;
		int chapterId;
		for (bookId=0; bookId< dataManage.BOOKS_NUMBER; bookId++) {
			listDataHeader.add(dm.convertBookIdToName(bookId));
			List<String> list = new ArrayList<>();  // Adding child data
			for (chapterId=0; chapterId < dataManage.chapterOfNispachim[bookId]; chapterId++){
				list.add(dm.getChapterName(bookId, chapterId));
			}
			listDataChild.put(listDataHeader.get(bookId), list); // Header, Child data

			String[] nisList = new String[dm.lastChapterOfBook[bookId]-dataManage.chapterOfNispachim[bookId]]; //String[] in the nispachim length
			int i = 0;
			for (chapterId= dataManage.chapterOfNispachim[bookId]; chapterId < dm.lastChapterOfBook[bookId]; chapterId++){
				nisList[i] = (dm.getChapterName(bookId, chapterId));
				i++;
			}
			listOfNispachim.add(nisList);
		}

		expListener = new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
				int bookId = i;
				int chapter = 0;
				if (expandableListView.getTag() != null){
					bookId = (int) expandableListView.getTag();
					chapter = dataManage.chapterOfNispachim[bookId] + i1;
				}
				else
					chapter = i1;
//					chapter+=dm.partsOfBook[bookId][i-1];
//				chapter+= i1;
				book_chapter[0] = bookId;
				book_chapter[1] = chapter;
				goToChapter(bookId, chapter);


				mDrawerLayuot.close();
				menuExpLv.collapseGroup(bookId);
				return false;
			}
		};
		adapter2 = new TwoAndHalfLvlAdapter(context, listDataHeader, listDataChild,listOfNispachim,expListener);
	}


	public void loadAdapterData(){
		String[] first = new String[dataManage.BOOKS_NUMBER];
		for (int i = 0; i<dataManage.BOOKS_NUMBER; i++)
			first[i] = dm.convertBookIdToName(i);

		List<String[]> seconds = new ArrayList<>();
		for (int i = 0; i<dataManage.BOOKS_NUMBER; i++){
			String[] tempArr = new String[dm.partsOfBook[i].length+1];
			tempArr[0] = dm.convertBookIdToName(i);
			for(int j = 0; j<dm.partsOfBook[i].length; j++){
				tempArr[j+1] = dm.getPartName(i, dm.partsOfBook[i][j]);
			}
			seconds.add(tempArr);
		}

		List<LinkedHashMap<String, String[]>> thirds = new ArrayList<>();
		for (int i = 0; i<dataManage.BOOKS_NUMBER; i++){
			LinkedHashMap<String, String[]> hashMap = new LinkedHashMap<>();
			int counter = 0;
			for(int j = 0; j < seconds.get(i).length; j++){
				int chapNum;
				if (j == 0)
					chapNum = dm.partsOfBook[i][j];
				else if (j == seconds.get(i).length-1)// the last one
					chapNum = dm.lastChapterOfBook[i] - dm.partsOfBook[i][j-1]; // chapters last to end of book
				else															// need to be 'j-1' because j run on "seconds", and "seconds" have one more limb
					chapNum = dm.partsOfBook[i][j] - dm.partsOfBook[i][j-1];

				String[] tempArr = new String[chapNum];
				for (int k = 0; k<tempArr.length; k++){
					tempArr[k] = dm.getChapterName(i, counter);
					counter++;
				}
				hashMap.put(seconds.get(i)[j], tempArr);
			}
			thirds.add(hashMap);
		}

		ExpandableListView.OnChildClickListener listener;
		listener = new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
				int bookId = (int) expandableListView.getTag();
				int chapter = 0;
				if (i != 0)
					chapter+=dm.partsOfBook[bookId][i-1];
				chapter+= i1;
				book_chapter[0] = bookId;
				book_chapter[1] = chapter;

				webview.clearMatches();
				lnrFindOptions.setVisibility(View.GONE);
				cameFromSearch = false;
				scrollY = 0;/*In order to jump to the beginning of the chapter*/
				//webview.loadUrl(dm.getURL(book_chapter[0], book_chapter[1]));
				chaper_text = getChapterText(book_chapter[0], book_chapter[1]);
				webview.loadDataWithBaseURL("file:///android_asset/",chaper_text, "text/html; charset=utf-8", "UTF-8",null);
				title = dm.convertBookIdToName(book_chapter[0]) + ": " + dm.convertAnchorIdToSection(book_chapter[1]);
				if(book_chapter[1] == dm.lastChapterOfBook[book_chapter[0]]-1) {//lastChapterOfBook return one number higher
					bNext_sec.setEnabled(false);
					bPrevious_sec.setEnabled(true);
				}
				if(book_chapter[1] == 0) {
					bPrevious_sec.setEnabled(false);
					bNext_sec.setEnabled(true);
				}

				ChangeChapter = true;
				shPrefEditor.putInt("fontSize", fontSize);/*in order to keep the fontSize when moving to next chapter*/

				mDrawerLayuot.close();
				menuExpLv.collapseGroup(bookId);
				return false;
			}
		};
		adapter = new ThreeLevelListAdapter(context, first, seconds, thirds, listener);
	}

	public void goToChapter(int book, int chapter){
		webview.clearMatches();
		lnrFindOptions.setVisibility(View.GONE);
		//secondLayout.setVisibility(View.GONE);
		cameFromSearch = false;
		scrollY = 0;/*In order to jump to the beginning of the chapter*/
		book_chapter[0] = book;
		book_chapter[1] = chapter;
		//webview.loadUrl(dm.getURL(book_chapter[0], book_chapter[1]));
		chaper_text = getChapterText(book_chapter[0], book_chapter[1]);
		webview.loadDataWithBaseURL("file:///android_asset/",chaper_text, "text/html; charset=utf-8", "UTF-8",null);
		title = dm.convertBookIdToName(book_chapter[0]) + ": " + dm.convertAnchorIdToSection(book_chapter[1]);
		if(book_chapter[1] == dm.lastChapterOfBook[book_chapter[0]]-1) {//lastChapterOfBook return one number higher
			bNext_sec.setEnabled(false);
			bPrevious_sec.setEnabled(true);
		}
		else if(book_chapter[1] == 0) {
			bPrevious_sec.setEnabled(false);
			bNext_sec.setEnabled(true);
		}
		else {
			bPrevious_sec.setEnabled(true);
			bNext_sec.setEnabled(true);
		}

		ChangeChapter = true;

		shPrefEditor.putInt("fontSize", fontSize);
	}

	

	public void onClickBackToMenuLl(View view){
		finish();
	}
}
