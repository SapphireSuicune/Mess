package com.retrosquare.mess

import androidx.compose.runtime.Composable
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DrawerState
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

@Composable
fun MessAppMenu(
    drawerState: DrawerState,
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

                // People
                NavigationDrawerItem(
                    label = { Text("People") },
                    selected = false,
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
                    selected = false,
                    onClick = onCollectionsClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.book_2_24px),
                            contentDescription = null
                        )
                    }
                )

                // Settings
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
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