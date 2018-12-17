package net.austinaryain.overflowquest.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import net.austinaryain.overflowquest.util.Constants

@Database(entities = [Question::class], version = 1)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionsDao(): QuestionDao

    companion object {
        private var INSTANCE: QuestionDatabase? = null

        fun getAppDataBase(context: Context): QuestionDatabase? {
            if (INSTANCE == null) {
                synchronized(QuestionDatabase::class) {
                    INSTANCE =
                            Room.databaseBuilder(context, QuestionDatabase::class.java, Constants.DATABASE_NAME).build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}