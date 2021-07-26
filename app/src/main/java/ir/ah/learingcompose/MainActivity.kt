package ir.ah.learingcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import ir.ah.learingcompose.ui.theme.LearingComposeTheme
import java.lang.reflect.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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