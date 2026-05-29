package ci.nsu.mobile.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Step1Screen(
    uiState: CalculatorUiState,
    amountText: String,
    monthsText: String,
    onAmountChange: (String) -> Unit,
    onMonthsChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Этап 1: Основные параметры", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = amountText,
            onValueChange = onAmountChange,
            label = { Text("Стартовый взнос (₽)") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.amountError != null,  // ← красная рамка
            supportingText = uiState.amountError?.let {
                { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        )

        OutlinedTextField(
            value = monthsText,
            onValueChange = onMonthsChange,
            label = { Text("Срок (месяцев)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            isError = uiState.monthsError != null,  // ← красная рамка
            supportingText = uiState.monthsError?.let {
                { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        )

        uiState.step1Error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onBack, Modifier.weight(1f)) {
                Text("В начало")
            }
            Button(
                onClick = onNext,
                Modifier.weight(1f),
                enabled = amountText.isNotBlank() &&
                        monthsText.isNotBlank() &&
                        uiState.amountError == null &&
                        uiState.monthsError == null
            ) {
                Text("Далее")
            }
        }
    }
}