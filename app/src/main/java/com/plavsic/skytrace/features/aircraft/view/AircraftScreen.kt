package com.plavsic.skytrace.features.aircraft.view

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity
import com.plavsic.skytrace.features.aircraft.viewmodel.AircraftViewModel
import com.plavsic.skytrace.utils.conversions.Conversions.extractDate
import com.plavsic.skytrace.utils.resource.UIState
import java.util.Locale

@Composable
fun AircraftScreen() {
    val viewModel:AircraftViewModel = hiltViewModel()

    val context = LocalContext.current

    val aircrafts = viewModel.aircrafts

    val isSortedAscending = remember { mutableStateOf(true) }
    val showDialog = remember { mutableStateOf(false) }
    val minAge = remember { mutableStateOf("") }
    val maxAge = remember { mutableStateOf("") }
    val statusFilter = remember { mutableStateOf("") }
    val modelFilter = remember { mutableStateOf("") }
    val enginesCountFilter = remember { mutableStateOf("") }



    LaunchedEffect(Unit) {
        viewModel.fetchAircrafts()
    }

    when(aircrafts.value){
        is UIState.Idle -> {}
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is UIState.Success -> {
            val aircraftsData = (aircrafts.value as UIState.Success<List<AircraftEntity>>).data

            val filteredAircrafts = aircraftsData.filter { aircraft ->
                // There are some planes with 2016 age - error in API (Filter them)
                val wrongDate = aircraft.planeAge.length == 2

                // Filter for years (from-to)
                val ageMatches = (minAge.value.isEmpty() || (aircraft.planeAge.toIntOrNull() ?: 0) >= (minAge.value.toIntOrNull() ?: 0)) &&
                        (maxAge.value.isEmpty() || (aircraft.planeAge.toIntOrNull() ?: 0) <= (maxAge.value.toIntOrNull() ?: 0))


                val statusMatches = if (statusFilter.value.isNotEmpty()) {
                    aircraft.planeStatus.equals(statusFilter.value, ignoreCase = true)
                } else true

                // Filter for model
                val modelMatches = if (modelFilter.value.isNotEmpty()) {
                    aircraft.airplaneIataType.contains(modelFilter.value, ignoreCase = true)
                } else true

                // Filter for num of engines
                val enginesCountMatches = if (enginesCountFilter.value.isNotEmpty()) {
                    aircraft.enginesCount == enginesCountFilter.value
                } else true

                // Returns true if aircraft has all filters set to true
                wrongDate && ageMatches && statusMatches && modelMatches && enginesCountMatches
            }

            // Sorting aircrafts by age
            val sortedAircrafts = remember(filteredAircrafts, isSortedAscending.value) {
                if (isSortedAscending.value) {
                    filteredAircrafts.sortedBy { it.planeAge.toIntOrNull() ?: 0 }.toList()
                } else {
                    filteredAircrafts.sortedByDescending { it.planeAge.toIntOrNull() ?: 0 }.toList()
                }
            }

            AircraftView(
                showDialog = showDialog,
                isSortedAscending = isSortedAscending,
                aircrafts=sortedAircrafts,
                minAge = minAge,
                maxAge = maxAge,
                statusFilter = statusFilter,
                modelFilter = modelFilter,
                enginesCountFilter = enginesCountFilter
            )

        }
        is UIState.Error.NetworkError -> {
            Toast.makeText(context,"Network Error", Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.ServerError -> {
            val error = (aircrafts.value as UIState.Error.ServerError)
            Toast.makeText(context,"Server Error ${error.code} - ${error.message}", Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.UnknownError -> {
            Toast.makeText(context,"Unknown Error", Toast.LENGTH_SHORT).show()
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AircraftView(
    modifier:Modifier = Modifier,
    isSortedAscending:MutableState<Boolean>,
    showDialog:MutableState<Boolean>,
    aircrafts:List<AircraftEntity>,
    minAge:MutableState<String>,
    maxAge:MutableState<String>,
    statusFilter:MutableState<String>,
    modelFilter:MutableState<String>,
    enginesCountFilter:MutableState<String>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
                ),
                title = { Text("Aircrafts") },
                actions = {
                    IconButton(onClick = {
                        isSortedAscending.value = !isSortedAscending.value
                    }) {
                        Icon(Icons.Default.Sort, contentDescription = "Sort by Age")
                    }

                    IconButton(onClick = { showDialog.value = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                }
            )
        },
        content = {
            Column(modifier = modifier.padding(top = 50.dp)) {
                AircraftList(aircrafts = aircrafts)
            }
            if(showDialog.value){
                FilterDialog(
                    minAge = minAge.value,
                    onMinAgeChange = { minAge.value = it },
                    maxAge = maxAge.value,
                    onMaxAgeChange = { maxAge.value = it },
                    statusFilter = statusFilter,
                    modelFilter = modelFilter.value,
                    onModelChange = { modelFilter.value = it },
                    enginesCountFilter = enginesCountFilter.value,
                    onEnginesCountChange = { enginesCountFilter.value = it },
                    onDismiss = { showDialog.value = false },
                    onApply = { showDialog.value = false }
                )
            }
        }
    )
}

@Composable
fun FilterDialog(
    minAge: String,
    onMinAgeChange: (String) -> Unit,
    maxAge: String,
    onMaxAgeChange: (String) -> Unit,
    statusFilter: MutableState<String>,
    modelFilter: String,
    onModelChange: (String) -> Unit,
    enginesCountFilter: String,
    onEnginesCountChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Filter Aircrafts") },
        text = {
            Column {
                OutlinedTextField(
                    value = minAge,
                    onValueChange = onMinAgeChange,
                    label = { Text("Minimum Age") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = maxAge,
                    onValueChange = onMaxAgeChange,
                    label = { Text("Maximum Age") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box {
                    OutlinedTextField(
                        value = statusFilter.value,
                        onValueChange = {},
                        label = { Text("Status") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(text = {Text("Active")}, onClick = { statusFilter.value = "Active"; expanded = false })

                        DropdownMenuItem(text = {Text("Inactive")}, onClick = { statusFilter.value = "Inactive"; expanded = false })
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = modelFilter,
                    onValueChange = onModelChange,
                    label = { Text("Model") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = enginesCountFilter,
                    onValueChange = onEnginesCountChange,
                    label = { Text("Engines Count") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = { onApply() }) {
                Text("Apply")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}


@Composable
fun AircraftCard(
    aircraft:AircraftEntity
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    )
    {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Model: ${aircraft.airplaneIataType}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    InfoRow(label = "Engines Count:", value = aircraft.enginesCount)
                    InfoRow(label = "Engines Type:", value = aircraft.enginesType)
                    InfoRow(label = "First Flight:", value = extractDate(aircraft.firstFlight))
                    InfoRow(label = "Model Code:", value = aircraft.modelCode)
                }

                Spacer(modifier = Modifier.width(16.dp)) // Razmak između stupaca

                Column(modifier = Modifier.weight(1f)) {
                    InfoRow(label = "Owner:", value = aircraft.planeOwner.ifBlank { "N/A" })
                    InfoRow(label = "Production Line:", value = aircraft.productionLine)
                    InfoRow(label = "Registration Date:", value = extractDate(aircraft.registrationDate))
                    InfoRow(label = "Status:", value = aircraft.planeStatus.capitalize(Locale.ROOT))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Registration Number: ${aircraft.numberRegistration}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                )
                Text(
                    text = "Age: ${aircraft.planeAge} years",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                )
            }
        }
    }
}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}


@Composable
fun AircraftList(aircrafts: List<AircraftEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 90.dp),
    ) {
        items(aircrafts) { aircraft ->
            AircraftCard(aircraft = aircraft)
        }
    }
}
