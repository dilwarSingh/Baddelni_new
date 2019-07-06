package com.baddelni.baddelni.fcm_services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.HttpURLConnection
import java.net.URL


class MyFirebaseMessageingService : FirebaseMessagingService() {
    val co: CommonObjects by lazy { CommonObjects(this) }

    val TAG = "MyFirebaseService"
    val CHANNEL_ID = "1245"
    override fun onMessageReceived(rMessage: RemoteMessage?) {
       // super.onMessageReceived(rMessage)
        Log.d("My-Message", rMessage.toString())

   /*     rMessage?.data?.isNotEmpty()?.let {
            Log.d(TAG, "Message data payload: " + rMessage.data)


        }*/
        rMessage?.data?.let {

            val type = it["type"] ?: ""
            val alert = it["alert"] ?: ""
            Log.d(TAG, "Message Notification Body: $alert")

            sendNotification(alert, type, null, null)
        }


    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)

        Log.d("My-Token", token)
        co.putStringPrams(AppConstants.TOKEN, token ?: "")
    }

    private fun sendNotification(messageBody: String, type: String, image: Bitmap?, TrueOrFalse: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("tabLocation", getScreenToOpen(type))
        intent.putExtra("type", type)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                //      .setLargeIcon(image)/*Notification icon image*/
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageBody)
                //.setStyle(NotificationCompat.BigPictureStyle())
                //              .bigPicture(image))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
            notificationBuilder.setChannelId(CHANNEL_ID)
        }
        notificationManager.notify(231, notificationBuilder.build())
    }

    private fun getScreenToOpen(type: String): Int {

        return when (type) {
            "normal" -> 3
            "category" -> 2
            "request" -> 13
            "my_request" -> 15
            else -> 0

        }


    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun getBitmapfromUrl(imageUrl: String): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            BitmapFactory.decodeStream(input)

        } catch (e: Exception) {
            e.printStackTrace()
            null

        }

    }
}