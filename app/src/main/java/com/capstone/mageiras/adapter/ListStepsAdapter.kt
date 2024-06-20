package com.capstone.mageiras.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mageiras.R
import com.capstone.mageiras.databinding.FragmentDetailStepsBinding

class ListStepsAdapter(private val listSteps: ArrayList<String>) :
    RecyclerView.Adapter<ListStepsAdapter.ListViewHolder>() {
    private lateinit var binding: FragmentDetailStepsBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_detail_steps_item_row, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val steps = listSteps[position]

        holder.stepsNumber.text = (position + 1).toString()
        holder.stepsDescription.text = steps

    }

    override fun getItemCount(): Int = listSteps.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  stepsNumber: TextView = itemView.findViewById(R.id.tv_step_number)
        val stepsDescription: TextView = itemView.findViewById(R.id.tv_step)
    }
}