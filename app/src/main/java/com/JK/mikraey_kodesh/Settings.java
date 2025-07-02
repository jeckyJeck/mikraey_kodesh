package com.JK.mikraey_kodesh;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends AppCompatActivity
{
	public static final String PREFS_NAME = "MyPrefsFile";
	static SharedPreferences mPrefs;
	SharedPreferences.Editor shPrefEditor;
	Switch sBlackBackground, sSleepScreen, /*sFullScreen,*/
			sAssistButtons, sLastLocation, sNotesState, sVolumeScroll;
	ActionBar actionBar;
	TextView tvDarkMode;
	boolean isFdbkVisible;
	LinearLayout fdbkInnerLayout;

    private static final int HEBREW	 = 0;
    private static final int ENGLISH = 1;
    private static final int RUSSIAN = 2;
    private static final int SPANISH = 3;
    private static final int FRENCH = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mPrefs = getSharedPreferences(PREFS_NAME, 0);
		shPrefEditor = mPrefs.edit();
		if (mPrefs.getInt("BlackBackground", 0) == 1){
			setTheme(R.style.AppThemeDark);
		}
		setContentView(R.layout.activity_settings);

		sSleepScreen = (Switch) findViewById(R.id.switchSleepScreen);
		sBlackBackground = (Switch) findViewById(R.id.switchDarkMode);
		sAssistButtons = (Switch) findViewById(R.id.switchAssistButtons);
		sLastLocation = (Switch) findViewById(R.id.switchLastLocation);
		sNotesState = (Switch) findViewById(R.id.switchNotesState);
		sVolumeScroll =  (Switch) findViewById(R.id.switchVolumeScroll);

		fdbkInnerLayout = (LinearLayout) findViewById(R.id.fdbkInnerLayout);


		sSleepScreen.setChecked(mPrefs.getInt("SleepScreen", 1) == 1);
		sBlackBackground.setChecked(mPrefs.getInt("BlackBackground", 0) == 1);
		sNotesState.setChecked(mPrefs.getInt("notesState", 0) == 1);
		sAssistButtons.setChecked(mPrefs.getInt("sAssistButtons", 1) == 1);
		sVolumeScroll.setChecked(mPrefs.getInt("sVolumeScroll", 1) == 1);
		sLastLocation.setChecked(mPrefs.getInt("StartInLastLocation", 0) == 1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_with_back_icon, menu);
//		actionBar = getSupportActionBar();
//		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_theme_color));

		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		if (item.getItemId() == R.id.settings_back)
			finish();
	return true;
	}

	public void onClickSleepScreen(View view){
		if (view.getId() != R.id.switchSleepScreen)
			sSleepScreen.setChecked(!sSleepScreen.isChecked());
		if (sSleepScreen.isChecked())
			shPrefEditor.putInt("SleepScreen", 1);
		else
			shPrefEditor.putInt("SleepScreen", 0);
		shPrefEditor.commit();
	}
	public void onClickBlackBackground(View view){
		if (view.getId() != R.id.switchDarkMode)
			sBlackBackground.setChecked(!sBlackBackground.isChecked());
		if (sBlackBackground.isChecked()) {
			shPrefEditor.putInt("BlackBackground", 1);
		}
		else {
			shPrefEditor.putInt("BlackBackground", 0);
		}
		shPrefEditor.commit();
		finish();
		startActivity(getIntent());

	}

	public void onClickAssistButtons(View view){
		if (view.getId() != R.id.switchAssistButtons)
			sAssistButtons.setChecked(!sAssistButtons.isChecked());
		if (sAssistButtons.isChecked())
			shPrefEditor.putInt("sAssistButtons", 1);
		else
			shPrefEditor.putInt("sAssistButtons", 0);
		shPrefEditor.commit();
	}
	public void onClickVolumeScroll(View view){
		if (view.getId() != R.id.switchVolumeScroll)
			sVolumeScroll.setChecked(!sVolumeScroll.isChecked());
		if (sVolumeScroll.isChecked())
			shPrefEditor.putInt("sVolumeScroll", 1);
		else
			shPrefEditor.putInt("sVolumeScroll", 0);
		shPrefEditor.commit();
	}

	public void onClickLastLocation(View view){
		if (view.getId() != R.id.switchLastLocation)
			sLastLocation.setChecked(!sLastLocation.isChecked());
		if (sLastLocation.isChecked())
			shPrefEditor.putInt("StartInLastLocation", 1);
		else
			shPrefEditor.putInt("StartInLastLocation", 0);
		shPrefEditor.commit();
	}
	public void onClickNoteState(View view){
		if (view.getId() != R.id.switchNotesState)
			sNotesState.setChecked(!sNotesState.isChecked());
		if (sNotesState.isChecked())
			shPrefEditor.putInt("notesState", 1);
		else
			shPrefEditor.putInt("notesState", 0);
		shPrefEditor.commit();
	}
	public void onClickFeedback(View view){
		if (isFdbkVisible) {
			fdbkInnerLayout.setVisibility(View.GONE);
			isFdbkVisible = false;
		}
		else {
			fdbkInnerLayout.setVisibility(View.VISIBLE);
			isFdbkVisible = true;
		}
	}
	public void onClickAbout(View view){
		try{
			Class ourClass = Class.forName("com.JK.mikraey_kodesh.About");
			Intent ourIntent = new Intent(Settings.this, ourClass);
			startActivity(ourIntent);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	public void onClickBugBtn(View view){
		Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/C7VxR8St5sFBAQFb9"));
		startActivity(intent);
	}

	public void onClickFdbkBtn(View view){
		String emailaddress = "", header = "";
		switch (view.getId()){
			case R.id.appFdbkBtn:
				emailaddress = "yakov9k@gmail.com";
				header = "בנוגע לאפליקציית מקראי קודש";
				break;
			case R.id.bookFdbkBtn:
				emailaddress = "mikraeikodsh1@gmail.com";
				header = "פניה בנוגע לתוכן הספרים";
				break;
			default:
				emailaddress = "yakov9k@gmail.com";
				header = "בנוגע לאפליקציית מקראי קודש";
		}
		Intent emailIntent = new Intent (Intent.ACTION_SEND);
		emailIntent.setType("message/rfc822");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to ", emailaddress});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, header);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
		try {
			startActivity(Intent.createChooser(emailIntent, "בחר אפליקציית מייל"));
		} catch (ActivityNotFoundException e){
			Toast.makeText(this,"לא מותקנת אפליקציה לשליחת מייל?", Toast.LENGTH_SHORT).show();
		}


	}


	public void onClickSwitch(View view)
	{
		
		//shPrefEditor.putInt("SleepScreen", 1);
	    // Is the view now checked?
	    boolean checked = ((Switch) view).isChecked();
	    
	    // Check which checkbox was clicked
	    switch(view.getId()) 
	    {
			case R.id.switchSleepScreen:
	            if (checked)
	            	shPrefEditor.putInt("SleepScreen", 1);
	            else
	            	shPrefEditor.putInt("SleepScreen", 0);
	            break;
			case R.id.switchDarkMode:
	            if (checked)
	            	shPrefEditor.putInt("BlackBackground", 1);
	            else
	            	shPrefEditor.putInt("BlackBackground", 0);
	            break;
			case R.id.switchNotesState:
				if (checked)
					shPrefEditor.putInt("notesState", 1);
				else
					shPrefEditor.putInt("notesState", 0);
				break;
//
			case R.id.switchAssistButtons:
	            if (checked)
	            	shPrefEditor.putInt("sAssistButtons", 1);
	            else
	            	shPrefEditor.putInt("sAssistButtons", 0);
	            break;
			case R.id.switchLastLocation:
				if (checked)
					shPrefEditor.putInt("StartInLastLocation", 1);
				else
					shPrefEditor.putInt("StartInLastLocation", 0);
				break;
	    }
	    shPrefEditor.commit();
	}

//	public void ChangeTextLengauge(int language)
//	{
//        if(language == ENGLISH) {
//            sSleepScreen.setText("Cancel monitor sleep");
//            sBlackBackground.setText("Black background");
//            //cbFullScreen.setText("Don't remove buttons in full screen mode");
//            sAssistButtons.setText("Locate the buttons in the bottom part of the screen");
//            sLastLocation.setText("Jump to the last location when application start");
//        }
//        else if(language == RUSSIAN) {
//            sSleepScreen.setText("");
//            sBlackBackground.setText("Чёрный фон");
//            //cbFullScreen.setText("");
//            sAssistButtons.setText("");
//            sLastLocation.setText("");
//        }
//        else if(language == SPANISH) {
//            sSleepScreen.setText("Cancelar el sueño del monitor");
//            sBlackBackground.setText("Fondo negro");
//            //cbFullScreen.setText("No remover los botones en modo de pantalla entera");
//            sAssistButtons.setText("Localiza los botones en la parte baja de la pantalla");
//            sLastLocation.setText("Saltar a la ultima locacion cuando la aplicacion comience");
//        }
//        else if(language == FRENCH  ) {
//            sSleepScreen.setText("Annuler mode veille");
//            sBlackBackground.setText("Fond noir");
//            //cbFullScreen.setText("Ne pas retirer les boutons en mode plein écran");
//            sAssistButtons.setText("Placer les boutons dans la partie inférieure de l'écran");
//            sLastLocation.setText("Au demarrage revenir a la precedente location");
//        }
//    }
}
