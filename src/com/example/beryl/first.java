package com.example.beryl;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class first extends Activity{
	public void onCreate(Bundle saved)
	{
		super.onCreate(saved);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first);
	}

}
