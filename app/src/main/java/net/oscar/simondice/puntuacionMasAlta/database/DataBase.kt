package net.oscar.simondice.puntuacionMasAlta.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([RecordSimon::class],version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun recordDao(): RecordDAO
}