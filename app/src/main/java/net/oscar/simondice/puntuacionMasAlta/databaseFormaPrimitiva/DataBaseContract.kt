package net.oscar.simondice.puntuacionMasAlta.databaseFormaPrimitiva

import android.provider.BaseColumns

object DataBaseContract {
    object TablaRecord : BaseColumns {
        const val TABLE_NAME = "records"
        const val COLUMNA_FECHA = "marcatiempo"
        const val COLUMNA_RECORD = "puntuacionMasAlta"
    }
}