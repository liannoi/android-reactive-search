package org.itstep.liannoi.reactivesearch.application.common.interfaces

import org.itstep.liannoi.reactivesearch.application.common.storage.Disposable
import org.itstep.liannoi.reactivesearch.application.storage.users.commands.CreateCommand
import org.itstep.liannoi.reactivesearch.application.storage.users.queries.ListQuery

interface UsersRepository : Disposable {

    fun getAll(request: ListQuery, notification: ListQuery.Notification)

    fun create(request: CreateCommand, notification: CreateCommand.Notification)
}
