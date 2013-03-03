package com.castillo;

import android.content.Context;
import android.os.Bundle;

import org.holoeverywhere.app.Activity;

public abstract class JDownloader4AndroidActivity extends Activity {

	public static Context context;
	
	@Override
    public void onCreate(Bundle icicle)
    {
    	super.onCreate(icicle);
    	context=getApplicationContext();
    }
}
