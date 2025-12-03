package net.oscar.simondice.puntuacionMasAlta

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import net.oscar.simondice.Datos.PuntuacionMasAlta
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PuntuacionMasAltaSharedPreferences(val application: Application,val formatter: DateTimeFormatter) : PuntuacionMasAltaHandler {
    private val preferencesName = "Records"
    private val keyRecord = "Record" // Solo puede almacenar un Record
    override fun obtenerRecord(key: String): PuntuacionMasAlta {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        return PuntuacionMasAlta(puntuacionMasAlta = preferences.getInt(key,0), marcaTiempo = LocalDateTime.parse(preferences.getString(key,"03/12/2025 11"),formatter))
    }

    override fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        val formateada = puntuacionMasAlta.marcaTiempo.format(formatter)
        preferences?.edit {
            putInt(keyRecord,puntuacionMasAlta.puntuacionMasAlta)
            putString(keyRecord,formateada)
        }
    }

    override fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        preferences?.edit { remove(keyRecord) }
    }

}