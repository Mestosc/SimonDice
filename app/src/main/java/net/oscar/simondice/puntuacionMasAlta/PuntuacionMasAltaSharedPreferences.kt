package net.oscar.simondice.puntuacionMasAlta

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import net.oscar.simondice.Datos.PuntuacionMasAlta
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PuntuacionMasAltaSharedPreferences(val application: Application) : PuntuacionMasAltaHandler {
    private val preferencesName = "Records"
    private val KEY_RECORD = "Record";
    override fun obtenerRecord(): PuntuacionMasAlta {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        PuntuacionMasAlta.puntuacionMasAlta = preferences.getInt(KEY_RECORD,0)
        PuntuacionMasAlta.marcaTiempo = LocalDateTime.parse(preferences.getString(KEY_RECORD,"03/12/2025 11"))
        return PuntuacionMasAlta
    }

    override fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH")
        val formateada = PuntuacionMasAlta.marcaTiempo.format(formatter)
        preferences?.edit {
            putInt(KEY_RECORD,puntuacionMasAlta.puntuacionMasAlta)
            putString(KEY_RECORD,formateada)
        }
    }

    override fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        preferences.edit { remove(KEY_RECORD) }
    }

}