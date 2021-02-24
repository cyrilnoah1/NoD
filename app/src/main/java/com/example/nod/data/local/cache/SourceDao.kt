package com.example.nod.data.local.cache

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nod.data.local.models.Source

@Dao
interface SourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSource(source: Source)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSources(sources: List<Source>)

    @Query("SELECT * FROM SOURCE ORDER BY name ASC")
    fun getSourcesInformation(): LiveData<List<Source>>
}