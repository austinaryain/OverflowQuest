package net.austinaryain.overflowquest.data

import androidx.room.*

@Dao
interface QuestionDao {

    @Insert
    fun insertSingleQuestion(question: Question)

    @Insert
    fun insertQuestions(questions: MutableList<Question>)

    @Query("SELECT * FROM Question WHERE question_id = :questionId")
    fun getQuestionById(questionId: Long): Question

    @Query("SELECT * FROM Question WHERE guessed = :guessed")
    fun getGuessedQuestions(guessed: Boolean): MutableList<Question>

    @Update
    fun updateQuestion(question: Question)

    @Delete
    fun deleteQuestion(question: Question)

}