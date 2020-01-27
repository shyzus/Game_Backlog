package nl.hva.fdmci.mad.gamebacklog.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.hva.fdmci.mad.gamebacklog.database.Card
import nl.hva.fdmci.mad.gamebacklog.database.CardRepository

class CreateCardViewModel(application: Application) : AndroidViewModel(application) {

    private val cardRepository = CardRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val card = MutableLiveData<Card?>()
    val error = MutableLiveData<String?>()
    val success = MutableLiveData<Boolean?>()

    fun updateCard() {
        if (isCardValid()) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    cardRepository.updateCard(card.value!!)
                }
                success.value = true
            }
        }
    }

    fun insertCard() {
        if (isCardValid()) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    cardRepository.insertCard(card.value!!)
                }
                success.value = true
            }
        }
    }

    fun deleteAllCards() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                cardRepository.deleteAllCards()

            }
        }
    }

    private fun isCardValid(): Boolean {
        return when {
            card.value!!.platform.isBlank() -> {
                error.value = "Platform must not be empty"
                false
            }
            card.value!!.title.isBlank() -> {
                error.value = "Title must not be empty"
                false
            }
            else -> true
        }
    }
}