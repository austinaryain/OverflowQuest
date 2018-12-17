package net.austinaryain.overflowquest.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import io.reactivex.annotations.NonNull
import org.jetbrains.annotations.NonNls
import java.io.Serializable

@Entity
data class Question(
    @NonNull
    @PrimaryKey
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