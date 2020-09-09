package org.itstep.liannoi.reactivesearch.application.storage.users.sources

/**
 * This interface doesn't need to be implemented in the form of any class for it, the Proxy class
 * will be created by Retrofit itself at runtime. It is implemented according to the conventions of
 * the library through which we go to a remote data source.
 *
 * In this example, this interface acts as a marker interface, since data isn't taken from a remote
 * source.
 */
interface UsersRemoteDataSource
