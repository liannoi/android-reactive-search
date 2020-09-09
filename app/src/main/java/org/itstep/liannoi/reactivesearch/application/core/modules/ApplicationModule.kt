package org.itstep.liannoi.reactivesearch.application.core.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.itstep.liannoi.reactivesearch.application.common.storage.LocalDataSource
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User
import org.itstep.liannoi.reactivesearch.application.storage.users.sources.UsersLocalDataSource
import org.itstep.liannoi.reactivesearch.infrastructure.persistence.ReactiveDatabase
import java.util.concurrent.Executors
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class UsersLocalSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class UsersRemoteSource

    @Singleton
    @UsersLocalSource
    @Provides
    fun provideUsersLocalDataSource(database: ReactiveDatabase): LocalDataSource<User, Int> =
        UsersLocalDataSource(database.userDao())

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ReactiveDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            ReactiveDatabase::class.java,
            "reactivesearch.db"
        ).build()
}
