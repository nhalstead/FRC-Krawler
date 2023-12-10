package com.team2052.frckrawler.data.model

import com.team2052.frckrawler.data.local.MetricCategory

sealed class Metric {
    abstract val id: Int
    abstract val name: String
    abstract val category: MetricCategory
    abstract val priority: Int
    abstract val enabled: Boolean

    data class BooleanMetric(
        override val id: Int,
        override val name: String,
        override val category: MetricCategory,
        override val priority: Int,
        override val enabled: Boolean,
    ) : Metric()

    data class CounterMetric(
        override val id: Int,
        override val name: String,
        override val category: MetricCategory,
        override val priority: Int,
        override val enabled: Boolean,
        val range: IntRange,
        val step: Int,
    ) : Metric()

    data class SliderMetric(
        override val id: Int,
        override val name: String,
        override val category: MetricCategory,
        override val priority: Int,
        override val enabled: Boolean,
        val range: IntRange,
    ) : Metric()

    data class ChooserMetric(
        override val id: Int,
        override val name: String,
        override val category: MetricCategory,
        override val priority: Int,
        override val enabled: Boolean,
        val options: List<String>,
    ) : Metric()

    data class CheckboxMetric(
        override val id: Int,
        override val name: String,
        override val category: MetricCategory,
        override val priority: Int,
        override val enabled: Boolean,
        val options: List<String>,
    ) : Metric()

    data class StopwatchMetric(
        override val id: Int,
        override val name: String,
        override val category: MetricCategory,
        override val priority: Int,
        override val enabled: Boolean,
    ) : Metric()

    data class TextFieldMetric(
        override val id: Int,
        override val name: String,
        override val category: MetricCategory,
        override val priority: Int,
        override val enabled: Boolean,
    ) : Metric()

}