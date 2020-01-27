package nl.hva.fdmci.mad.gamebacklog.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDao {

    @Insert
    suspend fun insertCard(card: Card)

    @Query("SELECT * FROM Card WHERE id = :id LIMIT 1")
    fun getCardById(id: Int): LiveData<Card?>

    @Query("SELECT * FROM Card")
    fun getAllCards(): LiveData<List<Card>>

    @Query("DELETE FROM Card")
    fun deleteAllCards()

    @Delete
    fun deleteCard(card: Card)

    @Update
    suspend fun updateCard(card: Card)
}