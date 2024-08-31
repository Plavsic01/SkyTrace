package com.plavsic.skytrace.features.aircraft.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plavsic.skytrace.features.aircraft.data.local.entity.AircraftEntity
import com.plavsic.skytrace.features.aircraft.viewmodel.AircraftViewModel
import com.plavsic.skytrace.utils.conversions.Conversions.extractDate
import java.util.Locale

@Composable
fun AircraftScreen() {
    val viewModel:AircraftViewModel = hiltViewModel()

    val aircrafts by viewModel.aircrafts
    val isLoadingAircrafts = viewModel.isLoading

    val showDialog = remember { mutableStateOf(false) }
    val minAge = remember { mutableStateOf("") }
    val maxAge = remember { mutableStateOf("") }
    val statusFilter = remember { mutableStateOf("") }
    val modelFilter = remember { mutableStateOf("") }
    val enginesCountFilter = remember { mutableStateOf("") }

    val filteredAircrafts = aircrafts?.filter { aircraft ->
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

        // Returns true if aircraft has all filters are true
        ageMatches && statusMatches && modelMatches && enginesCountMatches
    }

    LaunchedEffect(Unit) {
        viewModel.fetchAircrafts(onError = {})
    }
    if(isLoadingAircrafts){
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }else{
        if(!aircrafts.isNullOrEmpty()){
            AircraftView(
                showDialog = showDialog,
                aircrafts=filteredAircrafts!!,
                minAge = minAge,
                maxAge = maxAge,
                statusFilter = statusFilter,
                modelFilter = modelFilter,
                enginesCountFilter = enginesCountFilter
            )
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AircraftView(
    modifier:Modifier = Modifier,
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
    airplaneIataType: String,
    enginesCount: String,
    enginesType: String,
    firstFlight: String,
    modelCode: String,
    numberRegistration: String,
    planeAge: String,
    planeModel: String,
    planeOwner: String,
    planeStatus: String,
    productionLine: String,
    registrationDate: String
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Model: $airplaneIataType",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Engines Count: $enginesCount")
            Text(text = "Engines Type: $enginesType")
            Text(text = "First Flight: ${extractDate(firstFlight)}")
            Text(text = "Model Code: $modelCode")
            Text(text = "Registration Number: $numberRegistration")
            Text(text = "Age: $planeAge years")
            Text(text = "Model: $planeModel")
            Text(text = "Owner: ${planeOwner.ifBlank { "N/A" }}")
            Text(text = "Production Line: $productionLine")
            Text(text = "Registration Date: ${extractDate(registrationDate)}")
            Text(text = "Status: ${planeStatus.capitalize(Locale.ROOT)}")
        }
    }
}

@Composable
fun AircraftList(aircrafts: List<AircraftEntity>) {
    LazyColumn {
        items(aircrafts) { aircraft ->
            AircraftCard(
                airplaneIataType = aircraft.airplaneIataType,
                enginesCount = aircraft.enginesCount,
                enginesType = aircraft.enginesType,
                firstFlight = aircraft.firstFlight,
                modelCode = aircraft.modelCode,
                numberRegistration = aircraft.numberRegistration,
                planeAge = aircraft.planeAge,
                planeModel = aircraft.planeModel,
                planeOwner = aircraft.planeOwner,
                planeStatus = aircraft.planeStatus,
                productionLine = aircraft.productionLine,
                registrationDate = aircraft.registrationDate
            )
        }
    }
}
