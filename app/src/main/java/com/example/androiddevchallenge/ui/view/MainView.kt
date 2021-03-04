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
import com.example.androiddevchallenge.ui.theme.*
import kotlin.time.Duration

const val TAG = " >>>>>>>>> mainView"

@Composable
fun mainView() {
	
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
			timeIndicator(counter.value)
		}
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f),
			horizontalArrangement = Arrangement.Center,
		) {
			numPad(onClick = {
				counter.value += it
			})
		}
	}
}


@Composable
fun fab(visible: Boolean) {
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
fun textNumberIndicator(text: String) {
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
fun textNumberSeparator(text: String) {
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
fun indicatorDescription(description: String) {
	Text(
		text = description,
		fontSize = textTimeDescription
	)
}

@Composable
fun timeIndicator(time: String) {
	
	
	if (time != "") {
		val timeInt = time.toInt()
		
		Log.d(TAG, "mainView: ${timeInt}")
		
		
		
		
		
		
		
		
		
		
		var seconds = 0
		var minutes = 0
		var hours = 0
		when (timeInt) {
			in 1..59 -> {
				seconds = timeInt
			}
			in 60..3599 -> {
				minutes = timeInt
			}
			in 3600..356400 -> {
				hours = timeInt
			}
		}
		
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			textNumberIndicator(text = if (hours != 0) hours.toString() else "00")
			indicatorDescription("hours")
		}
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			textNumberSeparator(text = ":")
		}
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			textNumberIndicator(text = if (minutes != 0) minutes.toString() else "00")
			indicatorDescription("minutes")
		}
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			textNumberSeparator(text = ":")
		}
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			textNumberIndicator(text = if (seconds != 0) seconds.toString() else "00")
			indicatorDescription("seconds")
		}
		
	}
	
}

@Composable
fun padButton(
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
fun padIconButton(
	onClick: () -> Unit,
) {
	IconButton(
		onClick = onClick
	) {
		Icon(
			imageVector = Icons.Outlined.Backspace,
			contentDescription = "Delete timer",
			tint = Color.White
		)
	}
}

@Composable
fun numPad(
	onClick: (String) -> Unit
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
			padButton(
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
			padButton(
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
			padButton(
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
			padButton(
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
			padButton(
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
			padButton(
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
			padButton(
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
			padButton(
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
			padButton(
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
			padButton(
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
			padIconButton(
				onClick = {}
			)
		}
	}
}