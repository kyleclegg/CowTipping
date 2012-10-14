package com.kyleclegg.cowtipping;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {

	/**
	 * The AboutActivity class launches a predefined Android layout
	 * to display information about the developers and about 
	 * whereismymilkfrom.com 
	 */
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

}
