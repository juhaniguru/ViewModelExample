package com.example.viewmodelexample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailsScreenRoot(modifier: Modifier = Modifier) {
    val vm = viewModel<DetailsScreenViewModel>(factory = DetailsScreenViewModel.createFactory())
    val state by vm.detailsScreenState.collectAsStateWithLifecycle()

    DetailsScreen(state = state)
}

@Composable
fun DetailsScreen(modifier: Modifier = Modifier, state: DetailsScreenState) {
    Scaffold() { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.loading -> CircularProgressIndicator()
                state.err != null -> Text(state.err)
                else -> DetailsGraph()
            }
        }
    }

}

@Composable
fun DetailsGraph(modifier: Modifier = Modifier) {
    Text("Onko tama oikea paikka?")
}