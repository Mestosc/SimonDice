package net.oscar.simondice.datos

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Representa la puntuacion más alta
 */
data class PuntuacionMasAlta(var puntuacionMasAlta: Int = 0, var marcaTiempo: LocalDateTime = LocalDateTime.parse("03/12/2025 11", ConstantesVarias.DEFAULT_FORMATTER))