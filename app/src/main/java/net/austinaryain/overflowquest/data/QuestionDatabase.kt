package net.austinaryain.overflowquest.data

import android.content.Context
import androidx.room.*
import net.austinaryain.overflowquest.util.Constants

@Database(entities = [Question::class, Answer::class], version = 1, exportSchema = false)
//@TypeConverters(AnswerConverter::class)
abstract class QuestionDatabase : RoomDatabase() {

    abstract fun questionsDao(): QuestionDao

    companion object {
        private var INSTANCE: QuestionDatabase? = null

        fun getAppDataBase(context: Context): QuestionDatabase? {
            if (INSTANCE == null) {
                synchronized(QuestionDatabase::class) {
                    INSTANCE = Room.databaseBuilder<QuestionDatabase>(
                        context.applicationContext,
                        QuestionDatabase::class.java, Constants.DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}