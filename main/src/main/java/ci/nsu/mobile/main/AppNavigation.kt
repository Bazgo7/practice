package ci.nsu.mobile.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: CalculatorViewModel = viewModel(),
    onCloseApp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                onCalculate = { navController.navigate("step1") },
                onHistory = { navController.navigate("history") },
                onCloseApp = onCloseApp
            )
        }

        composable("step1") {
            var amountText by remember { mutableStateOf("") }
            var monthsText by remember { mutableStateOf("") }

            Step1Screen(
                uiState = uiState,
                amountText = amountText,
                monthsText = monthsText,
                onAmountChange = {
                    amountText = it
                    viewModel.updateStep1(it, monthsText)
                },
                onMonthsChange = {
                    monthsText = it
                    viewModel.updateStep1(amountText, it)
                },
                onNext = {
                    val amountHasLetters = amountText.any { !it.isDigit() } && amountText.isNotBlank()
                    val monthsHasLetters = monthsText.any { !it.isDigit() } && monthsText.isNotBlank()


                    val amountVal = amountText.toDoubleOrNull()
                    val monthsVal = monthsText.toIntOrNull()


                    if (amountHasLetters || monthsHasLetters ||
                        amountVal == null || monthsVal == null ||
                        amountVal <= 0 || monthsVal <= 0) {
                        return@Step1Screen
                    }

                    navController.navigate("step2")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("step2") {
            var topUpText by remember { mutableStateOf("") }

            Step2Screen(
                uiState = uiState,
                onTopUpChange = { topUpText ->
                    val hasLetters = topUpText.any { !it.isDigit() } && topUpText.isNotBlank()

                    if (hasLetters) {
                        viewModel.updateTopUp(topUpText)
                        return@Step2Screen
                    }

                    viewModel.updateTopUp(topUpText)
                },
                onCalculate = {
                    if (uiState.topUpError != null) return@Step2Screen

                    viewModel.calculate()
                    navController.navigate("result")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("result") {
            ResultScreen(
                uiState = uiState,
                onSave = {
                    viewModel.saveCalculation()
                    navController.popBackStack("main", inclusive = false)
                },
                onHome = {
                    viewModel.reset()
                    navController.popBackStack("main", inclusive = false)
                }
            )
        }

        composable("history") {
            HistoryScreen(
                calculations = viewModel.calculations.collectAsState(initial = emptyList()),
                onDelete = { viewModel.deleteCalculation(it) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}