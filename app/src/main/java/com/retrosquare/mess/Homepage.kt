package com.retrosquare.mess

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.retrosquare.mess.ui.theme.MessTheme
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import com.retrosquare.mess.R

@Composable
fun MessCard(sender: String, message: String, timestamp: Long) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        // A Column lets us stack Sender, Message, and Timestamp vertically!
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Gives room around the text inside the card
        ) {
            Text(
                text = sender,
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Saved ${parseTimestamp(timestamp)}",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessList(innerPadding: PaddingValues) {
    val ctx = LocalContext.current
    val db = AppDatabase.getDatabase(ctx)

    val messList by db.messDao().getAllMesses().collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), // Applies scaffold padding so top bar doesn't overlap!
        contentPadding = PaddingValues(
            top = 4.dp,      // Tightens space between App Bar & first card!
            bottom = 16.dp,   // Keeps space at the very bottom of the list
            start = 16.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp), // Spacing between each card!
        horizontalAlignment = Alignment.CenterHorizontally // Centers each card horizontally
    ) {
        // Generate 15 dummy cards to test scrolling
        items(messList) { mess ->
            MessCard(
                sender = mess.sender,
                message = mess.message,
                timestamp = mess.timestamp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.mess_wordmark),
                contentDescription = "Mess!",
                modifier = Modifier.height(32.dp)
            )
        }
    )
}