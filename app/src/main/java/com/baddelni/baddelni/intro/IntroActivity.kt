package com.baddelni.baddelni.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.loginRegister.GuestLoginActivity
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.AppLanguage
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    val co: CommonObjects by lazy { CommonObjects(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val list = if (co.getAppLanguage() == AppLanguage.ARABIC)
            listOf(R.drawable.android_ar_1,
                    R.drawable.android_ar_2,
                    R.drawable.android_ar_3)
        else
            listOf(R.drawable.android_en_1,
                    R.drawable.android_en_2,
                    R.drawable.android_en_3)

        viewpager.adapter = CustomPagerAdapter(this, list)

        skip.setOnClickListener {
            startActivity(Intent(this@IntroActivity, GuestLoginActivity::class.java))
            finish()
        }

        if (co.getBool(AppConstants.IS_INTRO_ALREADY_SHOWN)) {
            skip.performClick()
        } else {
            co.putBool(AppConstants.IS_INTRO_ALREADY_SHOWN, true)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, AppLanguage.ARABIC.langCode()))
    }

}
