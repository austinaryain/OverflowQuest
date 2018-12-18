package net.austinaryain.overflowquest.data.question

import androidx.room.*


@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestions(questions: MutableList<Question>)

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): MutableList<Question>

    @Query("SELECT * FROM questions WHERE question_id = :questionId")
    fun getQuestionById(questionId: Int): Question

    @Query("SELECT * FROM questions WHERE guessed = :guessed")
    fun getGuessedQuestions(guessed: Boolean): MutableList<Question>

    @Update
    fun updateQuestion(question: Question)

    @Delete
    fun deleteQuestion(question: Question)

    @Query("DELETE FROM questions")
    fun deleteAllQuestions()

}