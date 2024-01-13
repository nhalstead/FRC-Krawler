package com.team2052.frckrawler.ui.game.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2052.frckrawler.data.local.EventDao
import com.team2052.frckrawler.data.local.Game
import com.team2052.frckrawler.data.local.GameDao
import com.team2052.frckrawler.data.local.MetricDao
import com.team2052.frckrawler.data.local.MetricSet
import com.team2052.frckrawler.data.local.MetricSetDao
import com.team2052.frckrawler.data.local.TeamAtEventDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val gameDao: GameDao,
    private val eventDao: EventDao,
    private val teamAtEventDao: TeamAtEventDao,
    private val metricSetDao: MetricSetDao,
    private val metricDao: MetricDao,
) : ViewModel() {

    private val _state = MutableStateFlow<GameDetailState>(GameDetailState.Loading)
    val state: StateFlow<GameDetailState?> = _state

    private var gameId: Int = 0

    fun loadGame(gameId: Int) {
        this.gameId = gameId

        viewModelScope.launch {
            val game = gameDao.get(gameId)
            combine(
                getEvents(gameId),
                getMetricSets(game),
            ) { events, metrics ->
                GameDetailState.Content(
                    game = game,
                    metrics = metrics,
                    events = events
                )
            }.collectLatest {
                _state.value = it
            }
        }
    }

    fun deleteGame() {
        viewModelScope.launch {
            val game = gameDao.get(gameId)
            gameDao.delete(game)
        }
    }

    fun createNewMetricSet(name: String) {
        viewModelScope.launch {
            metricSetDao.insert(
                MetricSet(
                    name = name,
                    gameId = gameId
                )
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getEvents(gameId: Int): Flow<List<GameDetailEvent>> {
        return eventDao.getAllForGame(gameId).mapLatest { events ->
            // TODO try to parallelize
            events.map { event ->
                val teamCount = teamAtEventDao.getTeamCountAtEvent(event.id)
                GameDetailEvent(
                    id = event.id,
                    name = event.name,
                    teamCount = teamCount
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getMetricSets(game: Game): Flow<List<GameDetailMetricSet>> {
        return metricSetDao.getAllForGame(gameId).mapLatest { sets ->
            // TODO try to parallelize
            sets.map { set ->
                val metricCount = metricDao.getMetricCount(set.id)
                GameDetailMetricSet(
                    id = set.id,
                    name = set.name,
                    metricCount = metricCount,
                    isMatchMetrics = set.id == game.matchMetricsSetId,
                    isPitMetrics = set.id == game.pitMetricsSetId,
                )
            }
        }
    }

}