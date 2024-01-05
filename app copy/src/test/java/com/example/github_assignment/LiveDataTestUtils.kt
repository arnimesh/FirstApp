package com.example.github_assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.runners.model.TestTimedOutException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(): T {
    var data: T? = null
    val latch = CountDownLatch(2)

    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            this@getOrAwaitValue.removeObserver(this)
            latch.countDown()
        }
    }

    this.observeForever(observer)



    return data as T
 }