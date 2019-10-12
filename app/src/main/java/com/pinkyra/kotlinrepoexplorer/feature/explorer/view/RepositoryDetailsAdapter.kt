package com.pinkyra.kotlinrepoexplorer.feature.explorer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pinkyra.kotlinrepoexplorer.R
import com.pinkyra.kotlinrepoexplorer.databinding.ListItemRepositoryDetailBinding
import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail
import com.squareup.picasso.Picasso

class RepositoryDetailsAdapter(
    private var repositoryDetails: ArrayList<RepositoryDetail>
) : RecyclerView.Adapter<RepositoryDetailsAdapter.RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemRepositoryDetailBinding.inflate(layoutInflater, parent, false)
        return RepositoryViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) =
        holder.bind(repositoryDetails[position])

    override fun getItemCount(): Int = repositoryDetails.size

    fun updateItems(newRepositoryDetails: List<RepositoryDetail>) {
        val diffResult = DiffUtil.calculateDiff(
            RepositoryDetailsDiffCallback(
                repositoryDetails,
                newRepositoryDetails
            )
        )
        repositoryDetails.clear()
        repositoryDetails.addAll(newRepositoryDetails)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class RepositoryViewHolder(private val binding: ListItemRepositoryDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repositoryDetail: RepositoryDetail) {
            binding.item = repositoryDetail

            Picasso.get()
                .load(repositoryDetail.owner.avatarUrl)
                .placeholder(R.drawable.ic_placeholder_black)
                .into(binding.imgAvatar)

            binding.executePendingBindings()
        }
    }
}