package org.itstep.liannoi.reactivesearch.presentation.users

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.itstep.liannoi.reactivesearch.application.common.interfaces.UsersRepository
import org.itstep.liannoi.reactivesearch.application.storage.core.SampleDataSeeder
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User
import org.itstep.liannoi.reactivesearch.application.storage.users.queries.ListQuery

class UsersViewModel @ViewModelInject constructor(
    private val usersRepository: UsersRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val _users: MutableLiveData<List<User>> = MutableLiveData()
    val users: LiveData<List<User>> = _users

    init {
        SampleDataSeeder(usersRepository, disposable).seedAll(SampleDataSeederHandler())
    }

    ///////////////////////////////////////////////////////////////////////////
    // Dispose
    ///////////////////////////////////////////////////////////////////////////

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        disposable.dispose()
        usersRepository.stop()
        usersRepository.destroy()
    }

    companion object {
        private var TAG: String = this::class.simpleName!!
    }

    ///////////////////////////////////////////////////////////////////////////
    // Nested classes
    ///////////////////////////////////////////////////////////////////////////

    private inner class ListQueryHandler : ListQuery.Handler {

        override fun onUsersFetchedSuccess(users: List<User>) {
            this@UsersViewModel._users.postValue(users)
        }

        override fun onUsersFetchedError(throwable: Throwable) {
            /* no-op */
        }
    }

    private inner class SampleDataSeederHandler : SampleDataSeeder.Handler {

        override fun onUsersSeedingSuccess() {
            getAll()
        }

        override fun onUsersSeedingSkip() {
            getAll()
        }

        override fun onUsersSeedingError(throwable: Throwable) {
            /* no-op */
        }

        ///////////////////////////////////////////////////////////////////////////
        // Helpers
        ///////////////////////////////////////////////////////////////////////////

        private fun getAll() {
            usersRepository.getAll(ListQuery(), ListQueryHandler())
        }
    }
}
