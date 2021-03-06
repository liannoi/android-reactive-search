package org.itstep.liannoi.reactivesearch.application.core.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.itstep.liannoi.reactivesearch.application.common.interfaces.UsersRepository
import org.itstep.liannoi.reactivesearch.application.common.storage.LocalDataSource
import org.itstep.liannoi.reactivesearch.application.storage.users.DefaultUsersRepository
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UsersRepositoryModule {

    @Singleton
    @Provides
    fun provideUsersRepository(
        @ApplicationModule.UsersLocalSource localDataSource: LocalDataSource<User, Int>
    ): UsersRepository = DefaultUsersRepository(localDataSource)
}
