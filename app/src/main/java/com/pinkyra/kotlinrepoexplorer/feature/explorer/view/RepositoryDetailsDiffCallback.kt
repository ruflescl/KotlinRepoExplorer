package com.pinkyra.kotlinrepoexplorer.feature.explorer.view

import androidx.recyclerview.widget.DiffUtil
import com.pinkyra.kotlinrepoexplorer.model.RepositoryDetail

class RepositoryDetailsDiffCallback(
    private val oldList: List<RepositoryDetail>,
    private val newList: List<RepositoryDetail>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean =
        oldList[oldPosition] == newList[newPosition]
}