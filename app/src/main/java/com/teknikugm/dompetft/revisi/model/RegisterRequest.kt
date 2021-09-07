package com.teknikugm.dompetft.revisi.model

data class RegisterRequest (
    val username: String,
    val email: String,
    val password1: String,
    val password2: String,
)