package com.example.bmi

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import com.example.bmi.ui.theme.bmiColor

@Composable
fun SecondScreen(navController: NavHostController, bmiNO: Float) {

    val categoryString= when {
        bmiNO < 18.5 -> "Underweight"
        bmiNO < 25 -> "Normal Weight"
        bmiNO < 30 -> "Overweight"
        bmiNO < 35 -> "Obesity Class I"
        bmiNO < 40 -> "Obesity Class " + "II"
        else -> "Obesity Class " + "III"
    }
    val bmiAnimatable = remember { Animatable(0f) }
    val category = remember { Animatable(0F) }
    LeftToRightLayout{
        Column(
            Modifier
                .fillMaxSize()
                .background(bmiColor.lightGreen), Arrangement.Top, Alignment.CenterHorizontally
        ) {

            Row(Modifier.fillMaxWidth(.95f)) {
                IconButton(
                    onClick = { navController.navigate(home.route) },
                    Modifier
                        .padding(10.dp)
                        .background(
                            bmiColor.green,
                            RoundedCornerShape(50)
                        )
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Decrement"
                    )

                }
            }
            Circle(LaunchedEffect(bmiNO) {
                bmiAnimatable.animateTo(bmiNO)
                val bmiValue = bmiAnimatable.value

                category.animateTo(
                    when {
                        bmiValue < 18.5 -> 0f // Under 18.5
                        bmiValue < 25 -> 1f // 18.5 - 24.9
                        bmiValue < 30 -> 2f // 25-29.9
                        bmiValue < 35 -> 3f
                        bmiValue < 40 -> 4f// 30-34.9
                        else -> 5f // 35+
                    }
                )

            }, bmiAnimatable, category)


            Text(
                text = "${bmiNO.round(2)} BMI",
                modifier = Modifier,
                color = getColorBMI(bmiNO),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = " $categoryString",
                modifier = Modifier,
                color = getColorBMI(bmiNO),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
            AdviceCard(bmiNO)


        }
    }

}

fun getColorBMI(bmiNO:Float): Color {
    return when {
        bmiNO < 18.5 -> bmiColor.blue
        bmiNO < 25 -> bmiColor.green
        bmiNO < 30 -> bmiColor.orange
        bmiNO < 35 -> bmiColor.red
        bmiNO < 40 -> bmiColor.darkRed
        else -> bmiColor.darkOrange }
}

@Composable
fun AdviceCard(bmiNO: Float) {
    Card(shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            getColorBMI(bmiNO)
            ,
            contentColor = Color.White
        )


    ){
        Column (
            Modifier
                .fillMaxWidth()
                .padding(10.dp)){

            Row(Modifier,Arrangement.Start,Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .background(
                        bmiColor.lightGreen,
                        RoundedCornerShape(50)
                    )
                    .padding(5.dp)){
                    Icon(
                        painter = painterResource(id =R.drawable.baseline_lightbulb_24 ),
                        contentDescription = "Localized description",
                        modifier = Modifier
                            .size(30.dp),
                        tint = getColorBMI(bmiNO),
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "ADVICE",modifier = Modifier,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = getAdviceForBMI(20f),modifier = Modifier,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold)

        }

    }

}




fun Float.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun getAdviceForBMI(bmiNO: Float): String {
    return when {
        bmiNO < 18.5 -> {
            """
            |Underweight:
            |1. Increase calorie intake: Focus on nutrient-dense foods.
            |2. Eat frequently: Consume small, frequent meals.
            |3. Include healthy fats: Avocados, nuts, and olive oil.
            |4. Resistance training: Build muscle mass.
            |5. Consult a healthcare professional for personalized guidance.
            """.trimMargin()
        }
        bmiNO < 25 -> {
            """
            |Normal Weight:
            |1. Maintain balanced diet: Fruits, vegetables, lean proteins, and whole grains.
            |2. Stay active: Regular physical activity.
            |3. Monitor portion sizes: Be mindful of portion sizes.
            |4. Stay hydrated: Drink plenty of water.
            |5. Practice stress management techniques.
            """.trimMargin()
        }
        bmiNO < 30 -> {
            """
            |Overweight:
            |1. Adopt a balanced diet: Whole, unprocessed foods.
            |2. Increase physical activity: Regular exercise.
            |3. Portion control: Be mindful of portions.
            |4. Set realistic goals: Establish achievable weight loss goals.
            |5. Seek support: Consider joining a weight loss program.
            """.trimMargin()
        }
        bmiNO < 35 -> {
            """
            |Obesity Class I:
            |1. Set achievable goals: Break down weight loss goals.
            |2. Focus on lifestyle changes: Sustainable diet and exercise habits.
            |3. Prioritize sleep: Aim for 7-9 hours of quality sleep.
            |4. Practice mindful eating: Pay attention to hunger cues.
            |5. Stay consistent: Stick to healthy habits consistently.
            """.trimMargin()
        }
        bmiNO < 40 -> {
            """
            |Obesity Class II:
            |1. Medical evaluation: Consult with a healthcare provider.
            |2. Consider professional support: Explore weight loss surgery options.
            |3. Lifestyle modifications: Implement sustainable changes.
            |4. Mental health support: Address psychological factors.
            |5. Long-term management: Develop strategies for long-term weight management.
            """.trimMargin()
        }
        else -> {
            """
            |Obesity Class III:
            |1. Medical evaluation: Comprehensive medical evaluation.
            |2. Consider weight loss surgery: Explore surgical options.
            |3. Implement lifestyle changes: Sustainable diet and exercise.
            |4. Address mental health: Seek counseling or therapy.
            |5. Long-term management: Develop strategies for ongoing weight management.
            """.trimMargin()
        }
    }
}






@Composable
fun Circle(
    launchedEffect: Unit,
    bmiAnimatable: Animatable<Float, AnimationVector1D>,
    category: Animatable<Float, AnimationVector1D>
) {

    launchedEffect

    Column(
        Modifier
            .fillMaxWidth()
            .background(bmiColor.lightGreen),Arrangement.Center,Alignment.CenterHorizontally) {
//        Text(
//            text = "${(bmiAnimatable.value.round(1))} BMI",
//            modifier = Modifier,
//            color = bmiColor.bmiBody,
//            fontSize = 42.sp,
//        )

        Box(Modifier.background(bmiColor.lightGreen)) {
            Canvas(
                modifier = Modifier
                    .padding(10.dp)
                    .size(300.dp, 200.dp)
                    .clipToBounds()
                    .background(bmiColor.lightGreen)
            ) {


                drawArc(
                    color = bmiColor.bmiBody,
                    startAngle = 180f,
                    sweepAngle = 180f * 1f,
                    useCenter = true,
                    size = Size(size.width, size.width)
                )
                drawArc(
                    color = bmiColor.blue,
                    startAngle = 180f,
                    sweepAngle = 180f * 0.165f,
                    useCenter = true,
                    size = Size(size.width, size.width)
                )
                drawArc(
                    color = bmiColor.green,
                    startAngle = 210f,
                    sweepAngle = 180f * 0.165f,
                    useCenter = true,
                    size = Size(size.width, size.width)
                )
                drawArc(
                    color = bmiColor.orange,
                    startAngle = 240f,
                    sweepAngle = 180f * 0.165f,
                    useCenter = true,
                    size = Size(size.width, size.width)
                )

                drawArc(
                    color = bmiColor.darkOrange,
                    startAngle = 240f,
                    sweepAngle = 180f * 0.165f,
                    useCenter = true,
                    size = Size(size.width, size.width)
                )

                drawArc(
                    color = bmiColor.red,
                    startAngle = 270f,
                    sweepAngle = 180f * 0.165f,
                    useCenter = true,
                    size = Size(size.width, size.width)
                )
                drawArc(
                    color = bmiColor.darkRed,
                    startAngle = 300f,
                    sweepAngle = 180f * 0.165f,
                    useCenter = true,
                    size = Size(size.width, size.width)
                )
                drawArc(
                    color = Color.DarkGray,
                    startAngle = 330f,
                    sweepAngle = 180f * 0.165f,
                    useCenter = true,
                    size = Size(size.width, size.width)
                )
                translate(size.width/6 , size.height / 2.8F - 50) {
                    drawArc(
                        color = bmiColor.lightGreen,
                        startAngle = 180f,
                        sweepAngle = 180f * 1f,
                        useCenter = true,
                        size = Size(size.width /1.5f, size.width /1.5f),


                        )
                    translate(size.width / 4F, size.height/ 2.8F - 10) {
                        drawArc(
                            color = bmiColor.bmiPointer,
                            startAngle = 0f,
                            sweepAngle = 360f * 1f,
                            useCenter = true,
                            size = Size(size.width / 7, size.width / 7),


                            )


                    }


                }
                val startPoint = Offset(50F, size.width / 2 - 10F)
                val centerPoint = Offset(size.width / 2, size.height - 80F)
                val numberOfStages = 6
                val degreesPerStage = 180f / numberOfStages
                val startDegree = category.value * degreesPerStage


                val rotatedStartPoint =
                    rotatePoint(startPoint, centerPoint, startDegree + bmiAnimatable.value / 4.5f)

                drawLineWithPointer(
                    color = bmiColor.bmiPointer,
                    start = Offset(size.width / 2, size.width / 2 - 0F),
                    end = rotatedStartPoint,
                    strokeWidth = 35.0f
                )


            }


        }
    }




}
