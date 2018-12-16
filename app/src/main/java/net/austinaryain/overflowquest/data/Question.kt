package net.austinaryain.overflowquest.data

import java.io.Serializable

data class Question(
    val is_answered: Boolean,
    val accepted_answer_id: Long,
    val answer_count: Int,
    val score: Int,
    val question_id: Long,
    val body_markdown: String,
    val title: String,
    val body: String,
    var answers: MutableList<Answer>,
    val guessed: Boolean
) : Serializable