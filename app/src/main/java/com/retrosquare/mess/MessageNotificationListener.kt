package com.retrosquare.mess

import android.app.Notification
import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class MessageNotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        // Listen to Google Messages
        if (sbn?.packageName == "com.google.android.apps.messaging") {
            val extras = sbn.notification.extras
            
            // Try extracting the sender title using standard Android keys
            val senderName = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
                ?: extras.getString("android.title")

            Log.d("MessListener", "Caught message notification from: $senderName")

            if (!senderName.isNullOrEmpty()) {
                val prefs = getSharedPreferences("MessPrefs", Context.MODE_PRIVATE)
                prefs.edit().putString("LAST_SENDER", senderName).apply()
            }
        }
    }
}