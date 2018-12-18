package net.austinaryain.overflowquest.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity(), OnDataCallback<QuestionsResponse> {

    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var apiHelper: ApiHelper
    private lateinit var db: AppDatabase
    private lateinit var questionDao: QuestionDao
    private lateinit var answerDao: AnswerDao
    private var onGuessesTab: Boolean = false

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_questions -> {
                mViewModel.selectedTab = R.id.navigation_questions
                loadUnguessedQuestions()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_guesses -> {
                mViewModel.selectedTab = R.id.navigation_guesses
                loadGuessedQuestions()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initializeDatabase()

        initializeQuestionsList()

        hookupUI()

    }

    private fun isNetworkConnectionAvailable(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    private fun hookupUI() {
        if (mViewModel.selectedTab == 0) {
            mViewModel.selectedTab = R.id.navigation_questions
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filterQuestions(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initializeQuestionsList() {
        onGuessesTab = mViewModel.selectedTab == R.id.navigation_guesses

        loadQuestions(onGuessesTab)

        apiHelper = ApiHelper()

        if (!onGuessesTab) {
            getApiIfNetworkAvailable()
        }
    }

    private fun getApiIfNetworkAvailable() {
        if (isNetworkConnectionAvailable())
            apiHelper.getQuestions(this)
        else {
            alert("Network Connection Required!", "Please ensure you have an internet connection and try again.").show()
            progressBar.visibility = View.GONE
        }
    }

    private fun initializeDatabase() {
        db = AppDatabase.getAppDataBase(this@MainActivity)!!
        questionDao = db.questionsDao()
        answerDao = db.answersDao()
    }

    override fun onSuccess(data: QuestionsResponse) {
        if (mViewModel.unansweredQuestions.isEmpty() || mViewModel.unansweredQuestions.count() < 25) {
            mViewModel.unansweredQuestions.addAll(data.acceptedAnswerQuestions())
            doAsync {
                questionDao.insertQuestions(data.acceptedAnswerQuestions())
                for (question in data.acceptedAnswerQuestions()) {
                    answerDao.insertAnswers(question.answers)
                }
            }
        }
        rv_questions.layoutManager = LinearLayoutManager(this)
        rv_questions.adapter = QuestionsAdapter(mViewModel.unansweredQuestions.toMutableList(), this)
        (rv_questions.adapter as QuestionsAdapter).notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        alert("Something went wrong!", "Please try again later.").show()
        progressBar.visibility = View.GONE
    }

    private fun loadQuestions(guessed: Boolean) {
        doAsync {

            var questions = if (guessed) mViewModel.answeredQuestions.toMutableList()
            else mViewModel.unansweredQuestions.toMutableList()

            if (questions.isEmpty() || guessed) {
                questions = questionDao.getGuessedQuestions(guessed)

                if (guessed) mViewModel.answeredQuestions.addAll(questions)
                else mViewModel.unansweredQuestions.addAll(questions)
            }

            uiThread {
                rv_questions.layoutManager = LinearLayoutManager(this@MainActivity)
                var newAdapter = QuestionsAdapter(questions, this@MainActivity)
                rv_questions.swapAdapter(newAdapter, true)
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun loadGuessedQuestions() {
        loadQuestions(true)
    }

    private fun loadUnguessedQuestions() {
        loadQuestions(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.sync_menu_button) {
            if (mViewModel.selectedTab == R.id.navigation_questions) {
                progressBar.visibility = View.VISIBLE
                getApiIfNetworkAvailable()
            }
        }
        return true
    }

    private fun filterQuestions(searchQuery: String) {
        var filteredList = if (mViewModel.selectedTab == R.id.navigation_questions)
            mViewModel.unansweredQuestions.filter { question ->
                question.body.contains(
                    searchQuery,
                    true
                )
            } as MutableList
        else mViewModel.answeredQuestions.filter { question ->
            question.body.contains(
                searchQuery,
                true
            )
        } as MutableList
        var newAdapter = QuestionsAdapter(filteredList, this@MainActivity)
        rv_questions.swapAdapter(newAdapter, true)
    }

}
