package net.austinaryain.overflowquest.ui.viewmodels

import androidx.lifecycle.ViewModel
import net.austinaryain.overflowquest.data.question.Question

open class MainActivityViewModel : ViewModel() {
    var unansweredQuestions: MutableSet<Question> = mutableSetOf()
    var answeredQuestions: MutableSet<Question> = mutableSetOf()
    var selectedTab: Int = 0
}