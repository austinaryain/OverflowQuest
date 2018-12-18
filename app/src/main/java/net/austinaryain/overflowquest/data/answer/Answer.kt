package net.austinaryain.overflowquest.data.answer

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class Answer(
    @NonNull
    @PrimaryKey
    var answer_id: Int = 0,
    var accepted: Boolean = false,
    var is_accepted: Boolean = false,
    var score: Int = 0,
    var question_id: Int = 0,
    var body_markdown: String = "",
    var body: String = "",
    var userSelected: Boolean = false
) {
    constructor() : this(0, false, false, 0, 0, "", "", false)
}