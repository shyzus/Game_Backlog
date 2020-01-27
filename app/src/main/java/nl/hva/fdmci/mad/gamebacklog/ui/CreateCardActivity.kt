package nl.hva.fdmci.mad.gamebacklog.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_card.*
import nl.hva.fdmci.mad.gamebacklog.R
import nl.hva.fdmci.mad.gamebacklog.database.Card
import nl.hva.fdmci.mad.gamebacklog.model.CreateCardViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class CreateCardActivity : AppCompatActivity(){

    private lateinit var createCardViewModel: CreateCardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        supportActionBar?.title = "Add Card"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        saveCardFab.setOnClickListener {

            createCardViewModel.card.value?.apply {
                title = etTitle.text.toString()

                release = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    .parse(etDay.text.toString()+ "/" + etMonth.text.toString() + "/" + etYear.text.toString())
                platform = etPlatform.text.toString()
            }


            createCardViewModel.insertCard()
        }
    }

    private fun initViewModel() {

        createCardViewModel = ViewModelProviders.of(this).get(CreateCardViewModel::class.java)

        createCardViewModel.card.value = Card("", "", Date())

        createCardViewModel.card.observe(this, Observer { card ->
            if (card != null) {
                etTitle.setText(card.title)
                etPlatform.setText(card.platform)
                val calendar = Calendar.getInstance()
                calendar.time = card.release
                etDay.setText(SimpleDateFormat("dd", Locale.ENGLISH).format(card.release))
                etMonth.setText(SimpleDateFormat("MM", Locale.ENGLISH).format(card.release))
                etYear.setText(SimpleDateFormat("YYYY", Locale.ENGLISH).format(card.release))
            }
        })

        createCardViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        createCardViewModel.success.observe(this, Observer { success ->
            if (success != null && success) finish()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> { // Used to identify when the user has clicked the back button
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}