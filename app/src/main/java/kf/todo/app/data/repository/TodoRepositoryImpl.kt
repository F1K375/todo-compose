package kf.todo.app.data.repository

import kf.todo.app.data.ResultData
import kf.todo.app.data.room.ToDoDao
import kf.todo.app.domain.CreateToDoDomain
import kf.todo.app.domain.ToDoDomain
import kf.todo.app.domain.asDomain
import kf.todo.app.domain.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class TodoRepositoryImpl (
    private val toDoDao: ToDoDao
): ToDoRepository {
    override suspend fun createToDo(task: CreateToDoDomain): ResultData<Int> {
        return try {
            val createdToDoId = toDoDao.addToDo(task.asEntity).first()
            ResultData.Success(data = createdToDoId.toInt())
        }catch (error: Exception){
            ResultData.Failed(
                exception = error,
                message = "Failed to create todo"
            )
        }
    }

    override suspend fun updateToDo(task: ToDoDomain): ResultData<Boolean> {
        return try {
            toDoDao.updateToDo(task.asEntity)
            ResultData.Success(data = true)
        }catch (error: Exception){
            ResultData.Failed(exception = error, message = "Failed to update todo with id ${task.id}")
        }
    }

    override suspend fun removeTodo(id: Int): ResultData<Boolean> {
        return try {
            toDoDao.removeTodo(id)
            ResultData.Success(data = true)
        }catch (error: Exception){
            ResultData.Failed(exception = error, message = "Failed to remove todo with id $id")
        }
    }

    override fun getToDoById(id: Int): Flow<ResultData<ToDoDomain>> {
        return toDoDao.getToDoById(id)
            .map { ResultData.Success(data = it.asDomain) as ResultData<ToDoDomain> }
            .catch {error->
                emit(
                    ResultData.Failed(
                        exception = IOException(error.localizedMessage ?: "Error get detail"),
                        message = "Failed to get detail todo with id $id"
                    )
                )
            }
    }

    override fun getAllToDo(): Flow<ResultData<List<ToDoDomain>>> {
        return toDoDao.getToDos()
            .map {list->
                ResultData.Success(data = list.asSequence().map { it.asDomain }.toList()) as ResultData<List<ToDoDomain>>
            }.catch {error->
                emit(
                    ResultData.Failed(
                        exception = IOException(error.localizedMessage ?: "Error get all todo"),
                        message = "Failed to get all todos"
                    )
                )
            }
    }
}
