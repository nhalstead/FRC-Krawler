package com.team2052.frckrawler.metric.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout

import com.google.gson.JsonElement
import com.team2052.frckrawler.metric.data.MetricValue
import com.team2052.frckrawler.models.Metric

abstract class MetricWidget protected constructor(context: Context) : FrameLayout(context) {

    protected var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var metric: Metric

    protected constructor(context: Context, m: MetricValue) : this(context) {
        metric = m.metric
        setMetricValue(m)
    }

    init {
        initViews()
    }

    abstract fun setMetricValue(m: MetricValue)
    abstract fun initViews()

    fun getValue(): MetricValue = MetricValue(metric, data)

    abstract val data: JsonElement
}
