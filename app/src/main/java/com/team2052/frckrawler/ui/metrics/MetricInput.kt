package com.team2052.frckrawler.ui.metrics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team2052.frckrawler.data.model.Metric

@Composable
fun MetricInput(
    modifier: Modifier = Modifier,
    metric: Metric,
    state: String,
    onStateChanged: (String) -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = metric.name,
            style = MaterialTheme.typography.h5
        )

        Spacer(Modifier.width(16.dp))
        
        when (metric) {
            is Metric.BooleanMetric -> {
                BooleanMetric(state = state, onStateChanged = onStateChanged)
            }
            is Metric.CounterMetric -> {
                CounterMetric(
                    state = state.toInt(),
                    onStateChanged = { onStateChanged(it.toString()) },
                    range = metric.range,
                )
            }
            is Metric.SliderMetric -> {
                SliderMetric(
                    state = state.toInt(),
                    onStateChanged = { onStateChanged(it.toString()) },
                    range = metric.range
                )
            }

            is Metric.CheckboxMetric -> {
                CheckboxMetric(
                    state = state,
                    onStateChanged = onStateChanged,
                    options = metric.options
                )
            }
            is Metric.ChooserMetric -> TODO()
            is Metric.StopwatchMetric -> TODO()
            is Metric.TextFieldMetric -> TODO()
        }
    }
}