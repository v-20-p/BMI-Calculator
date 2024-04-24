package com.example.bmi

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bmi.ui.theme.bmiColor


@Composable
fun HomeScreen(navController: NavHostController) {

    var height by remember { mutableIntStateOf(170) }
    var weight by remember { mutableIntStateOf(60) }
    var age by remember { mutableIntStateOf(18) }

    fun bmiStates(ch:String){
        if (ch=="h+"){
            height++
        }else if (ch=="h-"){
            height--
            if(height<102){
                height=102
            }
        }else if (ch=="w+"){
            weight++
        }else if(ch=="w-"){
            weight--
        }else if (ch=="a+"){
            age++
        }else if(ch=="a-"){
            age--
        }
    }
    val bmiAnimatable = remember { Animatable(0f) }
    val category = remember { Animatable(0F) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bmiColor.lightGreen
    ) {
        LeftToRightLayout{
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Card(
                )
                {


                    Circle(height, weight,    LaunchedEffect(weight, height) {
                        bmiAnimatable.animateTo((weight / (height/100f * height/100f)))
                        val bmiValue = bmiAnimatable.value

                        category.animateTo( when {
                            bmiValue < 18.5 -> 0f
                            bmiValue < 25 -> 1f
                            bmiValue < 30 -> 2f
                            bmiValue < 35 -> 3f
                            bmiValue < 40 -> 4f
                            else -> 5f
                        })

                    },bmiAnimatable,category)

                }
                Row {
                    var isMaleSelected by remember { mutableStateOf(false) }
                    var isFemaleSelected by remember { mutableStateOf(false) }


                    Gender(
                        isSelected = isMaleSelected,
                        modifier = Modifier.padding(8.dp),
                        onGenderSelected = {
                            isMaleSelected = true; isFemaleSelected = false
                        },
                        gender = "Male"
                    )
                    Gender(
                        isSelected = isFemaleSelected,
                        modifier = Modifier.padding(8.dp),
                        onGenderSelected = {
                            isFemaleSelected = true; isMaleSelected = false
                        },
                        gender = "Female"
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Row {

                    HeightState(
                        "Height",
                        height,
                        { bmiStates("h+") },
                        { bmiStates("h-") })
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier,
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        HeightState(
                            "Weight",
                            weight,
                            { bmiStates("w+") },{ bmiStates("w-") })
                        Spacer(modifier = Modifier.height(20.dp))
                        HeightState("Age", age, { bmiStates("a+") }, { bmiStates("a-") })


                    }






                }

                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier, Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Show(NavHostController=navController,bmiAnimatable=bmiAnimatable)



                }


            }
        }

    }
}
fun Float.toRadians(): Float {
    return (this * kotlin.math.PI / 180).toFloat()
}
fun rotatePoint(point: Offset, center: Offset, angleDegrees: Float): Offset {
    val angleRadians = angleDegrees.toRadians()
    val cosAngle = kotlin.math.cos(angleRadians)
    val sinAngle = kotlin.math.sin(angleRadians)
    val newX = center.x + (point.x - center.x) * cosAngle - (point.y - center.y) * sinAngle
    val newY = center.y + (point.x - center.x) * sinAngle + (point.y - center.y) * cosAngle
    return Offset(newX, newY)
}


@Composable
fun Circle(
    height: Int,
    weight: Int,
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
                translate(size.width / 3F, size.height / 1.5F - 50) {
                    drawArc(
                        color = bmiColor.lightGreen,
                        startAngle = 180f,
                        sweepAngle = 180f * 1f,
                        useCenter = true,
                        size = Size(size.width / 3, size.width / 3),


                        )
                    translate(size.width / 10.5F, 35f) {
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

@Composable
fun LeftToRightLayout(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        content()
    }
}




@Composable
fun HeightState(name: String, height: Int, onPlusClick: () -> Unit, onMinusClick: () -> Unit){


    Card(shape = RoundedCornerShape(20.dp),
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            bmiColor.lightGreen
            ,
            contentColor = bmiColor.green
        )

    ) {
        Column(
            modifier = Modifier.padding(0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = name, style = MaterialTheme.typography.headlineSmall,fontWeight = FontWeight.SemiBold)




            if (name=="Height")
                Row {


                    Column(modifier = Modifier.padding(0.dp,20.dp),Arrangement.Center,Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(26.dp))
                        RepeatingButton(onClick = { onPlusClick() }) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Decrement",Modifier.size(30.dp))
                        }

                        Row(
                            Modifier
                                .size(width = 100.dp, height = 80.dp), Arrangement.Center, Alignment.CenterVertically
                        ) {
                            Text(text = "$height", style = MaterialTheme.typography.displaySmall,fontWeight = FontWeight.ExtraBold)

                            Column(Modifier.padding(3.dp, 15.dp, 0.dp, 0.dp), Arrangement.Center) {
                                Text(
                                    text = if (name == "Height")
                                        "cm"
                                    else {
                                        "KG"
                                    }, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold
                                )

                            }
                        }

                        RepeatingButton(onClick = { onMinusClick() }) {

                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Increment")
                        }


                    }

                    VerticalProgress(
                        progress =height/220f,
                        modifier = Modifier.padding(15.dp,25.dp),
                        size = Size(width = 13f, height = 220f)
                    )

                }

            else
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
//                modifier= Modifier.width(100.dp)
                ) {
                    RepeatingButton(onClick = {onMinusClick()}  ){
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Decrement")
                    }
                    Row(
                        Modifier.size(95.dp)
                        , Arrangement.Center,Alignment.CenterVertically) {
                        Text(text = "$height",style = MaterialTheme.typography.displaySmall,fontWeight = FontWeight.ExtraBold)

                        Column(Modifier.padding(3.dp,15.dp,0.dp,0.dp),Arrangement.Center){
                            Text(
                                text = if (name == "Height")
                                    "cm"
                                else if (name == "Weight") {
                                    "KG"
                                } else {
                                    ""

                                }, style = MaterialTheme.typography.bodySmall,fontWeight = FontWeight.Bold
                            )

                        }
                    }

                    RepeatingButton(onClick = {onPlusClick()}){
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Increment")
                    }
                }

        }
    }
}


@Composable
fun Gender(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onGenderSelected: () -> Unit,
    gender: String
) {
    val cardColor = if (isSelected)
        CardDefaults.cardColors(
            containerColor = bmiColor.orange,
            contentColor = Color.White
        )

    else CardDefaults.cardColors(
        containerColor = bmiColor.green,
        contentColor = Color.White
    )


    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .clickable { onGenderSelected() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = cardColor,
    )

    {

        Row(
            Modifier
                .size(170.dp,70.dp)
            , Arrangement.Center,Alignment.CenterVertically) {
            Box(modifier = Modifier.background(bmiColor.lightGreen,
                RoundedCornerShape(50)
            ).padding(5.dp)){
                Icon(
                    painter = painterResource(id = if (gender == "Male") R.drawable.baseline_male_24 else R.drawable.baseline_female_24),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .size(30.dp),
                    tint = bmiColor.green,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = gender,modifier=Modifier,  style = MaterialTheme.typography.headlineSmall)
        }
    }
}
@Composable
fun Show(NavHostController: NavHostController, bmiAnimatable: Animatable<Float, AnimationVector1D>) {
    val cardColor = CardDefaults.cardColors(
        containerColor = bmiColor.orange,
        contentColor = Color.White
    )


    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.clickable {
            NavHostController.navigate(second.route+"/"+bmiAnimatable.value )
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = cardColor,
    )

    {

        Row(
            Modifier.fillMaxWidth(.9f)

            , Arrangement.Center,Alignment.CenterVertically) {
            Text(text = "Next",modifier=Modifier.padding(15.dp),  style = MaterialTheme.typography.headlineSmall)
        }
    }
}
