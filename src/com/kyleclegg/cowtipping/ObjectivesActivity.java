package com.kyleclegg.cowtipping;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ObjectivesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.objectives);
		
		/**
		 * Create my admobs ad to make some moola
		 */
		// Create the adView with my admob pub id
	    AdView adView = new AdView(this, AdSize.BANNER, "a14e24e1c37b214");
	    // Lookup your LinearLayout assuming it’s been given
	    // the attribute android:id="@+id/mainLayout"
	    LinearLayout layout = (LinearLayout)findViewById(R.id.objectives_ad_layout);
	    // Add the adView to it
	    layout.addView(adView);
	    // Initiate a generic request to load it with an ad
	    adView.loadAd(new AdRequest());
		
	}

}
