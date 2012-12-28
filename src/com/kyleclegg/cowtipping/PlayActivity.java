package com.kyleclegg.cowtipping;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class PlayActivity extends Activity implements OnClickListener {
	
	/**
	 * Global Initializations
	 */
	
	TextView tvTimer;
	ImageView cow1, cow2, cow3, cow4, cow5, cow6, cow7, cow8, 
				cow9, cow10, cow11, cow12, cow13, cow14;
	ImageView cows[];
	
	int count = 0;
	public static int highScore1;
	public static int highScore2;
	public static int highScore3;
	public static int highScore4;
	public static int highScore5;
	//public static int highestScore = 0;
	int randNumOfClicks = 0;
	int scoreArray[];
	int cowclicks[];
	long timerTimeLeft = 21000;
	
	Map<ImageView, Integer> buttonMap;
	
	MediaPlayer mpCowNoise1;
	MediaPlayer mpCowNoise2;
	MediaPlayer mpCowNoise3;
	MediaPlayer mpCowNoise4;
	MediaPlayer mpCowNoise5;
	
	MediaPlayer mFarmNoise;
	MediaPlayer mMissionImpossible;
	MediaPlayer mpButtonClick;
	
	boolean firstTime = true;
	boolean done = false;
	
	private Animation mRotateAnimation90 = null;
	private Animation mRotateAnimation180 = null;
	private Animation mTranslateAnimationRight = null;
	private Animation mTranslateAnimationLeft = null;
	private Animation mFadeAwayAnimation = null;
	
	ImageButton mBackButton;
	ImageButton mReplayButton;
	ImageView mReplayImageView;
	CountDownTimer myTimer;

	/**
	 * End Initializations
	 */
	
	/***************************** BEGIN METHODS ********************************/
	

	private void addBackButton() {

		mBackButton.setBackgroundDrawable(null);
		mBackButton.setImageResource(R.drawable.back);
	}

	private void addReplayButton() {

		mReplayButton.setVisibility(View.VISIBLE);
	}
	
	private void animate(ImageView cow) {
		
		
		int rand = (int) (Math.random() * 5);
		
		if (rand == 0)
			cow.startAnimation(mRotateAnimation90);
		else if (rand == 1)
			cow.startAnimation(mRotateAnimation180);
		else if (rand == 2)
			cow.startAnimation(mTranslateAnimationRight);
		else if (rand == 3)
			cow.startAnimation(mTranslateAnimationLeft);
		else
			cow.startAnimation(mFadeAwayAnimation);
		
	}
	
	public static int getHighScore(int n) {
		
		int num = 0;
		
		switch (n) {
		case 1:
			num = highScore1;
			break;
		case 2:
			num = highScore2;
			break;
		case 3:
			num = highScore3;
			break;
		case 4:
			num = highScore4;
			break;
		case 5:
			num = highScore5;
			break;
		
		}
		return num;
	}
	
	/**
	   * Random number generating, which will be used to set the number of 
	   * clicks it takes to tip the cow, ranging from 1 to 5
	   */
	
	public int getRandomNumberOfClicks() {
		//Random number of clicks up from 2 to 5 (2,3,4,5)
	    
	    int random = 2 + (int) (Math.random() * 4);
	    
	    return random;
	    
	}
	
	/**
	 * Random number generating, which tells us which cow noise to play
	 * Just so it doesn't get boring to hear the same moo over and over :)
	 * 
	 * Note - was using if - elses here but the app was crashing when you'd 
	 * click extremely fast (at a rate faster than once per 100 ms - only 
	 * possible on computer).  Did some research and found support that 
	 * switch statements with a small range like this
	 * are more time-efficient
	 * 
	 * Follow-up - After adding the switch it appears the number of audio warnings
	 * (write blocked for xx seconds) 
	 */
	
	public void getRandomCowNoise() {
		
		int random = (int) (Math.random() * 5);
		
		switch (random) {
		case 0:
			mpCowNoise1.start();
			break;
		case 1:
			mpCowNoise2.start();
			break;
		case 2:
			mpCowNoise3.start();
			break;
		case 3:
			mpCowNoise4.start();
			break;
		default:
			mpCowNoise5.start();
			break;
		}
		
//		switch (random) {
//		case 0:
//			try {
//			mpCowNoise1.start();
//			}
//			catch (NullPointerException e) {}
//			break;
//		case 1:
//			try {
//			mpCowNoise2.start();
//			}
//			catch (NullPointerException e) {}
//			break;
//		case 2:
//			try {
//			mpCowNoise3.start();
//			}
//			catch (NullPointerException e) {}
//			break;
//		case 3:
//			try { 
//				mpCowNoise4.start();
//			}
//			catch (NullPointerException e) {}
//			
//			break;
//		default:
//			try {
//			mpCowNoise5.start();
//			}
//			catch (NullPointerException e) {}
//			
//			break;
//		}
//		
	}
	
	private void initializeCows() {
		
		/**
		 * Create the cow Image Views which we will use as buttons
		 */
		
		cow1 = (ImageView) findViewById(R.id.imageButton1);
		cow2 = (ImageView) findViewById(R.id.imageButton2);
		cow3 = (ImageView) findViewById(R.id.imageButton3);
		cow4 = (ImageView) findViewById(R.id.imageButton4);
		cow5 = (ImageView) findViewById(R.id.imageButton5);
		cow6 = (ImageView) findViewById(R.id.imageButton6);
		cow7 = (ImageView) findViewById(R.id.imageButton7);
		cow8 = (ImageView) findViewById(R.id.imageButton8);
		cow9 = (ImageView) findViewById(R.id.imageButton9);
		cow10 = (ImageView) findViewById(R.id.imageButton10);
		cow11 = (ImageView) findViewById(R.id.imageButton11);
		cow12 = (ImageView) findViewById(R.id.imageButton12);
		cow13 = (ImageView) findViewById(R.id.imageButton13);
		cow14 = (ImageView) findViewById(R.id.imageButton14);
		
		/**
		 * Create and manually add the ImageViews to an array to be more easily accessible
		 */
		
		cows = new ImageView[14];
		
		cows[0] = cow1;
		cows[1] = cow2;
		cows[2] = cow3;
		cows[3] = cow4;
		cows[4] = cow5;
		cows[5] = cow6;
		cows[6] = cow7;
		cows[7] = cow8;
		cows[8] = cow9;
		cows[9] = cow10;
		cows[10] = cow11;
		cows[11] = cow12;
		cows[12] = cow13;
		cows[13] = cow14;
		
		/**
		 * Create the Map that will be used to track the number of clicks per cow
		 * the map will store an ImageView as the key and an int (num of clicks)
		 * as the value
		 */

		buttonMap = new HashMap<ImageView, Integer>();

		buttonMap.put(cow1, 0);
		buttonMap.put(cow2, 0);
		buttonMap.put(cow3, 0);
		buttonMap.put(cow4, 0);
		buttonMap.put(cow5, 0);
		buttonMap.put(cow6, 0);
		buttonMap.put(cow7, 0);
		buttonMap.put(cow8, 0);
		buttonMap.put(cow9, 0);
		buttonMap.put(cow10, 0);
		buttonMap.put(cow11, 0);
		buttonMap.put(cow12, 0);
		buttonMap.put(cow13, 0);
		buttonMap.put(cow14, 0);
		
		/**
		 * Set up the buttons to listen for clicks
		 */
		
		cow1.setOnClickListener(this);
		cow2.setOnClickListener(this);
		cow3.setOnClickListener(this);
		cow4.setOnClickListener(this);
		cow5.setOnClickListener(this);
		cow6.setOnClickListener(this);
		cow7.setOnClickListener(this);
		cow8.setOnClickListener(this);
		cow9.setOnClickListener(this);
		cow10.setOnClickListener(this);
		cow11.setOnClickListener(this);
		cow12.setOnClickListener(this);
		cow13.setOnClickListener(this);
		cow14.setOnClickListener(this);

		/**
		 * Here we are setting the number of clicks it will take to tip the cow
		 * using the method omNumberofClicks()
		 * Decided to store in an array this time to make it simpler 
		 */

		cowclicks = new int[14];
		
		for (int i=0; i < cowclicks.length; i++) {
			
			cowclicks[i] = getRandomNumberOfClicks();
			
		}
		
		/**
		 *  Lastly, restore any changed images or animations
		 *  Note - this will only apply to re-populated cows
		 */
		
		cow1.setImageResource(R.drawable.cowmarker_small);
		cow2.setImageResource(R.drawable.cowmarker_small);
		cow3.setImageResource(R.drawable.cowmarker_small);
		cow4.setImageResource(R.drawable.cowmarker_small);
		cow5.setImageResource(R.drawable.cowmarker_small);
		cow6.setImageResource(R.drawable.cowmarker_small);
		cow7.setImageResource(R.drawable.cowmarker_small);
		cow8.setImageResource(R.drawable.cowmarker);
		cow9.setImageResource(R.drawable.cowmarker);
		cow10.setImageResource(R.drawable.cowmarker);
		cow11.setImageResource(R.drawable.cowmarker);
		cow12.setImageResource(R.drawable.cowmarker);
		cow13.setImageResource(R.drawable.cowmarker);
		cow14.setImageResource(R.drawable.cowmarker);
		
	}
	
	
	@Override
	public void onClick(View v) {
		
		/**
		 * Check to make sure timer is still active
		 */
		
		if (done) {
			return;
		}
		
		// VISIBILITY OF BUTTONS 0 = visible // 4 = invisible
		/**
		 * When cows are clicked we come here, where we can perform different
		 * tasks based on the cow clicked and the number of times
		 * it has been clicked
		 * 
		 * Each switch case has 5 parts
		 * 1 - Increase the value by one to track the number of clicks
		 * 2 - If the number of clicks = random number - 1 then change image
		 * 2 - If the number of clicks = random number then...3,4,5
		 * 3 - Make a mooing noise
		 * 4 - Animate the tipping of the cow
		 * 5 - Set Visibility to 0
		 * 6 - Increase the count
		 * 7 - Populate new cows
		 * 
		 * Note - images 1-7 are smaller than 8-14
		 */
		
		
		switch (v.getId()) {
		case R.id.imageButton1:
			buttonMap.put(cow1, buttonMap.get(cow1) + 1);
			
			if (buttonMap.get(cow1) == cowclicks[0]-1) {
				cow1.setImageResource(R.drawable.cowmarker5_small);
			}
			if (buttonMap.get(cow1) == cowclicks[0]) {
				getRandomCowNoise();
				animate(cow1);
				cow1.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton2:
			buttonMap.put(cow2, buttonMap.get(cow2) + 1);
			
			if (buttonMap.get(cow2) == cowclicks[1]-1) {
				cow2.setImageResource(R.drawable.cowmarker5_small);
			}
			if (buttonMap.get(cow2) == cowclicks[1]) {
				getRandomCowNoise();
				animate(cow2);
				cow2.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton3:
			buttonMap.put(cow3, buttonMap.get(cow3) + 1);
			
			if (buttonMap.get(cow3) == cowclicks[2]-1) {
				cow3.setImageResource(R.drawable.cowmarker5_small);
			}
			
			if (buttonMap.get(cow3) == cowclicks[2]) {
				getRandomCowNoise();
				animate(cow3);
				cow3.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton4:
			buttonMap.put(cow4, buttonMap.get(cow4) + 1);
			
			if (buttonMap.get(cow4) == cowclicks[3]-1) {
				cow4.setImageResource(R.drawable.cowmarker5_small);
			}
			
			if (buttonMap.get(cow4) == cowclicks[3]) {
				getRandomCowNoise();
				animate(cow4);
				cow4.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton5:
			buttonMap.put(cow5, buttonMap.get(cow5) + 1);
			
			if (buttonMap.get(cow5) == cowclicks[4]-1) {
				cow5.setImageResource(R.drawable.cowmarker5_small);
			}
			if (buttonMap.get(cow5) == cowclicks[4]) {
				getRandomCowNoise();
				animate(cow5);
				cow5.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton6:
			buttonMap.put(cow6, buttonMap.get(cow6) + 1);
			
			if (buttonMap.get(cow6) == cowclicks[5]-1) {
				cow6.setImageResource(R.drawable.cowmarker5_small);
			}
			if (buttonMap.get(cow6) == cowclicks[5]) {
				getRandomCowNoise();
				animate(cow6);
				cow6.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton7:
			buttonMap.put(cow7, buttonMap.get(cow7) + 1);
			
			if (buttonMap.get(cow7) == cowclicks[6]-1) {
				cow7.setImageResource(R.drawable.cowmarker5_small);
			}
			if (buttonMap.get(cow7) == cowclicks[6]) {
				getRandomCowNoise();
				animate(cow7);
				cow7.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton8:
			buttonMap.put(cow8, buttonMap.get(cow8) + 1);
			
			if (buttonMap.get(cow8) == cowclicks[7]-1) {
				cow8.setImageResource(R.drawable.cowmarker5);
			}
			if (buttonMap.get(cow8) == cowclicks[7]) {
				getRandomCowNoise();
				animate(cow8);
				cow8.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton9:
			buttonMap.put(cow9, buttonMap.get(cow9) + 1);
			
			if (buttonMap.get(cow9) == cowclicks[8]-1) {
				cow9.setImageResource(R.drawable.cowmarker5);
			}
			if (buttonMap.get(cow9) == cowclicks[8]) {
				getRandomCowNoise();
				animate(cow9);
				cow9.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton10:
			buttonMap.put(cow10, buttonMap.get(cow10) + 1);
			
			if (buttonMap.get(cow10) == cowclicks[9]-1) {
				cow10.setImageResource(R.drawable.cowmarker5);
			}
			if (buttonMap.get(cow10) == cowclicks[9]) {
				getRandomCowNoise();
				animate(cow10);
				cow10.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton11:
			buttonMap.put(cow11, buttonMap.get(cow11) + 1);
			
			if (buttonMap.get(cow11) == cowclicks[10]-1) {
				cow11.setImageResource(R.drawable.cowmarker5);
			}
			if (buttonMap.get(cow11) == cowclicks[10]) {
				getRandomCowNoise();
				animate(cow11);
				cow11.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton12:
			buttonMap.put(cow12, buttonMap.get(cow12) + 1);
			
			if (buttonMap.get(cow12) == cowclicks[11]-1) {
				cow12.setImageResource(R.drawable.cowmarker5);
			}
			if (buttonMap.get(cow12) == cowclicks[11]) {
				getRandomCowNoise();
				animate(cow12);
				cow12.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton13:
			buttonMap.put(cow13, buttonMap.get(cow13) + 1);
			
			if (buttonMap.get(cow13) == cowclicks[12]-1) {
				cow13.setImageResource(R.drawable.cowmarker5);
			}
			if (buttonMap.get(cow13) == cowclicks[12]) {
				getRandomCowNoise();
				animate(cow13);
				cow13.setVisibility(4);				
				count++;
				populate();
			}
			break;
		case R.id.imageButton14:
			buttonMap.put(cow14, buttonMap.get(cow14) + 1);
			
			if (buttonMap.get(cow14) == cowclicks[13]-1) {
				cow14.setImageResource(R.drawable.cowmarker5);
			}
			if (buttonMap.get(cow14) == cowclicks[13]) {
				getRandomCowNoise();
				animate(cow14);
				cow14.setVisibility(4);				
				count++;
				populate();
			}
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/**
		 * This is what the activity does upon being created. 
		 */
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		

		/**
		 * Create my admobs ad to pay the bills
		 */
		
		// Create the adView with my admob pub id
		// Android: a14e24e1c37b214
		// Amazon: a150dd3f5994b69
		
	    AdView adView = new AdView(this, AdSize.BANNER, "a150dd3f5994b69");
	    // Lookup your LinearLayout assuming it’s been given
	    // the attribute android:id="@+id/mainLayout"
	    LinearLayout layout = (LinearLayout)findViewById(R.id.playLayout);
	    // Add the adView to it
	    layout.addView(adView);
	    // Initiate a generic request to load it with an ad
	    adView.loadAd(new AdRequest());
		
		
		/**
		 * Begin background farm and game noises
		 * The farm noises were found on a free mp3 sites and are public domains
		 * Mission Impossible - Ragtime was also found on a free mp3 site
		 */
		
		mFarmNoise = MediaPlayer.create(this, R.raw.farm_background);
		mMissionImpossible = MediaPlayer.create(this, R.raw.mi_ragtime);
		mpButtonClick = MediaPlayer.create(this, R.raw.click);
		mFarmNoise.setLooping(true);
		//mMissionImpossible.start();
		
		
		/**
		 * Animation creations
		 */
		
		mRotateAnimation90 = AnimationUtils.loadAnimation(this, R.anim.rotate_90);
		mRotateAnimation180 = AnimationUtils.loadAnimation(this, R.anim.rotate_180);
		mTranslateAnimationRight = AnimationUtils.loadAnimation(this, R.anim.translate_right);
		mTranslateAnimationLeft = AnimationUtils.loadAnimation(this, R.anim.translate_left);
		mFadeAwayAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_away);
		
		/**
		 * Set up the back to button
		 */
		
		mBackButton = (ImageButton) findViewById(R.id.back_button);
		mBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (done) {
					mpButtonClick.start();
					finish();
				}
			}
		});
		
		/**
		 * Set up the replay button
		 */
		mReplayImageView = (ImageView) findViewById(R.id.replay_imageview);
		mReplayButton = (ImageButton) findViewById(R.id.replay_button);
		mReplayButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (done) {
					mpButtonClick.start();
					
					mReplayButton.setVisibility(View.GONE);
					
					RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

					mReplayImageView.setVisibility(View.VISIBLE);
					mReplayImageView.setAnimation(rotate);
					rotate.setDuration( 500 );
					
					rotate.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							finish();
							Intent intent = new Intent(PlayActivity.this, PlayActivity.class);
							startActivity(intent);
						}
					});
					rotate.start();
				}
			}
		});
		
		/**
		 * Create the timer Text View
		 */
		
		tvTimer = (TextView) findViewById(R.id.tvTimer);
		
		/**
		 * Created a method called initialize cows to handle all cow creations
		 * In initializeCows we:
		 * 1 - Create ImageViews and assign them to our images
		 * 2 - Add to an array
		 * 3 - Create a Map to track number of clicks
		 * 4 - Set a listener for all s
		 * 5 - Get a random number of clicks for each 
		 */
		
		initializeCows();

		/**
		 * Set up cow noises
		 */
		
		mpCowNoise1 = MediaPlayer.create(this, R.raw.calfmoo);
		mpCowNoise2 = MediaPlayer.create(this, R.raw.cow);
		mpCowNoise3 = MediaPlayer.create(this, R.raw.moo_a);
		mpCowNoise4 = MediaPlayer.create(this, R.raw.cow_moo1);
		mpCowNoise5 = MediaPlayer.create(this, R.raw.cow_moo2);

		/**
		 * Define what's showing and what isn't when the activity is created.
		 * First, even though we've created all the cows we'll start by showing none
		 * Then, we'll populate the cows randomly using the populateMany() call
		 */
		
		
		for (int i=0; i<cows.length; i++) {
			cows[i].setVisibility(4);
		}
		populateMany();
		
	}

	/**
	 * onDestroy() is where we will handle the app terminating before expected
	 */
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mFarmNoise.release();
		mMissionImpossible.release();
		
		mpCowNoise1.release();
		mpCowNoise2.release();
		mpCowNoise3.release();
		mpCowNoise4.release();
		mpCowNoise5.release();
	}

	/**
	 * onPause!!!!!
	 */
	
	@Override
	public void onPause()
	{
//	    if(this.timerTimeLeft > 0) {
//	        this.myTimer.cancel();
//	    }

		//myTimer.onFinish();
		myTimer.cancel();
		mFarmNoise.stop();
		mMissionImpossible.stop();
		
		


	    super.onPause();
	    
	    
	    
	    try {
	    
	    	if (mFarmNoise.isPlaying()) {
	    		mFarmNoise.pause();
	    	}
//	    	if (mMissionImpossible.isPlaying()) {
//	    		mMissionImpossible.release();
//	    	}
	    }
	    catch (NullPointerException e) {
	    	Log.d("ONPAUSE", "Could not pause media (probably because it is no longer playing)");
	    }

	}

	/**
	 * onResume
	 */
	
	@Override
	public void onResume()
	{
		/**
		 * Start the music!
		 */
			    
		mMissionImpossible.start();
		
    	/**
		 * Create timer to countdown activity, decided that it should go in onResume
		 */
		
        Log.d("Resuming", String.format("Resuming with %d", this.timerTimeLeft));			
	    
        if(this.timerTimeLeft > 0) {

	        //create a toast to send as a hint if needed - see ~20 lines below
	        final Toast hint = Toast.makeText(this, "HINT: Tap a cow repeatedly until it moos!", Toast.LENGTH_SHORT);

        	
	    	myTimer = new CountDownTimer(timerTimeLeft, 1000) {


				public void onTick(long msUntilFinished) {
			         tvTimer.setText("" + msUntilFinished / 1000);
			         timerTimeLeft = (int) (msUntilFinished / 1000);

					 //check to see if user has tipped two cows in 5 seconds
			         // if not, shows a hint
		        	
			         if (timerTimeLeft == 15 && count < 2) {
			        	 
			        	 hint.show();
			         }
			     }

			     public void onFinish() {
			    	 
			    	 addBackButton();
			    	 addReplayButton();
			    	 tvTimer.setText("");
			    	 //mBackButton.setImageDrawable(R.drawable.back);
			    	 
			    	 
			    	 /**
			 		 * Set done flag to true (used to turn off clickable cows
			 		 */
			 		done = true;
			 		
			 			
			 		/**
			 		 * Send a short vibrate
			 		 */
			    	 	Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			    	 	v.vibrate(300);
			 		
			 		/**
			 		 * End music
			 		 */
			    	 
			 		mMissionImpossible.stop();
			    	
			 		// set high scores
			 		setHighScores();
			    	 
			     }


				
				
			  }.start();
	    }
	    super.onResume();
	}

	private void setHighScores() {
		
		/**
		 * First, toast the high score!
		 */
		
		if (count == 0) {
	   		Toast cowsTipped = Toast.makeText(this, "Done! " + count + " cows tipped!", Toast.LENGTH_SHORT);
	   		cowsTipped.show();
	   		//extra toast for tipping zero
	   		Toast zeroTipped = Toast.makeText(this, "Yikes! Better luck next time!", Toast.LENGTH_SHORT);
	   		zeroTipped.show();
	   	}
		else if (count < 2) {
	   		Toast cowsTipped = Toast.makeText(this, "Done! " + count + " cow tipped!", Toast.LENGTH_SHORT);
	   		cowsTipped.show();
	   		Toast oneTipped = Toast.makeText(this, "You can do better!", Toast.LENGTH_SHORT);
	   		oneTipped.show();
	   	}
		else if (count < 4) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast twoTipped = Toast.makeText(this, "Try again. You can tip more than that!", Toast.LENGTH_SHORT);
			 twoTipped.show();
		}
		else if (count < 6) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast lessThanFiveTipped = Toast.makeText(this, "Not bad, now let's see some real tippin'!", Toast.LENGTH_SHORT);
			 lessThanFiveTipped.show();
		}
		else if (count < 8) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast lessThanEightTipped = Toast.makeText(this, "Pretty good, for an amateur!", Toast.LENGTH_SHORT);
			 lessThanEightTipped.show();
		}
		else if (count < 10) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast lessThanTenTipped = Toast.makeText(this, "Keep up the good work!", Toast.LENGTH_SHORT);
			 lessThanTenTipped.show();
		}
		else if (count < 12) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast lessThanTwelveTipped = Toast.makeText(this, "You are the bomb!", Toast.LENGTH_SHORT);
			 lessThanTwelveTipped.show();
		}
		else if (count < 14) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast lessThanFourteenTipped = Toast.makeText(this, "Call the sheriff we have a cow tipper on the loose!", Toast.LENGTH_SHORT);
			 lessThanFourteenTipped.show();
		}
		else if (count < 16) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast lessThanSixteenTipped = Toast.makeText(this, "Very impressive!", Toast.LENGTH_SHORT);
			 lessThanSixteenTipped.show();
		}
		else if (count < 20) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast lessThanTwentyTipped = Toast.makeText(this, "Holy Shnikeys! You're awesome!", Toast.LENGTH_SHORT);
			 lessThanTwentyTipped.show();
		}
		else if (count < 24) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast lessThanTwentyFourTipped = Toast.makeText(this, "All hail the Cow Tipper!", Toast.LENGTH_SHORT);
			 lessThanTwentyFourTipped.show();
		}
		else if (count < 28) {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast lessThanTwentyFourTipped = Toast.makeText(this, "You are THE master cow tipper", Toast.LENGTH_SHORT);
			 lessThanTwentyFourTipped.show();
		}
		else {
			 Toast cowsTipped = Toast.makeText(this, count + " cows tipped!", Toast.LENGTH_SHORT);
			 cowsTipped.show();
			 Toast aLotTipped = Toast.makeText(this, "Unbelieveable!", Toast.LENGTH_SHORT);
			 aLotTipped.show();
		}
		 
		/**
		 * Save top 5 high scores as preferences.
		 * Here, check to see if this is the highest score, if yes congratulate and update!
		 * If top 5, save
		 */
	   	 
	   	 if (count > highScore1) {
	   		Toast highScore = Toast.makeText(this, "New High Score!", Toast.LENGTH_SHORT);
   		 	highScore.show();
	   		
   		 	highScore5 = highScore4;
   		 	highScore4 = highScore3;
   		 	highScore3 = highScore2;
   		 	highScore2 = highScore1;
   		 	highScore1 = count;
   		 	
	   	 }
	   	 else if (count >= highScore2) {
	   		 
	   		highScore5 = highScore4;
   		 	highScore4 = highScore3;
   		 	highScore3 = highScore2;
   		 	highScore2 = count;
	   		 
	   	 }
	   	else if (count >= highScore3) {

	   		highScore5 = highScore4;
   		 	highScore4 = highScore3;
   		 	highScore3 = count;
	   	}
	   	else if (count >= highScore4) {
	   		 
	   		highScore5 = highScore4;
   		 	highScore4 = count;
	   	 }
	   	else if (count >= highScore5) {
	   		 highScore5 = count;
	   	 }
	}
	
	@Override
	protected void onRestart() {
		
		super.onRestart();
		
		/**
		 * Start(resume) background noise
		 */
		
	}
	
	

	/**
	 * onStart
	 */
	
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
	
	private void populate() {
		/**
		 * Populate new cows
		 */
		
		int numberOfVisibleCows = 0;
		
		for (int i=0; i < cows.length; i++) {
			if (cows[i].getVisibility() == 0) {
				numberOfVisibleCows++;
			}
		}
		
		if (numberOfVisibleCows < 3) {
			populateSome();
		}
		
		if (numberOfVisibleCows < 5) {
			populateFew();
		}
	}


	/**
	 * Populate many will be called in onCreate and will populate
	 * the field with many cows ready to be tipped
	 * It's okay to just set visibility here
	 */
	
	private void populateMany() {
		
		int rand = ((int) (Math.random() * 5));
		
		if (rand == 0) {
			
			for (int i=0; i<cows.length; i++) {
				
				// 1 in 5 chance about half the cows will show
				if ((int) (Math.random() * 2) == 0) {
					cows[i].setVisibility(0);
				}
				
			}

		}
		else if  (rand == 1 || rand == 2) {

			for (int i=0; i<cows.length; i++) {
				
				// 2 in 5 chance about one-third the cows will show
				if ((int) (Math.random() * 3) == 0) {
					cows[i].setVisibility(0);
				}
				
			}
		}
		else if (rand == 3 || rand == 4) {
			
			for (int i=0; i<cows.length; i++) {
				
				// 2 in 5 chance about one-quarter the cows will show
				if ((int) (Math.random() * 4) == 0) {
					cows[i].setVisibility(0);
				}
			}
		}
		//base case in the event none show up (must have at least one)
		cows[(int) (Math.random() * 14)].setVisibility(0);
		
	}

	/**
	 * PopulateSome will be called when there are less that 3 cows left on the field
	 * Must create new cows, not just set to visible
	 */
	private void populateSome() {
		
		initializeCows();
		
		int rand = ((int) (Math.random() * 3)); 
		
		if (rand == 0) {
			// populate one
			cows[(int) (Math.random() * 14)].setVisibility(0);
		}
		else if (rand == 1) {
			//populate up to 2
			cows[((int) (Math.random() * 14))].setVisibility(0);
			cows[((int) (Math.random() * 14))].setVisibility(0);
		}
		else if  (rand == 2) {
			//populate up to 3
			cows[((int) (Math.random() * 14))].setVisibility(0);
			cows[((int) (Math.random() * 14))].setVisibility(0);
			cows[((int) (Math.random() * 14))].setVisibility(0);
		}
		
	}

	/**
	 * PopulateFew will be called when there are less than 5 cows on the field
	 */
	
	private void populateFew() {
		
		initializeCows();
		
		if (((int) (Math.random() * 3) == 0)) {
			
			// populate one
			cows[((int) (Math.random() * 14))].setVisibility(0);
				
		}
		else if  (((int) (Math.random() * 3) == 1)) {
			//populate up to 2
			cows[((int) (Math.random() * 14))].setVisibility(0);
			cows[((int) (Math.random() * 14))].setVisibility(0);
		}		
	}
	
}
