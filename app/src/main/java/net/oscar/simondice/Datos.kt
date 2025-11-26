package net.oscar.simondice

import androidx.compose.ui.graphics.Color

object Datos {
    val secuenciaAdivinar = listOf(Colores.ROJO,Colores.AZUL,Colores.AMARILLO,Colores.VERDE)
    val secuenciaAdivinando = mutableListOf<Colores>()

}

/**
 * Colores que vamos a usar en el juego [color] y el nombre [txt] que va a mostrar
 */
enum class Colores(val color: Color, val colorOscuro: Color, val txt: String) {
    ROJO(Color.Red, Color(0xFF8B0000), "Rojo"),
    VERDE(Color.Green, Color(0xFF006400), "Verde"),
    AMARILLO(Color.Yellow, Color(0xFF9B870C), "Amarillo"),
    AZUL(Color.Blue, Color(0xFF00008B), "Azul"),
    START(Color.Gray, Color.Gray, "START")
}