package net.austinaryain.overflowquest.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.databinding.ActivityMainBinding
import net.austinaryain.overflowquest.http.ApiHelper
import net.austinaryain.overflowquest.http.OnDataCallback
import net.austinaryain.overflowquest.http.QuestionsResponse
import net.austinaryain.overflowquest.ui.adapters.QuestionsAdapter
import net.austinaryain.overflowquest.ui.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity(), OnDataCallback<QuestionsResponse> {

    private lateinit var binding: ActivityMainBinding

    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_questions -> {
                rv_questions.adapter = QuestionsAdapter(mViewModel.unansweredQuestions, this@MainActivity)
                rv_questions.adapter?.notifyDataSetChanged()
                rv_questions.invalidate()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_guesses -> {
                rv_questions.adapter = QuestionsAdapter(mViewModel.answeredQuestions, this@MainActivity)
                rv_questions.adapter?.notifyDataSetChanged()
                rv_questions.invalidate()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var apiHelper = ApiHelper()

        apiHelper.getQuestions(this)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onSuccess(data: QuestionsResponse) {

        if (mViewModel.unansweredQuestions.isEmpty()) {
            mViewModel.unansweredQuestions = data.acceptedAnswerQuestions()
        }

        rv_questions.layoutManager = LinearLayoutManager(this)
        rv_questions.adapter = QuestionsAdapter(mViewModel.unansweredQuestions, this)
        (rv_questions.adapter as QuestionsAdapter).notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        println("Something went wrong!")
    }

}
