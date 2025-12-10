package net.oscar.simondice.puntuacionMasAlta.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import net.oscar.simondice.datos.PuntuacionMasAlta
import net.oscar.simondice.puntuacionMasAlta.PuntuacionMasAltaHandler

class PuntuacionMasAltaSqlite(val context: Context) : PuntuacionMasAltaHandler {
    val db = BaseDatosHelper(context)
    override fun obtenerRecord(): PuntuacionMasAlta {
        TODO("Not yet implemented")
    }

    override fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val dbWriter = db.readableDatabase
        val values = ContentValues().apply {
            put(DataBaseContract.TablaRecord.COLUMNA_FECHA,)
        }
        db.close()
    }

    override fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        TODO("Not yet implemented")
    }

    override fun close() {
        db.close()
    }

}