package net.austinaryain.overflowquest.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.question_list_item.view.*
import net.austinaryain.overflowquest.R
import net.austinaryain.overflowquest.data.Question


class QuestionsAdapter(private val questions : ArrayList<Question>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.question_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.questionTitle?.text = questions[position].title
        holder.guessed?.isChecked = questions[position].guessed
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Toast.makeText(v?.context, guessed.isChecked.toString(), Toast.LENGTH_LONG).show()
    }

    // Holds the TextView that will add each animal to
    val questionTitle = view.tv_question_title!!
    val guessed = view.cb_user_guessed
}