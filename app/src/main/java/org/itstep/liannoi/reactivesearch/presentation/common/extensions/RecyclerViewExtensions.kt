package org.itstep.liannoi.reactivesearch.presentation.common.extensions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User
import org.itstep.liannoi.reactivesearch.presentation.users.UsersAdapter

@BindingAdapter("app:items")
fun RecyclerView.adapt(items: List<User>?) {
    items?.let { (adapter as UsersAdapter).submitList(items) }
}
