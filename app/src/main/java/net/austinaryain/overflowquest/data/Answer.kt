package net.austinaryain.overflowquest.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Answer(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val answer_id: Int,
    val accepted: Boolean,
    val is_accepted: Boolean,
    val score: Int,
    val question_id: Int,
    val body_markdown: String,
    val body: String,
    val userSelected: Boolean
) : Serializable