package com.pinkyra.kotlinrepoexplorer.feature.explorer.view

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.pinkyra.kotlinrepoexplorer.api.RetrofitServiceProvider
import com.pinkyra.kotlinrepoexplorer.room.AppDatabase
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExplorerActivityTest {
    fun prepare(func: ExplorerActivityTestPrepare.() -> Unit) {
        ExplorerActivityTestPrepare(activityRule, mockWebServer).apply(func)
    }

    fun execute(func: ExplorerActivityTestExecute.() -> Unit) {
        ExplorerActivityTestExecute().apply(func)
    }

    fun validate(func: ExplorerActivityTestValidate.() -> Unit) {
        ExplorerActivityTestValidate().apply(func)
    }

    @get:Rule
    val activityRule = ActivityTestRule(ExplorerActivity::class.java, false, false)

    private lateinit var mockWebServer: MockWebServer
    private val appDatabase: AppDatabase =
        AppDatabase.getInstance(InstrumentationRegistry.getInstrumentation().targetContext)

    @Before
    fun setup() {
        AppDatabase.TEST_MODE = true
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        RetrofitServiceProvider.TEST_URL = mockWebServer.url("/").toString()
    }

    @After
    fun shutdown() {
        mockWebServer.shutdown()
        appDatabase.clearAllTables()
    }

    @Test
    fun startActivity_mockingFullListResponse_hasNineItems_andEmptyStateGone() {
        prepare {
            mockRepositoryCallSucess()
            startActivity()
        }
        validate {
            emptyStateIsGone()
            repositoryDetailListIsVisible()
            repositoryDetailListIsFull()
            loadingIsGone()
        }
    }

    @Test
    fun startActivity_mockingErrorResponse_hasNoItems_andEmptyStateVisible() {
        prepare {
            mockRepositoryCallError()
            startActivity()
        }
        validate {
            repositoryDetailListIsGone()
            repositoryDetailListIsEmpty()
            emptyStateIsVisible()
            loadingIsGone()
        }
    }

    @Test
    fun scrollToLastItem_mockingFullListResponseThenNoResponse_shouldLoadNextPage() {
        prepare {
            mockRepositoryCallSucess()
            mockRepositoryCallNoResponse()
            startActivity()
        }
        execute {
            scrollToLastItemAndMore()
        }
        validate {
            repositoryDetailListIsVisible()
            emptyStateIsGone()
            loadingIsVisible()
        }
    }

    @Test
    fun swipeDown_mockingFullListResponseThenNoResponse_shouldRefresh() {
        prepare {
            mockRepositoryCallSucess()
            mockRepositoryCallNoResponse()
            startActivity()
        }
        execute {
            swipeDown()
        }
        validate {
            swipeRefreshIsRefreshing()
        }
    }
}