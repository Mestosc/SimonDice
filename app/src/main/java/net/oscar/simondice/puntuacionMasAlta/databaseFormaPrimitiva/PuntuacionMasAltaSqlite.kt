package net.oscar.simondice.puntuacionMasAlta.databaseFormaPrimitiva

import android.content.ContentValues
import android.content.Context
import android.util.Log
import net.oscar.simondice.datos.ConstantesVarias
import net.oscar.simondice.datos.PuntuacionMasAlta
import net.oscar.simondice.puntuacionMasAlta.PuntuacionMasAltaHandler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PuntuacionMasAltaSqlite(context: Context, val formatter: DateTimeFormatter = ConstantesVarias.DEFAULT_FORMATTER) : PuntuacionMasAltaHandler {
    private val LOG_TAG = "SqliteLog"
    val db = BaseDatosHelper(context)
    override fun obtenerRecord(): PuntuacionMasAlta {
        val dbReader = db.readableDatabase
        val projection = arrayOf(DataBaseContract.TablaRecord.COLUMNA_RECORD, DataBaseContract.TablaRecord.COLUMNA_FECHA)

        val sortOrder = "${DataBaseContract.TablaRecord.COLUMNA_RECORD} DESC"

        return dbReader.query(
            DataBaseContract.TablaRecord.TABLE_NAME,
            projection,
            null,
            null,
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder,
            "1"
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                try {
                    val puntuacion = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.TablaRecord.COLUMNA_RECORD))
                    val fecha = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.TablaRecord.COLUMNA_FECHA))
                    PuntuacionMasAlta(puntuacion, LocalDateTime.parse(fecha,formatter))
                } catch (e: Exception) {
                    Log.d(LOG_TAG,"Problems with $e")
                    PuntuacionMasAlta()
                }
            } else {
                PuntuacionMasAlta()
            }
            }
        }
    fun obtenerRecordPuntaucionIgualFechaMasAntigua(puntuacionMasAlta: PuntuacionMasAlta): PuntuacionMasAlta {
        val dbReader = db.readableDatabase
        val projection = arrayOf(DataBaseContract.TablaRecord.COLUMNA_RECORD, DataBaseContract.TablaRecord.COLUMNA_FECHA)

        val sortOrder = "${DataBaseContract.TablaRecord.COLUMNA_RECORD} DESC"
        var puntuacionAlta: PuntuacionMasAlta = puntuacionMasAlta
        dbReader.query(
                DataBaseContract.TablaRecord.TABLE_NAME,
                projection,
                null,
                null,
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder,
            ).use { cursor ->
                while (cursor.moveToNext()) {
                    val puntuacion = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.TablaRecord.COLUMNA_RECORD))
                    val fecha = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.TablaRecord.COLUMNA_FECHA))
                    if (puntuacion == puntuacionMasAlta.puntuacionMasAlta && (puntuacionMasAlta.marcaTiempo.dayOfMonth < LocalDateTime.parse(fecha,formatter).dayOfMonth || puntuacionMasAlta.marcaTiempo.monthValue < LocalDateTime.parse(fecha,formatter).monthValue)) {
                        puntuacionAlta = PuntuacionMasAlta(puntuacion, LocalDateTime.parse(fecha,formatter))
                        break
                    }
                }
            }
        return puntuacionAlta;
        }
    override fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        val dbWriter = db.writableDatabase
        if (contarElementos()>10) {
            return
        }
        val values = ContentValues().apply {
            put(DataBaseContract.TablaRecord.COLUMNA_FECHA,puntuacionMasAlta.marcaTiempo.format(formatter))
            put(DataBaseContract.TablaRecord.COLUMNA_RECORD,puntuacionMasAlta.puntuacionMasAlta)
        }
        dbWriter.insert(DataBaseContract.TablaRecord.TABLE_NAME,null,values)
    }

    override fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        TODO("Not yet implemented")
    }

    /**
     * Cuenta el número de elementos que hay actualmente en la base de datos
     */
    fun contarElementos(): Int {
        val dbReader = db.readableDatabase
        val projection = arrayOf(DataBaseContract.TablaRecord.COLUMNA_RECORD, DataBaseContract.TablaRecord.COLUMNA_FECHA)

        val sortOrder = "${DataBaseContract.TablaRecord.COLUMNA_RECORD} DESC"

        return dbReader.query(
            DataBaseContract.TablaRecord.TABLE_NAME,
            projection,
            null,
            null,
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder,
        ).use { cursor ->
            cursor.count
        }
    }

    override fun close() {
        db.close()
    }

}