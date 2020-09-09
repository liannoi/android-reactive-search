package org.itstep.liannoi.reactivesearch.presentation.users

import androidx.recyclerview.widget.DiffUtil
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User

class UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}
