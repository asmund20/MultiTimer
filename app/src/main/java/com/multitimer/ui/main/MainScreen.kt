package com.multitimer.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.multitimer.strToNum
import com.multitimer.ui.theme.MultiTimerTheme


@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    val state by viewModel.state.collectAsState()

    if (state.showAdditionPopup) {
        Dialog(onDismissRequest = { viewModel.toggleAdditionPopup() }) {
            NewTimerCard(viewModel)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Text(
                "MultiTimer",
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        IconButton(
            onClick = { viewModel.toggleAdditionPopup() },
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .width(80.dp)
                .height(45.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Plus icon",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        LazyColumn {
            items(state.displayTimers) { TimerCard(it) }
        }
    }
}

@Composable
fun TimerCard(t: DisplayTimer) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(t.name, fontSize = 20.sp)
                Text("${t.hours}:${t.minutes}:${t.seconds}", fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun NewTimerCard(viewModel: MainScreenViewModel) {
    var name = rememberSaveable { mutableStateOf("") }
    var hours = rememberSaveable { mutableStateOf("") }
    var minutes = rememberSaveable { mutableStateOf("") }
    var seconds = rememberSaveable { mutableStateOf("") }
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)
        ) {
            OutlinedTextField(value = name.value, { name.value = it }, label = { Text("Name") })
            Row {
                TimeTextField(
                    hours.value, {
                        if (it.isDigitsOnly() && it.isNotEmpty() && it.toInt() < 99) hours.value =
                            it else if (it.isEmpty()) hours.value = ""
                    }, "h"
                )
                TimeTextField(
                    minutes.value, {
                        if (it.isDigitsOnly() && it.isNotEmpty() && it.toInt() < 60) minutes.value =
                            it else if (it.isEmpty()) minutes.value = ""
                    }, "m"
                )
                TimeTextField(
                    seconds.value, {
                        if (it.isDigitsOnly() && it.isNotEmpty() && it.toInt() < 60) seconds.value =
                            it else if (it.isEmpty()) seconds.value = ""
                    }, "s"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                viewModel.addTimer(
                    name.value,
                    strToNum(hours.value),
                    strToNum(minutes.value),
                    strToNum(seconds.value)
                )
                viewModel.toggleAdditionPopup()
            }) {
                Text("Start timer")
            }
        }
    }
}

@Composable
fun TimeTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value,
        onValueChange,
        modifier = Modifier
            .width(65.dp)
            .height(75.dp)
            .padding(3.dp),
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}

@Preview
@Composable
fun PreviewNewTimerCard() {
    MultiTimerTheme {
        NewTimerCard(MainScreenViewModel())
    }
}

@Preview
@Composable
fun PreviewTimerCard() {
    MultiTimerTheme {
        TimerCard(t = DisplayTimer("Bananas", "0", "8", "45"))
    }
}