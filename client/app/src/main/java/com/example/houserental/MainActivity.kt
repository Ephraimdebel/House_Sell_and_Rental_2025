package com.example.houserental

import SignUpViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
<<<<<<< HEAD
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.houserental.data.repository.AuthRepository
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.ui.theme.HouseRentalTheme
import com.example.houserental.viewModel.HomeViewModel
import com.example.houserental.viewModel.LoginViewModel
import com.example.houserental.viewModel.SignUpViewModelFactory
import com.example.onlytry.SignUpScreen
import androidx.lifecycle.viewmodel.compose.viewModel
=======
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.houserental.ui.theme.HouseRentalTheme
import com.example.houserental.view.AdminDashboardScreen
import com.example.houserental.view.ManageUsersScreen
import com.example.houserental.view.ProfileScreen
import com.example.houserental.view.pages.manage_home.ManageHomeScreen
// if you've created this
import com.example.houserental.navigation.AppNavigation
import androidx.compose.ui.platform.LocalContext
import com.example.houserental.navigation.AdminNavGraph
import com.example.houserental.ui.theme.brand
>>>>>>> 34ef135a72682c8397f4d6d5013d42bb93349782

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

<<<<<<< HEAD
        val apiService = RetrofitInstance.api
        val authRepository = AuthRepository(apiService)
        val signUpViewModelFactory = SignUpViewModelFactory(authRepository)

        setContent {
            HouseRentalTheme {
                val signUpViewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = signUpViewModelFactory
                )

                SignUpScreen(
                    onBackClick = { /* handle back nav */ },
                    onLoginClick = { /* handle login nav */ },
                    viewModel = signUpViewModel
                )
=======
        setContent {
            HouseRentalTheme {
                AppNavigation()


>>>>>>> 34ef135a72682c8397f4d6d5013d42bb93349782
            }
        }
    }
}
<<<<<<< HEAD

=======
//                val navController = rememberNavController()
//                AdminNavGraph(navController = navController)
>>>>>>> 34ef135a72682c8397f4d6d5013d42bb93349782

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
