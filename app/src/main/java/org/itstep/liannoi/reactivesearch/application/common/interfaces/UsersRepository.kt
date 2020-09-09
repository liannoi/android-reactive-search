package org.itstep.liannoi.reactivesearch.application.common.interfaces

import org.itstep.liannoi.reactivesearch.application.common.repositories.CreationRepository
import org.itstep.liannoi.reactivesearch.application.common.repositories.ReadOnlyRepository
import org.itstep.liannoi.reactivesearch.application.common.storage.Disposable
import org.itstep.liannoi.reactivesearch.application.storage.users.commands.CreateCommand
import org.itstep.liannoi.reactivesearch.application.storage.users.queries.ListQuery

interface UsersRepository : ReadOnlyRepository<ListQuery, ListQuery.Handler>,
    CreationRepository<CreateCommand, CreateCommand.Handler>,
    Disposable