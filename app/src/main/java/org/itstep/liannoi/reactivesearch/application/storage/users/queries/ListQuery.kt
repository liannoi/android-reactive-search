package org.itstep.liannoi.reactivesearch.application.storage.users.queries

import org.itstep.liannoi.reactivesearch.application.storage.users.models.User

class ListQuery {

    interface Notification {

        fun onUsersFetchedSuccess(users: List<User>)

        fun onUsersFetchedError(throwable: Throwable)
    }

    open class NotificationHandler : Notification {

        override fun onUsersFetchedSuccess(users: List<User>) {
            /* no-op */
        }

        override fun onUsersFetchedError(throwable: Throwable) {
            /* no-op */
        }
    }
}
