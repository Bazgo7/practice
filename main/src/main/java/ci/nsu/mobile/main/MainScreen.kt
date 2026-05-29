package ci.nsu.mobile.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onCalculate: () -> Unit,
    onHistory: () -> Unit,
    onCloseApp: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Расчёт вкладов", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onCalculate,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Рассчитать")
        }

        Button(
            onClick = onHistory,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("История расчётов")
        }

        Button(
            onClick = onCloseApp,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Закрыть приложение")
        }
    }
}