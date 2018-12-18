package net.austinaryain.overflowquest.data.answer

import androidx.room.*

@Dao
interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleAnswer(answer: Answer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnswers(answerList: MutableList<Answer>)

    @Query("SELECT * FROM answers")
    fun getAllAnswers(): MutableList<Answer>

    @Query("SELECT * FROM answers WHERE answer_id = :answerId")
    fun getAnswerById(answerId: Long): Answer

    @Query("SELECT * FROM answers WHERE question_id = :questionId")
    fun getAnswersByQuestionId(questionId: Int): MutableList<Answer>

    @Query("SELECT * FROM answers WHERE userSelected = :userSelected")
    fun getUserSelectedAnswer(userSelected: Boolean): Answer

    @Update
    fun updateAnswer(answer: Answer)

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertAnswer(answer: Answer)

    @Delete
    fun deleteAnswer(answer: Answer)

    @Query("DELETE FROM answers")
    fun deleteAllAnswers()

}