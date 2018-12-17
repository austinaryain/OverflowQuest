package net.austinaryain.overflowquest.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import io.reactivex.annotations.NonNull
import java.io.Serializable

@Entity
data class Answer(
    @NonNull
    @PrimaryKey
    val answer_id: Int,
    val accepted: Boolean,
    val is_accepted: Boolean,
    val score: Int,
    val question_id: Int,
    val body_markdown: String,
    val body: String,
    val userSelected: Boolean
) : Serializable