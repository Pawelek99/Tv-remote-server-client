package com.pointlessapps.tvremote_client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		startActivity(
			Intent(this, MainActivity::class.java).putExtra(
				MainActivity.DESTINATION,
				intent.getIntExtra(MainActivity.DESTINATION, 0)
			)
		)
		overridePendingTransition(0, 0)
		finish()
	}
}