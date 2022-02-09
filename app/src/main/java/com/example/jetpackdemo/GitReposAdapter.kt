package com.example.jetpackdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.jetpackdemo.bean.GitRepoItem
import com.example.jetpackdemo.databinding.ItemRepoLayoutBinding
import dagger.hilt.android.qualifiers.ActivityContext
import java.io.File
import javax.inject.Inject

class GitReposAdapter @Inject constructor(@ActivityContext var context: Context) :
    ListAdapter<GitRepoItem, GitReposAdapter.GitRepoHolder>(REPO_COMPARATOR) {

    var saveClick: ((repo: GitRepoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoHolder {
        return GitRepoHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_repo_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GitRepoHolder, position: Int) {
        val repo = getItem(position)
        repo?.let {
            if (holder is GitRepoHolder) {
                holder.bind(repo)
            }
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GitRepoItem>() {
            override fun areItemsTheSame(oldItem: GitRepoItem, newItem: GitRepoItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GitRepoItem, newItem: GitRepoItem): Boolean =
                oldItem == newItem
        }
    }


    inner class GitRepoHolder(private val binding: ItemRepoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: GitRepoItem) {
            binding.tvRepoName.text = repo.name
            binding.tvOwnerName.text = repo.owner.login

            binding.tvRepoDescription.text = repo.description
            binding.tvUpdated.text = repo.updated_at
            Glide.with(binding.root.context).load(repo.owner.avatar_url).apply(
                RequestOptions.bitmapTransform(RoundedCorners(10))
            ).into(binding.imageAvatar)

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, repo.archive_url, Toast.LENGTH_SHORT).show()

            }

            binding.imgSave.setOnClickListener {
                saveClick?.invoke(repo)
            }

        }

    }

    fun setOnSaveClick(saveClick: (repo: GitRepoItem) -> Unit) {
        this.saveClick = saveClick
    }

}

