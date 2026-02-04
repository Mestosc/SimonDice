package net.oscar.simondice.datos

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Representa la puntuacion más alta
 */
data class PuntuacionMasAlta(var puntuacionMasAlta: Int = ConstantesVarias.DEFAULT_SCORE, var marcaTiempo: LocalDateTime = LocalDateTime.parse(
    ConstantesVarias.DEFAULT_DATE_STRING, ConstantesVarias.DEFAULT_FORMATTER), val nombre: String = "Jugador1")