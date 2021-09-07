package com.teknikugm.dompetft.revisi.api

import com.teknikugm.dompetft.utama.MainActivity

object Constants {

    const val BASE_URL = "https://api.dompetft.xyz/api/"
    const val LOGIN_URL = "rest-auth/login/"
    const val REGISTER_URL = "rest-auth/registration"
    const val DEVICES_LIST_URL = "devices/"
    const val DEVICES_DETAIL_URL = "devices/"
    const val USER_URL = "rest-auth/user/"
    const val CHANGEPASS_URL = "rest-auth/password/change/"

    const val username = "username"

    val PREFS_NAME = MainActivity::class.java.`package`?.toString()



}