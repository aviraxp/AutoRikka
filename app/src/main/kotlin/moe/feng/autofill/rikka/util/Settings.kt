package moe.feng.autofill.rikka.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable

import moe.feng.common.kt.getInstance

class Settings private constructor(private val context: Context) {

    private val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val enabledApps = getStringSet(ENABLED_APPS, DEFAULT_ENABLED_APPS_LIST).toMutableSet()

    @JvmOverloads
    fun getInt(key: String, defValue: Int = 0): Int {
        return sharedPref.getInt(key, defValue)
    }

    @JvmOverloads
    fun getString(key: String, defValue: String? = null): String? {
        return sharedPref.getString(key, defValue)
    }

    @JvmOverloads
    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return sharedPref.getBoolean(key, defValue)
    }

    @JvmOverloads
    fun getStringSet(key: String, defValue: Set<String> = emptySet()): Set<String> {
        return sharedPref.getStringSet(key, defValue)
    }

    fun putStringSet(key: String, value: Set<String>) {
        sharedPref.edit().putStringSet(key, value).apply()
    }

    fun isAppEnabled(packageName: String?) = enabledApps.contains(packageName)

    fun setAppEnabled(packageName: String, isEnabled: Boolean) {
        if (isEnabled) {
            enabledApps += packageName
        } else {
            enabledApps -= packageName
        }
        putStringSet(ENABLED_APPS, enabledApps)
    }

    fun getApplicationIcon(packageName: String): Drawable {
        return context.packageManager.getApplicationIcon(packageName)
    }

    fun getApplicationLabel(appInfo: ApplicationInfo): CharSequence {
        return context.packageManager.getApplicationLabel(appInfo)
    }

    companion object {

        private const val PREF_NAME = "default"
        private const val ENABLED_APPS = "enabled_apps"

        private val DEFAULT_ENABLED_APPS_LIST = setOf(
                "org.telegram.messenger"
        )

        @JvmStatic
        @JvmName("getInstance")
        @JvmOverloads
        fun getInstance(context: Context? = null): Settings = getInstance {
            Settings(context?.applicationContext
                    ?: throw IllegalArgumentException("Instance did not exist."))
        }

    }

}