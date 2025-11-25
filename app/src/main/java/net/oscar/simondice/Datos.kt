package net.oscar.simondice

import androidx.compose.ui.graphics.Color

object Datos {
    val secuenciaAdivinar = listOf(Colores.ROJO,Colores.AZUL,Colores.AMARILLO,Colores.VERDE)
    val secuenciaAdivinando = mutableListOf<Colores>()

}

/**
 * Colores que vamos a usar en el juego [color] y el nombre [txt] que va a mostrar
 */
enum class Colores(val color: Color, val txt: String) {
    ROJO(color = Color.Red, txt = "roxo"),
    AZUL(color = Color.Blue, txt = "azul"),
    VERDE(color = Color.Green, txt = "verde"),
    AMARILLO(color = Color.Yellow, txt = "melo"),
    START(Color.Magenta, txt = "Start")
}