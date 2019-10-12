package com.pinkyra.kotlinrepoexplorer.feature.explorer.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(
    private val linearLayoutManager: LinearLayoutManager,
    private val loadNewPage: () -> Unit
) : RecyclerView.OnScrollListener() {
    private var isLoadingNewData = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (isLoadingNewData) return

        val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
        val totalItemCount = linearLayoutManager.itemCount
        if (totalItemCount == lastVisibleItemPosition + 1) {
            isLoadingNewData = true
            loadNewPage()
            isLoadingNewData = false
        }
    }
}
