package net.austinaryain.overflowquest.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Question(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val question_id: Long,
    val is_answered: Boolean,
    val accepted_answer_id: Long,
    val answer_count: Int,
    val score: Int,
    val body_markdown: String,
    val title: String,
    val body: String,
    var answers: MutableList<Answer>,
    val guessed: Boolean
) : Serializable