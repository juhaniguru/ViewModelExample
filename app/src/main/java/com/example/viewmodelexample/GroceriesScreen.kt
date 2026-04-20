package com.example.viewmodelexample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GroceriesScreenRoot(
    modifier: Modifier = Modifier,
    vm: GroceriesViewModel,
    onNavigate: () -> Unit,
    onNavigateToDetails: (String) -> Unit
) {


    val state by vm.state.collectAsStateWithLifecycle()

    GroceriesScreen(
        state = state,
        onNavigate = onNavigate,
        onNavigateToDetails = onNavigateToDetails
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceriesScreen(
    modifier: Modifier = Modifier,
    state: GroceriesState,
    onNavigate: () -> Unit,
    onNavigateToDetails: (String) -> Unit

) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigate()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Groceries")
            }
        },
        topBar = {
            TopAppBar(title = {
                Text("Groceries")
            })
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.loading -> CircularProgressIndicator()
                state.error != null -> Text(state.error)
                else -> GroceriesList(
                    groceries = state.groceries,
                    onNavigateToDetails = onNavigateToDetails
                )
            }
        }

    }
}

@Composable
fun GroceriesList(
    modifier: Modifier = Modifier,
    groceries: List<GroceryItem>,
    onNavigateToDetails: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = groceries) {
            ElevatedCard(
                modifier = Modifier
                    .clickable {
                        onNavigateToDetails(it.name)
                    }
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = it.name.take(1).uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))


                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "KPL: ${it.itemCount}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )


                    }


                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete User",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}