package com.gve.testapplication.apium.albumdetail.data

/**
 * Created by gve on 15/11/2017.
 */


data class Song(val id: Long,
                val name: String,
                val artistName: String,
                val duration: Long,
                val albumId: Long) {

    companion object {

        fun timeFromMillis(millis: Long): String {
            val second = millis / 1000 % 60
            val minute = millis / (1000 * 60) % 60
            val hour = millis / (1000 * 60 * 60) % 24

            return if (hour > 0)
                String.format("%02d:%02d:%02d", hour, minute, second)
            else
                String.format("%02d:%02d", minute, second)
        }

    }
}
