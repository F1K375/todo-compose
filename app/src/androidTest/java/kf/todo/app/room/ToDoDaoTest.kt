package kf.todo.app.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kf.todo.app.data.room.RoomDb
import kf.todo.app.data.room.ToDoDao
import kf.todo.app.data.room.ToDoEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.util.Date
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
@SmallTest
class ToDoDaoTest {

    private lateinit var toDoDao: ToDoDao
    private lateinit var db: RoomDb

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RoomDb::class.java).build()
        toDoDao = db.todoDao
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addToDoTest()  = runBlocking{
        val newToDo = RoomTestUtil.dummyTodo()
        val createdId = toDoDao.addToDo(newToDo)
        val createdToDo = toDoDao.getToDoById(createdId.first().toInt()).first()

        Assert.assertEquals(newToDo.title, createdToDo.title)
        Assert.assertEquals(newToDo.description, createdToDo.description)
    }

    @Test
    @Throws(Exception::class)
    fun updateToDoTest() = runBlocking {
        val newToDo = RoomTestUtil.dummyTodo()
        val createdId = toDoDao.addToDo(newToDo)
        val createdToDo = toDoDao.getToDoById(createdId.first().toInt()).first()

        val newTitle = "title Updated"
        val newDescription = "description Updated"
        toDoDao.updateToDo(createdToDo.copy(
            title = newTitle,
            description = newDescription
        ))
        val updatedToDo = toDoDao.getToDoById(createdId.first().toInt()).first()

        Assert.assertEquals(updatedToDo.title, newTitle)
        Assert.assertEquals(updatedToDo.description, newDescription)


    }

    @Test
    @Throws(Exception::class)
    fun getAllToDosTest()  = runBlocking{
        val jobGetTodos = async { toDoDao.getToDos().first() }
        val newToDos = RoomTestUtil.dummyTodos(3)

        toDoDao.addToDo(*newToDos)
        val todos = jobGetTodos .await()

        Assert.assertEquals(newToDos.size, todos.size)
        Assert.assertEquals(newToDos[0].title, todos[0].title)
        Assert.assertEquals(newToDos[newToDos.size-1].title, todos[todos.size-1].title)
    }

    @Test
    @Throws(Exception::class)
    fun removeTodoTest() = runBlocking {
        val newToDos = RoomTestUtil.dummyTodos(3)

        val jobGetTodos = async { toDoDao.getToDos().first() }
        toDoDao.addToDo(*newToDos)
        val todos = jobGetTodos.await()

        val itemTobeRemoved = todos[0]
        val jobDelete = async { toDoDao.getToDos().first() }
        toDoDao.removeTodo(itemTobeRemoved.id)
        val afterDelete = jobDelete.await()

        Assert.assertTrue(!afterDelete.contains(itemTobeRemoved))
        Assert.assertTrue(todos.size != afterDelete.size)
    }
}
