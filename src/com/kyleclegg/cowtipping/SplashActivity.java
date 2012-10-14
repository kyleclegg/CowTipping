package com.kyleclegg.cowtipping;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class SplashActivity extends Activity {

	MediaPlayer mpSplash;
	private Thread SplashThread;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        /**
         * Begin the App with splash screen
         */
        
        mpSplash = MediaPlayer.create(this, R.raw.matches_2sec);
        mpSplash.start();
        
        SplashThread = new Thread() {
        	public void run() {
        		try {
        			int splashTimer = 0;
        			while (splashTimer < 1500) {
        				sleep(100);
        				splashTimer = splashTimer + 100;
        			}
        			startActivity(new Intent("com.kyleclegg.cowtipping.SPLASH"));
        		} catch (InterruptedException e) {
					Log.d("SplashActivity", "Splash Timer Error");
					e.printStackTrace();
				}

        		finally {
        			finish();  //ends the entire SplashActivity class
        		}
        		
        	}
        };
        SplashThread.start();
        
    }
    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(SplashThread){
                SplashThread.notifyAll();
            }
        }
        return true;
    }   
}
