package org.itstep.liannoi.reactivesearch.presentation.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.itstep.liannoi.reactivesearch.application.common.interfaces.UsersRepository
import org.itstep.liannoi.reactivesearch.application.storage.core.SampleDataSeeder
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User
import org.itstep.liannoi.reactivesearch.application.storage.users.queries.ListQuery

class UsersViewModel @ViewModelInject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val _users: MutableLiveData<List<User>> = MutableLiveData()
    val users: LiveData<List<User>> = _users

    init {
        SampleDataSeeder(usersRepository, disposable).seedAll(SampleDataSeederHandler())
    }

    fun filter(firstName: String) {
        usersRepository.getAll(ListQuery(), FilteringQueryHandler(firstName))
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

    ///////////////////////////////////////////////////////////////////////////
    // Companion object
    ///////////////////////////////////////////////////////////////////////////

    companion object {
        private var TAG: String = this::class.simpleName!!
    }

    ///////////////////////////////////////////////////////////////////////////
    // Nested classes
    ///////////////////////////////////////////////////////////////////////////

    private inner class FilteringQueryHandler constructor(
        private val firstName: String
    ) : ListQuery.NotificationHandler() {

        override fun onUsersFetchedSuccess(users: List<User>) {
            if (firstName.isEmpty()) {
                _users.value = users
                return
            }

            filter(users)
        }

        ///////////////////////////////////////////////////////////////////////////
        // Helpers
        ///////////////////////////////////////////////////////////////////////////

        private fun filter(users: List<User>) {
            Flowable.fromArray(users)
                .map { list: List<User> -> list.filter { it.firstName.startsWith(firstName) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _users.value = it }
                .addTo(disposable)
        }
    }

    private inner class ListQueryHandler : ListQuery.NotificationHandler() {

        override fun onUsersFetchedSuccess(users: List<User>) {
            this@UsersViewModel._users.postValue(users)
        }
    }

    private inner class SampleDataSeederHandler : SampleDataSeeder.NotificationHandler() {

        override fun onUsersSeedingSuccess() {
            getAll()
        }

        override fun onUsersSeedingSkip() {
            getAll()
        }

        ///////////////////////////////////////////////////////////////////////////
        // Helpers
        ///////////////////////////////////////////////////////////////////////////

        private fun getAll() {
            usersRepository.getAll(ListQuery(), ListQueryHandler())
        }
    }
}
