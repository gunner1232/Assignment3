package com.example.assignment3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var movieInput: EditText
    private val CHANNEL_ID = "movie_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create notification channel
        createNotificationChannel()

        movieInput = findViewById(R.id.movieInput)
        val recommendButton = findViewById<Button>(R.id.recommendButton)

        recommendButton.setOnClickListener {
            val movieType = movieInput.text.toString()
            val intent = Intent(this, RecommendationActivity::class.java)
            intent.putExtra("MOVIE_TYPE", movieType)

            // Save history in the Fragment
            saveRecommendationToHistory(movieType)

            // Start the RecommendationActivity
            startActivity(intent)

            // Send a notification
            sendNotification(movieType)
        }

        // Load the history in Fragment
        loadFragment(HistoryFragment())
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Movie Channel"
            val descriptionText = "Channel for movie recommendations"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(movieType: String) {
        val intent = Intent(this, RecommendationActivity::class.java)
        intent.putExtra("MOVIE_TYPE", movieType)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_movie_filter_24) // Replace with your actual icon ID
            .setContentTitle("Movie Recommendations")
            .setContentText("Recommended movies for $movieType")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1, builder.build())
    }

    private fun saveRecommendationToHistory(movieType: String) {
        val fragment = supportFragmentManager.findFragmentById(R.id.history_fragment_container)
        if (fragment is HistoryFragment) {
            fragment.addHistory(movieType)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.history_fragment_container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                movieInput.text.clear()
                true
            }
            R.id.action_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}