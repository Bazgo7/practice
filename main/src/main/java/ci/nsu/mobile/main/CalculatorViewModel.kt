package ci.nsu.mobile.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ci.nsu.mobile.main.data.CalculationEntity
import ci.nsu.mobile.main.data.CalculationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: CalculationRepository =
        CalculationRepository.getInstance(application)


    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()


    val calculations: Flow<List<CalculationEntity>> = repository.allCalculations


    fun updateStep1(amount: String, months: String) {
        val amountVal = amount.toDoubleOrNull()
        val monthsVal = months.toIntOrNull()

        val amountHasLetters = amount.any { !it.isDigit() } && amount.isNotBlank()
        val monthsHasLetters = months.any { !it.isDigit() } && months.isNotBlank()

        if (amountHasLetters || monthsHasLetters) {
            _uiState.update {
                it.copy(
                    amountError = if (amountHasLetters) "Только цифры!" else null,
                    monthsError = if (monthsHasLetters) "Только цифры!" else null,
                    step1Error = "Введите корректные числа"
                )
            }
            return
        }

        if (amount.isBlank() || months.isBlank()) {
            _uiState.update {
                it.copy(
                    initialAmount = amountVal ?: 0.0,
                    periodMonths = monthsVal ?: 0,
                    amountError = null,
                    monthsError = null,
                    step1Error = null
                )
            }
            return
        }

        if (amountVal == null || monthsVal == null || amountVal <= 0 || monthsVal <= 0) {
            _uiState.update {
                it.copy(
                    step1Error = "Введите числа больше 0",
                    amountError = null,
                    monthsError = null
                )
            }
            return
        }

        val rate = when {
            monthsVal < 6 -> 15.0
            monthsVal < 12 -> 10.0
            else -> 5.0
        }

        _uiState.update {
            it.copy(
                initialAmount = amountVal,
                periodMonths = monthsVal,
                interestRate = rate,
                amountError = null,
                monthsError = null,
                step1Error = null
            )
        }
    }


    fun updateTopUp(topUp: String) {
        val topUpVal = topUp.toDoubleOrNull()
        val hasLetters = topUp.any { !it.isDigit() } && topUp.isNotBlank()

        if (hasLetters) {
            _uiState.update {
                it.copy(
                    monthlyTopUp = 0.0,
                    topUpError = "Только цифры!"
                )
            }
            return
        }

        if (topUp.isBlank()) {
            _uiState.update {
                it.copy(
                    monthlyTopUp = 0.0,
                    topUpError = null
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                monthlyTopUp = topUpVal ?: 0.0,
                topUpError = null
            )
        }
    }


    fun calculate() {
        val state = _uiState.value
        val monthlyRate = state.interestRate / 100.0 / 12.0
        var current = state.initialAmount
        var totalInterest = 0.0

        repeat(state.periodMonths) {
            val interest = current * monthlyRate
            current += interest + state.monthlyTopUp
            totalInterest += interest
        }

        _uiState.update {
            it.copy(
                finalAmount = current,
                interestEarned = totalInterest
            )
        }
    }


    fun saveCalculation() {
        val state = _uiState.value
        viewModelScope.launch {
            val entity = CalculationEntity(
                initialAmount = state.initialAmount,
                periodMonths = state.periodMonths,
                interestRate = state.interestRate,
                monthlyTopUp = state.monthlyTopUp,
                finalAmount = state.finalAmount,
                interestEarned = state.interestEarned
            )
            repository.insert(entity)
        }
    }


    fun deleteCalculation(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }


    fun reset() {
        _uiState.value = CalculatorUiState()
    }
}