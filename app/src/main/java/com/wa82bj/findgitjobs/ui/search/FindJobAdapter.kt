package com.wa82bj.findgitjobs.ui.search

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wa82bj.findgitjobs.R
import com.wa82bj.findgitjobs.data.model.JobsModel
import kotlinx.android.synthetic.main.item_job.view.*

/*
** Created by Wael Baaj 17/10/2019
*/

class FindJobAdapter(private val jobsList:List<JobsModel>?
): RecyclerView.Adapter<FindJobAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val viewHolder = RecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_job, parent, false))

        viewHolder.itemView.cardView.setOnClickListener {
            val url= jobsList?.get(viewHolder.adapterPosition)?.url
            if (url != null) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(parent.context, browserIntent,null)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(jobsList?.get(position))
    }

    override fun getItemCount(): Int {
        return jobsList?.size ?: 0
    }

    class RecyclerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(job:JobsModel?) {
            view.textTitle.text =  job?.title
            view.textTime.text = job?.created_at
            view.textCompany.text = job?.company
            view.textLocation.text = job?.location

            Glide.with(view)
                .asBitmap()
                .load(job?.company_logo)
                .into(view.company_logo)

        }
    }
}