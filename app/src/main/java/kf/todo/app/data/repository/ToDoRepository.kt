package kf.todo.app.data.repository

import kf.todo.app.data.ResultData
import kf.todo.app.domain.CreateToDoDomain
import kf.todo.app.domain.ToDoDomain
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun createToDo(task: CreateToDoDomain): ResultData<Int>
    suspend fun updateToDo(task: ToDoDomain): ResultData<Boolean>
    suspend fun removeTodo(id:Int): ResultData<Boolean>

    fun getToDoById(id: Int): Flow<ResultData<ToDoDomain>>
    fun getAllToDo(): Flow<ResultData<List<ToDoDomain>>>


}
