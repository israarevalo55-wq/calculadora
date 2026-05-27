package com.example.calculadora.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.calculadora.navegacion.Rutas


@Composable
fun PantallaIngreso(navController: NavHostController) {

    // ─── Estados de los campos ──────────────────────────────────────────────
    var nombre by remember { mutableStateOf("") }
    var peso   by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }

    // [RETO 1] Estado para mostrar u ocultar el mensaje de error
    var mostrarError by remember { mutableStateOf(false) }

    // ─── UI ─────────────────────────────────────────────────────────────────
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Título
        Text(
            text = "Calculadora de IMC",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ingresa tus datos para calcular\ntu Índice de Masa Corporal",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ── Campo: Nombre ────────────────────────────────────────────────────
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            placeholder = { Text("Ej. Ana García") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Campo: Peso ──────────────────────────────────────────────────────
        OutlinedTextField(
            value = peso,
            onValueChange = {
                peso = it
                mostrarError = false   // Ocultar error al editar
            },
            label = { Text("Peso (kg)") },
            placeholder = { Text("Ej. 70.5") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = mostrarError && !esNumeroValido(peso)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Campo: Altura ────────────────────────────────────────────────────
        OutlinedTextField(
            value = altura,
            onValueChange = {
                altura = it
                mostrarError = false   // Ocultar error al editar
            },
            label = { Text("Altura (m)") },
            placeholder = { Text("Ej. 1.75") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = mostrarError && !esNumeroValido(altura)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // [RETO 1] Mensaje de error condicional en color rojo
        if (mostrarError) {
            Text(
                text = "Por favor, ingresa valores válidos",
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        // ── Botón Calcular ───────────────────────────────────────────────────
        Button(
            onClick = {
                // [RETO 1] Validación antes de navegar
                val pesoValido   = esNumeroValido(peso)
                val alturaValida = esNumeroValido(altura)

                if (!pesoValido || !alturaValida) {
                    // Activar mensaje de error; NO navegar
                    mostrarError = true
                } else {
                    // Datos válidos → calcular IMC y navegar
                    mostrarError = false
                    val pesoFloat   = peso.toFloat()
                    val alturaFloat = altura.toFloat()
                    val imc         = pesoFloat / (alturaFloat * alturaFloat)

                    // Usar el nombre capturado (o "Usuario" si está vacío)
                    val nombreFinal = nombre.ifBlank { "Usuario" }

                    // Navegar a Pantalla 2 con dos parámetros
                    navController.navigate(Rutas.resultadoRuta(nombreFinal, imc))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Calcular IMC",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ─── Función de validación ───────────────────────────────────────────────────
/**
 * Retorna true si [valor] se puede convertir a Float Y es mayor que cero.
 * Cubre los casos: vacío, letras, cero, negativo.
 */
private fun esNumeroValido(valor: String): Boolean {
    val numero = valor.toFloatOrNull() ?: return false
    return numero > 0f
}