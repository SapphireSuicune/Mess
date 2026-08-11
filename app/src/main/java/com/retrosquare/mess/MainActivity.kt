package com.retrosquare.mess

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.retrosquare.mess.ui.theme.MessTheme
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.content.pm.ShortcutManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

class MainActivity : ComponentActivity() {

    private var pendingText by mutableStateOf<String?>(null)
    private var pendingSender by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (handleShareIntent(intent)) return   // was a share → we're done, finishing

        setContent {
            MessTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController, startDestination = "home") {
                        composable("home") { Homepage(navController, currentRoute) }
                        composable("people") { PeopleScreen(navController, currentRoute) }
                    }

                    pendingText?.let { text ->
                        SenderPromptDialog(
                            message = text,
                            initialSender = pendingSender,
                            onSave = { name ->
                                saveMessage(name, text)
                                pendingText = null
                                finish()
                            },
                            onDismiss = {
                                pendingText = null
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleShareIntent(intent)
    }

    private fun handleShareIntent(intent: Intent?): Boolean {
        if (intent?.action != Intent.ACTION_SEND || intent.type != "text/plain") return false
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT) ?: return false

        pendingSender = intent.getStringExtra(ShortcutManagerCompat.EXTRA_SHORTCUT_ID)
            ?.removePrefix("sender_")
            ?: ""

        pendingText = sharedText
        return false
    }

    private fun saveMessage(sender: String, message: String) {
        val app = applicationContext
        appScope.launch {
            AppDatabase.getDatabase(app).messDao().insertMess(
                Mess(sender = sender, message = message, timestamp = System.currentTimeMillis())
            )
            if (sender.isNotBlank()) publishSenderShortcut(app, sender)
        }
    }
}