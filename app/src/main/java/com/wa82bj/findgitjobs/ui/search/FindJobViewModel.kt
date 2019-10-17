package com.wa82bj.findgitjobs.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wa82bj.findgitjobs.data.api.OfflineException
import com.wa82bj.findgitjobs.data.model.JobsModel
import com.wa82bj.findgitjobs.data.repository.FindGithubJobsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class FindJobViewModel @Inject constructor(
    private val repository: FindGithubJobsRepository): ViewModel() {

    enum class SearchState {DONE, IN_SEARCH, ERROR, ERROR_OFFLINE}

    private val TAG = FindJobViewModel::class.simpleName

    private var disposable: Disposable? = null

    var lastQuery:String? = null
        private set

    private val _searchState: MutableLiveData<SearchState> = MutableLiveData()
    val searchState: LiveData<SearchState>
        get() = _searchState


    private val _jobsResults: MutableLiveData<List<JobsModel>> = MutableLiveData()
    val jobsResults: LiveData<List<JobsModel>>
        get() = _jobsResults


    init {
        _searchState.value = SearchState.DONE
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun searchPositions(search:String?) {
        lastQuery = search


        if (search == null) {

            _jobsResults.value = listOf()
            return
        }

        disposable?.dispose()

        _searchState.value = SearchState.IN_SEARCH

        disposable = repository.searchPosition(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        data ->
                    _jobsResults.value = data
                    _searchState.value = SearchState.DONE
                    Log.d(TAG , " data : " + data)
                },
                {
                        error ->
                    if (error is OfflineException) {
                        _searchState.value = SearchState.ERROR_OFFLINE
                    } else {
                        _searchState.value = SearchState.ERROR
                    }
                    Log.e(TAG, Log.getStackTraceString(error))
                }
            )
    }

}
