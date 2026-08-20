package com.example.dashboarddefinanzaspersonales_molina.ui.dashboard

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FinanceViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando el ViewModel inicia, carga los datos mock y calcula el saldo correctamente`() {
        // Given: El ViewModel se inicializa (el init dispara loadMockData)
        val viewModel = FinanceViewModel()
        val state = viewModel.uiState.value

        // When: Se filtran los datos mock que pusimos en el ViewModel
        // Ingresos: Sueldo (2500) + Venta Freelance (300) = 2800
        // Egresos: Super (150.5) + Alquiler (800) + Netflix (15.99) = 966.49
        // Saldo esperado: 2800 - 966.49 = 1833.51

        // Then: Verificamos los cálculos
        assertEquals(2800.0, state.monthlyIncome, 0.001)
        assertEquals(966.49, state.monthlyExpense, 0.001)
        assertEquals(1833.51, state.totalBalance, 0.001)
        assertEquals(5, state.lastMovements.size)
    }
}