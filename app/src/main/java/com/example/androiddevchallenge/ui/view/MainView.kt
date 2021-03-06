package com.example.androiddevchallenge.ui.view

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.core.CountDown
import com.example.androiddevchallenge.data.*
import com.example.androiddevchallenge.ui.theme.*

@ExperimentalAnimationApi
@Composable
fun MyApp(
	showFab: MutableState<Boolean>
) {
	Scaffold(
		content = {
			MainView(
				showFab = {
					Log.d(TAG, "MyApp: $it ")
					showFab.value = it
				},
			)
		},
		floatingActionButton = { Fab(showFab) },
		floatingActionButtonPosition = FabPosition.Center,
	)
}

@ExperimentalAnimationApi
@Composable
fun MainView(
	showFab: (Boolean) -> Unit,
) {
	val counter = remember { mutableStateOf("") }
	
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(12.dp)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.weight(0.5f),
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
				.weight(1f),
			horizontalArrangement = Arrangement.Center,
		) {
			NumPad(
				onClick = { counter.value += it },
				onBackspace = {
					if (counter.value.isNotEmpty()) {
						counter.value =
							counter.value.subSequence(0, counter.value.length - 1).toString()
					}
				},
				timeStringLength = counter.value.length
			)
		}
	}
}

@ExperimentalAnimationApi
@Composable
fun Fab(visible: MutableState<Boolean>) {
	AnimatedVisibility(
		visible = visible.value,
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
			onClick = {
				//start timer
			},
		) {
			Icon(
				imageVector = Icons.Default.PlayArrow,
				contentDescription = "Start timer",
				tint = Color.White
			)
		}
	}
}

@Composable
fun TextNumberIndicator(text: String) {
	Text(
		text = text,
		fontSize = textNumberIndicator,
		fontFamily = FontFamily.Default,
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
		fontFamily = FontFamily.Monospace,
		fontStyle = FontStyle.Normal,
		textAlign = TextAlign.Center,
		modifier = Modifier.padding(smaller_4),
		color = if (isSystemInDarkTheme()) counterTextDarkTheme else counterTextLightTheme
	)
}

@Composable
fun IndicatorDescription(description: String) {
	Text(
		text = description,
		fontSize = textTimeDescription
	)
}

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
		verticalArrangement = Arrangement.Center,
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
		border = BorderStroke(0.dp, transparentColor)
	) {
		Text(
			text = text,
			modifier = Modifier.padding(large_12),
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

@ExperimentalAnimationApi
@Composable
fun NumPad(
	onClick: (String) -> Unit,
	onBackspace: () -> Unit,
	timeStringLength: Int,
) {
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
	Column(
		modifier = Modifier.padding(medium_8)
	) {
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