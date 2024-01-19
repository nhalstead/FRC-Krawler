package com.team2052.frckrawler.ui.scout.remote

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.team2052.frckrawler.R
import com.team2052.frckrawler.data.model.DeviceType
import com.team2052.frckrawler.ui.FrcKrawlerPreview
import com.team2052.frckrawler.ui.RequestEnableBluetooth
import com.team2052.frckrawler.ui.components.Card
import com.team2052.frckrawler.ui.components.CardHeader
import com.team2052.frckrawler.ui.components.FRCKrawlerAppBar
import com.team2052.frckrawler.ui.components.FRCKrawlerScaffold
import com.team2052.frckrawler.ui.permissions.BluetoothPermissionRequestDialogs
import com.team2052.frckrawler.ui.theme.FrcKrawlerTheme
import com.team2052.frckrawler.ui.theme.spaceLarge
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ScoutHomeScreen(
  modifier: Modifier = Modifier,
  navController: NavController,
) {
  val viewModel: RemoteScoutViewModel = hiltViewModel()

  // Don't love this, but it is what we need
  val context = LocalContext.current as ComponentActivity

  Box {
    if (viewModel.showPermissionRequests) {
      BluetoothPermissionRequestDialogs(
        deviceType = DeviceType.Client,
        onAllPermissionsGranted = { viewModel.connectToServer(context) },
        onCanceled = { viewModel.showPermissionRequests = false }
      )
    }

    if (viewModel.requestEnableBluetooth) {
      RequestEnableBluetooth(
        deviceType = DeviceType.Client,
        onEnabled = { viewModel.connectToServer(context) },
        onCanceled = { viewModel.requestEnableBluetooth = false }
      )
    }

    FRCKrawlerScaffold(
      modifier = modifier,
      appBar = {
        FRCKrawlerAppBar(
          navController = navController,
          title = {
            Text("Scout")
          }
        )
      },
    ) { contentPadding ->
      val syncState by viewModel.syncState.collectAsState(initial = ServerSyncState.NotSynced)
      ScoutHomeScreenContent(
        modifier = modifier.padding(contentPadding),
        serverState = viewModel.serverConnectionState,
        syncState = syncState,
        onFindServerClicked = { viewModel.connectToServer(context) },
        onSyncClicked = { viewModel.performSync() }
      )
    }
  }
}

@Composable
private fun ScoutHomeScreenContent(
  modifier: Modifier = Modifier,
  serverState: ServerConnectionState,
  syncState: ServerSyncState,
  onFindServerClicked: () -> Unit,
  onSyncClicked: () -> Unit,
) {
  Column(modifier = modifier.padding(spaceLarge)) {
    ServerStatusCard(
      serverState = serverState,
      syncState = syncState,
      onFindServerClicked = onFindServerClicked,
      onSyncClicked = onSyncClicked,
    )
    Spacer(Modifier.height(16.dp))
  }
}

@Composable
private fun ServerStatusCard(
  modifier: Modifier = Modifier,
  serverState: ServerConnectionState,
  syncState: ServerSyncState,
  onFindServerClicked: () -> Unit,
  onSyncClicked: () -> Unit,
) {
  Card(
    modifier = modifier,
    header = {
      CardHeader(
        title = { Text("Server Connection") }
      )
    },
  ) {
    when (serverState) {
      is ServerConnectionState.Connected -> {
        ServerConnected(
          serverState = serverState,
          syncState = syncState,
          onSyncClicked = onSyncClicked,
        )
      }

      is ServerConnectionState.Connecting -> {
        ServerConnecting()
      }

      else -> {
        ServerNotConnected(
          state = serverState,
          onFindServerClicked = onFindServerClicked
        )
      }
    }
  }
}

@Composable
private fun ServerConnected(
  serverState: ServerConnectionState.Connected,
  syncState: ServerSyncState,
  onSyncClicked: () -> Unit,
) {
  Column {
    val connectedText = buildAnnotatedString {
      append(stringResource(R.string.scout_connected_prefix))
      append(" ")
      pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
      append(serverState.name)
    }
    Text(connectedText)

    Spacer(modifier = Modifier.height(16.dp))

    if (syncState is ServerSyncState.Synced) {
      Text(
        text = stringResource(
          id = R.string.scout_last_sync_label,
          syncState.lastSyncTime.format(TIME_FORMAT)
        )
      )

      Spacer(modifier = Modifier.height(16.dp))

      val pendingCountText = buildAnnotatedString {
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append(syncState.pendingDataCount.toString())
        pop()
        append(" ")
        append(pluralStringResource(R.plurals.scout_unsynced_metrics_label, syncState.pendingDataCount, syncState.pendingDataCount))
      }
      Text(pendingCountText)

      Spacer(modifier = Modifier.height(16.dp))
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.End
    ) {
      Button(
        onClick = onSyncClicked,
        enabled = syncState != ServerSyncState.Syncing
      ) {
        Icon(
          imageVector = Icons.Default.Sync,
          contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        Text(stringResource(R.string.scout_sync_action))
      }
    }
  }
}


@Composable
private fun ServerConnecting() {
  Row {
    CircularProgressIndicator(
      modifier = Modifier.size(28.dp)
    )
    Spacer(modifier = Modifier.width(16.dp))
    Text("Connecting...")
  }
}

@Composable
private fun ServerNotConnected(
  state: ServerConnectionState,
  onFindServerClicked: () -> Unit
) {
  Column {
    Text("Connect to a server to start scouting.")

    when (state) {
      is ServerConnectionState.PairingFailed -> {
        Text(
          modifier = Modifier.padding(vertical = 16.dp),
          color = MaterialTheme.colors.error,
          text = "Pairing failed, please try again."
        )
      }

      is ServerConnectionState.NoFrcKrawlerServiceFound -> {
        Text(
          modifier = Modifier.padding(vertical = 16.dp),
          color = MaterialTheme.colors.error,
          text = "FRCKrawler server is not running on the selected device. " +
                  "Please ensure it is running and try again."
        )
      }

      else -> {} // No error, so no text needed
    }

    Spacer(modifier = Modifier.height(16.dp))

    Box(
      modifier = Modifier.fillMaxWidth(),
      contentAlignment = Alignment.BottomEnd
    ) {
      Button(
        onClick = onFindServerClicked,
      ) {
        Text("Find Server")
      }
    }
  }
}

private val TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a")

@FrcKrawlerPreview
@Composable
private fun ScoutScreenConnectedPreview() {
  FrcKrawlerTheme {
    Surface {
      ScoutHomeScreenContent(
        serverState = ServerConnectionState.Connected(
          "KnightKrawler Server"
        ),
        syncState = ServerSyncState.NotSynced,
        onFindServerClicked = { },
        onSyncClicked = { }
      )
    }
  }
}

@FrcKrawlerPreview
@Composable
private fun ScoutScreenNotConnectedPreview() {
  FrcKrawlerTheme {
    Surface {
      ScoutHomeScreenContent(
        serverState = ServerConnectionState.NotConnected,
        syncState = ServerSyncState.NotSynced,
        onFindServerClicked = { },
        onSyncClicked = { }
      )
    }
  }
}

@FrcKrawlerPreview
@Composable
private fun ScoutScreenPendingSyncPreview() {
  FrcKrawlerTheme {
    Surface {
      ScoutHomeScreenContent(
        serverState = ServerConnectionState.Connected(
          "KnightKrawler Server"
        ),
        syncState = ServerSyncState.Synced(
          pendingDataCount = 12,
          lastSyncTime = ZonedDateTime.now()
        ),
        onFindServerClicked = { },
        onSyncClicked = { }
      )
    }
  }
}