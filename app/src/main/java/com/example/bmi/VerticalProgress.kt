package com.example.bmi


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas


import androidx.compose.foundation.layout.size

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bmi.ui.theme.bmiColor

@Composable
fun VerticalProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = bmiColor.green,
    backgroundColor: Color = Color.LightGray,
    size: Size = Size(width = 40f, height = 200f),
    animationDurationMillis: Int = 1000,
    cornerRadius: Dp = 4.dp,
    pointerWidth: Dp = 10.dp,
    pointerHeight: Dp = 20.dp,
) {
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = animationDurationMillis), label = ""
    )

    Canvas(
        modifier = modifier
            .size(size.width.dp, size.height.dp)
    ) {

        drawRoundRect(
            color = backgroundColor,
            size = Size(width = size.width.dp.toPx(), height = size.height.dp.toPx()),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )
        // Progress made
        drawRoundRect(
            color = color,
            size = Size(size.width.dp.toPx(), height = (animatedProgress.value * size.height).dp.toPx()),
            topLeft = Offset(0.dp.toPx(), ((1 - animatedProgress.value) * size.height).dp.toPx()),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        )

        // Pointer
        val pointerTop =  ((1 - animatedProgress.value) * size.height -9).dp.toPx()
        translate(-50F,0F){
            drawPath(
                path = Path().apply {
                    moveTo(0f, pointerTop)
                    lineTo(pointerWidth.toPx(), pointerTop + (pointerHeight / 2).toPx())
                    lineTo(0f, pointerTop + pointerHeight.toPx())
                    close()

                },
                color = bmiColor.green,
                        style = Stroke(
                        width = 8.dp.toPx(),
                pathEffect = PathEffect.cornerPathEffect(1.dp.toPx())
            ))

        }

    }
}


