
package com.example.calculadora.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


// ─── Colores de categoría ────────────────────────────────────────────────────
private val ColorBajoPeso   = Color(0xFFE53935)   // Rojo
private val ColorNormal     = Color(0xFF43A047)   // Verde
private val ColorSobrepeso  = Color(0xFFFB8C00)   // Naranja
private val ColorObesidad   = Color(0xFFB71C1C)   // Rojo oscuro

@Composable
fun PantallaResultado(
    navController: NavHostController,
    nombre: String,
    imc: Float
) {
    // ─── Clasificación mediante when ─────────────────────────────────────────
    // [RETO 3] Colores dinámicos según el resultado del IMC
    val (categoria, colorCategoria) = when {
        imc < 18.5f              -> Pair("Bajo peso",    ColorBajoPeso)
        imc < 25.0f              -> Pair("Peso normal",  ColorNormal)
        imc < 30.0f              -> Pair("Sobrepeso",    ColorSobrepeso)
        else                     -> Pair("Obesidad",     ColorObesidad)
    }

    // Descripción/consejo breve por categoría
    val consejo = when (categoria) {
        "Bajo peso"   -> "Te recomendamos consultar a un nutriólogo."
        "Peso normal" -> "¡Excelente! Mantén tus hábitos saludables."
        "Sobrepeso"   -> "Considera mejorar tu alimentación y actividad física."
        else          -> "Es importante consultar a un médico."
    }

    // ─── UI ─────────────────────────────────────────────────────────────────
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Emoji representativo
        val emoji = when (categoria) {
            "Bajo peso"   -> "⚠️"
            "Peso normal" -> "✅"
            "Sobrepeso"   -> "🔶"
            else          -> "🔴"
        }
        Text(text = emoji, fontSize = 56.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Saludo personalizado con el nombre recibido por parámetro
        Text(
            text = "Hola $nombre, tu resultado es:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta con el valor del IMC
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorCategoria.copy(alpha = 0.12f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Tu IMC",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Valor del IMC con UN decimal
                Text(
                    text = "%.1f".format(imc),
                    fontSize = 52.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorCategoria
                )

                Spacer(modifier = Modifier.height(12.dp))

                // [RETO 3] Categoría con color dinámico
                Text(
                    text = categoria,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorCategoria,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Consejo breve
        Text(
            text = consejo,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Leyenda de rangos
        Text(
            text = "Rangos de referencia OMS",
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        FilaRango("< 18.5",        "Bajo peso",   ColorBajoPeso)
        FilaRango("18.5 – 24.9",   "Peso normal", ColorNormal)
        FilaRango("25.0 – 29.9",   "Sobrepeso",   ColorSobrepeso)
        FilaRango("≥ 30.0",        "Obesidad",    ColorObesidad)

        Spacer(modifier = Modifier.height(36.dp))

        // Botón Volver → popBackStack() limpia la pantalla 2 y regresa a la 1
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "← Volver a calcular",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ─── Composable auxiliar: fila de rango ─────────────────────────────────────
@Composable
private fun FilaRango(rango: String, etiqueta: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = rango,    fontSize = 13.sp, color = Color.Gray)
        Text(
            text = etiqueta,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}