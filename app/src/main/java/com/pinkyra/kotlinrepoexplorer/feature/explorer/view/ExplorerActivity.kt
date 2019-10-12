package com.pinkyra.kotlinrepoexplorer.feature.explorer.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinkyra.kotlinrepoexplorer.R
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.ExplorerRoomRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.ExplorerRetrofitRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase.KotlinExplorerUseCase
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerInteractor
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerViewModel
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerViewModelFactory
import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail
import com.pinkyra.kotlinrepoexplorer.room.AppDatabase
import kotlinx.android.synthetic.main.activity_explorer.*

class ExplorerActivity : AppCompatActivity() {
    private val viewModel: ExplorerViewModel by lazy {
        ViewModelProviders.of(
            this,
            ExplorerViewModelFactory(
                KotlinExplorerUseCase(
                    ExplorerRetrofitRepository(),
                    ExplorerRoomRepository(AppDatabase.getInstance(applicationContext))
                )
            )
        ).get(ExplorerViewModel::class.java)
    }

    private lateinit var adapter: RepositoryDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorer)
        setupSwipeRefreshLayout()
        setupRepositoryDetailsList()
        setupObservers()
    }

    private fun setupRepositoryDetailsList() {
        val layoutManager = LinearLayoutManager(this)

        adapter = RepositoryDetailsAdapter(arrayListOf())
        rvRepositoryDetails.layoutManager = layoutManager
        rvRepositoryDetails.itemAnimator = DefaultItemAnimator()
        rvRepositoryDetails.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            rvRepositoryDetails.context,
            layoutManager.orientation
        )
        rvRepositoryDetails.addItemDecoration(dividerItemDecoration)

        rvRepositoryDetails.addOnScrollListener(
            InfiniteScrollListener(layoutManager) { fetchNewPage() }
        )
    }

    private fun fetchNewPage() {
        pbLoadingNextPage.visibility = View.VISIBLE
        viewModel.interpret(ExplorerInteractor.FetchNextPage(true))
    }

    private fun setupSwipeRefreshLayout() {
        srlRoot.setOnRefreshListener {
            viewModel.interpret(ExplorerInteractor.ReloadData(true))
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(this, Observer {
            updateList(it)
        })
    }

    private fun updateList(it: List<RepositoryDetail>) {
        adapter.updateItems(it)
        srlRoot.isRefreshing = false
        pbLoadingNextPage.visibility = View.GONE

        clEmptyState.visibility = when (adapter.itemCount) {
            0 -> View.VISIBLE
            else -> View.GONE
        }
        rvRepositoryDetails.visibility = when (adapter.itemCount) {
            0 -> View.GONE
            else -> View.VISIBLE
        }
    }
}
