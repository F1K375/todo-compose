package kf.todo.app.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDo(vararg todo: ToDoEntity): List<Long>

    @Update
    suspend fun updateToDo(todo: ToDoEntity)

    @Query("SELECT * FROM todo WHERE id=:id")
    fun getToDoById(id: Int): Flow<ToDoEntity>
    @Query("SELECT * FROM todo")
    fun getToDos(): Flow<List<ToDoEntity>>


    @Query("DELETE FROM todo where id=:id")
    suspend fun removeTodo(id: Int)

}
