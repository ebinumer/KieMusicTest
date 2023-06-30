package com.ebinumer.kiemusictest.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(private val context: Context)  {
    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    init {
        val PRIVATE_MODE = 0
        pref = context.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
        editor = pref.edit()
    }

    var appOpenStatus: Boolean
        get() = pref.getBoolean(APP_OPEN_STATUS, false)
        set(value) {
            editor.putBoolean(APP_OPEN_STATUS, value).apply()
        }

    var token: String?
        get() = pref.getString(TOKEN,null)
        set(value) = editor.putString(TOKEN,value).apply()

    fun logout(){
        editor.clear()
        editor.commit()
    }

    companion object {
        private const val PREF_NAME = "room_app"
        private val APP_OPEN_STATUS = "appOpenStatus"
        private val TOKEN = "token"
    }
}