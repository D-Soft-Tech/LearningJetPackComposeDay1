package com.example.jetpackcompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                MainActivityScreen()
            }
        }
    }
}

@Composable
fun ButtonsLayout(names: List<String>) {
    LazyColumn(
        content = {
            items(names) {
                LayoutWithButton(name = it)
            }
        }
    )
}

@Preview("lightModeOfButtons", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewLayoutLightMode() {
    JetpackComposeTheme {
        ButtonsLayout(names = listOf("Adebayo", "Jetpack Compose"))
    }
}

@Composable
fun OnBoardingScreen(shouldShowOnboardingCallBack: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to Basics Codelab!")
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { shouldShowOnboardingCallBack() }) {
                Text(text = "Continue")
            }
        }
    }
}

@Preview(name = "stateHoisting", showBackground = true)
@Composable
fun MainActivityScreen() {
    JetpackComposeTheme {
        var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
        if (shouldShowOnboarding) OnBoardingScreen {
            shouldShowOnboarding = false
        } else ButtonsLayout(
            names = (1..100).map { it.toString() }.toList()
        )
    }
}

@Composable
fun LayoutWithButton(name: String) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        targetValue = if (isExpanded) 10.dp else 0.dp
    )
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            OutlinedButton(onClick = { isExpanded = !isExpanded }) {
                Text(text = if (!isExpanded) "Show more" else "Show less")
            }
        }
    }
}
