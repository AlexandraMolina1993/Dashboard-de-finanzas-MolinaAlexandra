package com.example.dashboarddefinanzaspersonales_molina.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dashboarddefinanzaspersonales_molina.ui.theme.DashboardDeFinanzasPersonalesMOLINATheme

import java.util.Locale

/**
 * 3. UI COMPOSABLES
 * Construcción de la interfaz utilizando componentes pequeños y reutilizables.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceDashboardScreen(
    viewModel: FinanceViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Billetera", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(FinanceUiEvent.RefreshData) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refrescar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // 1. Tarjeta de Saldo Total
            BalanceCard(uiState.totalBalance)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 2. Resumen Mensual
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SummaryCard(
                    label = "Ingresos",
                    amount = uiState.monthlyIncome,
                    color = Color(0xFF4CAF50),
                    icon = Icons.Default.ArrowUpward,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    label = "Egresos",
                    amount = uiState.monthlyExpense,
                    color = Color(0xFFF44336),
                    icon = Icons.Default.ArrowDownward,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Título de Movimientos
            Text(
                text = "Últimos movimientos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Lista de Movimientos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.lastMovements) { movement ->
                    MovementItem(movement)
                }
            }
        }
    }
}

@Composable
fun BalanceCard(balance: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Saldo Total", style = MaterialTheme.typography.labelLarge)
            Text(
                text = "$${String.format(Locale.getDefault(), "%.2f", balance)}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun SummaryCard(
    label: String,
    amount: Double,
    color: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = color)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            Text(
                text = "$${String.format(Locale.getDefault(), "%.1f", amount)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
fun MovementItem(movement: Movement) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    if (movement.type == MovementType.INCOME) Color(0xFF4CAF50).copy(alpha = 0.1f)
                    else Color(0xFFF44336).copy(alpha = 0.1f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (movement.type == MovementType.INCOME) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                contentDescription = null,
                tint = if (movement.type == MovementType.INCOME) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(text = movement.description, fontWeight = FontWeight.SemiBold)
            Text(text = movement.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        
        Text(
            text = "${if (movement.type == MovementType.INCOME) "+" else "-"}$${movement.amount}",
            fontWeight = FontWeight.Bold,
            color = if (movement.type == MovementType.INCOME) Color(0xFF4CAF50) else Color(0xFFF44336)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FinanceDashboardPreview() {
    DashboardDeFinanzasPersonalesMOLINATheme {
        FinanceDashboardScreen()
    }
}