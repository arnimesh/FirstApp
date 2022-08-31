package com.example.github_assignment.utils

import android.util.Log

import java.util.*

class Utils {



    fun formatDate(date: String): String {
        val splitDate = date.subSequence(0, date.indexOf('T'))
       // Log.d("check",splitDate.toString())
        val dateList: List<String> = splitDate.split('-')
       // Log.d("check",dateList.toString())

        val formattedTime = date.subSequence(date.indexOf('T')+1, date.indexOf('Z'))
        val timeList = formattedTime.split(':')
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hr = calendar.get(Calendar.HOUR_OF_DAY)

        var timeSpan: Int

        if (year != dateList[0].toInt()){
            timeSpan = year - dateList[0].toInt()
            if (timeSpan == 1)
                return "Updated $timeSpan year ago"
            return "Updated $timeSpan years ago"
        }

        if (month+1 != dateList[1].toInt()){
            timeSpan = month+1 - dateList[1].toInt()
            if (timeSpan == 1)
                return "Updated $timeSpan month ago"
            return "Updated $timeSpan months ago"
        }

        if (day != dateList[2].toInt()) {
            timeSpan = day - dateList[2].toInt()
            if (timeSpan == 1)
                return "Updated $timeSpan day ago"
            return "Updated $timeSpan days ago"
        }

        if (hr != timeList[0].toInt()) {
            timeSpan = hr - timeList[0].toInt()
            if (timeSpan == 1)
                return "Updated $timeSpan hour ago"
            return "Updated $timeSpan hours ago"
        }

        return "Updated few minutes ago"
    }



//@RequiresApi(Build.VERSION_CODES.O)
//fun format Date{
//val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
//val timestamp = 1565209665.toLong() // timestamp in Long
//
//
//val timestampAsDateString = java.time.format.DateTimeFormatter.ISO_INSTANT
//    .format(java.time.Instant.ofEpochSecond(timestamp))
//
//Log.d("parseTesting", timestampAsDateString) // prints 2019-08-07T20:27:45Z
//
//
//val date = LocalDate.parse(timestampAsDateString, secondApiFormat)
//
//Log.d("parseTesting", date.dayOfWeek.toString()) // prints Wednesday
//Log.d("parseTesting", date.month.toString()) // prints August
//}
}