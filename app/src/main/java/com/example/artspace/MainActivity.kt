package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.model.Art
import com.example.artspace.ui.theme.ArtSpaceTheme
import com.example.artspace.ui.theme.PurpleGrey80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                ArtSpaceApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}

@Composable
fun ArtSpaceApp() {

    var currentStep by remember {
        mutableIntStateOf(0)
    }

    val art = listOf(
        Art(
            title = "Colosseum",
            author = "John Smith",
            year = 2024,
            contentDescription = "Image of a colosseum",
            imageResource = R.drawable.image_1
        ),
        Art(
            title = "Some Street",
            author = "John Smith",
            year = 2024,
            contentDescription = "Image of a street",
            imageResource = R.drawable.image_2
        ),
        Art(
            title = "Library",
            author = "John Smith",
            year = 2024,
            contentDescription = "Image of a library",
            imageResource = R.drawable.image_3
        )
    )

    val currentArt = art[currentStep]

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ArtImage(
                imageResource = currentArt.imageResource,
                contentDescription = currentArt.contentDescription,
                modifier = Modifier.weight(1f)
            )
            ArtDescription(
                title = currentArt.title,
                author = currentArt.author,
                year = currentArt.year,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            NavigationButtons(
                currentStep = currentStep,
                lastStep = 2,
                onPreviousClick = {
                    currentStep -= 1
                },
                onNextClick = {
                    currentStep += 1
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }

    }
}

@Composable
fun ArtImage(
    @DrawableRes imageResource: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
            color = Color.White
        ) {
            Image(
                painter = painterResource(id = imageResource),
                modifier = Modifier.padding(24.dp),
                contentScale = ContentScale.FillWidth,
                contentDescription = contentDescription
            )
        }

    }
}

@Composable
fun ArtDescription(title: String, author: String, year: Int, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, color = PurpleGrey80) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 24.sp
            )
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(author)
                }
                append(" ")
                withStyle(style = SpanStyle(fontSize = 14.sp)) {
                    append("(${year})")
                }
            })
        }
    }
}

@Composable
fun NavigationButtons(
    currentStep: Int,
    lastStep: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onPreviousClick() },
            modifier = Modifier,
            enabled = currentStep > 0
        ) {
            Text(text = stringResource(id = R.string.previous))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = { onNextClick() },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            enabled = currentStep < lastStep
        ) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}
