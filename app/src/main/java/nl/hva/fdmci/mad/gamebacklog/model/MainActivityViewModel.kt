package nl.hva.fdmci.mad.gamebacklog.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.hva.fdmci.mad.gamebacklog.database.CardRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val cardRepository = CardRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    var cards = cardRepository.getAllCards()

    fun deleteAllCards() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                cardRepository.deleteAllCards()

            }
        }
    }

    fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val cardToDelete = cards.value?.get(position)
                cards.value?.drop(position)
                mainScope.launch {
                    withContext(Dispatchers.IO) {
                        cardRepository.deleteCard(cardToDelete!!)
                    }

                }
            }
        }
        return ItemTouchHelper(callback)
    }
}