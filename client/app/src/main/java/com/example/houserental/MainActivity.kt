package com.example.houserental

import SignUpViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.houserental.ui.theme.HouseRentalTheme

import com.example.houserental.navigation.AppNavigation
import androidx.compose.ui.platform.LocalContext
import com.example.houserental.navigation.AdminNavGraph
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HouseRentalTheme {
                AppNavigation()


            }
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyBottomAppBar(){
//    val navigationController = rememberNavController()
//    val context = LocalContext.current.applicationContext
//    val selected = remember{
//        mutableStateOf(Icons.Default.Home)
//    }
//
//    val sheetState = rememberModalBottomSheetState()
//    var showBottomSheet by remember {
//        mutableStateOf(false)
//    }
//    Scaffold(
//        bottomBar = {
//            BottomAppBar(
//                containerColor = brand
//            ) {
//                IconButton(
//                    onClick = {
//                        selected.value = Icons.Default.Home
//                        navigationController.navigate(Screens.Home.screen){
//                            popUpTo(0)
//                        }
//                    },
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Icon(Icons.Default.Home , contentDescription = null , modifier = Modifier.size(26.dp) , tint = if(selected.value == Icons.Default.Home) Color.White else Color.DarkGray)
//                }
//                IconButton(
//                    onClick = {
//                        selected.value = Icons.Default.Search
//                        navigationController.navigate(Screens.Search.screen){
//                            popUpTo(0)
//                        }
//                    },
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Icon(Icons.Default.Search , contentDescription = null , modifier = Modifier.size(26.dp) , tint = if(selected.value == Icons.Default.Search) Color.White else Color.DarkGray)
//                }
//
//                Box(modifier = Modifier.weight(1f)
//                    .padding(16.dp),
//                    contentAlignment = Alignment.Center){
//                    FloatingActionButton(onClick = { showBottomSheet = true}) {
//                        Icon(Icons.Default.Add, contentDescription = null, tint = GreenJc )
//                    }
//                }
//
//                IconButton(
//                    onClick = {
//                        selected.value = Icons.Default.Favorite
//                        navigationController.navigate(Screens.Favourite.screen){
//                            popUpTo(0)
//                        }
//                    },
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Icon(Icons.Default.Favorite, contentDescription = null , modifier = Modifier.size(26.dp) , tint = if(selected.value == Icons.Default.Favorite) Color.White else Color.DarkGray)
//                }
//
//                IconButton(
//                    onClick = {
//                        selected.value = Icons.Default.Person
//                        navigationController.navigate(Screens.Profile.screen){
//                            popUpTo(0)
//                        }
//                    },
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Icon(Icons.Default.Person , contentDescription = null , modifier = Modifier.size(26.dp) , tint = if(selected.value == Icons.Default.Person) Color.White else Color.DarkGray)
//                }
//            }
//        }
//    ) {paddingValues ->
//        NavHost(navController = navigationController,
//            startDestination = Screens.Home.screen,
//            modifier = Modifier.padding(paddingValues)){
//            composable(Screens.Home.screen){ Home()}
//            composable(Screens.Search.screen){ Search()}
//            composable(Screens.Favourite.screen){ Favourite()}
//            composable(Screens.Profile.screen){ Profile()}
//            composable(Screens.Post.screen){ Post()}
//        }
//
//    }
//
//    if(showBottomSheet){
//        ModalBottomSheet(onDismissRequest = {showBottomSheet = false} ,
//            sheetState = sheetState) {
//            Column(modifier = Modifier
//                .fillMaxWidth()
//                .padding(18.dp),
//                verticalArrangement = Arrangement.spacedBy(20.dp)) {
//                BottomSheetItem(icon = Icons.Default.ThumbUp, title = "Createj a post ") {
//                    showBottomSheet = false
//                    navigationController.navigate(Screens.Post.screen){
//                        popUpTo(0)
//                    }
//                }
//                BottomSheetItem(icon = Icons.Default.Star, title = "Add a astory") {
//                    Toast.makeText(context,"story",Toast.LENGTH_SHORT).show()
//                }
//                BottomSheetItem(icon = Icons.Default.PlayArrow, title = "create a reel") {
//                    Toast.makeText(context,"reel",Toast.LENGTH_SHORT).show()
//                }
//                BottomSheetItem(icon = Icons.Default.Favorite, title = "Go live") {
//                    Toast.makeText(context,"live",Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomSheetItem(icon:ImageVector,title: String,onClick:() -> Unit){
//    Row(verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
//        modifier = Modifier.clickable { onClick()}) {
//        Icon(icon, contentDescription = null, tint = GreenJc)
//        Text(text = title,color = GreenJc, fontSize = 22.sp)
//    }
//}
