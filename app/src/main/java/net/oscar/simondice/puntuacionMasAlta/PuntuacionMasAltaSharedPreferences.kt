package net.oscar.simondice.puntuacionMasAlta

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import net.oscar.simondice.datos.ConstantesVarias
import net.oscar.simondice.datos.PuntuacionMasAlta
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PuntuacionMasAltaSharedPreferences(val application: Application,val formatter: DateTimeFormatter = ConstantesVarias.DEFAULT_FORMATTER) : PuntuacionMasAltaHandler {
    private val preferencesName = "Records"
    private val keyRecord = "Record" // Solo puede almacenar un Record
    private val keyTiempo = "Tiempo"
    override suspend fun obtenerRecord(): PuntuacionMasAlta {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        return PuntuacionMasAlta(puntuacionMasAlta = preferences.getInt(keyRecord,0), marcaTiempo = LocalDateTime.parse(preferences.getString(keyTiempo,"03/12/2025 11"),formatter))
    }

    override suspend fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        val formateada = puntuacionMasAlta.marcaTiempo.format(formatter)
        preferences?.edit {
            putInt(keyRecord,puntuacionMasAlta.puntuacionMasAlta)
            putString(keyTiempo,formateada)
        }
    }

    override suspend fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        preferences?.edit { remove(keyRecord) }
    }

    override fun close() {
        Log.d("CLOSER","Aqui no hay que cerrar nada")
    }

}