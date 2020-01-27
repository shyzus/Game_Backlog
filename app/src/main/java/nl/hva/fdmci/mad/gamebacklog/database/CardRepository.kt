package nl.hva.fdmci.mad.gamebacklog.database

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.ArrayList

class CardRepository(context: Context) {

    private val cardDao: CardDao

    init {
        val database = CardRoomDatabase.getDatabase(context)
        cardDao = database!!.cardDao()
    }

    fun getAllCards(): LiveData<List<Card>> {
        return cardDao.getAllCards()
    }

    fun deleteAllCards() {
        cardDao.deleteAllCards()
    }

    suspend fun updateCard(card: Card) {
        cardDao.updateCard(card)
    }

    suspend fun insertCard(card: Card) {
        cardDao.insertCard(card)
    }

    suspend fun deleteCard(card: Card) {
        cardDao.deleteCard(card)
    }

}