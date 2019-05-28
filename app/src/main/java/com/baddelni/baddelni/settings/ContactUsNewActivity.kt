package com.baddelni.baddelni.settings

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.baddelni.baddelni.R

class ContactUsNewActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us_new)
    }
}
