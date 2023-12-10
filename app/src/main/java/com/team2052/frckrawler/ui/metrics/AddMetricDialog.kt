package com.team2052.frckrawler.ui.metrics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.team2052.frckrawler.data.local.MetricCategory
import com.team2052.frckrawler.data.local.MetricType
import com.team2052.frckrawler.data.model.Metric
import com.team2052.frckrawler.ui.components.fields.FRCKrawlerTextField
import com.team2052.frckrawler.ui.theme.FrcKrawlerTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditMetricDialog(
    category: MetricCategory,
    gameId: Int,
    onClose: () -> Unit
) {
    val viewModel: AddMetricViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(true) {
        viewModel.startEditingNewMetric(gameId, category)
    }

    AddEditMetricContent(
        state = state,
        onNameChange = { viewModel.updateName(it) },
        onTypeChange = { viewModel.updateType(it) },
        onSaveClick = { viewModel.save() },
        onOptionsChange = { viewModel.updateOptions(it) },
        onCancelClick = onClose
    )
}

@Composable
private fun AddEditMetricContent(
    state: AddEditMetricScreenState,
    onNameChange: (String) -> Unit,
    onTypeChange: (MetricType) -> Unit,
    onOptionsChange: (TypeOptions) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        if (state.previewMetric != null) {
            var metricState by remember { mutableStateOf("") }
            MetricInput(
                modifier = Modifier.fillMaxWidth(),
                metric = state.previewMetric,
                state = metricState,
                onStateChanged = { metricState = it}
            )
        }

        FRCKrawlerTextField(
            modifier = Modifier.padding(top = 24.dp),
            value = state.name,
            onValueChange = onNameChange,
            label = "Metric name"
        )

        MetricTypeSelector(
            metricType = state.type,
            onMetricTypeSelected = onTypeChange
        )

        when (state.options) {
            TypeOptions.None -> {}
            is TypeOptions.IntRange -> {
                IntRangeOptions(
                    options = state.options,
                    onOptionsChanged = onOptionsChange
                )
            }
            is TypeOptions.SteppedIntRange -> {
                SteppedIntRangeOptions(
                    options = state.options,
                    onOptionsChanged = onOptionsChange
                )
            }
            is TypeOptions.StringList -> {
                StringListOptions(
                    options = state.options,
                    onOptionsChanged = onOptionsChange
                )
            }
        }

        DialogButtons(
            saveEnabled = state.saveEnabled,
            onSaveClick = onSaveClick,
            onCancelClick = onCancelClick
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MetricTypeSelector(
    metricType: MetricType?,
    onMetricTypeSelected: (MetricType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { },
        ) {
            val buttonText = metricType?.name ?: "Select type "
            Text(buttonText)
            Icon(Icons.Rounded.Menu, contentDescription = "Open Menu")
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            MetricType.entries.forEach { type ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onMetricTypeSelected(type)
                    }
                ) {
                    Text(text = type.name)
                }
            }
        }
    }
}

@Composable
private fun StringListOptions(
    modifier: Modifier = Modifier,
    options: TypeOptions.StringList,
    onOptionsChanged: (TypeOptions.StringList) -> Unit
) {
    FRCKrawlerTextField(
        modifier = modifier,
        value = options.options.joinToString(","),
        onValueChange = {
            val newOptions = TypeOptions.StringList(it.split(","))
            onOptionsChanged(newOptions) },
        label = "Comma separated list of options"
    )
}

@Composable
private fun IntRangeOptions(
    modifier: Modifier = Modifier,
    options: TypeOptions.IntRange,
    onOptionsChanged: (TypeOptions.IntRange) -> Unit
) {
    Column(modifier = modifier) {
        FRCKrawlerTextField(
            keyboardType = KeyboardType.Number,
            value = options.range.first.toString(),
            onValueChange = {
                val newFirst = it.toIntOrNull()
                newFirst?.let {
                    val newOptions = TypeOptions.IntRange(newFirst..options.range.last)
                    onOptionsChanged(newOptions)
                }
                // TODO validation messaging?
            },
            label = "Minimum"
        )
        FRCKrawlerTextField(
            modifier = Modifier.padding(top = 12.dp),
            keyboardType = KeyboardType.Number,
            value = options.range.last.toString(),
            onValueChange = {
                val newLast = it.toIntOrNull()
                newLast?.let {
                    val newOptions = TypeOptions.IntRange(options.range.first..newLast)
                    onOptionsChanged(newOptions)
                }
                // TODO validation messaging?
            },
            label = "Maximum"
        )
    }
}

@Composable
private fun SteppedIntRangeOptions(
    modifier: Modifier = Modifier,
    options: TypeOptions.SteppedIntRange,
    onOptionsChanged: (TypeOptions.SteppedIntRange) -> Unit
) {
    Column(modifier = modifier) {
        FRCKrawlerTextField(
            keyboardType = KeyboardType.Number,
            value = options.range.first.toString(),
            onValueChange = {
                val newFirst = it.toIntOrNull()
                newFirst?.let {
                    val newOptions = options.copy(range = newFirst..options.range.last)
                    onOptionsChanged(newOptions)
                }
                // TODO validation messaging?
            },
            label = "Minimum"
        )

        FRCKrawlerTextField(
            modifier = Modifier.padding(top = 12.dp),
            keyboardType = KeyboardType.Number,
            value = options.range.last.toString(),
            onValueChange = {
                val newLast = it.toIntOrNull()
                newLast?.let {
                    val newOptions = options.copy(range = options.range.first..newLast)
                    onOptionsChanged(newOptions)
                }
                // TODO validation messaging?
            },
            label = "Maximum"
        )

        FRCKrawlerTextField(
            modifier = Modifier.padding(top = 12.dp),
            keyboardType = KeyboardType.Number,
            value = options.range.last.toString(),
            onValueChange = {
                val newStep = it.toIntOrNull()
                newStep?.let {
                    val newOptions = options.copy(step = newStep)
                    onOptionsChanged(newOptions)
                }
                // TODO validation messaging?
            },
            label = "Step size"
        )
    }
}

@Composable
private fun DialogButtons(
    saveEnabled: Boolean,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            modifier = Modifier.padding(12.dp),
            onClick = onCancelClick
        ) {
            Text("Cancel")
        }
        TextButton(
            modifier = Modifier.padding(12.dp),
            enabled = saveEnabled,
            onClick = onSaveClick
        ) {
            Text("Save")
        }
    }
}


@Preview
@Composable
private fun BooleanMetricPreview() {
    val state = AddEditMetricScreenState(
        name = "Sample boolean",
        type = MetricType.Boolean,
        previewMetric = Metric.BooleanMetric(
            category = MetricCategory.Match,
            name = "Sample boolean",
            priority = 0,
            enabled = true
        )
    )
    FrcKrawlerTheme {
        Surface {
            AddEditMetricContent(
                state = state,
                onNameChange = {},
                onTypeChange = {},
                onSaveClick = {},
                onCancelClick = {},
                onOptionsChange = {}
            )
        }
    }
}

@Preview
@Composable
private fun StringListOptionsPreview() {
    FrcKrawlerTheme {
        Surface {
            StringListOptions(
                options = TypeOptions.StringList(
                    listOf("One", "Two", "Three")
                ),
                onOptionsChanged = {}
            )
        }
    }
}

@Preview
@Composable
private fun IntRangeOptionsPreview() {
    FrcKrawlerTheme {
        Surface {
            IntRangeOptions(
                options = TypeOptions.IntRange(
                    range = 1..10
                ),
                onOptionsChanged = {}
            )
        }
    }
}

@Preview
@Composable
private fun SteppedIntRangeOptionsPreview() {
    FrcKrawlerTheme {
        Surface {
            SteppedIntRangeOptions(
                options = TypeOptions.SteppedIntRange(
                    range = 1..10,
                    step = 2
                ),
                onOptionsChanged = {}
            )
        }
    }
}