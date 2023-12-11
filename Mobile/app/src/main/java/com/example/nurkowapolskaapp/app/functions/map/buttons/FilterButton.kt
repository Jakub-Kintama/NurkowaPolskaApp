package com.example.nurkowapolskaapp.app.functions.map.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nurkowapolskaapp.app.functions.map.MarkerMockType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterButton(
    showMarker: MutableState<MarkerMockType>,
    filterType: List<String>,
    checkedFilter: MutableState<String>,
    selectedCrayfish: MutableState<Boolean>,
    selectedDangPoll: MutableState<Boolean>,
) {
    /*  TODO: Change so you cant deselect all filters */
    val openFilterBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    FloatingActionButton(
        onClick = { openFilterBottomSheet.value = true },
        modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 15.dp, end = 15.dp),
    ) {
        Icon(Icons.Filled.Search, "Filtr znaczników: raki")
    }
    when {
        openFilterBottomSheet.value -> {
            ModalBottomSheet(
                onDismissRequest = { openFilterBottomSheet.value = false },
                sheetState = sheetState
            ) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    FilterChip(
                        modifier = Modifier.padding(15.dp),
                        selected = selectedCrayfish.value,
                        onClick = {
                            selectedCrayfish.value = !selectedCrayfish.value },
                        label = { Text("Raki") },
                        leadingIcon = if (selectedCrayfish.value) {
                            {
                                checkedFilter.value = filterType[0]
                                showMarker.value = MarkerMockType.CRAYFISH
                                Icon(
                                    Icons.Filled.Done,
                                    null,
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                    FilterChip(
                        modifier = Modifier.padding(15.dp),
                        selected = selectedDangPoll.value,
                        onClick = { selectedDangPoll.value = !selectedDangPoll.value },
                        label = { Text("Zagrożenia/Zanieczyszczenia") },
                        leadingIcon = if (selectedDangPoll.value) {
                            {
                                checkedFilter.value = filterType[1]
                                showMarker.value = MarkerMockType.POLLUTION
                                Icon(
                                    Icons.Filled.Done,
                                    null,
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                    if (selectedCrayfish.value && selectedDangPoll.value) {
                        checkedFilter.value = filterType[2]
                    }
                }
            }
        }
    }
}