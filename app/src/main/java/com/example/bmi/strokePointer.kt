package com.example.bmi

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.drawLineWithPointer(
    color: Color,
    start: Offset,
    end: Offset,
    strokeWidth: Float,
    pointerLength: Float = strokeWidth * 2,
    pointerAngleDegrees: Float = 30f
) {

    drawLine(color, start, end, strokeWidth = strokeWidth)

    // Calculate the angle of the line
    val angle = kotlin.math.atan2((end.y - start.y).toDouble(), (end.x - start.x).toDouble())

    // Calculate the coordinates of the pointer
    val pointerEndX = end.x - pointerLength * cos(angle - PI.toFloat() / pointerAngleDegrees)
    val pointerEndY = end.y - pointerLength * sin(angle - PI.toFloat() / pointerAngleDegrees)
    val pointerStartX = end.x - pointerLength * cos(angle + PI.toFloat() / pointerAngleDegrees)
    val pointerStartY = end.y - pointerLength * sin(angle + PI.toFloat() / pointerAngleDegrees)


    drawPath(
        Path().apply {
            moveTo(end.x, end.y)
            lineTo(pointerEndX.toFloat(), pointerEndY.toFloat())
            lineTo(pointerStartX.toFloat(), pointerStartY.toFloat())
            close()
        },
        color = color,
        style = Stroke(width = strokeWidth)
    )
}
