package ci.nsu.mobile.main.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class CalculationRepository private constructor(
    private val dao: CalculationDao
) {
    val allCalculations: Flow<List<CalculationEntity>> = dao.getAllCalculations()

    suspend fun insert(calculation: CalculationEntity) {
        dao.insertCalculation(calculation)
    }

    suspend fun delete(id: Long) {
        dao.deleteCalculation(id)
    }

    suspend fun deleteAll() {
        dao.deleteAllCalculations()
    }

    companion object {
        @Volatile
        private var INSTANCE: CalculationRepository? = null

        fun getInstance(context: Context): CalculationRepository {
            return INSTANCE ?: synchronized(this) {
                val database = AppDatabase.getDatabase(context)
                val repository = CalculationRepository(database.calculationDao())
                INSTANCE = repository
                repository
            }
        }
    }
}