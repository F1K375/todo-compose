package kf.todo.app.data.room

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateConverter{

    @TypeConverter
    fun fromLocalDate(date: LocalDateTime?): Long? {
        return date?.let { date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() }
    }

    fun toLocalDate(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault()) }
    }
}
