package com.baddelni.baddelni.Response.Notification

import com.baddelni.baddelni.util.NetworkCode
import com.google.gson.annotations.SerializedName

data class NotificationResponse(

        @field:SerializedName("code")
        val code: NetworkCode? = null,

        @field:SerializedName("notifications")
        val notifications: List<NotificationsItem>? = null
)