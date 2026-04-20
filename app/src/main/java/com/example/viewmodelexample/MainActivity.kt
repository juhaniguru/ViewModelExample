package com.example.viewmodelexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
//import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.viewmodelexample.ui.theme.ViewModelExampleTheme

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
    factory: ViewModelProvider.Factory? = null
): T {
    val navGraphRoute = this.destination.parent?.route ?: return viewModel(factory = factory)
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return viewModel(factory = factory, viewModelStoreOwner = parentEntry)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelExampleTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "groceries_feature") {


                    navigation(startDestination = "groceries", route = "groceries_feature") {

                        composable(route = "groceries") {

                            val vm = it.sharedViewModel<GroceriesViewModel>(
                                factory = GroceriesViewModel.createFactory(),
                                navController = navController
                            )

                            GroceriesScreenRoot(vm = vm, onNavigate = {
                                navController.navigate("addGroceries")
                            }, onNavigateToDetails = {item ->
                                navController.navigate("groceryDetails/$item")
                            })
                        }
                        composable(route = "addGroceries") {


                            val vm = it.sharedViewModel<GroceriesViewModel>(
                                factory = GroceriesViewModel.createFactory(),
                                navController = navController
                            )


                            AddGroceriesScreenRoot(vm = vm, onGoBack = {
                                navController.navigateUp()
                            })
                        }
                    }

                    composable(route = "groceryDetails/{groceryId}") {

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ViewModelExampleTheme {
        Greeting("Android")
    }
}