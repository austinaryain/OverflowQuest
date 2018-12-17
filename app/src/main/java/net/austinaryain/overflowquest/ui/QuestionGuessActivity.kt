package net.austinaryain.overflowquest.ui

import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_question_guess.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.Question
import net.austinaryain.overflowquest.databinding.ActivityQuestionGuessBinding
import net.austinaryain.overflowquest.ui.adapters.AnswersAdapter
import net.austinaryain.overflowquest.ui.adapters.OnAnwerClickListener
import net.austinaryain.overflowquest.ui.viewmodels.MainActivityViewModel

class QuestionGuessActivity : AppCompatActivity(), OnAnwerClickListener {

    private lateinit var binding: ActivityQuestionGuessBinding

    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_question_guess)

        val question: Question = intent.getSerializableExtra("Question") as Question

        tv_question_guess_title.text = question.title
        wv_question_body.loadData(question.body, "text/html; charset=utf-8", "UTF-8")
        question.answers.sortBy { answer -> answer.body }
        rv_answers.layoutManager = LinearLayoutManager(this)
        rv_answers.adapter = AnswersAdapter(question.answers, this)
    }

    override fun onAnswerClick(question: Question) {
        mViewModel.answeredQuestions.add(question)
    }

}
