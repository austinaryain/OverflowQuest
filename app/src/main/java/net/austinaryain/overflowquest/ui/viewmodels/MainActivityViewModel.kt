package net.austinaryain.overflowquest.ui.viewmodels

import androidx.lifecycle.ViewModel
import net.austinaryain.overflowquest.data.question.Question

open class MainActivityViewModel : ViewModel() {
    var unansweredQuestions: MutableList<Question> = mutableListOf()
    var answeredQuestions: MutableList<Question> = mutableListOf()
    var selectedTab: Int = 0
}