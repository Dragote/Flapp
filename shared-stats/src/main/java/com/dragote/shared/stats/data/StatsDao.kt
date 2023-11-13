package com.dragote.shared.stats.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class Stats(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "percentage") val percentage: Float,
)

@Dao
interface StatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(vararg stats: Stats)

    @Query("SELECT * FROM stats WHERE id=:id ")
    suspend fun loadSingle(id: String): Stats

    @Query("DELETE FROM stats")
    suspend fun clear()
}

@Database(entities = [Stats::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statsDao(): StatsDao
}