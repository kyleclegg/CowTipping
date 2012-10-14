package com.kyleclegg.cowtipping;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class HighScores extends Activity {

	/**
	 * Global initializations
	 */
	
	TextView mScore1, mScore2, mScore3, mScore4, mScore5;
	
	/**
	 * Shared Preferences - will be used to keep track of names and  high scores
	 * 
	 */ 
	
	private SharedPreferences mPrefs;
	
	public static final String PREF_SCORE1 = "";
	public static final String PREF_SCORE2 = "";
	public static final String PREF_SCORE3 = "";
	public static final String PREF_SCORE4 = "";
	public static final String PREF_SCORE5 = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_scores);
		
		/**
		 * Connect the Views of Buttons to the UI
		 */
		
		mScore1 = (TextView) findViewById(R.id.tvScore1);
		mScore2 = (TextView) findViewById(R.id.tvScore2);
		mScore3 = (TextView) findViewById(R.id.tvScore3);
		mScore4 = (TextView) findViewById(R.id.tvScore4);
		mScore5 = (TextView) findViewById(R.id.tvScore5);
		

		/**
		 * Here we will update the prefs to show the HowTo if it's the first time
		 */
		
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPrefs.edit();
		
		
		/**
		 * Get highest score from PlayActivity and set it to display
		 * 
		 * Using preferences to create a high scores table 
		 * of the five highest scores ever
		 * 
		 */
		
		if (PlayActivity.getHighScore(1) != 0) {
			mScore1.setText(Integer.toString(PlayActivity.getHighScore(1)));
			editor.putInt(PREF_SCORE1, PlayActivity.getHighScore(1));
            editor.commit();
		}
		if (PlayActivity.getHighScore(2) != 0) {
			mScore2.setText(Integer.toString(PlayActivity.getHighScore(2)));
			editor.putInt(PREF_SCORE2, PlayActivity.getHighScore(2));
            editor.commit();
		}
		if (PlayActivity.getHighScore(3) != 0) {
			mScore3.setText(Integer.toString(PlayActivity.getHighScore(3)));
			editor.putInt(PREF_SCORE3, PlayActivity.getHighScore(3));
            editor.commit();
		}
		if (PlayActivity.getHighScore(4) != 0) {
			mScore4.setText(Integer.toString(PlayActivity.getHighScore(4)));
			editor.putInt(PREF_SCORE4, PlayActivity.getHighScore(4));
            editor.commit();
		}
		if (PlayActivity.getHighScore(5) != 0) {
			mScore5.setText(Integer.toString(PlayActivity.getHighScore(5)));
			editor.putInt(PREF_SCORE5, PlayActivity.getHighScore(5));
	        editor.commit();
		}
		
	}
}
