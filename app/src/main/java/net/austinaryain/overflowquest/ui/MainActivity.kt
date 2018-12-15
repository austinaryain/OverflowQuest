package net.austinaryain.overflowquest.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.Question
import net.austinaryain.overflowquest.databinding.ActivityMainBinding
import net.austinaryain.overflowquest.ui.adapters.QuestionsAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val questions: ArrayList<Question> = ArrayList()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_questions -> {
                mockQuestions(false)
                rv_questions.adapter.notifyDataSetChanged()
                rv_questions.invalidate()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_guesses -> {
                mockQuestions(true)
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

        mockQuestions(false)

        rv_questions.layoutManager = LinearLayoutManager(this)
        rv_questions.adapter = QuestionsAdapter(questions, this)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun mockQuestions(guessed: Boolean) {

        questions.clear()
        questions.add(Question("Example Question One", "Example Body", "", guessed))
        questions.add(Question("Example Question Two", "Example Body", "", guessed))
        questions.add(Question("Example Question Three", "Example Body", "", guessed))
        questions.add(Question("Example Question Four", "Example Body", "", guessed))
        questions.add(Question("Example Question Five", "Example Body", "", guessed))
        questions.add(Question("Example Question Six", "Example Body", "", guessed))
        questions.add(Question("Example Question Seven", "Example Body", "", guessed))
        questions.add(Question("Example Question Eight", "Example Body", "", guessed))
        questions.add(Question("Example Question Nine", "Example Body", "", guessed))
        questions.add(Question("Example Question Ten", "Example Body", "", guessed))
        questions.add(Question("Example Question Eleven", "Example Body", "", guessed))
        questions.add(Question("Example Question Twelve", "Example Body", "", guessed))
        questions.add(Question("Example Question Thirteen", "Example Body", "", guessed))
        questions.add(Question("Example Question Fourteen", "Example Body", "", guessed))

    }
}
