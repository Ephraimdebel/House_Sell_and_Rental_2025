package com.example.houserental.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BottomNavBar(navController: NavController? = null) {
    val navItems = listOf(
        "home" to Icons.Outlined.Home,
        "search" to Icons.Outlined.Search,
        "favorite" to Icons.Outlined.Favorite,
        "profile" to Icons.Outlined.Person
    )

    Box(modifier = Modifier.height(70.dp)) {
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier.height(66.dp)
        ) {
            navItems.forEachIndexed { index, (screen, icon) ->
                if (index == 2) {
                    Spacer(Modifier.weight(1f))
                }

                NavigationBarItem(
                    selected = navController?.currentDestination?.route == screen,
                    onClick = { navController?.navigate(screen) },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = screen,
                            modifier = Modifier.size(38.dp)
                        )
                    },
                    label = {
                        Text(screen.replaceFirstChar { it.uppercase() })
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = { /* handle + click */ },
            containerColor = Color(0xFF5D9DF0),
            contentColor = Color.White,

            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(60.dp)
                .offset(y = (-28).dp)
        ) {
            Icon(Icons.Default.Add,
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
