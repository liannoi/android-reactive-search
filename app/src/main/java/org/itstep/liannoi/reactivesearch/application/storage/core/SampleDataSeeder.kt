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

    override fun seedAll(notification: Notification) {
        usersRepository.getAll(ListQuery(), ListQueryHandler(notification))
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    private fun seedUsers(notification: Notification) {
        Flowable.range(0, 1000)
            .map { User(firstName = UUID.randomUUID().toString()) }
            .map { usersRepository.create(CreateCommand(it), CreateCommand.NotificationHandler()) }
            .subscribe(
                { notification.onUsersSeedingSuccess() },
                { notification.onUsersSeedingError(it) }
            ).addTo(disposable)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Nested members
    ///////////////////////////////////////////////////////////////////////////

    interface Notification {

        fun onUsersSeedingSuccess()

        fun onUsersSeedingError(throwable: Throwable)

        fun onUsersSeedingSkip()
    }

    open class NotificationHandler : Notification {

        override fun onUsersSeedingSuccess() {
            /* no-op */
        }

        override fun onUsersSeedingError(throwable: Throwable) {
            /* no-op */
        }

        override fun onUsersSeedingSkip() {
            /* no-op */
        }
    }

    private inner class ListQueryHandler constructor(
        private val notification: Notification
    ) : ListQuery.NotificationHandler() {

        override fun onUsersFetchedSuccess(users: List<User>) {
            if (users.isNotEmpty()) {
                notification.onUsersSeedingSkip()
                return
            }

            seedUsers(notification)
        }
    }
}
