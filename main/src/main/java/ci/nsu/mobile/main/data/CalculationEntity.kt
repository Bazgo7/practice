package ci.nsu.mobile.main.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculations")
data class CalculationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val initialAmount: Double,
    val periodMonths: Int,
    val interestRate: Double,
    val monthlyTopUp: Double,
    val finalAmount: Double,
    val interestEarned: Double,
    val timestamp: Long = System.currentTimeMillis()
)