package ci.nsu.mobile.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Step2Screen(
    uiState: CalculatorUiState,
    onTopUpChange: (String) -> Unit,
    onCalculate: () -> Unit,
    onBack: () -> Unit
) {
    var topUpText by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Этап 2: Доп. параметры", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Срок: ${uiState.periodMonths} мес.")
                Text("Ставка: ${uiState.interestRate}%")
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = topUpText,
            onValueChange = {
                topUpText = it
                onTopUpChange(it)
            },
            label = { Text("Ежемес. пополнение (₽, необязательно)") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.topUpError != null,
            supportingText = uiState.topUpError?.let { error ->
                { Text(error, color = MaterialTheme.colorScheme.error) }
            }
        )

        Spacer(Modifier.height(24.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onBack, Modifier.weight(1f)) {
                Text("Назад")
            }
            Button(
                onClick = onCalculate,
                Modifier.weight(1f),
                enabled = uiState.topUpError == null
            ) {
                Text("Рассчитать")
            }
        }
    }
}