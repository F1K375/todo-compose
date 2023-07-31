package kf.todo.app.domain

import kf.todo.app.data.room.ToDoEntity
import java.time.LocalDateTime

data class CreateToDoDomain(
    val title: String,
    val description: String,
    val dueDate: LocalDateTime
)

data class ToDoDomain(
    val id: Int,
    val title: String,
    val description: String,
    val isDone: Boolean,
    val dueDate: LocalDateTime
){
    val isOverDeadline: Boolean
    get() = dueDate.isBefore(LocalDateTime.now())

    val shortDescription: String
    get() = if (description.length > 20) description.take(20) + "...." else description
}

val ToDoEntity.asDomain
    get() = ToDoDomain(id, title, description, isDone, dueDate)

val ToDoDomain.asEntity
    get() = ToDoEntity(id, title, description, isDone, dueDate)

val CreateToDoDomain.asEntity
    get() = ToDoEntity(title = title, description = description, dueDate = dueDate)
