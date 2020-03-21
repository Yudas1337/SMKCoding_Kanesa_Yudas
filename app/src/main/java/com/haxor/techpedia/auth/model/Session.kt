package com.haxor.techpedia.auth.model

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.google.gson.Gson

/**
 * Created by Yudas Malabi
 */
object Session {

   const val USER_SESSION = "user_session"

   private fun getSharedPreference(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun putUser(context: Context, key: String, user: User?) {
        val gson = Gson()
        val json = gson.toJson(user)
        putString(context, key, json)
    }

    fun getUser(context: Context, key: String): User? {
        val gson = Gson()
        val json = getString(context, key)
        return gson.fromJson(json, User::class.java)
    }

   private fun putString(context: Context, key: String, value: String) {
        getSharedPreference(context).edit().putString(key, value).apply()
    }

   private fun getString(context: Context, key: String): String? {
        return getSharedPreference(context).getString(key, null)
    }

    fun clear(context: Context) {
        getSharedPreference(context).edit().clear().apply()
    }

}