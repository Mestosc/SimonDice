package net.oscar.simondice.puntuacionMasAlta.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.reflect.KClass

abstract class ControllerBajoNivel<T: RoomDatabase>(context: Context, room: KClass<T>)  {
    val db = Room.databaseBuilder(context,room.java,"database-name").build()
    val recordDAO = obtenerTipoDao(db)
    private fun <T: RoomDatabase> obtenerTipoDao(roomer: T): InterfazDao {
        return when (roomer) {
            is DataBase -> { roomer.recordDao() }
            else -> { throw RuntimeException("No existe una implementacion DAO") }
        }
    }
}