package com.filemanager.explorer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filemanager.common.ui.extensions.setTint
import com.filemanager.core.entity.FileModel
import com.filemanager.features.explorer.databinding.ItemBreadcrumbBinding

class BreadcrumbAdapter(val click: (FileModel) -> Unit) :
    ListAdapter<FileModel, RecyclerView.ViewHolder>(BreadcrumbDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as BreadcrumbViewHolder).bind(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BreadcrumbViewHolder(
            ItemBreadcrumbBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class BreadcrumbViewHolder(
        private val binding: ItemBreadcrumbBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(_item: FileModel, position: Int) {
            binding.apply {
                item = _item
                executePendingBindings()
            }
            itemView.setOnClickListener {
                if (position != currentList.size - 1) {
                    click(_item)
                }
            }
            // TODO: dynamic feature module show "Resource not found: color colorPrimary" when using condition for data binding inside xml
            //  data binding does not understand com.filemanager.R vs com.filemanager.explorer.R ?
            if (position == currentList.size - 1) {
                binding.imvArrow.setTint(com.filemanager.R.color.colorPrimary)
                binding.nameTextView.setTextColor(binding.nameTextView.resources.getColor(com.filemanager.R.color.colorPrimary))
            } else {
                binding.imvArrow.setTint(com.filemanager.R.color.black)
                binding.nameTextView.setTextColor(binding.nameTextView.resources.getColor(com.filemanager.R.color.black))

            }
        }
    }
}

private class BreadcrumbDiffCallback : DiffUtil.ItemCallback<FileModel>() {

    override fun areItemsTheSame(oldItem: FileModel, newItem: FileModel): Boolean {
        return oldItem.path == newItem.path && oldItem.name != newItem.name
    }

    override fun areContentsTheSame(oldItem: FileModel, newItem: FileModel): Boolean {
        return oldItem == newItem
    }
}