package ci.nsu.mobile.main

data class CalculatorUiState(
    val initialAmount: Double = 0.0,
    val periodMonths: Int = 0,
    val interestRate: Double = 0.0,
    val monthlyTopUp: Double = 0.0,
    val finalAmount: Double = 0.0,
    val interestEarned: Double = 0.0,
    val step1Error: String? = null,
    val amountError: String? = null,
    val monthsError: String? = null,
    val topUpError: String? = null
)