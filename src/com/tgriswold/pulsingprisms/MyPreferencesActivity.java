package com.tgriswold.pulsingprisms;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class MyPreferencesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferencesFragment())
                .commit();
    }
    
	public static class MyPreferencesFragment extends PreferenceFragment {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	
	        addPreferencesFromResource(R.xml.prefs);
	    }
	}
}

