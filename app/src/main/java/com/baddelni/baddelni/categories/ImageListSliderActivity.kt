package com.baddelni.baddelni.categories

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import com.baddelni.baddelni.R
import com.baddelni.baddelni.settings.LocaleHelper
import kotlinx.android.synthetic.main.activity_image_list_slider.*
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.RemoteBanner


class ImageListSliderActivity : AppCompatActivity() {

    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list_slider)
        backBt.setOnClickListener {
            finish()
        }
        zoomOutImg.setOnClickListener { finish() }
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        val imageList = intent?.extras?.getSerializable("imageList") as Array<String>

        if (imageList.size > 1) {
            slider.setInterval(4000)
            slider.setLoopSlides(true)
        }

        makeSlider(imageList)

        slider.setOnTouchListener(View.OnTouchListener { v, event ->
            mScaleGestureDetector?.onTouchEvent(event)
            true
        })

    }

  /*  override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        mScaleGestureDetector?.onTouchEvent(ev)
        return true
    }
*/
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        mScaleGestureDetector?.onTouchEvent(motionEvent)
        return true
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    private fun makeSlider(sliderArray: Array<String>) {

        val banners1: ArrayList<Banner> = arrayListOf()

        for (imgPath in sliderArray) {
            val remoteBanner = RemoteBanner(imgPath)
            banners1.add(remoteBanner)
            remoteBanner.placeHolder = resources.getDrawable(R.mipmap.ic_launcher)

        }

        slider.setBanners(banners1)


    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {

            mScaleFactor *= scaleGestureDetector.scaleFactor

            mScaleFactor = Math.max(0.75f,
                    Math.min(mScaleFactor, 4.0f))

            slider.scaleX = mScaleFactor
            slider.scaleY = mScaleFactor

            return true

        }
    }
}
