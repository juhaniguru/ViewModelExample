package com.example.viewmodelexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddGroceriesScreenRoot(
    modifier: Modifier = Modifier,
    vm: GroceriesViewModel,
    onGoBack: () -> Unit
) {
    val state by vm.addGroceriesState.collectAsStateWithLifecycle()

    AddGroceriesScreen(state = state, onGoBack = {
        vm.setIsDone(false)
        onGoBack()
    }, onUpdateName = {
        vm.updateName(it)
    }, onUpdateItemCount = {
        vm.updateItemCount(it)
    }, onCreateGroceries = {
        vm.createGroceries()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroceriesScreen(
    modifier: Modifier = Modifier,
    state: AddGroceriesState,
    onGoBack: () -> Unit,
    onUpdateName: (String) -> Unit,
    onUpdateItemCount: (String) -> Unit,
    onCreateGroceries: () -> Unit
) {


    LaunchedEffect(state.isDone) {
        if (state.isDone) {
            onGoBack()

        }

    }

    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    onGoBack()
                }) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, stringResource(R.string.go_back))
                }
            },
            title = {
                Text(stringResource(R.string.add_groceries))
            }
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value = state.name, onValueChange = {
                    onUpdateName(it)
                }, placeholder = {
                    Text(stringResource(R.string.name))
                })
                OutlinedTextField(
                    value = state.itemCount,
                    onValueChange = {
                        onUpdateItemCount(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    placeholder = {
                        Text(stringResource(R.string.count))
                    })

                Button(onClick = {
                    onCreateGroceries()
                }, enabled = state.name != "" && state.itemCount != "") {
                    Text(stringResource(R.string.add_groceries))
                }
            }
        }
    }
}