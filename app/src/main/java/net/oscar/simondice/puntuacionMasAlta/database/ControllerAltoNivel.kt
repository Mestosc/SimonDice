package net.oscar.simondice.puntuacionMasAlta.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import net.oscar.simondice.datos.ConstantesVarias
import net.oscar.simondice.datos.PuntuacionMasAlta
import net.oscar.simondice.puntuacionMasAlta.PuntuacionMasAltaHandler
import java.time.LocalDateTime
import kotlin.reflect.KClass

class ControllerAltoNivel<T: RoomDatabase>(context: Context, room: KClass<T>)  : PuntuacionMasAltaHandler, ControllerBajoNivel<T>(context,room) {

    override fun obtenerRecord(): PuntuacionMasAlta {
        if (recordDAO is RecordDAO) {
            val p = recordDAO.obtenerPuntuacionMasReciente()
            return PuntuacionMasAlta(p.record ?: 0, LocalDateTime.parse(p.fecha, ConstantesVarias.DEFAULT_FORMATTER))
        }
        return PuntuacionMasAlta()
    }

    override fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        if (recordDAO is RecordDAO) {
            recordDAO.anadirRecord(RecordSimon(1,puntuacionMasAlta.puntuacionMasAlta,puntuacionMasAlta.marcaTiempo.format(ConstantesVarias.DEFAULT_FORMATTER)))
        }
    }

    override fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta) {
        if (recordDAO is RecordDAO) {
            recordDAO.eliminarRecord(RecordSimon(1,puntuacionMasAlta.puntuacionMasAlta,puntuacionMasAlta.marcaTiempo.format(ConstantesVarias.DEFAULT_FORMATTER)))
        }
    }

    override fun close() {
        Log.d("ControllerDBRoom","No hace falta cerrar")
    }

}