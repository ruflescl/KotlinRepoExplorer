package com.pinkyra.kotlinrepoexplorer.feature.explorer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pinkyra.kotlinrepoexplorer.R
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.remote.ExplorerRetrofitRepository
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerInteractor
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerViewModel
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerViewModelFactory
import com.pinkyra.kotlinrepoexplorer.feature.explorer.viewmodel.ExplorerViewState
import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail
import kotlinx.android.synthetic.main.activity_explorer.*

class ExplorerActivity : AppCompatActivity() {
    private val viewModel: ExplorerViewModel by lazy {
        ViewModelProviders.of(this, ExplorerViewModelFactory(ExplorerRetrofitRepository()))
            .get(ExplorerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorer)

        setupObservers()

        viewModel.interpret(ExplorerInteractor.FetchData(true))
    }

    private fun setupObservers() {
        viewModel.viewState.observe(this, Observer {
            when (it) {
                is ExplorerViewState.FetchSucessful -> updateList(it.repositoryDetail)
            }
        })
    }

    private fun updateList(repositoryDetail: List<RepositoryDetail>) {
        txtDetails.text = buildResultText(repositoryDetail)
    }

    private fun buildResultText(repositoryDetail: List<RepositoryDetail>): String {
        val stringBuilder = StringBuilder()

        repositoryDetail.forEach {
            stringBuilder.append("Nome: ").append(it.name)
            stringBuilder.append(" / Forks: ").append(it.forksCount)
            stringBuilder.append(" / Stars: ").append(it.stargazersCount)
            stringBuilder.append("\n----------------\n")
        }

        return stringBuilder.toString()
    }
}
