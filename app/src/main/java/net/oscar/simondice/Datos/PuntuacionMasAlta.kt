package net.oscar.simondice.Datos

import android.text.format.DateUtils
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

/**
 * Representa la puntuacion más alta
 */
data class PuntuacionMasAlta(var puntuacionMasAlta: Int = 0, var marcaTiempo: LocalDateTime = LocalDateTime.parse("03/12/2025 11", DateTimeFormatter.ofPattern("dd/MM/yyyy HH")))