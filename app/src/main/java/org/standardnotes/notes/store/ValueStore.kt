package org.standardnotes.notes.store

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import org.standardnotes.notes.BuildConfig
import org.standardnotes.notes.SApplication

class ValueStore(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("values", Context.MODE_PRIVATE)

    fun setTokenAndMasterKey(token: String?, mk: String?, ak: String?) {
        prefs.edit().putString("masterKey", mk).putString("authKey", ak).putString("token", token).apply()
    }

    val masterKey: String?
        get() = prefs.getString("masterKey", null)

    val authKey: String?
        get() = prefs.getString("authKey", null)

    val token: String?
        get() = prefs.getString("token", null)

    fun clear() {
        prefs.edit().clear().apply()
    }
}
