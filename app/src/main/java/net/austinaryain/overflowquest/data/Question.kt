package net.austinaryain.overflowquest.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "questions")
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
    val guessed: Boolean,
    @Ignore
    var answers: MutableList<Answer>
) : Serializable