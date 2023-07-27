package com.wildan.todolist.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.wildan.todolist.R
import com.wildan.todolist.data.model.Task
import com.wildan.todolist.ui.theme.TodoListTheme
import com.wildan.todolist.ui.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        setContent {
            TodoListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //TaskList(viewModel = viewModel)
                    TaskList(appViewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun TaskList(appViewModel : AppViewModel){
    val tasklist = appViewModel.getTasks()
    val list = tasklist.observeAsState().value
    if (list != null) {
        if(list.isNotEmpty()) {
            LazyColumn {
                items(list) {
                    Card {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = it.title)
                            Text(text = it.description)
                            Text(text = it.dueDate)
                        }
                        Row(modifier = Modifier.padding(16.dp)) {
                            check(it.isCompleted)
                        }
                    }
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = stringResource(R.string.empty_list),
                    textAlign = TextAlign.Center,
                    fontSize = 23.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(appViewModel: AppViewModel) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var date by remember {
        mutableStateOf("")
    }
    val calendarState = rememberUseCaseState(false, true)
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date{
            date = it.toString()
        }
    )
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(value = title,
                onValueChange = {title = it},
                label = { Text(text = "Title")})
        }
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            OutlinedTextField(value = description,
                onValueChange = {description = it},
                label = { Text(
                text = "Description"
            )})
            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                Button(onClick = {
                    calendarState.show()           
                }) {
                    Text(text = "Select Date")
                }
                Text(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                    text = date)
            }
        }
        Button(onClick = {
            val task = Task(title = title, description = description, dueDate = date, isCompleted = false)
            appViewModel.insertTask(task)
        }, modifier = Modifier.fillMaxWidth())
        {
            Text(text = "Add Task")
        }
    }
}
