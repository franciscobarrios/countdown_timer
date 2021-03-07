package com.example.androiddevchallenge.ui.amin

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import kotlin.math.PI
import kotlin.math.sin

// Animate reveal fab button
// Animate reveal delete button

@Composable
fun DrawWave() {
	
	val infiniteTransition = rememberInfiniteTransition()
	val value by infiniteTransition.animateFloat(
		initialValue = 0f,
		targetValue = (2f * PI).toFloat(),
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 1000, easing = LinearEasing)
		)
	)
	
	Canvas(
		modifier = Modifier.fillMaxSize(),
		onDraw = {
			
			val points = mutableListOf<Offset>()
			for (x in 0 until size.width.toInt()) {
				val y = (sin(x.toFloat()) * value)
				points.add(Offset(x.toFloat(), y))
			}
			
			drawPoints(
				strokeWidth = 8f,
				points = points,
				pointMode = PointMode.Lines,
				color = Color.Cyan
			)
		}
	)
}

//val middleW = size.width / 2
//			val middleH = size.height / 2
//

/*	val value by animateFloatAsState(
		targetValue = 2.5f,
		animationSpec = keyframes {
			durationMillis = 3000
			0.0f at 0 with LinearOutSlowInEasing
			2f at 1500 with FastOutLinearInEasing
			0.0f at 3000 with LinearOutSlowInEasing
		},
		finishedListener = {
			finishAnimation = it
		}
	)
	*/