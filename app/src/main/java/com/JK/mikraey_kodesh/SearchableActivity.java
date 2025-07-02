package com.JK.mikraey_kodesh;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

public class SearchableActivity extends AppCompatActivity
{

	dataManage dm = new dataManage();
	public List<String> listBookLocation = new ArrayList<String>();
	public List<String> listStrAnchor = new ArrayList<String>();
	public ListView searchListView = null;
	public String query;
	public static final String PREFS_NAME = "MyPrefsFile";
	public String sectionsForToast = null;
	public int i = 0;
	int blackBackground = 0;
	public String hebCharacter = ":אבגדהוזחטיכלמנסעפצקרשתםןץףך -'\"";
	public boolean validQuery = false;
	public Button helpBtn;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);
		final SharedPreferences mPrefs;
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		blackBackground = mPrefs.getInt("BlackBackground", 0);
//		if (blackBackground == 1)
//			setTheme(R.style.mActionbardark);
		helpBtn = findViewById(R.id.helpBtn);

		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
		{
			query = intent.getStringExtra(SearchManager.QUERY);
			//query = "ו";// for test of the search
			for (i=0; i<query.length(); i++)
			{
				validQuery = hebCharacter.contains(query.substring(i, i+1));
				if(!validQuery)
				{
					break;
				}
			}

			if(validQuery)
			{
				searchListView = (ListView) findViewById(R.id.list);
				doMySearch();
				showResults();

				searchListView.setOnItemClickListener(new OnItemClickListener() 
				{
					boolean cameFromSearch = false;
					String searchPosition = null;
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
					{
						if (position>0) {
							try {
								Class ourClass = Class.forName("com.JK.mikraey_kodesh.textMain");
								Intent ourIntent = new Intent(SearchableActivity.this, ourClass);


								searchPosition = listStrAnchor.get(position - 1);
								cameFromSearch = true;

								ourIntent.putExtra("cameFromSearch", cameFromSearch);
								ourIntent.putExtra("searchPosition", searchPosition);
								ourIntent.putExtra("query", query);
								sectionsForToast = listBookLocation.get(position - 1);
								if (sectionsForToast.indexOf("הערות:") != -1) {
									sectionsForToast = sectionsForToast.substring(sectionsForToast.indexOf("הערות: ") + 7, sectionsForToast.indexOf("}"));
								} else {
									sectionsForToast = "";
								}
								ourIntent.putExtra("sectionsForToast", sectionsForToast);

								startActivity(ourIntent);
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
			else
			{
				final Context context = this;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.bookmarkDeleteAlert);

				// set title
				alertDialogBuilder.setTitle("חיפוש לא חוקי");

				// set dialog message
				alertDialogBuilder
				.setMessage("הסימן "+query.substring(i, i+1)+" אינו חוקי")
				.setCancelable(false)
				.setPositiveButton("חזור",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close current activity
						SearchableActivity.this.finish();
					}
				});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		}

//		helpBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				onButtonClick(view);
//			}
//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.searchable, menu);
		return true;
	}

	public boolean doMySearch()
	{
		InputStream is;
		int size, i, j, index, index_anchor_start, index_anchor_end, anchorId=0,
						lastanchorId=0, globalCounter=0, chapterCounter=0, noteIndex = 0;
		byte[] buffer;
		String strText = null, strAnchor=null, section=null, sections=null; 
		String prefixAnchor="<a name=" ;

		for(i=0; i<dataManage.BOOKS_NUMBER; i++)
		{
			for(j=0; j<=dm.lastChapterOfBook[i]; j++)
			{
				try 
				{
					chapterCounter = 0;
					sections="";
					is = getAssets().open(dm.convertBookIdToEnglishName(i)+"_"+j+".html");
					size = is.available(); 
					buffer = new byte[size];
					is.read(buffer);
					is.close();
					strText  = new String(buffer);

					index = 0;
					index_anchor_start = 0;
					index_anchor_end = 0;
					noteIndex = strText.indexOf("<div class=\"notes\" style=\"display:none;\">", 0);
					while(index != (-1))
					{
						//System.out.println("book="+i+" chapter="+j+" chapterCounter="+chapterCounter);/*for test - if need to check crash with searching*/
						index = strText.indexOf(query, index+1);
						if(index != (-1))
						{
							if((noteIndex != -1) && (noteIndex < index))/*found in note*/
							{
								index_anchor_end = strText.lastIndexOf("</a>", index);
								index_anchor_end = strText.lastIndexOf("]", index_anchor_end);
								index_anchor_start = strText.lastIndexOf("[", index_anchor_end) + 1;
								strAnchor = strText.substring (index_anchor_start, index_anchor_end);
								anchorId = Integer.parseInt(strAnchor);//convert the anchor ID from string to int
								section = strAnchor;
								if (sections.indexOf("הערות") == -1)//if this is the first find in note make lastanchorId = -1. otherwise don't do it to prevent mentioning of the same note
								{
									lastanchorId = -1;//to separate the anchor ID if the main text and the notes
									if (sections.compareTo("") == 0)
										section =  "הערות: " + strAnchor;
									else
										section =  " הערות: " + strAnchor;
								}
							}
							else
							{
								index_anchor_start = strText.lastIndexOf(prefixAnchor, index);
								index_anchor_start += prefixAnchor.length()+1;
								index_anchor_end = strText.indexOf("\"", index_anchor_start);
								strAnchor = strText.substring (index_anchor_start, index_anchor_end);
								anchorId = Integer.parseInt(strAnchor);//convert the anchor ID from string to int
								section = convertAnchorIdToSection(anchorId);
							}

							if(chapterCounter==0)/*the first is the link*/
							{
								sections += section;
								if(noteIndex < index)/*find in note*/
									listStrAnchor.add(dm.getURL(i, j)+":"+strAnchor);/*if all the results are in notes so the link will be to the first note*/
								else
									listStrAnchor.add(dm.getURL(i, j) + "#" + anchorId);
							}

							else if(lastanchorId != anchorId)
							{
								sections += ","+section;
							}
							globalCounter++;
							chapterCounter++;
							lastanchorId = anchorId;
						}
					}
					if(chapterCounter > 0)
					{
						listBookLocation.add("["+chapterCounter+"] "+dm.getFullNameOfChapter(i,j)+ " {" + sections+ "}");/*only one item in the list per chapter*/
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}			
		}
		TextView textView = new TextView(this);
		textView.setText(query + ": נמצאו "+globalCounter+" תוצאות");
		textView.setTextSize(30);
		searchListView.addHeaderView(textView);

		return true;
	}

	public void showResults()
	{
		ArrayAdapter  adapter;
		adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listBookLocation);
		searchListView.setAdapter(adapter);
	}

	public void onButtonClick(View view){
		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.activity_search_help);
		ImageView imageView = (ImageView) dialog.findViewById(R.id.imgview);
		Button bNext = (Button) dialog.findViewById(R.id.nextBtn);
		Button bprev = (Button) dialog.findViewById(R.id.prevBtn);
		final int[] counter = {1};

		bNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (counter[0] == 1) {
					imageView.setBackground(getResources().getDrawable(R.drawable.srch_help2));
					counter[0] = 2;
				} else if(counter[0] == 2){
					imageView.setBackground(getResources().getDrawable(R.drawable.srch_help3));
					counter[0] = 3;
					bNext.setText("סיום");
				} else {
					dialog.dismiss();
				}
			}
		});


		bprev.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (counter[0]==1){
				} else if (counter[0]==2){
					imageView.setBackground((getResources().getDrawable(R.drawable.srch_help1)));
					counter[0] = 1;
				} else {
					imageView.setBackground(getResources().getDrawable(R.drawable.srch_help2));
					bNext.setText("הבא");
					counter[0] = 2;
				}
			}
		});

		dialog.show();

	}

	public String convertAnchorIdToSection(int Id)
	{

		switch (Id)
		{
		case 98:
		case 99:
		case 100:
		case 0:
			return "כותרת";
		case 101:
			return "פתיחה";
		case 1:
			return "א";
		case 2:
			return "ב";
		case 3:
			return "ג";
		case 4:
			return "ד";
		case 5:
			return "ה";
		case 6:
			return "ו";
		case 7:
			return "ז";
		case 8:
			return "ח";
		case 9:
			return "ט";
		case 10:
			return "י";
		case 11:
			return "יא";
		case 12:
			return "יב";
		case 13:
			return "יג";
		case 14:
			return "יד";
		case 15:
			return "טו";
		case 16:
			return "טז";
		case 17:
			return "יז";
		case 18:
			return "יח";
		case 19:
			return "יט";
		case 20:
			return "כ";
		case 21:
			return "כא";
		case 22:
			return "כב";
		case 23:
			return "כג";
		case 24:
			return "כד";
		case 25:
			return "כה";
		case 26:
			return "כו";
		case 27:
			return "כז";
		case 28:
			return "כח";
		case 29:
			return "כט";
		case 30:
			return "ל";
		case 31:
			return "לא";
		case 32:
			return "לב";
		case 33:
			return "לג";
		case 34:
			return "לד";
		case 35:
			return "לה";
		case 36:
			return "לו";
		case 37:
			return "לז";
		case 38:
			return "לח";
		case 39:
			return "לט";
		case 40:
			return "מ";
		default:
			return "תת";
		}
	}


}
