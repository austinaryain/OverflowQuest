package net.austinaryain.overflowquest.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.answer_list_item.view.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.AppDatabase
import net.austinaryain.overflowquest.data.answer.Answer
import org.jetbrains.anko.doAsync


open class AnswersAdapter(private val answers: MutableList<Answer>, private val context: Context) :
    RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

    var questionDao = AppDatabase.getAppDataBase(context)?.questionsDao()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.answer_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.answerBody.loadData(answers[position].body, "text/html", "UTF-8")

        holder.correctButton.setOnClickListener {
            var wasCorrect = "Sorry, that is not the right answer..."
            if (answers[position].is_accepted) {
                wasCorrect = "You are correct!"
            }

            doAsync {
                var question = questionDao?.getQuestionById(answers[position].question_id)
                question?.guessed = true
                questionDao?.insertSingleQuestion(question!!)
            }
            Toast.makeText(context, wasCorrect, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return answers.count()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val answerBody = view.wv_answer_body!!
        val correctButton = view.btn_correct_answer!!
    }
}

