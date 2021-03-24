package com.pointlessapps.tvremote_client

import android.app.Application
import com.google.android.tv.support.remote.core.Device
import com.pointlessapps.tvremote_client.services.PreferencesService
import kotlinx.coroutines.GlobalScope

class App : Application() {

	val preferencesService: PreferencesService by lazy {
		PreferencesService(
			GlobalScope,
			applicationContext
		)
	}

	var device: Device? = null
}