package net.oscar.simondice.puntuacionMasAlta.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import net.oscar.simondice.datos.ConstantesVarias
import net.oscar.simondice.datos.PuntuacionMasAlta
import net.oscar.simondice.puntuacionMasAlta.PuntuacionMasAltaHandler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PuntuacionMasAltaSqlite(context: Context, val formatter: DateTimeFormatter = ConstantesVarias.DEFAULT_FORMATTER) : PuntuacionMasAltaHandler {
    val db = BaseDatosHelper(context)
    override fun obtenerRecord(): PuntuacionMasAlta {
        val dbReader = db.readableDatabase
        val projection = arrayOf(DataBaseContract.TablaRecord.COLUMNA_RECORD, DataBaseContract.TablaRecord.COLUMNA_FECHA)


// How you want the results sorted in the resulting Cursor
        val sortOrder = "${DataBaseContract.TablaRecord.COLUMNA_RECORD} DESC"

        val cursor = dbReader.query(
            DataBaseContract.TablaRecord.TABLE_NAME,
            projection,
            null,
            null,
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        var puntuacion = ConstantesVarias.DEFAULT_SCORE
        var fecha = ConstantesVarias.DEFAULT_DATE_STRING
        with(cursor) {
            if (moveToNext()) {
                puntuacion = getInt(getColumnIndexOrThrow(DataBaseContract.TablaRecord.COLUMNA_RECORD))
                fecha = getString(getColumnIndexOrThrow(DataBaseContract.TablaRecord.COLUMNA_FECHA))
            }
        }
        cursor.close()
        return PuntuacionMasAlta(puntuacion, LocalDateTime.parse(fecha,formatter))
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