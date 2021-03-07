package com.example.androiddevchallenge.core

import android.util.Log
import com.example.androiddevchallenge.data.TAG

fun calculateNotFormatTime(time: String): CountDown {
	try {
		if (time != "") {
			if (time.toInt() <= 356400 + 5940 + 99) { // <--- 99:99:99
				
				var seconds = 0
				var hours = 0
				var minutes = 0
				
				when (time.toInt()) {
					in 0..99 -> {
						seconds = time.toInt()
					}
					in 100..999 -> {
						minutes = time.subSequence(0, 1).toString().toInt()
						seconds = time.subSequence(1, 3).toString().toInt()
					}
					in 1000..9999 -> {
						minutes = time.subSequence(0, 2).toString().toInt()
						seconds = time.subSequence(2, 4).toString().toInt()
					}
					in 10000..99999 -> {
						hours = time.subSequence(0, 1).toString().toInt()
						minutes = time.subSequence(1, 3).toString().toInt()
						seconds = time.subSequence(3, 5).toString().toInt()
					}
					in 100000..1000000 -> {
						hours = time.subSequence(0, 2).toString().toInt()
						minutes = time.subSequence(2, 4).toString().toInt()
						seconds = time.subSequence(4, 6).toString().toInt()
					}
				}
				
				//Log.d(TAG, "hours: $hours, minutes: $minutes, seconds: $seconds ")
				
				val strHours: String = if (hours < 10) "0$hours" else hours.toString()
				val strMinutes: String = if (minutes < 10) "0$minutes" else minutes.toString()
				val strSeconds: String = if (seconds < 10) "0$seconds" else seconds.toString()
				
				return CountDown(
					hours = strHours,
					minutes = strMinutes,
					seconds = strSeconds
				)
			}
			return CountDown()
		}
		return CountDown()
	} catch (e: Exception) {
		Log.e(TAG, "calculateTime: $e")
		return CountDown()
	}
}

fun formatTime(time: String): CountDown {
	try {
		if (time != "") {
			if (time.toInt() < 362439) { // <--- 99:99:99
				Log.d(TAG, "mainView: $time.toInt()")
				val hours = time.toInt() / 3600
				var remain = time.toInt() - hours * 3600
				val minutes = remain / 60
				remain -= minutes * 60
				val seconds = remain
				Log.d(TAG, "hours: $hours, minutes: $minutes, seconds: $seconds ")
				
				val strHours: String = if (hours < 10) "0$hours" else hours.toString()
				val strMinutes: String = if (minutes < 10) "0$minutes" else minutes.toString()
				val strSeconds: String = if (seconds < 10) "0$seconds" else seconds.toString()
				
				return CountDown(
					hours = strHours,
					minutes = strMinutes,
					seconds = strSeconds
				)
			}
			return CountDown()
		}
		return CountDown()
	} catch (e: Exception) {
		Log.e(TAG, "calculateTime: $e")
		return CountDown()
	}
}
