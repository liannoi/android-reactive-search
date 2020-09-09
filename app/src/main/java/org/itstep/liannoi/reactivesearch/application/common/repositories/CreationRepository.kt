package org.itstep.liannoi.reactivesearch.application.common.repositories

interface CreationRepository<TCreateRequest, TCreateHandler> {

    fun create(request: TCreateRequest, handler: TCreateHandler)
}
