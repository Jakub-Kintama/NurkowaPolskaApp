package com.example.nurkowapolskaapp.map.buttons

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nurkowapolskaapp.functions.convertMillisToLocalDate
import com.example.nurkowapolskaapp.functions.millisToDateString
import com.example.nurkowapolskaapp.map.model.DateRange
import com.example.nurkowapolskaapp.map.model.MarkerFilterOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterButton(
    filterOptions: MutableState<MarkerFilterOptions>
) {
    val openDialogWindow = remember { mutableStateOf(false) }
    val openModalBottomSheet = remember { mutableStateOf(false) }
    val sheetStateModalBottom = rememberModalBottomSheetState()

    val dateBegin = DateRange().dateBegin
    val dateEnd = DateRange().dateEnd
    val chosenDates = remember { mutableStateOf<Pair<String, String?>>("$dateBegin" to "$dateEnd") }

    val state = rememberDateRangePickerState(
        initialDisplayMode = DisplayMode.Picker,
    )

    FloatingActionButton(
        onClick = { openDialogWindow.value = true },
        modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 15.dp, end = 15.dp),
    ) {
        Icon(Icons.Filled.Search, "Filtr znaczników: raki")
    }

    when {
        openDialogWindow.value -> {
            if(!openModalBottomSheet.value) {
                Dialog(
                    onDismissRequest = { openDialogWindow.value = false },
                ) {
                    Box(modifier = Modifier
                        .clip(shape = RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.background)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(modifier = Modifier.padding(26.dp, 0.dp)) {
                                Text(text = "Filtruj typy znaczników:", fontSize = 14.sp)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(26.dp, 0.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                FilterChip(
                                    onClick = { filterOptions.value = filterOptions.value.copy(showCrayfish = !filterOptions.value.showCrayfish) },
                                    label = {
                                        Text("Raki")
                                    },
                                    selected = filterOptions.value.showCrayfish,
                                    leadingIcon = {
                                        if(filterOptions.value.showCrayfish) {
                                            Icon(
                                                imageVector = Icons.Filled.Done,
                                                contentDescription = "Done icon",
                                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                                            )
                                        }
                                    },
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                FilterChip(
                                    onClick = { filterOptions.value = filterOptions.value.copy(showOther = !filterOptions.value.showOther) },
                                    label = {
                                        Text("Inne")
                                    },
                                    selected = filterOptions.value.showOther,
                                    leadingIcon = {
                                        if(filterOptions.value.showOther) {
                                            Icon(
                                                imageVector = Icons.Filled.Done,
                                                contentDescription = "Done icon",
                                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                                            )
                                        }
                                    },
                                )
                            }

                            Row(modifier = Modifier.padding(26.dp, 0.dp)) {
                                Text(text = "Pokaż niezweryfikowane znaczniki:", fontSize = 14.sp)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(26.dp, 0.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                FilterChip(
                                    onClick = { filterOptions.value = filterOptions.value.copy(showUnverified = !filterOptions.value.showUnverified) },
                                    label = {
                                        Text("Pokaż niezweryfikowane znaczniki")
                                    },
                                    selected = filterOptions.value.showUnverified,
                                    leadingIcon = {
                                        if(filterOptions.value.showUnverified) {
                                            Icon(
                                                imageVector = Icons.Filled.Done,
                                                contentDescription = "Done icon",
                                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                                            )
                                        }
                                    },
                                )
                            }
                            Row(modifier = Modifier.padding(26.dp, 0.dp)) {
                                Text(text = "Pokaż znaczniki z wybranych dat:", fontSize = 14.sp)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(onClick = { openModalBottomSheet.value = true }) {
                                    Text("Od: ${chosenDates.value.first} do: ${chosenDates.value.second}")
                                }
                                if(filterOptions.value.dateRange != DateRange()) {
                                    IconButton(onClick = {
                                        chosenDates.value = Pair("$dateBegin", "$dateEnd")
                                        filterOptions.value = filterOptions.value.copy(dateRange = DateRange())
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            when {
                openModalBottomSheet.value -> {
                    ModalBottomSheet(
                        onDismissRequest = { openModalBottomSheet.value = false },
                        sheetState = sheetStateModalBottom,
                    ) {
                        DateRangePicker(
                            state = state,
                            title = { Text(text = "     Wybierz daty") },
                            dateFormatter = DatePickerDefaults.dateFormatter("dd MM yyyy", "dd MM yyyy", "dd MM yyyy"),
                            headline = {
                                val startDateString = state.selectedStartDateMillis?.let {
                                    millisToDateString(it)
                                }
                                val endDateString = state.selectedEndDateMillis?.let {
                                    millisToDateString(it)
                                }
                                val startDateLong = state.selectedStartDateMillis
                                val endDateLong = state.selectedEndDateMillis
                                // Filter dates values
                                startDateLong?.let {
                                    endDateLong?.let {
                                        filterOptions.value = filterOptions.value.copy(dateRange = DateRange(
                                            dateBegin = convertMillisToLocalDate(startDateLong),
                                            dateEnd = convertMillisToLocalDate(endDateLong)
                                        ))
                                    }
                                }

                                // Visual dates for button
                                chosenDates.value = Pair(
                                    startDateString ?: "$dateBegin",
                                    endDateString ?: "$dateEnd"
                                )

                                Log.d("DATA_START", "${filterOptions.value.dateRange.dateBegin}")
                                Log.d("DATA_END", "${filterOptions.value.dateRange.dateEnd}")
                                Row {
                                    Spacer(modifier = Modifier.width(26.dp))
                                    Text("Od: ${startDateString ?: "dd/mm/yyyy"} do ${endDateString ?: "dd/mm/yyyy"}")
                                }
                            },
                            showModeToggle = false,
                        )
                    }
                }
            }
        }
    }
}