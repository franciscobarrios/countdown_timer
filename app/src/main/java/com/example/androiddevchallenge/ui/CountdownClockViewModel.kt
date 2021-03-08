/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.core.CountDown
import kotlin.math.absoluteValue

class CountdownClockViewModel : ViewModel() {

    val timerLiveData = MutableLiveData<Long>()
    fun observeTimer(): LiveData<Long> = timerLiveData

    private val timerStateLiveData = MutableLiveData<TimerState>()
    fun observeTimerState(): LiveData<TimerState> = timerStateLiveData

    private val countDownTimerLiveData = MutableLiveData<Long>()
    fun observeTotalTime(): LiveData<Long> = countDownTimerLiveData

    private val paramsForAnimationLiveData = MutableLiveData<Pair<Long, Long>>()

    private val saveTimerLiveData = MutableLiveData<Long>()

    private val fabViewState = MutableLiveData<Boolean>()
    private val fabStopViewState = MutableLiveData<Boolean>()
    private val fabContinueViewState = MutableLiveData<Boolean>()
    private val numPabViewState = MutableLiveData<Boolean>()
    private val waveAnimation = MutableLiveData<Boolean>()

    private var timer: CountDownTimer? = null

    init {
        timerStateLiveData.postValue(TimerState.IDLE)
    }

    fun viewState() {
        when (timerStateLiveData.value) {
            TimerState.IDLE -> {
                fabViewState.postValue(true)
                fabStopViewState.postValue(false)
                fabContinueViewState.postValue(false)
                numPabViewState.postValue(true)
                waveAnimation.postValue(false)
            }
            TimerState.RUNNING -> {
                fabViewState.postValue(false)
                fabStopViewState.postValue(true)
                fabContinueViewState.postValue(false)
                numPabViewState.postValue(false)
                waveAnimation.postValue(true)
            }
            TimerState.PAUSED -> {
                fabViewState.postValue(false)
                fabStopViewState.postValue(false)
                fabContinueViewState.postValue(true)
                numPabViewState.postValue(true)
                waveAnimation.postValue(true)
            }
            TimerState.FINISHED -> {
                fabViewState.postValue(true)
                fabStopViewState.postValue(false)
                fabContinueViewState.postValue(false)
                numPabViewState.postValue(true)
                waveAnimation.postValue(true)
            }
        }
    }

    fun setCountDownTimer(time: CountDown) {
        val hours = time.hours.toLong()
        val minutes = time.minutes.toLong()
        val seconds = time.seconds.toLong()
        val countDown = (hours * 3600000) + (minutes * 60000) + (seconds * 1000)
        countDownTimerLiveData.postValue(countDown)
    }

    fun startTimer() {
        countDownTimerLiveData.value?.let { setTimer(it) }
    }

    private fun setTimer(time: Long) {
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerStateLiveData.postValue(TimerState.RUNNING)
                timerLiveData.postValue(millisUntilFinished.absoluteValue)
            }

            override fun onFinish() {
                timerStateLiveData.postValue(TimerState.FINISHED)
            }
        }
        timer?.start()
    }

    fun pauseTimer() {
        saveTimerLiveData.postValue(timerLiveData.value)
        timerStateLiveData.postValue(TimerState.PAUSED)
        timer?.cancel()
    }

    fun continueTimer() {
        if (saveTimerLiveData.value != null) {
            timer = object : CountDownTimer(saveTimerLiveData.value!!, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerStateLiveData.postValue(TimerState.RUNNING)
                    timerLiveData.postValue(millisUntilFinished.absoluteValue)
                }

                override fun onFinish() {
                    timerStateLiveData.postValue(TimerState.FINISHED)
                }
            }
            timer?.start()
        }
    }
}

enum class TimerState {
    IDLE, RUNNING, PAUSED, FINISHED
}
