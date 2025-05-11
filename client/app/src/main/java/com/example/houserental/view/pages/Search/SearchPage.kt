import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.houserental.data.repository.FavoriteRepository
import com.example.houserental.data.repository.HomeRepository
import com.example.houserental.network.RetrofitInstance
import com.example.houserental.preferences.UserPreferences
import com.example.houserental.ui.theme.Background
import com.example.houserental.view.components.CustomSearchBar
import com.example.houserental.viewModel.FavoriteViewModel
import com.example.houserental.viewModel.FavoriteViewModelFactory
import com.example.houserental.viewModel.HomeViewModel
import com.example.houserental.viewModel.HomeViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    val repository = remember { HomeRepository(RetrofitInstance.api) }
    val factory = remember { HomeViewModelFactory(repository) }
    val viewModel: HomeViewModel = viewModel(factory = factory)

    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val favoriteRepository = remember { FavoriteRepository(RetrofitInstance.api) }
    val favoriteViewModelFactory = remember { FavoriteViewModelFactory(favoriteRepository) }

    val favoriteViewModel: FavoriteViewModel = viewModel(
        factory = favoriteViewModelFactory,
        viewModelStoreOwner = viewModelStoreOwner!!
    )

    val userDetails = UserPreferences.getUserDetails(context).collectAsState(
        initial = LoginResponse("", "", "", -1, "", "")
    )
    val userId = userDetails.value.userid

    var query by remember { mutableStateOf("") }

    val filteredListings = viewModel.listings.filter {
        it.city.contains(query, ignoreCase = true) || it.title.contains(query, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.getHomes()
    }

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text("Search", fontSize = 20.sp, color = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // "Find properties" title
            Text(
                text = "Find properties",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Custom search input
            CustomSearchBar(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )


            if (filteredListings.isEmpty()) {
                Text("No results found", color = Color.Gray, modifier = Modifier.padding(top = 16.dp))
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    filteredListings.forEach { house ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp) // add horizontal space
                        ) {
                            PropertyCard(
                                house = house,
                                isHorizontal = false,
                                userId = userId,
                                viewModel = favoriteViewModel
                            ) {
                                navController.navigate("property_detail/${house.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}


