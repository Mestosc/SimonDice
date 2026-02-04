package net.oscar.simondice.puntuacionMasAlta.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDAO : InterfazDao {
    @Query("select * from recordSimon")
    suspend fun getAll(): List<RecordSimon>

    @Query("select * from recordSimon order by record DESC limit 1")
    suspend fun obtenerPuntuacionMasReciente(): RecordSimon?

    @Insert
    suspend fun anadirRecord(record: RecordSimon)

    @Delete
    suspend fun eliminarRecord(record: RecordSimon)

    @Query("DELETE FROM recordSimon WHERE record = :puntos AND fecha = :fecha")
    suspend fun eliminarRecordPorDatos(puntos: Int, fecha: String)
}