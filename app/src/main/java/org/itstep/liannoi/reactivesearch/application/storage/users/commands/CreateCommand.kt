package org.itstep.liannoi.reactivesearch.application.storage.users.commands

import org.itstep.liannoi.reactivesearch.application.storage.users.models.User

class CreateCommand constructor(val user: User) {

    interface Notification {

        fun onUserCreatedSuccess()

        fun onUserCreatedError(throwable: Throwable)
    }

    class NotificationHandler : Notification {

        override fun onUserCreatedSuccess() {
            /* no-op */
        }

        override fun onUserCreatedError(throwable: Throwable) {
            /* no-op */
        }
    }
}
