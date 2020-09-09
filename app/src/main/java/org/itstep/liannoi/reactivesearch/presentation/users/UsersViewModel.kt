package org.itstep.liannoi.reactivesearch.presentation.users

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.itstep.liannoi.reactivesearch.application.common.interfaces.UsersRepository
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User
import org.itstep.liannoi.reactivesearch.application.storage.users.queries.ListQuery

class UsersViewModel @ViewModelInject constructor(
    private val usersRepository: UsersRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(),
    ListQuery.Handler {

    private val _size: MutableLiveData<Int> = MutableLiveData<Int>()
    val size: LiveData<Int> = _size

    init {
        usersRepository.getAll(ListQuery(), this)
    }

    override fun onUsersFetchedSuccess(users: List<User>) {
        _size.value = users.size
    }

    override fun onUsersFetchedError(throwable: Throwable) {
        Log.d(TAG, "onUsersFetchedError: " + throwable.message!!)
    }

    companion object {
        private var TAG: String = this::class.simpleName!!
    }
}
