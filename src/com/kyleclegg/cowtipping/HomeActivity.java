package com.kyleclegg.cowtipping;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class HomeActivity extends Activity {

	/**
	 * Global instantiations
	 */

	MediaPlayer mpBgNoise = null;
	MediaPlayer mpButtonClick = null;
	MediaPlayer mpTweetNoise = null;

	/**
	 * Shared Preferences - will be used for high scores
	 */

	private SharedPreferences mPrefs;

	public static final String FIRST_RUN = "FirstRun";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/**
		 * Create my admobs ad to make some moola
		 */

		// Create the adView with my admob pub id
		AdView adView = new AdView(this, AdSize.BANNER, "a14e24e1c37b214");
		// Lookup your LinearLayout assuming it’s been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout2);
		// Add the adView to it
		layout.addView(adView);
		// Initiate a generic request to load it with an ad
		AdRequest request = new AdRequest();
		adView.loadAd(request);

		/**
		 * Here we will update the prefs to show the HowTo if it's the first
		 * time
		 */

		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = mPrefs.edit();

		boolean firstRun = mPrefs.getBoolean(FIRST_RUN, true);
		if (firstRun) {

			showHowTo();
			editor.putBoolean(FIRST_RUN, false);
			editor.commit();
		}

		/**
		 * Set up MediaPlayer sounds
		 */

		mpButtonClick = MediaPlayer.create(this, R.raw.click);
		mpBgNoise = MediaPlayer.create(this, R.raw.farm_background);
		mpTweetNoise = MediaPlayer.create(this, R.raw.twitter_noise);

		/**
		 * Plays the background noise in a loop
		 */

		mpBgNoise.setLooping(true);

		/**
		 * Set up the play button
		 */

		Button mPlayGameButton = (Button) findViewById(R.id.game_button);
		mPlayGameButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Setting up the button to go to the playactivity class

				final Intent classicGameplay = new Intent(HomeActivity.this,
						PlayActivity.class);
				final Intent frenzyGameplay = new Intent(HomeActivity.this,
						FrenzyActivity.class);

				mpButtonClick.start();

				final CharSequence[] items = { "Classic", "Frenzy" };

				AlertDialog.Builder builder = new AlertDialog.Builder(
						HomeActivity.this);
				builder.setTitle("Select Play Mode");
				builder.setIcon(R.drawable.icon);
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {
							startActivity(classicGameplay);
						} else {
							startActivity(frenzyGameplay);
						}
					}
				});
				AlertDialog alert = builder.create();

				alert.show();

			}
		});

		/**
		 * Set up the how to button
		 */
		ImageButton mQuestionButton = (ImageButton) findViewById(R.id.question_button);
		mQuestionButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showHowTo();
				mpButtonClick.start();
			}
		});

		/**
		 * Set up the awards button
		 */
		ImageButton mObjectivesButton = (ImageButton) findViewById(R.id.award_button);
		mObjectivesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mpButtonClick.start();
				Intent i = new Intent(HomeActivity.this, HighScores.class);
				startActivity(i);
			}
		});

		/**
		 * Set up the tweet button
		 */
		Button mTweetButton = (Button) findViewById(R.id.tweet_button);
		mTweetButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mpTweetNoise.start();
				twitterShare();
			}
		});
	}

	@Override
	protected void onPause() {

		super.onPause();
		mpBgNoise.pause();
	}

	@Override
	protected void onResume() {

		super.onResume();
		mpBgNoise.start();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		mpBgNoise.release();
	}

	@Override
	protected void onStart() {
		// FLURRY ANALYTICS
		super.onStart();
		FlurryAgent.onStartSession(this, "H7G4PL7ZTLCCM2G34BB9");
	}

	@Override
	protected void onStop() {
		// FLURRY ANALYTICS
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

	/**
	 * Creates a menu that is accessible to the user by clicking the menu button
	 * on their device
	 */

	public boolean onCreateOptionsMenu(android.view.Menu menu) {

		super.onCreateOptionsMenu(menu);

		MenuInflater MI = getMenuInflater();
		MI.inflate(R.menu.main_menu, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// COMMENTING OUT THE HIGH SCORES PAGE FOR NOW
		case R.id.menuHighScores:
			startActivity(new Intent("com.kyleclegg.cowtipping.HIGHSCORES"));
			return true;
		case R.id.menuAbout:
			startActivity(new Intent("com.kyleclegg.cowtipping.ABOUT"));
			return true;
		}
		return false;
	}

	/**
	 * Method to be called when showing the How To, which happens 1 - the first
	 * time the app is run and 2 - when the '?' button is clicked
	 */

	private void showHowTo() {

		new AlertDialog.Builder(this).setMessage(R.string.htp_desciption)
				.setTitle(R.string.htp_title).setCancelable(true)
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).show();
	}

	
	/**
	 * Twitter share
	 */
	/**
	 * Method to share results via twitter
	 */
	public void twitterShare() {
		// Twitter share				
		Intent shareIntent = findTwitterClient(); //
		String shareMsgTwitter = "I am a #CowTipping master! Get it on Google Play. #android #cowtipping http://bit.ly/T6dLfD";
		
		if (shareIntent != null) {
			FlurryAgent.logEvent("Results - Shared Successfully");
			shareIntent.putExtra(Intent.EXTRA_TEXT, shareMsgTwitter); //
			startActivity(Intent.createChooser(shareIntent, "Share"));
		}
		else {
			Toast.makeText(getApplicationContext(), "Please Install a Twitter Client in order to Share", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Method to find the device's twitter client returns an intent
	 */
	public Intent findTwitterClient() {
		final String[] twitterApps = {
				// package // name - nb installs (thousands)
				"com.twitter.android", // official - 10 000
				"com.twidroid", // twidroid - 5 000
				"com.handmark.tweetcaster", // Tweecaster - 5 000
				"com.thedeck.android" }; // TweetDeck - 5 000 };
		Intent tweetIntent = new Intent();
		tweetIntent.setType("text/plain");
		final PackageManager packageManager = getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(
				tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

		for (int i = 0; i < twitterApps.length; i++) {
			for (ResolveInfo resolveInfo : list) {
				String p = resolveInfo.activityInfo.packageName;
				if (p != null && p.startsWith(twitterApps[i])) {
					tweetIntent.setPackage(p);
					return tweetIntent;
				}
			}
		}
		return null;

	}
}
