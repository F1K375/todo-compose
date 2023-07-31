package kf.todo.app.repository

import kf.todo.app.RepoTestUtil
import kf.todo.app.data.ResultData
import kf.todo.app.data.repository.TodoRepositoryImpl
import kf.todo.app.data.room.ToDoDao
import kf.todo.app.data.room.ToDoEntity
import kf.todo.app.domain.CreateToDoDomain
import kf.todo.app.domain.asEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime


@RunWith(MockitoJUnitRunner::class)
class ToDoRepositoryTest {

    @Mock
    private lateinit var mockToDoDao: ToDoDao
    private lateinit var repositoryTest: TodoRepositoryImpl

    @Before
    fun setUp(){
        repositoryTest = TodoRepositoryImpl(mockToDoDao)
    }

    @Test
    fun `create todo`() = runBlocking {
        val createToDoDomain = RepoTestUtil.dummyCreateToDoDomain()
        `when`(mockToDoDao.addToDo(createToDoDomain.asEntity)).thenReturn(listOf(1))

        val result = repositoryTest.createToDo(createToDoDomain)
        Assert.assertTrue(result is ResultData.Success)
        result as ResultData.Success
        Assert.assertTrue(result.data == 1)
    }

    @Test
    fun updateToDo() = runBlocking {
        val sampleTodo = RepoTestUtil.dummyToDoDomain()
        `when`(mockToDoDao.updateToDo(sampleTodo.asEntity)).thenReturn(Unit)

        val result = repositoryTest.updateToDo(sampleTodo)
        Assert.assertTrue(result is ResultData.Success)
        result as ResultData.Success
        Assert.assertTrue(result.data)
    }

    @Test
    fun removeTodo() = runBlocking {
        `when`(mockToDoDao.removeTodo(1)).thenReturn(Unit)

        val result = repositoryTest.removeTodo(1)
        Assert.assertTrue(result is ResultData.Success)
        result as ResultData.Success
        Assert.assertTrue(result.data)
    }

    @Test
    fun getToDoById() = runBlocking {
        val sampleToDoEntity = RepoTestUtil.dummyToDoEntity()
        `when`(mockToDoDao.getToDoById(sampleToDoEntity.id)).thenReturn(flow { emit(sampleToDoEntity) })

        val result = repositoryTest.getToDoById(sampleToDoEntity.id).first()
        Assert.assertTrue(result is ResultData.Success)
        result as ResultData.Success

        val todoDetail = result.data
        Assert.assertTrue(todoDetail.id == sampleToDoEntity.id)
        Assert.assertTrue(todoDetail.title == sampleToDoEntity.title)
        Assert.assertTrue(todoDetail.description == sampleToDoEntity.description)
        Assert.assertTrue(todoDetail.isDone == sampleToDoEntity.isDone)
        Assert.assertTrue(todoDetail.dueDate == sampleToDoEntity.dueDate)

    }

    @Test
    fun getAllToDo() = runBlocking {
        val sampleListToDoEntity = RepoTestUtil.dummyListToDoEntity()
        `when`(mockToDoDao.getToDos()).thenReturn(flow { emit(sampleListToDoEntity) })

        val result = repositoryTest.getAllToDo().first()
        Assert.assertTrue(result is ResultData.Success)
        result as ResultData.Success

        val listTodo = result.data
        Assert.assertTrue(listTodo.size == sampleListToDoEntity.size)

        Assert.assertEquals(listTodo.first().id, sampleListToDoEntity.first().id)
        Assert.assertEquals(listTodo.first().title, sampleListToDoEntity.first().title)

        Assert.assertEquals(listTodo.last().id, sampleListToDoEntity.last().id)
        Assert.assertEquals(listTodo.last().title, sampleListToDoEntity.last().title)
    }
}
