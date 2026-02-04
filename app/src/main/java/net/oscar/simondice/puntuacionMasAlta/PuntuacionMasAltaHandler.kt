package net.oscar.simondice.puntuacionMasAlta

import net.oscar.simondice.datos.PuntuacionMasAlta

/**
 * Guardar los records en el juego del simon dice
 */
interface PuntuacionMasAltaHandler : AutoCloseable {

    suspend fun obtenerRecord(): PuntuacionMasAlta;
    suspend fun anadirRecord(puntuacionMasAlta: PuntuacionMasAlta);
    suspend fun eliminarRecord(puntuacionMasAlta: PuntuacionMasAlta);
}