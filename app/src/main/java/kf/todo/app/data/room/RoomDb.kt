package kf.todo.app.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(version = 1, entities = [ToDoEntity::class], exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class)
abstract class RoomDb: RoomDatabase() {
    abstract val todoDao: ToDoDao
}
