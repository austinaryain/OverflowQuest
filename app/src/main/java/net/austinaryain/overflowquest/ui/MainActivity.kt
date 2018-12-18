package net.austinaryain.overflowquest.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.AppDatabase
import net.austinaryain.overflowquest.data.answer.AnswerDao
import net.austinaryain.overflowquest.data.question.QuestionDao
import net.austinaryain.overflowquest.databinding.ActivityMainBinding
import net.austinaryain.overflowquest.http.ApiHelper
import net.austinaryain.overflowquest.http.OnDataCallback
import net.austinaryain.overflowquest.http.QuestionsResponse
import net.austinaryain.overflowquest.ui.adapters.QuestionsAdapter
import net.austinaryain.overflowquest.ui.viewmodels.MainActivityViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity(), OnDataCallback<QuestionsResponse> {

    private lateinit var binding: ActivityMainBinding

    private lateinit var apiHelper: ApiHelper

    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private lateinit var db: AppDatabase
    private lateinit var questionDao: QuestionDao
    private lateinit var answerDao: AnswerDao

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_questions -> {
                loadUnguessedQuestions()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_guesses -> {
                loadGuessedQuestions()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        db = AppDatabase.getAppDataBase(this@MainActivity)!!
        questionDao = db.questionsDao()
        answerDao = db.answersDao()

        loadUnguessedQuestions()

        apiHelper = ApiHelper()

        apiHelper.getQuestions(this)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onSuccess(data: QuestionsResponse) {
        if (mViewModel.unansweredQuestions.isEmpty() || mViewModel.unansweredQuestions.count() < 25) {
            mViewModel.unansweredQuestions.addAll(data.acceptedAnswerQuestions())
            doAsync {
                //=== DEBUG ONLY DANGER
                //questionDao.deleteAllQuestions()
                //answerDao.deleteAllAnswers()
                //=== DEBUG ONLY DANGER
                questionDao.insertQuestions(data.acceptedAnswerQuestions())
                for (question in data.acceptedAnswerQuestions()) {
                    answerDao.insertAnswers(question.answers)
                }
            }
        }

        rv_questions.layoutManager = LinearLayoutManager(this)
        rv_questions.adapter = QuestionsAdapter(mViewModel.unansweredQuestions, this)
        (rv_questions.adapter as QuestionsAdapter).notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        // TODO: Check if we have other answers and if not, let the user know we're sorry
        println("Something went wrong!")
    }

    private fun loadQuestions(guessed: Boolean) {
        doAsync {

            var questions = if (guessed) mViewModel.answeredQuestions
            else mViewModel.unansweredQuestions

            if (questions.isEmpty() || guessed) {
                questions = questionDao.getGuessedQuestions(guessed)

                if (guessed) mViewModel.answeredQuestions.addAll(questions)
                else mViewModel.unansweredQuestions.addAll(questions)
            }

            uiThread {
                rv_questions.adapter = QuestionsAdapter(questions, this@MainActivity)
                rv_questions.adapter?.notifyDataSetChanged()
                rv_questions.invalidate()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.sync_menu_button) {
            apiHelper.getQuestions(this)
        }
        return true
    }

    private fun loadGuessedQuestions() {
        loadQuestions(true)
    }

    private fun loadUnguessedQuestions() {
        loadQuestions(false)
    }

}
