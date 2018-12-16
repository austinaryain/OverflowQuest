package net.austinaryain.overflowquest.data

import java.io.Serializable

data class Answer(
    val accepted: Boolean,
    val is_accepted: Boolean,
    val score: Int,
    val answer_id: Int,
    val question_id: Int,
    val body_markdown: String,
    val body: String,
    val userSelected: Boolean
) : Serializable