package net.austinaryain.overflowquest.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.answer_list_item.view.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.Answer


open class AnswersAdapter(private val answers: MutableList<Answer>, private val context: Context) :
    RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

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
            Toast.makeText(context, wasCorrect, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return answers.count()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val answerBody = view.wv_answer_body!!
        val correctButton = view.btn_correct_answer!!
        val backingView = view
    }
}

