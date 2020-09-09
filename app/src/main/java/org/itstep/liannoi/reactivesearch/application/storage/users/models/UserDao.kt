package org.itstep.liannoi.reactivesearch.application.storage.users.models

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Maybe
import org.itstep.liannoi.reactivesearch.application.common.storage.BaseDao

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT U.UserId, U.FirstName FROM Users AS U")
    fun getAll(): Maybe<List<User>>
}
