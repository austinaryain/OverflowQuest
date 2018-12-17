package net.austinaryain.overflowquest.http

import net.austinaryain.overflowquest.data.Question

data class QuestionsResponse(
    val items: MutableList<Question>
) {
    fun acceptedAnswerQuestions(): MutableList<Question> {
        return items.filter { question ->
            question.is_answered &&
                    question.accepted_answer_id > 0 &&
                    question.answer_count > 1
        } as MutableList<Question>
    }
}