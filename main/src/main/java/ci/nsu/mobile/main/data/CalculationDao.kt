package ci.nsu.mobile.main.data  // ← Тот же пакет!

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculationDao {
    @Query("SELECT * FROM calculations ORDER BY timestamp DESC")
    fun getAllCalculations(): Flow<List<CalculationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalculation(calculation: CalculationEntity)

    @Query("DELETE FROM calculations WHERE id = :id")
    suspend fun deleteCalculation(id: Long)

    @Query("DELETE FROM calculations")
    suspend fun deleteAllCalculations()
}