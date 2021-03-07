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
package com.example.androiddevchallenge.ui.amin

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.example.androiddevchallenge.ui.theme.backgroundColorLayer1
import com.example.androiddevchallenge.ui.theme.backgroundColorLayer2
import com.example.androiddevchallenge.ui.theme.backgroundColorLayer3
import com.example.androiddevchallenge.ui.theme.backgroundColorLayer4
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun DrawWave(height: Float) {

    val infiniteTransition = rememberInfiniteTransition()

    val speed by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing)
        )
    )

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val halfHeight = size.height / 2
            for (x in 0 until size.width.toInt()) {
                val y1 =
                    (sin(-speed + x * (PI / size.width)) * halfHeight / 8 + height).toFloat()

                val y2 =
                    (sin(speed + x * (PI / size.width)) * halfHeight / 8 + 60 + height).toFloat()

                val y3 =
                    (sin(PI - speed + x * (PI / size.width)) * halfHeight / 8 + 120 + height).toFloat()

                val y4 =
                    (sin(PI + speed + x * (PI / size.width)) * halfHeight / 8 + 180 + height).toFloat()

                drawLine(
                    color = backgroundColorLayer1,
                    start = Offset(x = x.toFloat(), y = size.height),
                    end = Offset(x = x.toFloat(), y = y1),
                    strokeWidth = 1f
                )

                drawLine(
                    color = backgroundColorLayer2,
                    start = Offset(x = x.toFloat(), y = size.height),
                    end = Offset(x = x.toFloat(), y = y2),
                    strokeWidth = 1f,
                )

                drawLine(
                    color = backgroundColorLayer3,
                    start = Offset(x = x.toFloat(), y = size.height),
                    end = Offset(x = x.toFloat(), y = y3),
                    strokeWidth = 1f,
                )

                drawLine(
                    color = backgroundColorLayer4,
                    start = Offset(x = x.toFloat(), y = size.height),
                    end = Offset(x = x.toFloat(), y = y4),
                    strokeWidth = 1f,
                )
            }

            /*  drawPoints(
                  strokeWidth = 8f,
                  points = points1,
                  pointMode = PointMode.Lines,
                  color = Color.Cyan
              )*/
        }
    )
}
