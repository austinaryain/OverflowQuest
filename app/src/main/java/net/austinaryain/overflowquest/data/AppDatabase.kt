package net.austinaryain.overflowquest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.austinaryain.overflowquest.data.answer.Answer
import net.austinaryain.overflowquest.data.answer.AnswerDao
import net.austinaryain.overflowquest.data.question.Question
import net.austinaryain.overflowquest.data.question.QuestionDao
import net.austinaryain.overflowquest.util.Constants

@Database(entities = [Question::class, Answer::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionsDao(): QuestionDao
    abstract fun answersDao(): AnswerDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder<AppDatabase>(
                        context.applicationContext,
                        AppDatabase::class.java, Constants.DATABASE_NAME
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