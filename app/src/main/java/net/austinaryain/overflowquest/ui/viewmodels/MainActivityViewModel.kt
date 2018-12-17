package net.austinaryain.overflowquest.ui.viewmodels

import androidx.lifecycle.ViewModel
import net.austinaryain.overflowquest.data.Question

open class MainActivityViewModel : ViewModel() {

    var unansweredQuestions: MutableList<Question> = mutableListOf()
    var answeredQuestions: MutableList<Question> = mutableListOf()

    fun addAnsweredQuestion(question: Question) {
        answeredQuestions.add(question)
        unansweredQuestions.remove(question)
    }

}