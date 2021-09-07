package com.teknikugm.dompetft.revisi.api

import android.content.Context
import android.content.SharedPreferences
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.revisi.model.Profile_m

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "Token"
        const val DEVICE_ID = "id"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun saveProfile(profileM: Profile_m?) {
        val editor = prefs.edit()
        editor.putString("id", profileM?.id)
        editor.putString("username", profileM?.username)
        editor.putString("saldo", profileM?.saldo)
        editor.apply()
    }

    fun getProfile(): Profile_m{
        val profileM = Profile_m()
        profileM.id = prefs.getString("id", "")
        profileM.username = prefs.getString("username", "")
        profileM.saldo = prefs.getString("saldo", "")

        return profileM
    }

    fun saveDeviceId(id: Int?) {
        val editor = prefs.edit()
        if (id != null) {
            editor.putInt(DEVICE_ID, id)
        }
        editor.apply()
    }

    fun fetchDeviceId(): Int? {
        return prefs.getInt(DEVICE_ID, 0)
    }

    fun saveLocation(lat: Float, lng: Float) {
        val editor = prefs.edit()
        editor.putFloat("Current Latitude", lat)
        editor.putFloat("Current Longitude", lng)
        editor.apply()
    }

    fun fetchLat(): Float {
        return prefs.getFloat("Current Latitude", 0f)
    }

    fun fetchLng(): Float {
        return prefs.getFloat("Current Longitude", 0f)
    }

    fun saveUsername(username: String?) {
        val editor = prefs.edit()
        editor.putString("username", username)
        editor.apply()
    }

    fun fetchUsername(): String? {
        return prefs.getString("username", null)
    }
}
