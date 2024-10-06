package com.kartikey.todocompose.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kartikey.todocompose.data.Todo
import com.kartikey.todocompose.ui.home.components.TodoItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    // if this is null, then return for using floating action button
    onNavigate: (Todo?) -> Unit
) {
    val viewModel = viewModel(HomeViewModel::class.java)
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.primary,
                elevation = 10.dp,
                contentColor = Color.White,
                title = { Text("Todo List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigate(null) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn() {
            items(state.todoList) { todo ->
                TodoItem(
                    todo = todo,
                    onChecked = { viewModel.updateTodo(it, todo.id) },
                    onDelete = { viewModel.deleteTodo(it) },
                    onNavigation = { onNavigate(it) }
                )
            }
        }
    }
}