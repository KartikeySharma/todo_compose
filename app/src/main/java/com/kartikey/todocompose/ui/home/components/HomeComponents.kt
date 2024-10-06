package com.kartikey.todocompose.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kartikey.todocompose.data.Todo

@Composable
fun TodoItem(
    todo: Todo,
    onChecked: (Boolean) -> Unit,
    onDelete: (Todo) -> Unit,
    onNavigation: (Todo) -> Unit,
    ) {
    Card(
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(16.dp).clickable {
            onNavigation(todo)
        }
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // pass todo to reassign variables
            Checkbox(checked = todo.isComplete, onCheckedChange = {onChecked(it)})
            Column(modifier = Modifier.weight(1f)) {
                Text(text = todo.todo, style = androidx.compose.material.MaterialTheme.typography.subtitle2)
                Spacer(modifier = Modifier.size(16.dp))
                // different view
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = todo.time, style = androidx.compose.material.MaterialTheme.typography.body2)
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            IconButton(onClick = { onDelete(todo) }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }
    }
}