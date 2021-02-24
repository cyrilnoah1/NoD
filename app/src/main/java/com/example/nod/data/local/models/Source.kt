package com.example.nod.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SOURCE_TABLE_NAME)
data class Source(
    @ColumnInfo(name = ID) @PrimaryKey val id: String,
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = DESCRIPTION) val description: String
) {

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
    }
}

const val SOURCE_TABLE_NAME = "source"