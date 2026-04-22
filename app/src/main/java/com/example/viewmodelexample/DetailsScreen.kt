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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.TextComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import java.time.LocalDate

@Composable
fun DetailsScreenRoot(modifier: Modifier = Modifier) {
    val vm = viewModel<DetailsScreenViewModel>(factory = DetailsScreenViewModel.createFactory())
    val state by vm.detailsScreenState.collectAsStateWithLifecycle()
    val modelProducer = vm.modelProducer
    val currentDate by vm.currentDateState.collectAsStateWithLifecycle()

    DetailsScreen(state = state, modelProducer = modelProducer, currentDate = currentDate)
}

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    state: DetailsScreenState,
    modelProducer: CartesianChartModelProducer,
    currentDate: LocalDate
) {
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
                else -> DetailsGraph(modelProducer = modelProducer, currentDate = currentDate)
            }
        }
    }

}

@Composable
fun DetailsGraph(
    modifier: Modifier = Modifier,
    modelProducer: CartesianChartModelProducer,
    currentDate: LocalDate
) {
    CartesianChartHost(
        chart = rememberCartesianChart(

            // koska käytämme columnSeriesia viewmodelissa
            // luomme sille layerin
            rememberColumnCartesianLayer(),

            // startAxis on chartin vasen y-akseli
            startAxis = VerticalAxis.rememberStart(
                title = {
                    "€"
                },
                titleComponent = rememberTextComponent(style = TextStyle(color = Color.Black))
            ),
            // x-akseli
            bottomAxis = HorizontalAxis.rememberBottom(
                title = {
                    "${currentDate.month}"
                },
                titleComponent = rememberTextComponent(style = TextStyle(color = Color.Black)),
                        // valueFormatterilla voi muokata x-akselilla näkyviä tekstejä
                        valueFormatter = CartesianValueFormatter { _, value, _ ->
                    val index = value.toInt()
                    "${index + 1}"
                }
            ),
        ),
        modelProducer = modelProducer,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}