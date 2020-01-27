package nl.hva.fdmci.mad.gamebacklog.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_component.*
import nl.hva.fdmci.mad.gamebacklog.R
import nl.hva.fdmci.mad.gamebacklog.database.CardAdapter
import nl.hva.fdmci.mad.gamebacklog.model.MainActivityViewModel
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var cardAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener {
            val intent = Intent(this, CreateCardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.cards.observe(this, Observer { cards ->
            cards?.forEach { card ->
                tvTitle?.text = card.title
                tvPlatform?.text = card.platform
                val calendar = Calendar.getInstance()
                calendar.time = card.release
                val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH)
                tvRelease?.text = "Release: " + df.format(card.release)
            }

            cardAdapter = CardAdapter(mainActivityViewModel.cards.value)

            cardRV.adapter = cardAdapter
            cardRV.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)

            cardAdapter.notifyDataSetChanged()
        })

        mainActivityViewModel.createItemTouchHelper().attachToRecyclerView(cardRV)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.deleteAllBtn -> {
                mainActivityViewModel.deleteAllCards()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
