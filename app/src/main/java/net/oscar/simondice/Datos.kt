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
    AMARILLO(color = Color.Yellow, txt = "melo")
}

/**
 * Los estados que va a tener la aplicacion, que contiene la siguiente informacion [boton_activo] indica si los botones
 * normales estan activados y el [start_activo] que indica si el inicio esta activo
 */
enum class Estados(val boton_activo: Boolean, val start_activo: Boolean) {
    INICIO(boton_activo = false, start_activo = false),
    GENERANDO(boton_activo = false, start_activo = false),
    ADIVINAR(boton_activo = true, start_activo = false),
    FINALIZANDO(boton_activo = false, start_activo = false)
}