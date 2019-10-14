package com.example.swipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions = ArrayList<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        rvQuestions.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        rvQuestions.adapter = questionAdapter
        rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        createItemTouchHelper().attachToRecyclerView(rvQuestions)

        for (i in Question.QUESTIONS.indices) {
            questions.add(Question(Question.QUESTIONS[i], Question.ISCORRECT[i]))
        }
        questionAdapter.notifyDataSetChanged()
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var answer = false
                if (ItemTouchHelper.LEFT == direction) {
                    answer = true
                } else if (ItemTouchHelper.RIGHT == direction) {
                    answer = false
                }

                val position = viewHolder.adapterPosition
                if (questions[position].isCorrect == answer) {
                    var snack = Snackbar.make(root_layout, "Correct", Snackbar.LENGTH_LONG)
                    snack.show()
                } else {
                    var snack = Snackbar.make(root_layout, "Incorrect", Snackbar.LENGTH_LONG)
                    snack.show()
                }

                questionAdapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }

        return ItemTouchHelper(callback)
    }
}
