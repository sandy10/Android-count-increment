package com.sandeep.countincrementthroughc.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sandeep.countincrementthroughc.viewmodel.MainViewModel

/**
 * Main UI screen displaying:
 * - Increment button
 * - List of counter updates
 *
 * @param viewModel ViewModel providing UI state
 */
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {

    val items by viewModel.items.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)
    ) {

        /**
         * Button to trigger counter increment.
         */
        Button(
            onClick = { viewModel.onButtonClick() },
            modifier = Modifier.padding(20.dp).align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text("Click Me")
        }

        /**
         * show this message when items is empty
         */
        if (items.isEmpty()) {
            Text(
                text = "Click the button to add count.",
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            )
        }

        /**
         * List displaying counter updates.
         */
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text (
                            text = "Count: ${item.count}",
                            color = Color.White
                        )
                        Text (
                            text = "Time: ${item.timestamp}",
                            color = Color.White

                        )
                    }
                }
            }
        }
    }
}