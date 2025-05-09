package com.example.houserental.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.houserental.ui.theme.BrandColor


@Composable
fun SelectableChips(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { option ->
            FilterChip(
                selected = selected == option,
                onClick = { onSelected(option) },
                enabled = true, // or false, depending on your case
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = BrandColor,
                    containerColor = Color.White,
                    labelColor = Color.Black,
                    selectedLabelColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.LightGray,
                    selectedBorderColor = Color.LightGray,
                    enabled = true,
                    selected = selected == option
                ),
                label = { Text(option) }
            )


        }
    }
}
