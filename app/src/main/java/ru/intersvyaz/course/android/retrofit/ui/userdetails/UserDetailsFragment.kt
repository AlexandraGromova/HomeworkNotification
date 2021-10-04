package ru.intersvyaz.course.android.retrofit.ui.userdetails

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.*
import kotlinx.android.synthetic.main.user_fragment.*
import okhttp3.internal.notify
import ru.intersvyaz.course.android.retrofit.R
import ru.intersvyaz.course.android.retrofit.data.model.User

class UserDetailsFragment : Fragment(R.layout.user_fragment) {

    private val userId by lazy { arguments?.getString(USER_ID) }
    private val viewModel: UserDetailsViewModel by lazy { ViewModelProvider(this).get(UserDetailsViewModel::class.java) }

    private val userObserver = Observer<User?> {
        it ?: return@Observer
        requireView().findViewById<TextView>(R.id.userName).text = it.name
        requireView().findViewById<TextView>(R.id.userId).text = it.id
        requireView().findViewById<TextView>(R.id.userEmail).text = it.email

        val imageViewAvatar = requireView().findViewById<ImageView>(R.id.userAvatar)

        Glide.with(imageViewAvatar.context)
            .load(it.avatar)
            .into(imageViewAvatar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId?.let { viewModel.loadUserInfo(it) }
        viewModel.user.observe(viewLifecycleOwner, userObserver)

        val notificationButton = view.findViewById<Button>(R.id.sendNotification)
        notificationButton.setOnClickListener {
            val notification = Notification()
            notification.createNotification(view, requireContext() )
        }


        val shareButton = view.findViewById<Button>(R.id.shareButton)
        shareButton.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${userName.text}")
                type = "text/plain"
            }
            try {
                startActivity(sendIntent)
            } catch (e: ActivityNotFoundException) {
            }
        }
    }


    companion object {
        const val TAG = "userFragment"
        const val USER_ID = "userId"
    }
}