package com.JK.mikraey_kodesh;

import android.content.ActivityNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class About extends Activity implements View.OnClickListener
{

	TextView linkToPurchase;
	TextView linkyakov;
	TextView linkrafi;
	TextView linkyoav;
	LinkMovementMethod linkMethod;
	ImageView topFrame, bottomFrame, textImage;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		linkToPurchase = (TextView) findViewById(R.id.tvLinkToBuy);
		linkToPurchase.setMovementMethod(LinkMovementMethod.getInstance());
		linkToPurchase.setLinkTextColor(Color.BLUE);

		linkMethod = new LinkMovementMethod(){
			String emailAdress;
			@Override
			public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
				switch (widget.getId()){
					case R.id.yakovLink:
						emailAdress = "yakov9k@gmail.com";
						break;
					case R.id.janLink:
						emailAdress = "rafraph@gmail.com";
						break;
					case R.id.yoavLink:
						emailAdress = "yoavsc4@gmail.com";
						break;
				}
				int action = event.getAction();
				if (action == MotionEvent.ACTION_UP){
					Intent emailIntent = new Intent (Intent.ACTION_SEND);
					emailIntent.setType("message/rfc822");
					emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"to ", emailAdress});
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
					emailIntent.putExtra(Intent.EXTRA_TEXT, "");
					try {
						startActivity(Intent.createChooser(emailIntent, "בחר אפליקציית מייל"));
					} catch (ActivityNotFoundException e){
						Toast.makeText(About.this,"לא מותקנת אפליקציה לשליחת מייל?", Toast.LENGTH_SHORT).show();
					}
					return true;
				}
				return super.onTouchEvent(widget, buffer, event);
			}
		};

		linkyakov = (TextView) findViewById(R.id.yakovLink);
		linkrafi = (TextView) findViewById(R.id.janLink);
		linkyoav = (TextView) findViewById(R.id.yoavLink);

		linkyakov.setMovementMethod(linkMethod);
		linkrafi.setMovementMethod(linkMethod);
		linkyoav.setMovementMethod(linkMethod);

		linkyakov.setLinkTextColor(Color.BLACK);
		linkrafi.setLinkTextColor(Color.BLACK);
		linkyoav.setLinkTextColor(Color.BLACK);

		topFrame = findViewById(R.id.top_frame);
		bottomFrame = findViewById(R.id.bottom_frame);
		textImage = findViewById(R.id.about_text);




		/*version*/
		String myVersionName = "not available"; // initialize String

		Context context = getApplicationContext(); // or activity.getApplicationContext()
		PackageManager packageManager = context.getPackageManager();
		String packageName = context.getPackageName();

		try 
		{
		    myVersionName = "גירסה: " + packageManager.getPackageInfo(packageName, 0).versionName;
		} 
		catch (PackageManager.NameNotFoundException e) 
		{
		    e.printStackTrace();
		}
		TextView tvVersion = (TextView) findViewById(R.id.textViewVersion);
		tvVersion.setText(myVersionName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
	
		Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://shop.yhb.org.il/"));
        startActivity(intent);
	}
}
