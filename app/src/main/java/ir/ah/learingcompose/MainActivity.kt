package ir.ah.learingcompose

import android.os.Bundle
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*


import ir.ah.learingcompose.ui.theme.LearingComposeTheme
import kotlinx.coroutines.*
import java.lang.reflect.*
import kotlin.random.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var sizeState by remember { mutableStateOf(200.dp) }
            val size by animateDpAsState(
                targetValue = sizeState,
                  tween(durationMillis = 3000, delayMillis = 300, easing = LinearOutSlowInEasing)
                //  spring(Spring.DampingRatioHighBouncy)
                   /* keyframes { durationMillis =5000
                        sizeState at 0 with LinearEasing
                        sizeState * 1.5f at 1000 with FastOutLinearInEasing
                        sizeState * 2f at 5000
                    }*/


            )
            val infiniteTransition = rememberInfiniteTransition()
            val color by infiniteTransition.animateColor(
                initialValue = Color.Black,
                targetValue = Color.White,
                animationSpec = infiniteRepeatable(
                    tween(durationMillis = 3000, delayMillis = 300, easing = LinearOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse

                )
            )


            Box(
                modifier = Modifier
                    .size(size)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    sizeState += 50.dp
                }) {
                    Text(text = "go")

                }
            }


        }
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