package net.austinaryain.overflowquest.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.Question
import net.austinaryain.overflowquest.databinding.ActivityMainBinding
import net.austinaryain.overflowquest.http.SOService
import net.austinaryain.overflowquest.ui.adapters.QuestionsAdapter
import net.austinaryain.overflowquest.ui.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var questions: MutableList<Question> = mutableListOf()

    private val ANSWER_RESULT_CODE = 64

    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_questions -> {
                // TODO: If we have untried questions in SQLite, get those
                //       otherwise get some new questions with SOService
                rv_questions.adapter.notifyDataSetChanged()
                rv_questions.invalidate()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_guesses -> {
                // TODO: Get this list from SQLite
                rv_questions.adapter.notifyDataSetChanged()
                rv_questions.invalidate()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (mViewModel.unansweredQuestions.isEmpty()) {
            mViewModel.unansweredQuestions = SOService().getQuestions()
        }

        rv_questions.layoutManager = LinearLayoutManager(this)
        rv_questions.adapter = QuestionsAdapter(mViewModel.unansweredQuestions, this)
        rv_questions.adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
