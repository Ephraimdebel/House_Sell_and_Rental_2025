package com.example.houserental.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.houserental.ui.theme.BlackText
import com.example.houserental.ui.theme.BrandColor
import com.example.houserental.ui.theme.BrownText

@Composable
fun DashboardCard(
    title: String,
    value: Int,
    color: Color = BrandColor,
    icon: ImageVector,
    iconSize: Dp = 28.dp
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // Colored line strip
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(color)
            )

            // Content
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {Box(
                modifier = Modifier
                    .size(iconSize + 16.dp) // Slightly larger to fit padding
                    .background(color = Color(0xFFE0E0E0), shape = CircleShape), // Gray circular background
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize), // Actual icon size
                    tint = color
                )
            }

                Spacer(Modifier.width(12.dp))
                Column {
                    Text("$value", fontWeight = FontWeight.Bold, fontSize = 18.sp,color=BlackText)
                    Text(title,color=BrownText)
                }
            }
        }
    }
}


@Composable
fun ValueCard(title: String, value: String, background: Color = Color(0xFFFEEAE6),modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(120.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = background),
    ) {
        Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,// Center vertically

        ) {
            Text(title, color = BlackText, fontSize = 15.sp)
            Spacer(Modifier.height(4.dp))
            Text(value, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = BlackText)
        }
    }
}


@Composable
fun ManagementCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .padding(4.dp)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = RoundedCornerShape(16.dp), // Rounded corners
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFF4F3F3), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = BrandColor,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, color = BrownText)
        }
    }
}


