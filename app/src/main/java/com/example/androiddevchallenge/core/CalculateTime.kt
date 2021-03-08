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
package com.example.androiddevchallenge.core

fun calculateNotFormatTime(time: String): CountDown {
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
}

fun formatTime(time: Long): CountDown {
    if (time < 356400000) { // <--- 99:99:99
        val hours = time / 3600000
        var remain = time - hours * 3600000
        val minutes = remain / 60000
        remain -= minutes * 60000
        val seconds = remain / 1000

        val strHours: String = if (hours < 10) "0$hours" else hours.toString()
        val strMinutes: String = if (minutes < 10) "0$minutes" else minutes.toString()
        val strSeconds: String = if (seconds < 10) "0$seconds" else seconds.toString()

        return CountDown(
            hours = strHours,
            minutes = strMinutes,
            seconds = strSeconds
        )
    } else {
        return CountDown()
    }
}
