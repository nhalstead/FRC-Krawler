package com.team2052.frckrawler.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team2052.frckrawler.ui.navigation.Screen
import java.util.Locale

@Composable
fun FRCKrawlerTabBar(
    modifier: Modifier = Modifier,
    navigation: Screen,
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit = { },
) {
    if (navigation.screens.contains(currentScreen)) {
        val selectedTabIndex = navigation.screens.indexOf(currentScreen)
        TabRow(
            modifier = modifier.height(48.dp),
            selectedTabIndex = selectedTabIndex,
            backgroundColor = MaterialTheme.colors.primary,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(
                        tabPositions[selectedTabIndex]
                    ),
                    color = MaterialTheme.colors.secondary,
                )
            },
        ) {
            navigation.screens.forEachIndexed { index, screen ->
                Tab(
                    modifier = Modifier.fillMaxHeight(),
                    selected = index == selectedTabIndex,
                    onClick = { onTabSelected(screen) },
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = MaterialTheme.colors.onPrimary,
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 48.dp),
                        text = screen.title.uppercase(Locale.getDefault()),
                    )
                }
            }
        }
    }
}