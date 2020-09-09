package org.itstep.liannoi.reactivesearch.application.common.repositories

interface ReadOnlyRepository<TAllRequest, TAllHandler> {

    fun getAll(request: TAllRequest, handler: TAllHandler)
}
