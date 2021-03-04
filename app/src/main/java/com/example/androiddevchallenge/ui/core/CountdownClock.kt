package com.example.androiddevchallenge.ui.core

import android.content.SharedPreferences

class CountdownClock(
	private val storage: SharedPreferences
) {
	fun countDown(hour: Int, minute: Int, second: Int) {
		val hMilli = hoursToMilli(hour)
		val mMilli = minutesToMilli(minute)
		val sMilli = secondsToMilli(second)
		
		val totalTime = hMilli + mMilli + sMilli
	}
	
	fun increaseCounter(number: Int): Int {
		val localCount = storage
		return 1
	}
	
	private fun hoursToMilli(hour: Int): Int {
		return hour * 3600000
	}
	
	private fun minutesToMilli(minute: Int): Int {
		return minute * 60000
	}
	
	private fun secondsToMilli(second: Int): Int {
		return second * 1000
	}
	
	fun isValidInput(input: Int): Boolean {
		return when (input) {
			in 0..99 -> return true
			else -> false
		}
	}
}



