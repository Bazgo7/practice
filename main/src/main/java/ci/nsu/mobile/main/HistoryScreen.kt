package ci.nsu.mobile.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ci.nsu.mobile.main.data.CalculationEntity
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HistoryScreen(
    calculations: State<List<CalculationEntity>>,
    onDelete: (Long) -> Unit,
    onBack: () -> Unit
) {
    val fmt = NumberFormat.getCurrencyInstance(Locale("ru", "RU"))  // ← Исправлено!

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("История расчётов", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        if (calculations.value.isEmpty()) {
            Text("История пуста", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(calculations.value) { calc ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Дата: ${formatDate(calc.timestamp)}")
                            Text("Взнос: ${fmt.format(calc.initialAmount)}")
                            Text("Срок: ${calc.periodMonths} мес.")
                            Text("Ставка: ${calc.interestRate}%")
                            Text("Пополнение: ${fmt.format(calc.monthlyTopUp)}")
                            Text("Итого: ${fmt.format(calc.finalAmount)}",
                                style = MaterialTheme.typography.titleMedium)
                            Text("Проценты: ${fmt.format(calc.interestEarned)}",
                                color = MaterialTheme.colorScheme.primary)

                            Spacer(Modifier.height(8.dp))

                            Button(
                                onClick = { onDelete(calc.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                ),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Удалить")
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Button(onClick = onBack, Modifier.fillMaxWidth()) {
            Text("Назад")
        }
    }
}

fun formatDate(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val dateTime = instant.atZone(ZoneId.systemDefault())
    return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(dateTime)
}