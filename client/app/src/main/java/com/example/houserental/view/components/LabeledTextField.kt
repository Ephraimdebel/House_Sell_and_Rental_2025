package com.example.houserental.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.houserental.ui.theme.BrandColor
import com.example.houserental.ui.theme.Placeholder

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isMultiLine: Boolean = false,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color(0xFFF9F9F9),
    borderColor: Color = Color(0xFFCCCCCC)
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = modifier.fillMaxWidth(), // ✅ Only this modifier
        maxLines = if (isMultiLine) 5 else 1,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedBorderColor = BrandColor,
            unfocusedBorderColor = Color.LightGray,
        )
    )
}




//OutlinedTextField(
//value = value,
//onValueChange = onValueChange,
//label = { Text(label, color = Color.Gray) },
//keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
//maxLines = if (isMultiLine) 5 else 1,
//textStyle = TextStyle(color = textColor),
//shape = RoundedCornerShape(10.dp),
//modifier = modifier
//.fillMaxWidth()
////            .background(backgroundColor, RoundedCornerShape(10.dp))
////            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
//.padding(horizontal = 8.dp)
//)