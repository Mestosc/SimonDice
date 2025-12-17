package net.oscar.simondice.puntuacionMasAlta.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecordSimon(@PrimaryKey val uid: Int,
                       @ColumnInfo("record") val record: Int?,
                       @ColumnInfo val fecha: String?)