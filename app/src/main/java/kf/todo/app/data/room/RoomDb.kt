package kf.todo.app.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [ToDoEntity::class])
abstract class RoomDb: RoomDatabase() {
    abstract val todoDao: ToDoDao
}
