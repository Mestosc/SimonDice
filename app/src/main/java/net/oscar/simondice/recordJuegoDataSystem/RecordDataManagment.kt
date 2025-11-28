package net.oscar.simondice.recordJuegoDataSystem

/**
 * Guardar los records en el juego del simon dice
 */
interface RecordDataManagment {
    fun obtenerRecord(rondaMasAlta: Int);
    fun escribirRecord(marcaTiempo: String, rondaMasAlta: Int);
    fun actualizarRecord(rondaMasAlta: Int,nuevaRondaMasAlta: Int, nuevaMarcaTiempo: String);
    fun eliminarRecord(rondaMasAlta: Int);
}