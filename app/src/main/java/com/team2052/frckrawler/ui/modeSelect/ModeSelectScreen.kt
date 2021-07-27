package com.team2052.frckrawler.ui.modeSelect

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.team2052.frckrawler.R
import com.team2052.frckrawler.ui.nav.NavScreen
import com.team2052.frckrawler.ui.components.*
import com.team2052.frckrawler.ui.theme.FrcKrawlerTheme

@Composable
fun ModeSelectScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel: ModeSelectViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()

    FRCKrawlerScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        refreshing = viewModel.isRefreshing,
        onRefresh = { viewModel.refresh() },
        appBar = {
            FRCKrawlerAppBar(
                navController = navController,
                scaffoldState = scaffoldState,
                title = {
                    Text(stringResource(R.string.mode_select_screen_title))
                }
            )
        },
        drawerContent = {
            FRCKrawlerDrawer()
        },
    ) { contentPadding ->
        ModeSelectScreenContent(
            modifier = modifier.padding(contentPadding),
            viewModel = viewModel,
            navController = navController,
        )
    }
}

@Composable
private fun ModeSelectScreenContent(
    modifier: Modifier = Modifier,
    viewModel: ModeSelectViewModel,
    navController: NavController,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProvideTextStyle(MaterialTheme.typography.h6) {
            Text(stringResource(R.string.welcome_message))
            Text(stringResource(R.string.getting_started_message))
        }
    }

    // TODO: Hoisting the state to the view model may be unnecessary if we use rememberSaveable
    FRCKrawlerExpandableCardGroup(modifier = modifier) {
        listOf(
            { modifier, id ->
                var game by remember { mutableStateOf(viewModel.serverData.game) }
                var event by remember { mutableStateOf(viewModel.serverData.event) }

                FRCKrawlerExpandableCard(
                    modifier = modifier,
                    header = {
                        FRCKrawlerCardHeader(
                            title = { Text(stringResource(R.string.mode_remote_scout)) },
                            description = { Text(stringResource(R.string.mode_remote_scout_description)) },
                        )
                    },
                    actions = mapOf(
                        Pair(stringResource(R.string.mode_remote_scout_continue), {
                            if (game.isNotBlank() && event.isNotBlank()) {
                                navController.navigate(NavScreen.SCOUT.route) {
                                    popUpTo(NavScreen.MODE_SELECT.route) { inclusive = true }
                                }
                            }
                        })
                    ),
                    expanded = viewModel.expandedCard == id,
                    onExpanded = { expanded -> viewModel.expandedCard = if (expanded) id else -1 },
                    content = {
                        FRCKrawlerDropdown(
                            modifier = Modifier.padding(bottom = 12.dp),
                            value = game,
                            onValueChange = {
                                game = it
                                viewModel.serverData = viewModel.serverData.copy(game = game)
                            },
                            label = "Game",
                            dropdownItems = listOf("Infinite Recharge at Home", "Infinite Recharge", "Rover Ruckus")
                        )

                        FRCKrawlerDropdown(
                            value = event,
                            onValueChange = {
                                event = it
                                viewModel.serverData = viewModel.serverData.copy(event = event)
                            },
                            label = "Event",
                            dropdownItems = listOf("Northern Lights")
                        )
                    },
                )
            },
            { modifier, id ->
                var teamNumber by remember { mutableStateOf(viewModel.serverData.teamNumber) }
                var teamNumberValidity by remember { mutableStateOf(false) }

                var serverName by remember { mutableStateOf(viewModel.serverData.serverName) }
                var serverNameValidity by remember { mutableStateOf(false) }

                // TODO: In the future using something other than strings, which could be unreliable, could be good
                var game by remember { mutableStateOf(viewModel.serverData.game) }
                var event by remember { mutableStateOf(viewModel.serverData.event) }

                FRCKrawlerExpandableCard(
                    modifier = modifier,
                    header = {
                        FRCKrawlerCardHeader(
                            title = { Text(stringResource(R.string.mode_server)) },
                            description = { Text(stringResource(R.string.mode_server_description)) },
                        )
                    },
                    actions = mapOf(
                        Pair(stringResource(R.string.mode_server_continue), {
                            if (teamNumberValidity && serverNameValidity && game.isNotBlank() && event.isNotBlank()) {
                                navController.navigate(NavScreen.SERVER.route) {
                                    popUpTo(NavScreen.MODE_SELECT.route) { inclusive = true }
                                }
                            }
                        })
                    ),
                    expanded = viewModel.expandedCard == id,
                    onExpanded = { expanded -> viewModel.expandedCard = if (expanded) id else -1 },
                    content = {
                        FRCKrawlerTextField(
                            modifier = Modifier.padding(bottom = 12.dp),
                            value = teamNumber,
                            onValueChange = {
                                if (it.length <= 4) teamNumber = it
                                viewModel.serverData = viewModel.serverData.copy(teamNumber = teamNumber)
                            },
                            validate = { it.length == 4 },
                            onValidityChange = { teamNumberValidity = it },
                            label = "Team Number",
                            keyboardType = KeyboardType.NumberPassword,
                        )

                        var serverNameHasFocus by remember { mutableStateOf(false) }
                        FRCKrawlerTextField(
                            modifier = Modifier.padding(bottom = 12.dp),
                            value = serverName,
                            onValueChange = {
                                if (it.length <= 50) serverName = it
                                viewModel.serverData = viewModel.serverData.copy(serverName = serverName)
                            },
                            validate = { it.length >= 4 },
                            onValidityChange = { serverNameValidity = it },
                            label = "Server Name" + if (serverNameHasFocus) " - ${serverName.length}/50" else "",
                            onFocusChange = { serverNameHasFocus = it },
                        )

                        FRCKrawlerDropdown(
                            modifier = Modifier.padding(bottom = 12.dp),
                            value = game,
                            onValueChange = {
                                game = it
                                viewModel.serverData = viewModel.serverData.copy(game = game)
                            },
                            label = "Game",
                            dropdownItems = listOf("Infinite Recharge at Home", "Infinite Recharge", "Rover Ruckus")
                        )

                        FRCKrawlerDropdown(
                            value = event,
                            onValueChange = {
                                event = it
                                viewModel.serverData = viewModel.serverData.copy(event = event)
                            },
                            label = "Event",
                            dropdownItems = listOf("Northern Lights")
                        )
                    },
                )
            },
            { modifier, id ->
                var game by remember { mutableStateOf(viewModel.serverData.game) }
                var event by remember { mutableStateOf(viewModel.serverData.event) }

                FRCKrawlerExpandableCard(
                    modifier = modifier,
                    header = {
                        FRCKrawlerCardHeader(
                            title = { Text(stringResource(R.string.mode_solo_scout)) },
                            description = { Text(stringResource(R.string.mode_solo_scout_description)) },
                        )
                    },
                    actions = mapOf(
                        Pair(stringResource(R.string.mode_solo_scout_continue), {
                            if (game.isNotBlank() && event.isNotBlank()) {
                                navController.navigate(NavScreen.SCOUT.route) {
                                    popUpTo(NavScreen.MODE_SELECT.route) { inclusive = true }
                                }
                            }
                        })
                    ),
                    expanded = viewModel.expandedCard == id,
                    onExpanded = { expanded -> viewModel.expandedCard = if (expanded) id else -1 },
                    content = {
                        FRCKrawlerDropdown(
                            modifier = Modifier.padding(bottom = 12.dp),
                            value = game,
                            onValueChange = {
                                game = it
                                viewModel.serverData = viewModel.serverData.copy(game = game)
                            },
                            label = "Game",
                            dropdownItems = listOf("Infinite Recharge at Home", "Infinite Recharge", "Rover Ruckus")
                        )

                        FRCKrawlerDropdown(
                            value = event,
                            onValueChange = {
                                event = it
                                viewModel.serverData = viewModel.serverData.copy(event = event)
                            },
                            label = "Event",
                            dropdownItems = listOf("Northern Lights")
                        )
                    },
                )
            },
        )
    }
}

@Preview
@Composable
private fun ModeSelectScreenPreviewLight() {
    FrcKrawlerTheme(darkTheme = false) {
        ModeSelectScreen(navController = rememberNavController())
    }
}

@Preview
@Composable
private fun ModeSelectScreenPreviewDark() {
    FrcKrawlerTheme(darkTheme = true) {
        ModeSelectScreen(navController = rememberNavController())
    }
}