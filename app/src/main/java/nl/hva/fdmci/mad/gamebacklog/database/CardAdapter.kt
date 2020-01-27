package nl.hva.fdmci.mad.gamebacklog.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_component.view.*
import nl.hva.fdmci.mad.gamebacklog.R
import java.text.SimpleDateFormat
import java.util.*

class CardAdapter(private val cards: List<Card>?) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_component, parent, false)
        )
    }
    override fun getItemCount(): Int {
        return cards?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards?.get(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(card : Card?) {
            itemView.tvTitle.text = card?.title
            itemView.tvPlatform.text = card?.platform
            println(card?.release.toString())
            val sdf=SimpleDateFormat("d LLLL YYYY", Locale.ENGLISH)
            itemView.tvRelease.text = "Release: " + sdf.format(card?.release)
        }
    }
}