package com.team2052.frckrawler.ui.server

import com.team2052.frckrawler.data.model.Event

data class ServerConfiguration(
    val event: Event?,
    val metricSetName: String?
)