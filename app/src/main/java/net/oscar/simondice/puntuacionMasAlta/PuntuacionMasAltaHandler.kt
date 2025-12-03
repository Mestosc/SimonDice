package net.oscar.simondice.puntuacionMasAlta

import net.oscar.simondice.Datos.PuntuacionMasAlta

/**
 * Guardar los records en el juego del simon dice
 */
interface PuntuacionMasAltaHandler {

    fun obtenerRecord(key: String): PuntuacionMasAlta;
    fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta);
    fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta);
}