package nl.hva.fdmci.mad.gamebacklog.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "Card")
data class Card (
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "platform")
    var platform: String,
    @ColumnInfo(name = "release")
    var release: Date,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
) : Parcelable