package kf.todo.app.data

import java.lang.Exception

sealed class ResultData<out T: Any> {

    data class Success<out T: Any>(val data: T): ResultData<T>()
    data class Failed(val exception: Exception, val message: String): ResultData<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success<*> -> """
                success, the data : $data
            """.trimIndent()
            is Failed -> """
                Error, there is something wrong
                may coused by ${exception.localizedMessage}
            """.trimIndent()
        }
    }

}
