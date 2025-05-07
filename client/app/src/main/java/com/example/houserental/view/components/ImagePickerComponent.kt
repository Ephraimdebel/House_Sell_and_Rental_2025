package com.example.houserental.view.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun ImagePickerComponent(
    imageUris: MutableList<Uri>,
    onImagesSelected: (List<Uri>) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            imageUris.clear()
            imageUris.addAll(uris)
            onImagesSelected(uris)
        }
    )

    Button(onClick = { launcher.launch("image/*") }) {
        Icon(Icons.Default.AccountBox, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text("Select Images")
    }

    if (imageUris.isNotEmpty()) {
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            imageUris.forEach { uri ->
                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
