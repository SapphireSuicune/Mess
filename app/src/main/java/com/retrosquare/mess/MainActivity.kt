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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedText = if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            intent.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }

        if (sharedText != null) {
            // 1. Get the last recorded sender from NotificationListener
            val prefs = getSharedPreferences("MessPrefs", Context.MODE_PRIVATE)
            val lastSender = prefs.getString("LAST_SENDER", "{unknown}") ?: "{unknown}"

            // 2. Save the memory
            saveMessage(sender = lastSender, message = sharedText)

            // 3. Show a toast showing who was tagged
            Toast.makeText(this, "Saved message from $lastSender!", Toast.LENGTH_SHORT).show()

            // 4. Return to Google Messages instantly
            finish()
        } else {
            setContent {
                MessTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(
                            topBar = { MessTopAppBar() }
                        ) { innerPadding -> 
                                MessList(innerPadding = innerPadding)
                            }
                    }
                }
            }
        }
    }

    private fun saveMessage(sender: String, message: String) {
        // Run database operation in a background thread using Coroutines
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(applicationContext)
            
            val newMess = Mess(
                sender = sender,
                message = message,
                timestamp = System.currentTimeMillis()
            )
            
            db.messDao().insertMess(newMess)
        }
    }
}