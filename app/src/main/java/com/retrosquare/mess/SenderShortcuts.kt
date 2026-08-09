package com.retrosquare.mess

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.app.Person

fun publishSenderShortcut(ctx: Context, sender: String) {
    val shortcut = ShortcutInfoCompat.Builder(ctx, "sender_$sender")
        .setShortLabel(sender)
        .setLongLived(true)
        .setIcon(initialsIcon(sender))
        .setCategories(setOf("com.retrosquare.mess.category.SAVE_MESS"))
        .setPerson(Person.Builder().setName(sender).build())
        .setIntent(Intent(ctx, MainActivity::class.java).apply {
            action = Intent.ACTION_SEND
        })
        .build()

    ShortcutManagerCompat.pushDynamicShortcut(ctx, shortcut)
}