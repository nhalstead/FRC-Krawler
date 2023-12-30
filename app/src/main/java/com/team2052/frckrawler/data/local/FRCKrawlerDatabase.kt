package com.team2052.frckrawler.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        MetricSet::class,
        MetricRecord::class,
        Event::class,
        TeamAtEvent::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class FRCKrawlerDatabase: RoomDatabase() {
    abstract fun metricsSetDao(): MetricSetDao
    abstract fun metricDao(): MetricDao
}