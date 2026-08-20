package com.example.dashboarddefinanzaspersonales_molina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.dashboarddefinanzaspersonales_molina.ui.dashboard.FinanceDashboardScreen
import com.example.dashboarddefinanzaspersonales_molina.ui.theme.DashboardDeFinanzasPersonalesMOLINATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardDeFinanzasPersonalesMOLINATheme {
                FinanceDashboardScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinanceDashboardPreviewMain() {
    DashboardDeFinanzasPersonalesMOLINATheme {
        FinanceDashboardScreen()
    }
}
