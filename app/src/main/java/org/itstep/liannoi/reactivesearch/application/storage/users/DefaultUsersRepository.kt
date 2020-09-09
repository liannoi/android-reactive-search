package org.itstep.liannoi.reactivesearch.application.storage.users

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.itstep.liannoi.reactivesearch.application.common.interfaces.UsersRepository
import org.itstep.liannoi.reactivesearch.application.common.storage.LocalDataSource
import org.itstep.liannoi.reactivesearch.application.storage.users.commands.CreateCommand
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User
import org.itstep.liannoi.reactivesearch.application.storage.users.queries.ListQuery
import org.itstep.liannoi.reactivesearch.application.storage.users.sources.UsersRemoteDataSource

class DefaultUsersRepository constructor(
    private val usersLocalDataSource: LocalDataSource<User, Int>,
    private val usersRemoteDataSource: UsersRemoteDataSource? = null
) : UsersRepository {

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun create(request: CreateCommand, handler: CreateCommand.Handler) {
        Completable.fromAction { usersLocalDataSource.create(request.user) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { handler.onUserCreatedSuccess() },
                { handler.onUserCreatedError(it) }
            ).addTo(disposable)
    }

    override fun getAll(request: ListQuery, handler: ListQuery.Handler) {
        usersLocalDataSource.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { handler.onUsersFetchedSuccess(it) },
                { handler.onUsersFetchedError(it) }
            ).addTo(disposable)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Dispose
    ///////////////////////////////////////////////////////////////////////////

    override fun stop() {
        disposable.clear()
    }

    override fun destroy() {
        disposable.dispose()
    }
}
