package com.kartikey.todocompose.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kartikey.todocompose.Graph
import com.kartikey.todocompose.data.Todo
import com.kartikey.todocompose.data.TodoDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DetailViewModel(
    private val todoDataSource: TodoDataSource = Graph.todoRepository,
    private val id: Long
): ViewModel() {
    private val todoText = MutableStateFlow("")
    private val todoTime = MutableStateFlow("")
    private val selectId = MutableStateFlow(-1L)

    private val _state = MutableStateFlow(DetailViewState())
    val state : StateFlow<DetailViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(todoText, todoTime, selectId) { text, time, id ->
                DetailViewState(text, time, id)
            }.collect {
                _state.value = it
            }
        }
    }

    // access the data, collect to get id and check if the value is present inside
    init {
        viewModelScope.launch {
            todoDataSource.selectAll.collect { todo ->
                todo.find {
                    it.id == selectId.value
                }.also {
                    // update the state with actual values
                    selectId.value = it?.id ?: -1
                    if (selectId.value != -1L) {
                        todoText.value = it?.todo ?: ""
                        todoTime.value = it?.time ?: ""
                    }
                }
            }
        }

    }

    fun onTextChange(newText: String) {
        todoText.value = newText
    }

    fun onTimeChange(newText: String) {
        todoTime.value = newText
    }

    fun insert(todo: Todo) = viewModelScope.launch {
        todoDataSource.insertTodo(todo)
    }
}

data class DetailViewState(
    val todo: String = "",
    val time: String = "",
    val selectId: Long = -1L,
)

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val id: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(id = id) as T
        } else {
            throw IllegalArgumentException("unKnown view model class")
        }
    }
}