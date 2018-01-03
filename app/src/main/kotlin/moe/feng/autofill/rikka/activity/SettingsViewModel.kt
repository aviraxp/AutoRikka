package moe.feng.autofill.rikka.activity

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.databinding.ObservableField
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class SettingsViewModel {

    val list = ObservableField<List<ApplicationInfo>>(emptyList())

    fun loadList(context: Context) = async(UI) {
        val data = async(CommonPool) {
            context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        }.await()
        list.set(data)
    }

}