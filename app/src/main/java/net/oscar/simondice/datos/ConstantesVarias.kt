package net.oscar.simondice.datos

import java.time.format.DateTimeFormatter

object ConstantesVarias {
    val DEFAULT_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    const val DEFAULT_SCORE = 0
    const val DEFAULT_DATE_STRING = "12/12/2012 12:12"
}