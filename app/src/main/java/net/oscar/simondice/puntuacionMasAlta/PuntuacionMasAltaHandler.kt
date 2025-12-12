package net.oscar.simondice.puntuacionMasAlta

import net.oscar.simondice.datos.PuntuacionMasAlta

/**
 * Guardar los records en el juego del simon dice
 */
interface PuntuacionMasAltaHandler : AutoCloseable {

    fun obtenerRecord(): PuntuacionMasAlta;
    fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta);
    fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta);
}