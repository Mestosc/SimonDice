package net.oscar.simondice.puntuacionMasAlta

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import net.oscar.simondice.Datos.PuntuacionMasAlta
import java.sql.Date

class PuntuacionMasAltaSharedPreferences(val application: Application) : PuntuacionMasAltaHandler {
    private val preferencesName = "Records"
    private val KEY_RECORD = "Record";
    override fun obtenerRecord(): PuntuacionMasAlta {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        PuntuacionMasAlta.puntuacionMasAlta = preferences.getInt(KEY_RECORD,0)
        PuntuacionMasAlta.marcaTiempo = Date.valueOf(preferences.getString(KEY_RECORD,"0-0-0"))
        return PuntuacionMasAlta
    }

    override fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        preferences?.edit {
            putInt(KEY_RECORD,puntuacionMasAlta.puntuacionMasAlta)
            putString(KEY_RECORD,puntuacionMasAlta.marcaTiempo.toString())
        }
    }

    override fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val preferences = application.getSharedPreferences(preferencesName,Context.MODE_PRIVATE)
        preferences.edit { remove(KEY_RECORD) }
    }

}