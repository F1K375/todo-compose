package kf.todo.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDo(todo: ToDoEntity)

    @Query("SELECT * FROM todo")
    fun getToDo(): Flow<List<ToDoEntity>>

    @Query("DELETE FROM todo where id=:id")
    suspend fun removeTodo(id: Int)

}
