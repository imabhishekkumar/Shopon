package me.abhishekkumar.shopon

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import me.abhishekkumar.shopon.utils.isNight

@HiltAndroidApp
class Shopon : Application() {
    override fun onCreate() {
        super.onCreate()
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

}