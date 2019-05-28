package com.baddelni.baddelni.settings

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import com.baddelni.baddelni.R
import kotlinx.android.synthetic.main.activity_settings_explain.*

class SettingsExplain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_explain)
        backBt.setOnClickListener { finish() }
        val extras = intent.extras

        if (extras != null) {

            val text = extras.getString("text")
            textContent.text = Html.fromHtml(text)
        }

    }   override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }
}
