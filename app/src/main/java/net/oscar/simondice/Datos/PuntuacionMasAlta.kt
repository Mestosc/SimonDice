package net.oscar.simondice.Datos

import android.text.format.DateUtils
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date

data class PuntuacionMasAlta(var puntuacionMasAlta: Int = 0, var marcaTiempo: LocalDateTime = LocalDateTime.parse("03/12/2025 11"))