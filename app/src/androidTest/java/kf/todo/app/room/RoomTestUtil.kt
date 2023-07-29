package kf.todo.app.room

import kf.todo.app.data.room.ToDoEntity
import java.time.LocalDateTime

object RoomTestUtil {

    fun dummyTodo () = ToDoEntity(
        title = "new Task",
        description = "urgent, need to be execute immediately",
        dueDate = LocalDateTime.of(2023,4,26,10,0,0),
    )

    fun dummyTodos (size: Int) = Array(size) { dummyTodo() }

}
