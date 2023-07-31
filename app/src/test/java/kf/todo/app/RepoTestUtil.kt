package kf.todo.app

import kf.todo.app.data.room.ToDoEntity
import kf.todo.app.domain.CreateToDoDomain
import kf.todo.app.domain.ToDoDomain
import java.time.LocalDateTime

object RepoTestUtil {

    fun dummyToDoEntity(customId: Int? = null) =  ToDoEntity(
        id = customId ?: 1,
        title = "new Task",
        description = "urgent, need to be execute immediately",
        dueDate = LocalDateTime.of(2023,4,26,10,0,0),
    )
    fun dummyListToDoEntity() = Array(10) { dummyToDoEntity() }.mapIndexed { index, toDoEntity ->  toDoEntity.copy(id = index+1) }

    fun dummyCreateToDoDomain() = CreateToDoDomain(title = "new todo", description = "todo description", dueDate = LocalDateTime.now())
    fun dummyToDoDomain() = ToDoDomain(
        id = 1,
        title = "todo title",
        description = "todo description",
        isDone = false,
        dueDate = LocalDateTime.now()
    )

}
