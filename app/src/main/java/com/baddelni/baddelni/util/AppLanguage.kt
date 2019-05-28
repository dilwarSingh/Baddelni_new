package com.baddelni.baddelni.util

enum class AppLanguage {
    ENGLISH, ARABIC, HINDI;


    fun isEnglish(): Boolean {
        return this == ENGLISH
    }

    fun isArabic(): Boolean {
        return this == ARABIC
    }

    fun isHindi(): Boolean {
        return this == HINDI
    }

    fun langCode() = if (isEnglish()) "en" else if (isHindi()) "hi" else "ar"
}