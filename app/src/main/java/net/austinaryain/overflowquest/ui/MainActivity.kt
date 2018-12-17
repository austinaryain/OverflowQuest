package net.austinaryain.overflowquest.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.QuestionDao
import net.austinaryain.overflowquest.data.QuestionDatabase
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

    private var db: QuestionDatabase? = null
    private var questionDao: QuestionDao? = null


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_questions -> {
                rv_questions.adapter.notifyDataSetChanged()
                rv_questions.invalidate()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_guesses -> {
                //var newData = questionDao?.getGuessedQuestions(true)!!
                //rv_questions.adapter = QuestionsAdapter(newData, this)
                rv_questions.adapter.notifyDataSetChanged()
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

        Observable.fromCallable {
            db = QuestionDatabase.getAppDataBase(applicationContext)
            questionDao = db?.questionsDao()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        var apiHelper = ApiHelper()

        apiHelper.getQuestions(this)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onSuccess(data: QuestionsResponse) {

        if (mViewModel.unansweredQuestions.isEmpty()) {
            mViewModel.unansweredQuestions = data.acceptedAnswerQuestions()
            questionDao?.insertQuestions(data.acceptedAnswerQuestions())
        }

        rv_questions.layoutManager = LinearLayoutManager(this)
        rv_questions.adapter = QuestionsAdapter(mViewModel.unansweredQuestions, this)
        rv_questions.adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        println("Something went wrong!")
    }

}
