package net.oscar.simondice.recordJuegoDataSystem

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import net.oscar.simondice.PuntuacionMasAlta

/**
 * Guardar los records en el juego del simon dice
 */
interface RecordDataManagment {
    fun obtenerRecord(): PuntuacionMasAlta;
    fun escribirRecord(marcaTiempo: String, rondaMasAlta: Int);
    fun actualizarRecord(rondaMasAlta: Int,nuevaRondaMasAlta: Int, nuevaMarcaTiempo: String);
    fun eliminarRecord(rondaMasAlta: Int);
}