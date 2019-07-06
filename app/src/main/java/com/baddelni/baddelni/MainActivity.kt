package com.baddelni.baddelni

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.baddelni.baddelni.account.AccountHomeFragment
import com.baddelni.baddelni.account.RequestsActivity
import com.baddelni.baddelni.categories.CategoryFragment
import com.baddelni.baddelni.home.HomeFragment
import com.baddelni.baddelni.notifications.NotificationFragment
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.settings.SettingsFragment
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.YesNoInterface
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_navigation.view.*


class MainActivity : AppCompatActivity() {
    val co by lazy { CommonObjects(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLocation = intent?.extras?.getInt("tabLocation", 0)
        initBottomNavigationView()

        openTab(tabLocation ?: 0)
    }

    private fun initBottomNavigationView() {

        homeBt.title.text = getString(R.string.home)
        accountBt.title.text = getString(R.string.account)
        notificationBt.title.text = getString(R.string.notifications)
        settingsBt.title.text = getString(R.string.settings)
        categoryBt.title.text = getString(R.string.categories)

        homeBt.setOnClickListener {
            makeAllUnActive()
            homeBt.image.setImageDrawable(resources.getDrawable(R.drawable.home_selected))
            makeActive(it)
            setContentFragment(HomeFragment())

        }
        accountBt.setOnClickListener {
            makeAllUnActive()
            makeActive(it)
            accountBt.image.setImageDrawable(resources.getDrawable(R.drawable.account_selected))
            setContentFragment(AccountHomeFragment())

        }
        categoryBt.setOnClickListener {
            makeAllUnActive()
            categoryBt.image.setImageDrawable(resources.getDrawable(R.drawable.category_selected))
            makeActive(it)
            setContentFragment(CategoryFragment())
        }
        notificationBt.setOnClickListener {
            makeAllUnActive()
            notificationBt.image.setImageDrawable(resources.getDrawable(R.drawable.notification_selected))
            makeActive(it)
            setContentFragment(NotificationFragment())
        }

        settingsBt.setOnClickListener {
            makeAllUnActive()
            makeActive(it)
            settingsBt.image.setImageDrawable(resources.getDrawable(R.drawable.setting_selected))
            setContentFragment(SettingsFragment())
        }

        makeAllUnActive()


        //   (applicationContext as App).printHashKey(applicationContext)

    }

    private fun makeActive(view: View) {
        view.title.setTextColor(resources.getColor(R.color.gradient2))
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    private fun makeAllUnActive() {

        homeBt.title.setTextColor(resources.getColor(R.color.greyText))
        accountBt.title.setTextColor(resources.getColor(R.color.greyText))
        notificationBt.title.setTextColor(resources.getColor(R.color.greyText))
        settingsBt.title.setTextColor(resources.getColor(R.color.greyText))
        categoryBt.title.setTextColor(resources.getColor(R.color.greyText))

        homeBt.image.setImageDrawable(resources.getDrawable(R.drawable.home_unselected))
        accountBt.image.setImageDrawable(resources.getDrawable(R.drawable.account_unselected))
        notificationBt.image.setImageDrawable(resources.getDrawable(R.drawable.notifications_unselected))
        settingsBt.image.setImageDrawable(resources.getDrawable(R.drawable.settings_unselected))
        categoryBt.image.setImageDrawable(resources.getDrawable(R.drawable.category_unselected))
    }

    fun setContentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(frameLayout.id, fragment).commitNow()
    }

    fun shiftFragment(@IdRes btId: Int): Unit {

        findViewById<View>(btId).performClick()


    }

    fun openTab(tabLocation: Int): Unit {


        when (tabLocation) {
            0 -> homeBt.performClick()
            1 -> accountBt.performClick()
            2 -> categoryBt.performClick()
            3 -> notificationBt.performClick()
            4 -> settingsBt.performClick()
            13 -> {

                accountBt.performClick()
                val intent = Intent(this, RequestsActivity::class.java)
                intent.putExtra("fromScreen", "request")
                startActivity(intent)

            }
            15 -> {
                accountBt.performClick()
                val intent = Intent(this, RequestsActivity::class.java)
                intent.putExtra("fromScreen", "myRequest")
                startActivity(intent)

            }

            else -> homeBt.performClick()
        }


    }

    override fun onBackPressed() {

        co.showYesNoDialog(getString(R.string.exit), getString(R.string.exitApp), object : YesNoInterface {
            override fun onClickYes() {
                finish()
            }
        })
    }

}
