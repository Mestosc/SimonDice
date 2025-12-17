package net.oscar.simondice.puntuacionMasAlta.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


interface RecordDAO : InterfazDao {
    @Query("select * from recordSimon")
    fun getAll(): List<RecordSimon>

    @Query("select * from recordSimon limit 1")
    fun obtenerPuntuacionMasReciente(): RecordSimon

    @Insert
    fun anadirRecord(record: RecordSimon)

    @Delete
    fun eliminarRecord(record: RecordSimon)
}