package com.example.houserental.view.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.houserental.ui.theme.BlackText

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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .background(
                color = Color.White,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Text(text = "Add Images",color=BlackText)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(68.dp)) {
                // Gallery button
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.LightGray, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconCircleButton(icon = Icons.Default.Image) {
                            launcher.launch("image/*")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Gallery", color = BlackText)
                }

                // Sample/upload button
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.LightGray, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconCircleButton(icon = Icons.Default.AccountBox) {
                            launcher.launch("image/*")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Upload",color = BlackText)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Tap an option to add an image. You can add multiple images.", color = BlackText)

        if (imageUris.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
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
}

@Composable
fun IconCircleButton(icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .background(color = Color.White, shape = CircleShape)
            .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Color(0xFF3D8FF1)
        )
    }
}
