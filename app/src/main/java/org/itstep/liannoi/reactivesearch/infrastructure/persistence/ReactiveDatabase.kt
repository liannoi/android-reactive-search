package org.itstep.liannoi.reactivesearch.infrastructure.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import org.itstep.liannoi.reactivesearch.application.storage.users.models.User
import org.itstep.liannoi.reactivesearch.application.storage.users.models.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class ReactiveDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
