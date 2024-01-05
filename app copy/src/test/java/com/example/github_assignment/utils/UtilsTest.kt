package com.example.github_assignment.utils

import android.util.Log
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mock
import java.util.*

class UtilsTest {

    @Test
    fun formatDate() {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val hr = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        assertEquals("Updated 1 year ago", Utils().formatDate("2021-06-14T19:47:22Z"))
        assertEquals("Updated 1 month ago", Utils().formatDate("2022-07-24T18:42:39Z"))
        assertEquals("Updated 10 days ago", Utils().formatDate("2022-08-12T18:42:39Z"))
        assertEquals("Updated few minutes ago", Utils().formatDate("2022-08-22T01:02:39Z"))
    }
}