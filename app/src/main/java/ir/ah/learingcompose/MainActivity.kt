package ir.ah.learingcompose

import android.os.Bundle
import android.view.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.*


import ir.ah.learingcompose.ui.theme.LearingComposeTheme
import kotlinx.coroutines.*
import java.lang.reflect.*
import kotlin.math.*
import kotlin.random.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(2.dp, Color.Green, RoundedCornerShape(10.dp))
                        .padding(20.dp)
                ) {
                    var value by remember {
                      mutableStateOf(0f)
                    }
                    val barCount =20
                    MusicKnob(
                        Modifier.size(100.dp)
                    ){
                        value =it
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    ValueBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        activeBars =(barCount*value).toInt(),
                        barCount = barCount
                    )

                }

            }


        }
    }
}

@Composable
fun ValueBar(
    modifier: Modifier = Modifier,
    activeBars: Int = 0,
    barCount: Int = 0
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val barWidth = remember {
            constraints.maxWidth / (2f * barCount)
        }
        Canvas(modifier = modifier) {
            for (i in 0 until barCount) {
                drawRoundRect(
                    color = if (i in 0..activeBars) Color.Green else Color.DarkGray,
                    topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                    size = Size(barWidth, constraints.maxHeight.toFloat()),
                    cornerRadius = CornerRadius(0f)
                )
            }
        }

    }

}


@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    onValueChange: (Float) -> Unit,

    ) {
    var rotation by remember {
        mutableStateOf(limitingAngle)
    }
    var touchX by remember {
        mutableStateOf(0f)
    }
    var touchY by remember {
        mutableStateOf(0f)
    }
    var centerX by remember {
        mutableStateOf(0f)
    }
    var centerY by remember {
        mutableStateOf(0f)
    }
    Image(
        painter = painterResource(id = R.drawable.knob),
        contentDescription = "music knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val windowBounds = it.boundsInParent()
                centerX = windowBounds.size.width / 2f
                centerY = windowBounds.size.width / 2f

            }
            .pointerInteropFilter { event ->
                touchX = event.x
                touchY = event.y
                val angle = -atan2(centerX - touchX, centerY - touchY) * (180f / PI).toFloat()
                when (event.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                        if (angle !in -limitingAngle..limitingAngle) {
                            val fixedAngle = if (angle in -180f..-limitingAngle) {
                                360f + angle
                            } else {
                                angle
                            }
                            rotation = fixedAngle

                            val percent = (fixedAngle - limitingAngle) / (360f - 2 * limitingAngle)
                            onValueChange(percent)
                            true
                        } else false
                    }
                    else -> false
                }

            }
            .rotate(rotation)
    )

}


@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (curPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }

}


@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    colorUpdate: (Color) -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.Red)
            .clickable {
                colorUpdate(
                    Color(
                        Random.nextFloat(),
                        Random.nextFloat(),
                        Random.nextFloat(),
                        1f
                    )
                )

            })

}


@Composable
fun ImageCard(
    painter: Painter,
    title: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {


    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(15.dp),
        elevation = 4.dp
    ) {
        Box(modifier = modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 300f
                    )
                )
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(text = title, style = TextStyle(color = Color.White), fontSize = 16.sp)
        }

    }


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LearingComposeTheme {
        val painter = painterResource(id = R.drawable.food1)
        val title = "Asghar hosseini"
        val contentDescription = "Asghar hosseini"

        Box(
            modifier =
            Modifier
                .fillMaxSize(0.5f)
                .padding(16.dp)
        ) {
            ImageCard(painter = painter, title = title, contentDescription = contentDescription)
        }
    }
}