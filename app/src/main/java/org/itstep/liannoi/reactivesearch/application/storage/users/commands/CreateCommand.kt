package org.itstep.liannoi.reactivesearch.application.storage.users.commands

import org.itstep.liannoi.reactivesearch.application.storage.users.models.User

class CreateCommand constructor(val user: User) {

    interface Handler {

        fun onUserCreatedSuccess()

        fun onUserCreatedError(throwable: Throwable)
    }
}
