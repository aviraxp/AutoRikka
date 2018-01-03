package moe.feng.autofill.rikka

import moe.feng.autofill.rikka.util.Settings

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Settings.getInstance(this)
        RIKKA_WORDS = resources.getStringArray(R.array.rikka_words)
    }

}