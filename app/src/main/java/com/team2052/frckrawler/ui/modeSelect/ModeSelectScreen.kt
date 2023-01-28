package com.team2052.frckrawler.ui.modeSelect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.team2052.frckrawler.R
import com.team2052.frckrawler.ui.components.*
import com.team2052.frckrawler.ui.navigation.Screen
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
        appBar = {
            FRCKrawlerAppBar(
                navController = navController,
                scaffoldState = scaffoldState,
                title = {
                    Text(stringResource(R.string.mode_select_screen_title))
                }
            )
        },
        drawerContent = { FRCKrawlerDrawer() },
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

    ExpandableCardGroup {
        expandableCard { id ->
            RemoteScoutCard(
                id = id,
                modifier = modifier,
                viewModel = viewModel,
                navController = navController,
            )
        }
        expandableCard { id ->
            ServerCard(
                id = id,
                modifier = modifier,
                viewModel = viewModel,
                navController = navController,
            )
        }
        expandableCard { id ->
            SoloScoutCard(
                id = id,
                modifier = modifier,
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}

@Composable
private fun RemoteScoutCard(
    id: Int,
    modifier: Modifier = Modifier,
    viewModel: ModeSelectViewModel,
    navController: NavController,
) {
    var server by remember { mutableStateOf(viewModel.remoteScoutData.server) }
    var serverValidity by remember { mutableStateOf(true) }

    ExpandableCard(
        modifier = modifier,
        header = {
            CardHeader(
                title = { Text(stringResource(R.string.mode_remote_scout)) },
                description = { Text(stringResource(R.string.mode_remote_scout_description)) },
            )
        },
        actions = {
            TextButton(onClick = {
                if (server.isEmpty()) serverValidity = false

                if (serverValidity) {
                    navController.navigate(Screen.Scout.route) {
                        popUpTo(Screen.ModeSelect.route) { inclusive = true }
                    }
                }
            }) {
                Text(stringResource(R.string.mode_remote_scout_continue))
            }
        },
        expanded = viewModel.expandedCard == id,
        onExpanded = { expanded -> viewModel.expandedCard = if (expanded) id else -1 },
        content = {
            // Server selection dropdown
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                FRCKrawlerDropdown(
//                    modifier = Modifier,
//                    value = server,
//                    onValueChange = {
//                        server = it
//                        viewModel.remoteScoutData = viewModel.remoteScoutData.copy(server = server)
//                    },
//                    validity = serverValidity,
//                    validityCheck = {
//                        serverValidity = it.isNotEmpty()
//                    },
//                    label = "Server",
//                    dropdownItems = listOf("KnightKrawler", "team 3053")
//                )
//                Spacer(modifier = Modifier.width(spaceLarge))
//                TextButton(onClick = { /*TODO*/ }) {
//                    Text("Refresh")
//                }
//            }
        },
    )
}

@Composable
private fun ServerCard(
    id: Int,
    modifier: Modifier = Modifier,
    viewModel: ModeSelectViewModel,
    navController: NavController,
) {

    ExpandableCard(
        modifier = modifier,
        header = {
            CardHeader(
                title = { Text(stringResource(R.string.mode_server)) },
                description = { Text(stringResource(R.string.mode_server_description)) },
            )
        },
        actions = {
            TextButton(onClick = {
                navController.navigate(Screen.Server.route) {
                    popUpTo(Screen.ModeSelect.route) { inclusive = true }
                }
            }) {
                Text(stringResource(R.string.mode_server_continue))
            }
        },
        expanded = viewModel.expandedCard == id,
        onExpanded = { expanded -> viewModel.expandedCard = if (expanded) id else -1 },
        content = {
        },
    )
}

@Composable
private fun SoloScoutCard(
    id: Int,
    modifier: Modifier = Modifier,
    viewModel: ModeSelectViewModel,
    navController: NavController,
) {
    ExpandableCard(
        modifier = modifier,
        header = {
            CardHeader(
                title = { Text(stringResource(R.string.mode_solo_scout)) },
                description = { Text(stringResource(R.string.mode_solo_scout_description)) },
            )
        },
        actions = {
            TextButton(onClick = {
                navController.navigate(Screen.Scout.route) {
                    popUpTo(Screen.ModeSelect.route) { inclusive = true }
                }
            }) {
                Text(stringResource(R.string.mode_solo_scout_continue))
            }
        },
        expanded = viewModel.expandedCard == id,
        onExpanded = { expanded -> viewModel.expandedCard = if (expanded) id else -1 },
        content = {

        },
    )
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