package net.austinaryain.overflowquest.data.question

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import net.austinaryain.overflowquest.data.answer.Answer

@Entity(tableName = "questions")
data class Question(
    @NonNull
    @PrimaryKey
    var question_id: Long = 0,
    var answered: Boolean = false,
    var accepted_answer_id: Long = 0,
    var answer_count: Int = 0,
    var score: Int = 0,
    var body_markdown: String = "",
    var title: String = "",
    var body: String = "",
    var guessed: Boolean = false,
    @Ignore var answers: MutableList<Answer> = mutableListOf()
) {
    constructor() : this(0, true, 0, 0, 0, "", "", "", false, mutableListOf())
}