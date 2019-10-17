package com.wa82bj.findgitjobs


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.wa82bj.findgitjobs.data.api.FindGithubJobsApi
import com.wa82bj.findgitjobs.data.model.JobsModel
import com.wa82bj.findgitjobs.data.repository.FindGithubJobsRepository
import com.wa82bj.findgitjobs.ui.search.FindJobViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.util.concurrent.Executor

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class JobsViewModelUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var gitHubJobsApiService: FindGithubJobsApi
    private lateinit var gitHubJobsRepository: FindGithubJobsRepository
    private lateinit var jobsViewModel: FindJobViewModel

    @Before
    fun doBefore() {
        gitHubJobsApiService = Mockito.mock(FindGithubJobsApi::class.java)
        gitHubJobsRepository = Mockito.mock(FindGithubJobsRepository::class.java)
        jobsViewModel = FindJobViewModel(gitHubJobsRepository)
    }

    companion object {
        private val immediate: Scheduler = object : Scheduler() {
            override fun createWorker(): Scheduler.Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        @BeforeClass @JvmStatic
        fun setupSchedulers() {
            RxJavaPlugins.setInitIoSchedulerHandler{ _ -> immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler{ _ -> immediate}
        }
    }

    @Test
    fun nullSearchQuery() {
        val observer = mock<Observer<List<JobsModel>>>()
        jobsViewModel.jobsResults.observeForever(observer)

        Mockito.verifyNoMoreInteractions(observer)
        Mockito.verifyNoMoreInteractions(gitHubJobsRepository)

        jobsViewModel.searchPositions(null)
        Mockito.verify(observer).onChanged(listOf())
    }

    @Test
    fun jobsResults() {
        val result = listOf(
            JobsModel("ID_1","0","TITLE_1","LOCATION_1","TYPE","DESCRIPTION","","company",null,null,""),
            JobsModel("ID_2","0","TITLE_2","LOCATION_2","TYPE","DESCRIPTION","","company",null,null,""),
            JobsModel("ID_3","0","TITLE_3","LOCATION_3","TYPE","DESCRIPTION","","company",null,null,""),
            JobsModel("ID_4","0","TITLE_4","LOCATION_4","TYPE","DESCRIPTION","","company",null,null,"")
        )

        val observer = mock<Observer<List<JobsModel>>>()
        jobsViewModel.jobsResults.observeForever(observer)
        Mockito.`when`(gitHubJobsRepository.searchPosition(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(result))

        Mockito.verifyNoMoreInteractions(observer)
        Mockito.verifyNoMoreInteractions(gitHubJobsRepository)

        jobsViewModel.searchPositions("Test")
        Mockito.verify(observer).onChanged(result)
    }

    @Test
    fun liveDataTest() {
        val liveData = MutableLiveData<Int>()
        liveData.postValue(123)
        Assert.assertEquals(123, liveData.value)

        liveData.value = 321
        Assert.assertEquals(321, liveData.value)
    }
}
