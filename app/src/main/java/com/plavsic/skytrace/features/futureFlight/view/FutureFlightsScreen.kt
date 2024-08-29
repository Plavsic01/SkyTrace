package com.plavsic.skytrace.features.futureFlight.view

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplaneTicket
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plavsic.skytrace.features.futureFlight.model.FutureFlightResponse
import com.plavsic.skytrace.features.futureFlight.viewmodel.FutureFlightViewModel
import com.plavsic.skytrace.features.map.model.FlightResponse
import com.plavsic.skytrace.utils.WeekDays.Companion.fromDayNumber
import com.plavsic.skytrace.utils.conversions.Conversions
import com.plavsic.skytrace.utils.conversions.Conversions.capitalizeWords
import com.plavsic.skytrace.utils.conversions.Conversions.formatDateFromMiliseconds
import com.plavsic.skytrace.utils.resource.UIState
import kotlinx.coroutines.delay


@Composable
fun FutureFlightsScreen(
    viewModel: FutureFlightViewModel
) {
    val context = LocalContext.current

    val state by viewModel.futureFlights

    FilterScreen{
            date,type,iataCode,flightNumber,airlineIata ->

            viewModel.fetchFutureFlights(
                iataCode = iataCode,
                type = type,
                date = date,
                airlineIata = airlineIata,
                flightNum = flightNumber
            )
    }

    when(state){
        is UIState.Idle -> {}
        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is UIState.Success -> {
            var futureFlights = (state as UIState.Success<List<FutureFlightResponse>>).data
            futureFlights = futureFlights.filter {
                it.codeshared == null
            }
            Column(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .background(Color.White)
            ){
                DisplayFutureFlights(flightList = futureFlights)
            }
        }
        is UIState.Error.NetworkError -> {
            Toast.makeText(context,"Network Error", Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.ServerError -> {
            Toast.makeText(context,"Server Error", Toast.LENGTH_SHORT).show()
        }
        is UIState.Error.UnknownError -> {
            Toast.makeText(context,"Unknown Error", Toast.LENGTH_SHORT).show()
        }
    }


}


@Composable
fun DisplayFutureFlights(
    modifier: Modifier = Modifier,
    flightList: List<FutureFlightResponse>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(flightList) { flight ->
            FutureFlightCard(futureFlight = flight)
        }
    }
}




@Composable
fun FutureFlightCard(
    modifier: Modifier = Modifier,
    futureFlight:FutureFlightResponse
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(16.dp)) {

//            futureFlight.codeshared?.airline ?:
//            futureFlight.codeshared?.flight ?:

            val displayedAirline = futureFlight.airline
            val displayedFlight = futureFlight.flight

            // Airline Name and Flight Number
            Text(
                text = "${capitalizeWords(displayedAirline.name)} (${displayedAirline.iataCode.uppercase()})",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1976D2)
            )
            Text(
                text = "Flight: ${displayedFlight.iataNumber.uppercase()}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = modifier.height(8.dp))

            // Departure and Arrival Information
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Departure", fontWeight = FontWeight.Bold,color = Color(0xFF1976D2))
                    Text(text = "IATA: ${futureFlight.departure.iataCode.uppercase()}")
                    Text(text = "Terminal: ${futureFlight.departure.terminal.uppercase()}")
                    Text(text = "Gate: ${futureFlight.departure.gate.uppercase().ifEmpty { "N/A" }}")
                    Text(text = "Time: ${futureFlight.departure.scheduledTime}")
                }
                Column {
                    Text(text = "Arrival", fontWeight = FontWeight.Bold,color = Color(0xFF1976D2))
                    Text(text = "IATA: ${futureFlight.arrival.iataCode.uppercase()}")
                    Text(text = "Terminal: ${futureFlight.arrival.terminal.uppercase()}")
                    Text(text = "Gate: ${futureFlight.arrival.gate.uppercase().ifEmpty { "N/A" }}")
                    Text(text = "Time: ${futureFlight.arrival.scheduledTime}")
                }
            }

            Spacer(modifier = modifier.height(8.dp))

            // Aircraft Information
            Text(
                text = "Aircraft: ${futureFlight.aircraft.modelText} (${futureFlight.aircraft.modelCode})",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = modifier.height(8.dp))

            // Weekday Information
            Text(
                text = "Weekday: ${fromDayNumber(futureFlight.weekday)}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = modifier.height(16.dp))
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    onFilterApplied: (String, String, String, String, String) -> Unit
) {
    var showFilterDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
                ),
                title = { Text("Future Flights") },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                }
            )
        },
        content = { paddingValues ->

            FilterContent(
                modifier = Modifier.padding(paddingValues),
                onFilterApplied = onFilterApplied,
                showFilterDialog = showFilterDialog,
                onDismissDialog = { showFilterDialog = false }
            )
        }
    )
}



@Composable
fun FilterContent(
    modifier: Modifier = Modifier,
    onFilterApplied: (String, String, String, String, String) -> Unit,
    showFilterDialog: Boolean,
    onDismissDialog: () -> Unit
) {
    // Content inside the screen
    if (showFilterDialog) {
        FilterDialog(
            onFilterApplied = { date, type, iataCode, flightNumber, airlineIata ->
                onFilterApplied(date, type, iataCode, flightNumber, airlineIata)
                onDismissDialog()
            },
            onDismissRequest = onDismissDialog
        )
    }
}

@Composable
fun FilterDialog(
    onFilterApplied: (String, String, String, String, String) -> Unit,
    onDismissRequest: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    var date by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("departure") }  // Default value
    var iataCode by remember { mutableStateOf("") }
    var flightNumber by remember { mutableStateOf("") }
    var airlineIata by remember { mutableStateOf("") }

    val isFilterEnabled = date.isNotEmpty() && type.isNotEmpty() && iataCode.isNotEmpty().and(iataCode.length == 3)

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Filter Options") },
        text = {
            Column {

                Row {

                    IconButton(onClick = {
                        showDatePicker = !showDatePicker
                    }
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = "Date Icon")

                        if(showDatePicker){
                            DatePickerModal(onDateSelected = {
                                date = formatDateFromMiliseconds(it!!)
                            }) {
                                showDatePicker = !showDatePicker
                            }
                        }
                    }

                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        readOnly = true,
                        label = { Text("Date (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth(),
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                Box {
                    OutlinedTextField(
                        value = type,
                        onValueChange = {},
                        label = { Text("Type") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded, // replace with proper state management
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(text = {Text("Departure")}, onClick = { type = "departure"; expanded = false })

                        DropdownMenuItem(text = {Text("Arrival")}, onClick = { type = "arrival"; expanded = false })
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = iataCode,
                    onValueChange = {
                        if(it.length <= 3){
                            iataCode = it.uppercase()
                        }
                    },
                    label = { Text("IATA Code") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.AirplaneTicket, contentDescription = "IATA Code Icon")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = flightNumber,
                    onValueChange = { flightNumber = it.uppercase() },
                    label = { Text("Flight Number (Optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.ConfirmationNumber, contentDescription = "Flight Number Icon")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = airlineIata,
                    onValueChange = { airlineIata = it.uppercase() },
                    label = { Text("Airline IATA Code (Optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Flight, contentDescription = "Airline Icon")
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onFilterApplied(date, type, iataCode, flightNumber, airlineIata) },
                enabled = isFilterEnabled
            ) {
                Text("Apply Filters")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

