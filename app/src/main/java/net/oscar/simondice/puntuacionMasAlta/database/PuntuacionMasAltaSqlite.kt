package net.oscar.simondice.puntuacionMasAlta.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import net.oscar.simondice.datos.ConstantesVarias
import net.oscar.simondice.datos.PuntuacionMasAlta
import net.oscar.simondice.puntuacionMasAlta.PuntuacionMasAltaHandler
import java.time.format.DateTimeFormatter

class PuntuacionMasAltaSqlite(context: Context, val formatter: DateTimeFormatter = ConstantesVarias.DEFAULT_FORMATTER) : PuntuacionMasAltaHandler {
    val db = BaseDatosHelper(context)
    override fun obtenerRecord(): PuntuacionMasAlta {
        val dbReader = db.readableDatabase

    }

    override fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val dbWriter = db.writableDatabase
        val values = ContentValues().apply {
            put(DataBaseContract.TablaRecord.COLUMNA_RECORD,puntuacionMasAlta.marcaTiempo.format(formatter))
            put(DataBaseContract.TablaRecord.COLUMNA_RECORD,puntuacionMasAlta.puntuacionMasAlta)
        }
        dbWriter.insert(DataBaseContract.TablaRecord.TABLE_NAME,null,values)
    }

    override fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        TODO("Not yet implemented")
    }

    override fun close() {
        db.close()
    }

}