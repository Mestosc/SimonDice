package net.oscar.simondice.recordJuegoDataSystem

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import net.oscar.simondice.PuntuacionMasAlta

/**
 * Guardar los records del Simon Dice en un DataStore
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "records") // Segun lo que he visto revisando documentaccion esto ya es como un singleton así que puedo declarlo aqui sin problema
class RecordDataStoreHandler : RecordDataManagment {
    override fun obtenerRecord(): PuntuacionMasAlta {
        TODO("Not yet implemented")
    }

    override fun escribirRecord(marcaTiempo: String, rondaMasAlta: Int) {
        TODO("Not yet implemented")
    }

    override fun actualizarRecord(rondaMasAlta: Int, nuevaRondaMasAlta: Int, nuevaMarcaTiempo: String) {
        TODO("Not yet implemented")
    }

    override fun eliminarRecord(rondaMasAlta: Int) {
        TODO("Not yrondaMasAlta: Intet implemented")
    }

}