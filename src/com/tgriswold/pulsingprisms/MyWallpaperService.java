package com.tgriswold.pulsingprisms;

import android.content.Context;
import android.preference.PreferenceManager;
import net.rbgrn.android.glwallpaperservice.*;

// Original code provided by Robert Green
// http://www.rbgrn.net/content/354-glsurfaceview-adapted-3d-live-wallpapers
public class MyWallpaperService extends GLWallpaperService {
	public MyWallpaperService() {
		super();
	}
	public Engine onCreateEngine() {
		MyEngine engine = new MyEngine(this);
		return engine;
	}

	class MyEngine extends GLEngine {
		MyRenderer renderer;
		public MyEngine(Context context) {
			super();
			
			// handle prefs, other initialization
			PreferenceManager.setDefaultValues(context, R.xml.prefs, false);
			
			renderer = new MyRenderer(context);
			setRenderer(renderer);
			setRenderMode(RENDERMODE_CONTINUOUSLY);
		}

		public void onDestroy() {
			super.onDestroy();
			if (renderer != null) {
				renderer.release(); // assuming yours has this method - it should!
			}
			renderer = null;
		}
	}
}
