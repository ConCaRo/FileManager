package com.filemanager.explorer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filemanager.core.entity.FileModel
import com.filemanager.features.explorer.databinding.ItemFileBinding

class ListFileAdapter(val click: (FileModel) -> Unit) :
    ListAdapter<FileModel, RecyclerView.ViewHolder>(ListFileDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ListFileViewHolder).bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListFileViewHolder(
            ItemFileBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class ListFileViewHolder(
        private val binding: ItemFileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(_item: FileModel) {
            binding.apply {
                item = _item
                executePendingBindings()
            }
            itemView.setOnClickListener {
                click(_item)
            }
        }
    }
}

private class ListFileDiffCallback : DiffUtil.ItemCallback<FileModel>() {

    override fun areItemsTheSame(oldItem: FileModel, newItem: FileModel): Boolean {
        return oldItem.path == newItem.path && oldItem.name != newItem.name
    }

    override fun areContentsTheSame(oldItem: FileModel, newItem: FileModel): Boolean {
        return oldItem == newItem
    }
}