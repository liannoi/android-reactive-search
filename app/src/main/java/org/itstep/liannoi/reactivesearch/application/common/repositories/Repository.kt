package org.itstep.liannoi.reactivesearch.application.common.repositories

interface Repository<TCreateRequest, TCreateHandler,
        TAllRequest, TAllHandler,
        TIdRequest, TIdHandler,
        TUpdateRequest, TUpdateHandler,
        TDeleteRequest, TDeleteHandler> {

    fun create(request: TCreateRequest, handler: TCreateHandler)

    fun getAll(request: TAllRequest, handler: TAllHandler)

    fun getById(request: TIdRequest, handler: TIdHandler)

    fun update(request: TUpdateRequest, handler: TUpdateHandler)

    fun delete(request: TDeleteRequest, handler: TDeleteHandler)
}
