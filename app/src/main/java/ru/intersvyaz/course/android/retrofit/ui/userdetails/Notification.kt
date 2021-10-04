package ru.intersvyaz.course.android.retrofit.ui.userdetails

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.user_fragment.view.*
import ru.intersvyaz.course.android.retrofit.R

private const val TAG = "Notification"

class Notification() {


    fun createNotification(view : View, fragmentContext: Context,){

    val notificationButton = view.findViewById<Button>(R.id.sendNotification)
    notificationButton.setOnClickListener {
        createNotificationChannel(fragmentContext)

        val options = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeResource(
            Resources.getSystem(),
            ,
            options)

        val builder = NotificationCompat.Builder(it.context, "My channel")
            .setSmallIcon(R.drawable.ic_baseline_3p_24)
            .setContentTitle(view.userName.text)
            .setContentText(view.userEmail.text)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        NotificationManagerCompat.from(fragmentContext).notify(1, builder)
    }
    }

    private fun createNotificationChannel(fragmentContext: Context){
        Log.v(UserDetailsFragment.TAG, "channel")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "My channel"
            val descriptionText = "My channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("My channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager : NotificationManager = ContextCompat.getSystemService(
                fragmentContext,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}