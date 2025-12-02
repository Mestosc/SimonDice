package net.oscar.simondice.recordJuegoDataSystem

import net.oscar.simondice.PuntuacionMasAlta

/**
 * Guardar los records en el juego del simon dice
 */
interface RecordDataManagment {
    fun obtenerRecord(): PuntuacionMasAlta;
    fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta);
    fun eliminarRecord(rondaMasAlta: Int);
}