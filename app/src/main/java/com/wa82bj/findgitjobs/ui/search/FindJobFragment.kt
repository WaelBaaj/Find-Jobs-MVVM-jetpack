package com.wa82bj.findgitjobs.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wa82bj.findgitjobs.R
import com.wa82bj.findgitjobs.data.model.JobsModel
import com.wa82bj.findgitjobs.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.find_job_fragment.*
import javax.inject.Inject

class FindJobFragment : DaggerFragment() {

    val TAG = FindJobFragment::class.simpleName

    lateinit var viewModel: FindJobViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    var errorSnackbar: Snackbar? = null

    companion object {
        fun newInstance() = FindJobFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.find_job_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val layoutManager = LinearLayoutManager(context)
        recyclerViewJobs.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(recyclerViewJobs.context, layoutManager.orientation)
        recyclerViewJobs.addItemDecoration(dividerItemDecoration)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FindJobViewModel::class.java)

        viewModel.jobsResults.observe(this, Observer<List<JobsModel>> {
                it ->  recyclerViewJobs.adapter = FindJobAdapter(it)

            if (it.isNotEmpty()) {
                noResultsLayout.visibility = View.GONE
            } else {
                noResultsLayout.visibility = View.VISIBLE
            }
        })

        if (!viewModel.lastQuery.isNullOrEmpty()) {
            searchViewJobs.setQuery(viewModel.lastQuery,false)
        }

        viewModel.searchState.observe(
            this,Observer<FindJobViewModel.SearchState> {

            if (it == FindJobViewModel.SearchState.IN_SEARCH) {
                progressBar.visibility = View.VISIBLE
                progressBackdrop.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                progressBackdrop.visibility = View.GONE
            }

            val errorMessage = when (it) {
                FindJobViewModel.SearchState.ERROR -> getString(R.string.network_connection_error)
                FindJobViewModel.SearchState.ERROR_OFFLINE ->  getString(R.string.offline_error)
                else -> null
            }

            if (errorMessage != null) {
                errorSnackbar = Snackbar.make(recyclerViewJobs, errorMessage, Snackbar.LENGTH_INDEFINITE)
                errorSnackbar?.view?.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorAccent,null))
                errorSnackbar?.show()
            } else {
                errorSnackbar?.dismiss()
            }
        })

        searchViewJobs.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                viewModel.searchPositions(query)
                searchViewJobs.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        // First Search by default is "Germany"
        viewModel.searchPositions("Germany") // you can change it

    }

}
