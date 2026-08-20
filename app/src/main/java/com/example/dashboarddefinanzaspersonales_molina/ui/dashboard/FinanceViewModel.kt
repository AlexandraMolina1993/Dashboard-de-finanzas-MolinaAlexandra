package com.example.dashboarddefinanzaspersonales_molina.ui.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * 2. VIEWMODEL
 * Lógica de negocio y manejo del estado reactivo.
 */
class FinanceViewModel : ViewModel() {

    // Estado interno (Mutable)
    private val _uiState = MutableStateFlow(FinanceUiState())
    // Estado expuesto a la UI (Inmutable)
    val uiState: StateFlow<FinanceUiState> = _uiState.asStateFlow()

    init {
        loadMockData()
    }

    fun onEvent(event: FinanceUiEvent) {
        when (event) {
            is FinanceUiEvent.RefreshData -> loadMockData()
            is FinanceUiEvent.AddMovement -> {
                // Aquí iría la lógica para agregar un movimiento y recalcular
            }
        }
    }

    private fun loadMockData() {
        _uiState.update { it.copy(isLoading = true) }
        
        // Simulamos datos iniciales
        val movements = listOf(
            Movement(1, "Sueldo", 2500.0, "20 Ago", MovementType.INCOME),
            Movement(2, "Supermercado", 150.5, "19 Ago", MovementType.EXPENSE),
            Movement(3, "Alquiler", 800.0, "10 Ago", MovementType.EXPENSE),
            Movement(4, "Venta Freelance", 300.0, "05 Ago", MovementType.INCOME),
            Movement(5, "Netflix", 15.99, "01 Ago", MovementType.EXPENSE)
        )

        val income = movements.filter { it.type == MovementType.INCOME }.sumOf { it.amount }
        val expense = movements.filter { it.type == MovementType.EXPENSE }.sumOf { it.amount }
        
        _uiState.update {
            it.copy(
                totalBalance = income - expense,
                monthlyIncome = income,
                monthlyExpense = expense,
                lastMovements = movements,
                isLoading = false
            )
        }
    }
}