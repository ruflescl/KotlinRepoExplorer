package com.pinkyra.kotlinrepoexplorer.feature.explorer.view

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.pinkyra.kotlinrepoexplorer.R
import com.pinkyra.kotlinrepoexplorer.feature.explorer.view.Constants.EMPTY_LIST_COUNT
import com.pinkyra.kotlinrepoexplorer.feature.explorer.view.Constants.FULL_LIST_COUNT
import com.pinkyra.kotlinrepoexplorer.feature.explorer.view.Constants.REPOSITORY_WITH_ITEMS_ASSET_PATH
import com.pinkyra.kotlinrepoexplorer.feature.explorer.view.Constants.emptyState
import com.pinkyra.kotlinrepoexplorer.feature.explorer.view.Constants.loading
import com.pinkyra.kotlinrepoexplorer.feature.explorer.view.Constants.repositoryDetailList
import com.pinkyra.kotlinrepoexplorer.feature.explorer.view.Constants.swipeRefresh
import com.pinkyra.utils.AssetFileReader
import com.pinkyra.utils.hasItemCount
import com.pinkyra.utils.isRefreshing
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy

private object Constants {
    const val FULL_LIST_COUNT = 9
    const val EMPTY_LIST_COUNT = 0

    const val REPOSITORY_WITH_ITEMS_ASSET_PATH = "list_with_nine_repo_details.json"

    fun repositoryDetailList(): ViewInteraction = onView(withId(R.id.rvRepositoryDetails))
    fun emptyState(): ViewInteraction = onView(withId(R.id.clEmptyState))
    fun loading(): ViewInteraction = onView(withId(R.id.pbLoadingNextPage))
    fun swipeRefresh(): ViewInteraction = onView(withId(R.id.srlRoot))
}

class ExplorerActivityTestPrepare(
    private val rule: ActivityTestRule<ExplorerActivity>,
    private val server: MockWebServer
) {
    fun startActivity() {
        rule.launchActivity(Intent())
        Thread.sleep(200) // Couldn't import EspressoIdlingResources...
    }

    fun mockRepositoryCallSucess() {
        server.enqueue(createSucessResponse())
    }

    fun mockRepositoryCallError() {
        server.enqueue(createErrorResponse())
    }

    fun mockRepositoryCallNoResponse() {
        server.enqueue(createNoResponse())
    }

    private fun createNoResponse(): MockResponse = MockResponse().setResponseCode(200)
        .setSocketPolicy(SocketPolicy.NO_RESPONSE)
        .setBody(AssetFileReader().readFile(REPOSITORY_WITH_ITEMS_ASSET_PATH))

    private fun createErrorResponse(): MockResponse =
        MockResponse().setResponseCode(400)

    private fun createSucessResponse(): MockResponse =
        MockResponse().setResponseCode(200)
            .setBody(AssetFileReader().readFile(REPOSITORY_WITH_ITEMS_ASSET_PATH))
}

class ExplorerActivityTestExecute {
    fun scrollToLastItemAndMore() {
        repositoryDetailList().perform(
            RecyclerViewActions.scrollToPosition<RepositoryDetailsAdapter.RepositoryViewHolder>(
                FULL_LIST_COUNT - 1
            )
        )
        repositoryDetailList().perform(ViewActions.swipeUp())
        Thread.sleep(200) // Couldn't import EspressoIdlingResources...
    }

    fun swipeDown() {
        repositoryDetailList().perform(ViewActions.swipeDown())
    }
}

class ExplorerActivityTestValidate {
    fun repositoryDetailListIsFull() = repositoryDetailList().hasItemCount(FULL_LIST_COUNT)
    fun repositoryDetailListIsEmpty() = repositoryDetailList().hasItemCount(EMPTY_LIST_COUNT)
    fun repositoryDetailListIsVisible() =
        repositoryDetailList().check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    fun repositoryDetailListIsGone() =
        repositoryDetailList().check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    fun emptyStateIsVisible() =
        emptyState().check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    fun emptyStateIsGone() =
        emptyState().check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    fun loadingIsVisible() =
        loading().check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    fun loadingIsGone() =
        loading().check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    fun swipeRefreshIsRefreshing() =
        swipeRefresh().isRefreshing()
}