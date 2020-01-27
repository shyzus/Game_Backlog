package nl.hva.fdmci.mad.gamebacklog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Database(entities = [Card::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CardRoomDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {
        private const val DATABASE_NAME = "CARD_DATABASE"

        @Volatile
        private var INSTANCE: CardRoomDatabase? = null

        fun getDatabase(context: Context): CardRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(CardRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CardRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    INSTANCE?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            database.cardDao().insertCard(
                                                Card(
                                                    "Title",
                                                    "",
                                                    Date()
                                                )
                                            )
                                        }
                                    }
                                }
                            })
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}