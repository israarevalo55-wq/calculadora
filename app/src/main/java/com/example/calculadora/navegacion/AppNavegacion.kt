package com.example.calculadora.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.calculadora.screens.PantallaIngreso
import com.example.calculadora.screens.PantallaResultado

object Rutas {
    const val PANTALLA_INGRESO   = "ingreso"
    const val PANTALLA_RESULTADO = "resultado/{nombre}/{imc}"

    fun resultadoRuta(nombre: String, imc: Float): String {
        return "resultado/$nombre/$imc"
    }
}

@Composable
fun AppNavegacion(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Rutas.PANTALLA_INGRESO
    ) {
        composable(route = Rutas.PANTALLA_INGRESO) {
            PantallaIngreso(navController = navController)
        }
        composable(
            route = Rutas.PANTALLA_RESULTADO,
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("imc")    { type = NavType.FloatType  }
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val imc    = backStackEntry.arguments?.getFloat("imc")    ?: 0f
            PantallaResultado(navController, nombre, imc)
        }
    }
}