package com.retrosquare.mess

import androidx.compose.runtime.Composable
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment

@Composable
fun MessAppMenu(
    drawerState: DrawerState,
    currentRoute: String?,
    onMessesClick: () -> Unit = {},
    onQuotesClick: () -> Unit = {},
    onPeopleClick: () -> Unit = {},
    onCollectionsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.mess_wordmark),
                    contentDescription = "Mess",
                    modifier = Modifier
                        .height(48.dp)
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    tint = MaterialTheme.colorScheme.onSurface
                )

                // Messes
                NavigationDrawerItem(
                    label = { Text("Messes") },
                    selected = currentRoute == "home",
                    onClick = onMessesClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.mop_24px),
                            contentDescription = null
                        )
                    }
                )

                // Quotes
                NavigationDrawerItem(
                    label = { Text("Quotes") },
                    selected = currentRoute == "quotes",
                    onClick = onQuotesClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.forum_24px),
                            contentDescription = null
                        )
                    }
                )

                // People
                NavigationDrawerItem(
                    label = { Text("People") },
                    selected = currentRoute == "people",
                    onClick = onPeopleClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.person_24px),
                            contentDescription = null
                        )
                    }
                )
                
                // Collections
                NavigationDrawerItem(
                    label = { Text("Collections") },
                    selected = currentRoute == "collections",
                    onClick = onCollectionsClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.book_2_24px),
                            contentDescription = null
                        )
                    }
                )

                Spacer(Modifier.weight(1f))

                HorizontalDivider(Modifier.padding(horizontal = 12.dp))

                // Settings
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = currentRoute == "settings",
                    onClick = onSettingsClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.settings_24px),
                            contentDescription = null
                        )
                    }
                )
            }
        },
        content = content
    )
}