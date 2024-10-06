package com.kartikey.todocompose.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kartikey.todocompose.data.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun selectAll(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("UPDATE todo SET isComplete = :complete WHERE id = :id")
    suspend fun updateTodo(complete: Boolean, id: Long)

}