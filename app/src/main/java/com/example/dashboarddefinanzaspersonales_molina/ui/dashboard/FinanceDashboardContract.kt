package com.example.dashboarddefinanzaspersonales_molina.ui.dashboard

import java.util.Date

/**
 * 1. STATE & EVENTS
 * Definición del estado de la UI y las acciones del usuario.
 */

// Modelo de datos para un movimiento
data class Movement(
    val id: Int,
    val description: String,
    val amount: Double,
    val date: String,
    val type: MovementType
)

enum class MovementType {
    INCOME, EXPENSE
}

// Estado de la UI
data class FinanceUiState(
    val totalBalance: Double = 0.0,
    val monthlyIncome: Double = 0.0,
    val monthlyExpense: Double = 0.0,
    val lastMovements: List<Movement> = emptyList(),
    val isLoading: Boolean = false
)

// Eventos (acciones) del usuario
sealed interface FinanceUiEvent {
    data object RefreshData : FinanceUiEvent
    data class AddMovement(val description: String, val amount: Double, val type: MovementType) : FinanceUiEvent
}