package ci.nsu.mobile.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.NumberFormat

@Composable
fun ResultScreen(
    uiState: CalculatorUiState,
    onSave: () -> Unit,
    onHome: () -> Unit
) {
    val fmt = NumberFormat.getCurrencyInstance(java.util.Locale("ru", "RU"))

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Результат", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(24.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Взнос: ${fmt.format(uiState.initialAmount)}")
                Text("Срок: ${uiState.periodMonths} мес.")
                Text("Ставка: ${uiState.interestRate}%")
                Text("Пополнение: ${fmt.format(uiState.monthlyTopUp)}")
                Divider(Modifier.padding(vertical = 8.dp))
                Text("Итого: ${fmt.format(uiState.finalAmount)}",
                    style = MaterialTheme.typography.titleMedium)
                Text("Проценты: ${fmt.format(uiState.interestEarned)}",
                    color = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onSave, Modifier.weight(1f)) {
                Text("Сохранить")
            }
            Button(onClick = onHome, Modifier.weight(1f)) {
                Text("В начало")
            }
        }
    }
}