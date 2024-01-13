package com.team2052.frckrawler.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MetricDao {
    @Delete
    suspend fun delete(metric: MetricRecord)

    @Delete(entity = MetricRecord::class)
    suspend fun delete(recordId: MetricRecordId)

    @Upsert
    suspend fun insert(metric: MetricRecord)

    @Query("SELECT * FROM metric WHERE metricSetId = :metricSetId")
    fun getMetrics(metricSetId: Int ): Flow<List<MetricRecord>>

    @Query("SELECT COUNT(id) FROM metric WHERE metricSetId = :metricSetId")
    suspend fun getMetricCount(metricSetId: Int): Int
}
