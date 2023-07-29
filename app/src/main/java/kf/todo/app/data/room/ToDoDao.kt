package kf.todo.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDo(vararg todo: ToDoEntity): List<Long>

    @Query("SELECT * FROM todo")
    fun getToDos(): Flow<List<ToDoEntity>>

    @Query("SELECT * FROM todo WHERE id=:id")
    suspend fun getToDoById(id: Int): ToDoEntity

    @Query("DELETE FROM todo where id=:id")
    suspend fun removeTodo(id: Int)

}
