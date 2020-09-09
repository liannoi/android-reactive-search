package org.itstep.liannoi.reactivesearch.application.storage.users.queries

import org.itstep.liannoi.reactivesearch.application.storage.users.models.User

class ListQuery {

    interface Handler {

        fun onUsersFetchedSuccess(users: List<User>)

        fun onUsersFetchedError(throwable: Throwable)
    }
}
