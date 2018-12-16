package net.austinaryain.overflowquest.ui

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_question_guess.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.Question
import net.austinaryain.overflowquest.databinding.ActivityQuestionGuessBinding
import net.austinaryain.overflowquest.ui.adapters.AnswersAdapter

class QuestionGuessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionGuessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_question_guess)

        val question: Question = intent.getSerializableExtra("Question") as Question

        tv_question_guess_title.text = question.title
        wv_question_body.loadData(question.body, "text/html; charset=utf-8", "UTF-8")

        rv_answers.layoutManager = LinearLayoutManager(this)
        rv_answers.adapter = AnswersAdapter(question.answers, this)

    }

}
