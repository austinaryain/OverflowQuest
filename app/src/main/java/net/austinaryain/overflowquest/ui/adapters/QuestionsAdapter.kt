package net.austinaryain.overflowquest.ui.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.question_list_item.view.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.Question
import net.austinaryain.overflowquest.ui.QuestionGuessActivity


open class QuestionsAdapter(private val questions: MutableList<Question>, private val context: Context) :
    RecyclerView.Adapter<QuestionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.question_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.questionTitle.text = questions[position].title
        holder.answerCount.text = questions[position].answer_count.toString()

        holder.backingView.setOnClickListener {
            val intent: Intent = Intent(context, QuestionGuessActivity::class.java)
            intent.putExtra("Question", questions[position])
            startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val questionTitle = view.tv_question_title!!
        val answerCount = view.tv_num_answers!!
        val backingView = view

    }
}

