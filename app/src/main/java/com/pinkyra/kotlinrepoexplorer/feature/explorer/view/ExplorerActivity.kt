package com.pinkyra.kotlinrepoexplorer.feature.explorer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinkyra.kotlinrepoexplorer.CustomApplication
import com.pinkyra.kotlinrepoexplorer.R
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.ExplorerRoomRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.ExplorerRetrofitRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.usecase.KotlinExplorerUseCase
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerInteractor
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerViewModel
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerViewModelFactory
import kotlinx.android.synthetic.main.activity_explorer.*

class ExplorerActivity : AppCompatActivity() {
    private val viewModel: ExplorerViewModel by lazy {
        ViewModelProviders.of(
            this,
            ExplorerViewModelFactory(
                KotlinExplorerUseCase(
                    ExplorerRetrofitRepository(),
                    ExplorerRoomRepository((application as CustomApplication).database)
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
    }

    private fun setupSwipeRefreshLayout() {
        srlRoot.setOnRefreshListener {
            viewModel.interpret(ExplorerInteractor.ReloadData(true))
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(this, Observer {
            adapter.updateItems(it)
            srlRoot.isRefreshing = false
        })
    }
}
