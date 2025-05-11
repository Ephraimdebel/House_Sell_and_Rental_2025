package com.example.houserental.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person3
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.houserental.ui.theme.Background
import com.example.houserental.ui.theme.BlackText

@Composable
fun BottomNavBar(navController: NavController? = null) {
    val navItems = listOf(
        "home" to Icons.Outlined.Home,
        "search" to Icons.Outlined.Search,
        "favorite" to Icons.Default.FavoriteBorder,
        "profile" to Icons.Outlined.Person3
    )

    Box(modifier = Modifier.background(Background).height(90.dp)) {
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier.height(86.dp)
        ) {
            navItems.forEachIndexed { index, (screen, icon) ->
                if (index == 2) {
                    Spacer(Modifier.weight(1f))
                }

                val isSelected = navController?.currentDestination?.route == screen

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { navController?.navigate(screen) },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = screen,
                            tint = if (isSelected) Color(0xFF5D9DF0) else BlackText,
                            modifier = Modifier.size(42.dp)
                        )
                    },
                    label = {
                        Text(
                            screen.replaceFirstChar { it.uppercase() },
                            color = BlackText,
                            fontSize = 14.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.White // Force selected background to remain white
                    )
                )
            }
        }

        FloatingActionButton(
            onClick = { /* handle + click */ },
            containerColor = Color(0xFF5D9DF0),
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(64.dp)
                .offset(y = (-28).dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    BottomNavBar()
}
