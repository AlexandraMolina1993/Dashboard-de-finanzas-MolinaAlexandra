package com.example.salitafeliz

import android.app.DatePickerDialog
import java.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
//import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.salitafeliz.ui.theme.SalitaFelizTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

data class Turno(
    val paciente: String,
    val fecha: String,
    val horario: String,
    val vacuna: String,
    val enfermero: String
)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            SalitaFelizTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    PantallaTurnos(
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTurnos(
    modifier: Modifier = Modifier
) {

    var nombre by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    val vacunas = listOf(
        "COVID-19",
        "Antigripal",
        "Hepatitis B",
        "Triple Viral",
        "BCG"
    )

    val enfermeros = listOf(
        "María González",
        "Juan Pérez",
        "Ana López",
        "Carlos Fernández"
    )
    val horarios = listOf(
        "08:00",
        "09:00",
        "10:00",
        "11:00",
        "12:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00"
    )

    var vacunaSeleccionada by remember { mutableStateOf("") }
    var enfermeroSeleccionado by remember { mutableStateOf("") }
    var resumenTurno by remember { mutableStateOf("") }
    var horarioSeleccionado by remember { mutableStateOf("") }
    val turnos = remember {
        mutableStateListOf<Turno>()
    }
    val context = LocalContext.current

    var expandedVacuna by remember { mutableStateOf(false) }
    var expandedEnfermero by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        Text(
            text = "🏥 Salita Feliz",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Solicitud de Turno de Vacunación"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del paciente") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                val calendario = Calendar.getInstance()

                Button(
                    onClick = {

                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->

                                fecha = "$dayOfMonth/${month + 1}/$year"

                            },
                            calendario.get(Calendar.YEAR),
                            calendario.get(Calendar.MONTH),
                            calendario.get(Calendar.DAY_OF_MONTH)
                        ).show()

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    if (fecha.isBlank()) {

                        Text("Seleccionar fecha")

                    } else {

                        Text("Fecha: $fecha")

                    }

                }
                Text(
                    text = "Horario"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column {

                    horarios.forEach { horario ->

                        Button(
                            onClick = {
                                horarioSeleccionado = horario
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text(horario)

                        }

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedVacuna,
                    onExpandedChange = {
                        expandedVacuna = !expandedVacuna
                    }
                ) {

                    OutlinedTextField(
                        value = vacunaSeleccionada,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Vacuna") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expandedVacuna
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedVacuna,
                        onDismissRequest = {
                            expandedVacuna = false
                        }
                    ) {

                        vacunas.forEach { vacuna ->

                            DropdownMenuItem(
                                text = {
                                    Text(vacuna)
                                },
                                onClick = {
                                    vacunaSeleccionada = vacuna
                                    expandedVacuna = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedEnfermero,
                    onExpandedChange = {
                        expandedEnfermero = !expandedEnfermero
                    }
                ) {

                    OutlinedTextField(
                        value = enfermeroSeleccionado,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Enfermero") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expandedEnfermero
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedEnfermero,
                        onDismissRequest = {
                            expandedEnfermero = false
                        }
                    ) {

                        enfermeros.forEach { enfermero ->

                            DropdownMenuItem(
                                text = {
                                    Text(enfermero)
                                },
                                onClick = {
                                    enfermeroSeleccionado = enfermero
                                    expandedEnfermero = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {

                        if (
                            nombre.isBlank() ||
                            fecha.isBlank() ||
                            horarioSeleccionado.isBlank() ||
                            vacunaSeleccionada.isBlank() ||
                            enfermeroSeleccionado.isBlank()
                        ) {

                            Toast.makeText(
                                context,
                                "Complete todos los campos",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {

                            resumenTurno =
                                """
    Paciente: $nombre
    
    Fecha: $fecha
    
    Horario: $horarioSeleccionado
    
    Vacuna: $vacunaSeleccionada
    
    Enfermero: $enfermeroSeleccionado
    """.trimIndent()
                            turnos.add(
                                Turno(
                                    paciente = nombre,
                                    fecha = fecha,
                                    vacuna = vacunaSeleccionada,
                                    horario = horarioSeleccionado,
                                    enfermero = enfermeroSeleccionado
                                )

                            )
                            nombre = ""
                            fecha = ""
                            vacunaSeleccionada = ""
                            enfermeroSeleccionado = ""
                            Toast.makeText(
                                context,
                                "Turno solicitado correctamente",
                                Toast.LENGTH_LONG
                            ).show()

                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Solicitar Turno")

                }
                Spacer(modifier = Modifier.height(20.dp))

                Spacer(modifier = Modifier.height(20.dp))

                if (resumenTurno.isNotEmpty()) {

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "Resumen del Turno",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = resumenTurno
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (turnos.isNotEmpty()) {

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "Turnos Registrados",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            turnos.forEach { turno ->

                                Text(
                                    text = "• ${turno.paciente} | ${turno.fecha} | ${turno.horario} | ${turno.vacuna}"
                                )

                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }
                }
                    }
                }
            }
        }

@Preview(showBackground = true)
@Composable
fun PantallaTurnosPreview() {
    SalitaFelizTheme {
        PantallaTurnos()
    }
}