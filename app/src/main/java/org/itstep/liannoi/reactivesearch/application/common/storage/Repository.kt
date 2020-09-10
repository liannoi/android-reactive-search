package org.itstep.liannoi.reactivesearch.application.common.storage

interface Repository<TCreateRequest, TCreateNotification,
        TAllRequest, TAllNotification,
        TIdRequest, TIdNotification,
        TUpdateRequest, TUpdateNotification,
        TDeleteRequest, TDeleteNotification> {

    fun create(request: TCreateRequest, notification: TCreateNotification)

    fun getAll(request: TAllRequest, notification: TAllNotification)

    fun getById(request: TIdRequest, notification: TIdNotification)

    fun update(request: TUpdateRequest, notification: TUpdateNotification)

    fun delete(request: TDeleteRequest, notification: TDeleteNotification)
}
