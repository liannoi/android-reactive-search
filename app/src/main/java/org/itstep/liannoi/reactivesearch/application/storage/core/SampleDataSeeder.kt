package org.itstep.liannoi.reactivesearch.application.storage.core

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.itstep.liannoi.reactivesearch.application.common.interfaces.UsersRepository
import org.itstep.liannoi.reactivesearch.application.common.storage.Seeder
import org.itstep.liannoi.reactivesearch.application.storage.users.commands.CreateCommand
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User
import org.itstep.liannoi.reactivesearch.application.storage.users.queries.ListQuery
import java.util.*

class SampleDataSeeder constructor(
    private val usersRepository: UsersRepository,
    private val disposable: CompositeDisposable
) : Seeder {

    override fun seedAll(handler: Handler) {
        usersRepository.getAll(ListQuery(), ListQueryHandler(handler))
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    private fun seedUsers(handler: Handler) {
        Flowable.range(0, 1000)
            .map { User(firstName = UUID.randomUUID().toString()) }
            .map { usersRepository.create(CreateCommand(it), CreateCommandHandler()) }
            .subscribe(
                { handler.onUsersSeedingSuccess() },
                { handler.onUsersSeedingError(it) }
            ).addTo(disposable)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Nested members
    ///////////////////////////////////////////////////////////////////////////

    interface Handler {

        fun onUsersSeedingSuccess()

        fun onUsersSeedingError(throwable: Throwable)

        fun onUsersSeedingSkip()
    }

    private class CreateCommandHandler : CreateCommand.Handler {

        override fun onUserCreatedSuccess() {
            /* no-op */
        }

        override fun onUserCreatedError(throwable: Throwable) {
            /* no-op */
        }
    }

    private inner class ListQueryHandler constructor(
        private val handler: Handler
    ) : ListQuery.Handler {

        override fun onUsersFetchedSuccess(users: List<User>) {
            if (users.isNotEmpty()) {
                handler.onUsersSeedingSkip()
                return
            }

            seedUsers(handler)
        }

        override fun onUsersFetchedError(throwable: Throwable) {
            /* no-op */
        }
    }
}
