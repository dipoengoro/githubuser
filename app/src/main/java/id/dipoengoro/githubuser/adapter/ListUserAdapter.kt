package id.dipoengoro.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.dipoengoro.githubuser.databinding.ItemUserBinding
import id.dipoengoro.githubuser.model.User

class ListUserAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<User, ListUserAdapter.ListUserViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.username == newItem.username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder =
        ListUserViewHolder.from(parent)

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        holder.apply {
            getItem(position).let {
                itemView.setOnClickListener { _ -> onClickListener.onClick(it) }
                bind(it)
            }
        }
    }

    class ListUserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: User) {
            binding.apply {
                user = userItem
            }
        }

        companion object {
            fun from(parent: ViewGroup): ListUserViewHolder =
                ListUserViewHolder(
                    ItemUserBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }
    }

    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }
}