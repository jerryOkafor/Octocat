package com.jerryokafor.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jerryokafor.compose.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ComposeApp() }
    }
}

@Composable
fun ComposeApp() {
    AppTheme {
        Surface(color = MaterialTheme.colors.background) {}
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        ComposeApp()
    }
}