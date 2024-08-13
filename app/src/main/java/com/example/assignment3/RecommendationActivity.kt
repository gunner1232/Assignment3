package com.example.assignment3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecommendationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        // get data
        val movieType = intent.getStringExtra("MOVIE_TYPE") ?: "action"

        // get recommendation list
        val recommendations = MovieDataSource.getRecommendations(movieType)

        // set RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MovieAdapter(recommendations)

        // send notification
        sendNotification(movieType)
    }

    private fun sendNotification(movieType: String) {
        val intent = Intent(this, RecommendationActivity::class.java)
        intent.putExtra("MOVIE_TYPE", movieType)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE // use FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, "movie_channel")
            .setSmallIcon(R.drawable.baseline_movie_filter_24)
            .setContentTitle("Movie Recommendations")
            .setContentText("Recommended movies for $movieType")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1, builder.build())
    }
}