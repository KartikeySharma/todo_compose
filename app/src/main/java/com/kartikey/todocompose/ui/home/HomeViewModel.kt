package com.kartikey.todocompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kartikey.todocompose.Graph
import com.kartikey.todocompose.data.Todo
import com.kartikey.todocompose.data.TodoDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeViewModel(private val todoDataSource: TodoDataSource = Graph.todoRepository) : ViewModel() {
    // use mutable state
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState>
        get() = _state

    // not using a mutable stateflow as selectAll returns that
    val todoList = todoDataSource.selectAll
    val selected = MutableStateFlow(_state.value.selected)

    init {
        viewModelScope.launch {
            // just create, not return value
            combine(
                todoList,
                selected
            ) { todoList: List<Todo>, selected: Boolean ->
                HomeViewState(todoList, selected)
            }. collect{
                // get values here
                _state.value = it
            }
        }
    }

    fun updateTodo(selected: Boolean, id: Long) = viewModelScope.launch {
        todoDataSource.updateTodo(selected, id)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch {
        todoDataSource.deleteTodo(todo)
    }

}

data class HomeViewState(
    val todoList: List<Todo> = emptyList(),
    val selected: Boolean = false
)