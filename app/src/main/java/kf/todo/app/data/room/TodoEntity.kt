package kf.todo.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val isDone: Boolean
)
