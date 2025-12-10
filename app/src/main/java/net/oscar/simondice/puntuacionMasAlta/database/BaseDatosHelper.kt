package net.oscar.simondice.puntuacionMasAlta.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


class BaseDatosHelper(val context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION), AutoCloseable {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TODO("Not yet implemented")
    }

    companion object {
        const val DATABASE_NAME = "RecordsDataBase"
        const val DATABASE_VERSION = 1
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DataBaseContract.TablaRecord.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${DataBaseContract.TablaRecord.COLUMNA_RECORD} INTEGER," +
                    "${DataBaseContract.TablaRecord.COLUMNA_FECHA} TEXT)"

    }

}