package com.example.androiddevchallenge.ui.view

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.core.CountDown
import com.example.androiddevchallenge.ui.theme.*

const val TAG = " >>>>>>>>> mainView"
const val NO_TIME = "00"
const val TIME_SEPARATOR = ":"

@Composable
fun MainView() {
	
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
			//val time = calculateTime(counter.value)
			val time = calculateNotFormatTime(counter.value)
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


@Composable
fun Fab(visible: Boolean) {
	if (visible) {
		FloatingActionButton(
			onClick = {}
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
		modifier = Modifier.padding(small),
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
		modifier = Modifier.padding(small),
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
						Log.d(TAG, "seconds: $seconds")
					}
					in 100..999 -> {
						minutes = time.subSequence(0, 1).toString().toInt()
						seconds = time.subSequence(1, 3).toString().toInt()
						Log.d(TAG, "minutes: $minutes")
						Log.d(TAG, "seconds: $seconds")
					}
					in 1000..9999 -> {
						minutes = time.subSequence(0, 2).toString().toInt()
						seconds = time.subSequence(2, 4).toString().toInt()
						Log.d(TAG, "minutes: $minutes")
						Log.d(TAG, "seconds: $seconds")
					}
					in 10000..99999 -> {
						hours = time.subSequence(0, 1).toString().toInt()
						minutes = time.subSequence(1, 3).toString().toInt()
						seconds = time.subSequence(3, 5).toString().toInt()
						Log.d(TAG, "hours: $hours")
						Log.d(TAG, "minutes: $minutes")
						Log.d(TAG, "seconds: $seconds")
					}
					in 100000..1000000 -> {
						hours = time.subSequence(0, 2).toString().toInt()
						minutes = time.subSequence(2, 4).toString().toInt()
						seconds = time.subSequence(4, 6).toString().toInt()
						Log.d(TAG, "hours: $hours")
						Log.d(TAG, "minutes: $minutes")
						Log.d(TAG, "seconds: $seconds")
					}
				}
				
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
	
	Log.d(TAG, "timeIndicator: $hours")
	
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
			modifier = Modifier.padding(large),
			textAlign = TextAlign.Center,
			fontSize = numPadSizeNumber,
		)
	}
}

@Composable
fun PadIconButton(
	onClick: () -> Unit,
	enabled: Boolean = false
) {
	if (enabled) {
		IconButton(
			onClick = onClick,
			enabled = enabled
		) {
			Icon(
				imageVector = Icons.Outlined.Backspace,
				contentDescription = "Delete timer",
				tint = if (isSystemInDarkTheme()) Color.White else Color.DarkGray
			)
		}
	} else {
		IconButton(
			onClick = onClick,
			enabled = enabled
		) {
			Icon(
				imageVector = Icons.Outlined.Backspace,
				contentDescription = "Delete timer",
				tint = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
			)
		}
	}
}

@Composable
fun NumPad(
	onClick: (String) -> Unit,
	onBackspace: () -> Unit,
	timeStringLength: Int
) {
	Column(modifier = Modifier.padding(medium)) {
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "1",
				onClick = {
					onClick("1")
				},
			)
		}
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "4",
				onClick = {
					onClick("4")
				},
			)
		}
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "7",
				onClick = {
					onClick("7")
				},
			)
		}
	}
	Column(
		modifier = Modifier.padding(medium)
	) {
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "2",
				onClick = {
					onClick("2")
				},
			)
		}
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "5",
				onClick = {
					onClick("5")
				},
			)
		}
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "8",
				onClick = {
					onClick("8")
				},
			)
		}
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "0",
				onClick = {
					onClick("0")
				},
			)
		}
	}
	Column(modifier = Modifier.padding(medium)) {
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "3",
				onClick = {
					onClick("3")
				},
			)
		}
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "6",
				onClick = {
					onClick("6")
				},
			)
		}
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadButton(
				text = "9",
				onClick = {
					onClick("9")
				},
			)
		}
		Row(
			modifier = Modifier.padding(
				start = large,
				top = small,
				end = large,
				bottom = small
			)
		) {
			PadIconButton(
				onClick = {
					onBackspace()
				}, enabled = timeStringLength > 0
			)
		}
	}
}