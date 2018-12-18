package net.austinaryain.overflowquest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_question_guess.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.AppDatabase
import net.austinaryain.overflowquest.databinding.ActivityQuestionGuessBinding
import net.austinaryain.overflowquest.ui.adapters.AnswersAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class QuestionGuessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionGuessBinding

    var answerDao = AppDatabase.getAppDataBase(this)?.answersDao()
    var questionDao = AppDatabase.getAppDataBase(this)?.questionsDao()
    private var questionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_question_guess)

        questionId = intent.getIntExtra("questionId", 0)

        updateUI()
    }

    private fun updateUI() {
        doAsync {
            var answers = answerDao?.getAnswersByQuestionId(questionId)
            var question = questionDao?.getQuestionById(questionId)
            answers?.sortBy { answer -> answer.body }
            uiThread {
                tv_question_guess_title.text = question?.title
                wv_question_body.loadData(question?.body, "text/html; charset=utf-8", "UTF-8")
                rv_answers.layoutManager = LinearLayoutManager(this@QuestionGuessActivity)
                rv_answers.adapter = AnswersAdapter(answers!!, this@QuestionGuessActivity)
            }
        }
    }

}
