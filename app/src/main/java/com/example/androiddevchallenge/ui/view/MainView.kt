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
package com.example.androiddevchallenge.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.core.calculateNotFormatTime
import com.example.androiddevchallenge.data.NUM_PAD_0
import com.example.androiddevchallenge.data.NUM_PAD_1
import com.example.androiddevchallenge.data.NUM_PAD_2
import com.example.androiddevchallenge.data.NUM_PAD_3
import com.example.androiddevchallenge.data.NUM_PAD_4
import com.example.androiddevchallenge.data.NUM_PAD_5
import com.example.androiddevchallenge.data.NUM_PAD_6
import com.example.androiddevchallenge.data.NUM_PAD_7
import com.example.androiddevchallenge.data.NUM_PAD_8
import com.example.androiddevchallenge.data.NUM_PAD_9
import com.example.androiddevchallenge.data.TIME_SEPARATOR
import com.example.androiddevchallenge.ui.amin.DrawWave
import com.example.androiddevchallenge.ui.theme.backgroundColorLayer1
import com.example.androiddevchallenge.ui.theme.counterTextDarkTheme
import com.example.androiddevchallenge.ui.theme.counterTextLightTheme
import com.example.androiddevchallenge.ui.theme.large_12
import com.example.androiddevchallenge.ui.theme.larger_16
import com.example.androiddevchallenge.ui.theme.medium_8
import com.example.androiddevchallenge.ui.theme.numPadSizeNumber
import com.example.androiddevchallenge.ui.theme.smaller_4
import com.example.androiddevchallenge.ui.theme.textNumberIndicator
import com.example.androiddevchallenge.ui.theme.textNumberSeparator
import com.example.androiddevchallenge.ui.theme.textTimeDescription

@ExperimentalAnimationApi
@Composable
fun MyApp(
    numPadVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
) {
    Scaffold(
        content = {
            MainView(
                showFab = { fabVisibility.value = it },
                showNumPad = numPadVisibility,
            )
        },
        floatingActionButton = {
            Fab(
                fabVisibility = fabVisibility,
                onClick = { numPadVisibility.value = it },
                numPadVisibility = numPadVisibility,
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    )
}

@ExperimentalAnimationApi
@Composable
fun MainView(
    showNumPad: MutableState<Boolean>,
    showFab: (Boolean) -> Unit,
) {
    val counter = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .weight(
                    weight = if (showNumPad.value) 0.3f else 1f,
                    fill = true
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        delayMillis = 100,
                        easing = LinearOutSlowInEasing
                    )
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val time = calculateNotFormatTime(counter.value)
            showFab(counter.value.isNotEmpty())
            TimeIndicator(
                hours = time.hours,
                minutes = time.minutes,
                seconds = time.seconds
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .weight(
                    weight = (if (showNumPad.value) 0.7f else 0.01f),
                    fill = true
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        delayMillis = 0,
                        easing = LinearOutSlowInEasing
                    )
                ),
            horizontalArrangement = Arrangement.Center,
        ) {
            NumPad(
                onClick = {
                    counter.value += it
                },
                onBackspace = {
                    if (counter.value.isNotEmpty()) {
                        counter.value =
                            counter.value.subSequence(0, counter.value.length - 1).toString()
                    }
                },
                timeStringLength = counter.value.length,
                visible = showNumPad.value
            )
        }

        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .weight(0.5f)
                .alpha(1f),
            Alignment.BottomCenter
        ) {
            DrawWave()
        }
    }
}

@Composable
fun Background() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .background(backgroundColorLayer1),
            verticalAlignment = Alignment.Bottom,
            content = {
            }
        )
    }
}

@Composable
fun TimeIndicator(
    hours: String?,
    minutes: String?,
    seconds: String?
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        hours?.let { TextNumberIndicator(text = it) }
        IndicatorDescription("hours")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextNumberSeparator(text = TIME_SEPARATOR)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        minutes?.let { TextNumberIndicator(text = it) }
        IndicatorDescription("minutes")
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextNumberSeparator(text = TIME_SEPARATOR)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        seconds?.let { TextNumberIndicator(text = it) }
        IndicatorDescription("seconds")
    }
}

@ExperimentalAnimationApi
@Composable
fun NumPad(
    onClick: (String) -> Unit,
    onBackspace: () -> Unit,
    timeStringLength: Int,
    visible: Boolean
) {

    if (visible) {
        NumPadNumbers(
            onClick = onClick,
            onBackspace = onBackspace,
            timeStringLength = timeStringLength
        )
    }
    /*AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = spring(stiffness = Spring.StiffnessMedium)
        ),
        initiallyVisible = true,
    ) {

    }*/
}

@ExperimentalAnimationApi
@Composable
fun Fab(
    fabVisibility: MutableState<Boolean>,
    numPadVisibility: MutableState<Boolean>,
    onClick: (Boolean) -> Unit
) {
    AnimatedVisibility(
        visible = fabVisibility.value,
        enter = expandIn(
            expandFrom = Alignment.Center,
            initialSize = {
                IntSize(42, 42)
            },
            animationSpec = tween(120, easing = LinearOutSlowInEasing)
        ) + fadeIn(
            initialAlpha = 0f,
            animationSpec = tween(durationMillis = 50)
        ),
        exit = shrinkOut(
            shrinkTowards = Alignment.Center,
            targetSize = { fullSize -> IntSize(fullSize.width, fullSize.height) },
            animationSpec = tween(100, easing = FastOutSlowInEasing)
        ) + fadeOut(targetAlpha = 0.2f),
        initiallyVisible = false,
    ) {
        FloatingActionButton(
            onClick = { onClick(!numPadVisibility.value) },
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Start timer",
                tint = Color.White
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun NumPadNumbers(
    onClick: (String) -> Unit,
    onBackspace: () -> Unit,
    timeStringLength: Int,
) {
    Row {
        Column(modifier = Modifier.padding(medium_8)) {
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_1,
                    onClick = { onClick(NUM_PAD_1) },
                )
            }
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_4,
                    onClick = { onClick(NUM_PAD_4) },
                )
            }
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_7,
                    onClick = { onClick(NUM_PAD_7) },
                )
            }
        }
        Column(modifier = Modifier.padding(medium_8)) {
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_2,
                    onClick = { onClick(NUM_PAD_2) },
                )
            }
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_5,
                    onClick = { onClick(NUM_PAD_5) },
                )
            }
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_8,
                    onClick = { onClick(NUM_PAD_8) },
                )
            }
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_0,
                    onClick = { onClick(NUM_PAD_0) },
                )
            }
        }
        Column(modifier = Modifier.padding(medium_8)) {
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_3,
                    onClick = { onClick(NUM_PAD_3) },
                )
            }
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_6,
                    onClick = { onClick(NUM_PAD_6) },
                )
            }
            Row(
                modifier = Modifier.padding(
                    start = large_12,
                    top = smaller_4,
                    end = large_12,
                    bottom = smaller_4
                )
            ) {
                PadButton(
                    text = NUM_PAD_9,
                    onClick = { onClick(NUM_PAD_9) },
                )
            }
            Row(
                modifier = Modifier.padding(
                    start = 22.dp,
                    top = larger_16,
                    end = larger_16,
                    bottom = larger_16
                ),
            ) {
                PadIconButton(
                    onClick = { onBackspace() },
                    enabled = timeStringLength > 0
                )
            }
        }
    }
}

@Composable
fun IndicatorDescription(description: String) {
    Text(
        text = description,
        fontSize = textTimeDescription
    )
}

@Composable
fun TextNumberIndicator(text: String) {
    Text(
        text = text,
        fontSize = textNumberIndicator,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(smaller_4),
        color = if (isSystemInDarkTheme()) counterTextDarkTheme else counterTextLightTheme
    )
}

@Composable
fun TextNumberSeparator(text: String) {
    Text(
        text = text,
        fontSize = textNumberSeparator,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(smaller_4),
        color = if (isSystemInDarkTheme()) counterTextDarkTheme else counterTextLightTheme
    )
}

@Composable
fun PadButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    OutlinedButton(
        shape = CircleShape,
        enabled = enabled,
        onClick = onClick,
        border = BorderStroke(
            width = 0.dp,
            color = Color.Transparent
        ),
        modifier = Modifier.background(Color.Transparent)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(large_12)
                .background(Color.Transparent),
            textAlign = TextAlign.Center,
            fontSize = numPadSizeNumber,
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun PadIconButton(
    onClick: () -> Unit,
    enabled: Boolean = false
) {
    AnimatedVisibility(
        visible = enabled,
        enter = fadeIn(5f),
        exit = fadeOut(5f)
    ) {
        IconButton(
            onClick = onClick,
            enabled = enabled
        ) {
            Icon(
                imageVector = Icons.Outlined.Backspace,
                contentDescription = "Delete timer",
                tint = if (enabled) {
                    if (isSystemInDarkTheme()) Color.White else Color.DarkGray
                } else {
                    if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                },
            )
        }
    }
}
